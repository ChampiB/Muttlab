package streaming.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.FileHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.Matrix;
import muttlab.ui.HomeView;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class CommandSaveFile extends Command {

    private String fileName;
    private List<Matrix> matrices = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSaveFile(String command) {
        setCommand(command);
        // Get the file name.
        String[] args = getCommand().split(" ");
        fileName = (args.length < 2) ? HomeView.get().pickFile() : args[1];
    }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.SAVE_FILE_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.SAVE_FILE_COMMAND_KEY.toString());
    }

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
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.SAVE_FILE_COMMAND_NAME.toString();
    }

    /**
     * Save all the matrices of the stream into the file.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of elements.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check if the current stream is present.
        CurrentStream.checkIsPresent();
        // Compute the stream.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> matrices = s.collect(Collectors.toList()));
        CurrentStream.getInstance().setCurrentStream(null);
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    @Override
    protected void flush(ObservableStackWrapper<Matrix> elements) throws Exception {
        // Open the output file.
        final FileWriter fileWriter = FileHelper.openWriter(fileName);
        // Save all the matrix of the stream in the stack.
        for (Matrix m : matrices) {
            saveMatrixInFile(fileWriter, m);
        }
    }
}
