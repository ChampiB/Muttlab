package streaming.commands;

import muttlab.helpers.CommandHelper;
import muttlab.helpers.FileHelper;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.Element;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

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
     * Save all the matrices of the stream into the file.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Open the output file.
        final FileWriter fileWriter = FileHelper.openWriter(args[1]);
        // Save all the matrix of the stream in the stack.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s ->
            s.forEach(m -> saveMatrixInFile(fileWriter, m))
        );
        CurrentStream.getInstance().setCurrentStream(null);
        return false;
    }
}
