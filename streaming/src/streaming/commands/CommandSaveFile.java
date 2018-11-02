package streaming.commands;

import muttlab.helpers.DisplayHelper;
import muttlab.helpers.FileHelper;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.Element;
import muttlab.math.elements.MatrixWrapper;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CommandSaveFile extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSaveFile(String command) { setCommand(command); }

    /**
     * Save the matrix into the file.
     * @param fileWriter: The file writer used to save the matrix.
     * @param m: The matrix.
     */
    private void saveMatrixInFile(FileWriter fileWriter, Matrix m) {
        try {
            fileWriter.write(m.asString() + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            Logging.log(LoggingLevel.WARNING, "Can't save matrix in file.");
        }
    }

    /**
     * Add the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Check that there is at least one parameter.
        String[] parameters = getCommand().split(" ");
        if (parameters.length < 2) {
            return DisplayHelper.printErrAndReturn(
                ui, StreamingKeys.NOT_ENOUGH_PARAMETERS.toString(), StreamingDictionary.getInstance(), false
            );
        }
        try {
            // Open the output file.
            final FileWriter fileWriter = FileHelper.openWriter(parameters[1]);
            // Save all the matrix of the stream in the stack.
            CurrentStream.getInstance().getCurrentStream().ifPresent(s ->
                s.forEach(m -> saveMatrixInFile(fileWriter, m))
            );
            CurrentStream.getInstance().setCurrentStream(null);
        } catch (IOException e) {
            DisplayHelper.printErrAndReturn(
                ui, StreamingKeys.FAIL_TO_WRITE_IN_FILE.toString(), StreamingDictionary.getInstance(), false
            );
        }
        return false;
    }
}
