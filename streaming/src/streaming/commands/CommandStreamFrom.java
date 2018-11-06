package streaming.commands;

import muttlab.commands.Command;
import muttlab.languages.MuttLabStrings;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.DenseMatrix;
import muttlab.math.Matrix;
import muttlab.ui.HomeView;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class CommandStreamFrom extends Command {

    private String fileName;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandStreamFrom(String command) {
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
        return MuttLabStrings.STREAM_FROM_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.STREAM_FROM_COMMAND_KEY.toString());
    }

    /**
     * Load the matrix from the string.
     * @param str: the string representation of the matrix.
     * @return the matrix or null if not valid.
     */
    private Matrix loadMatrix(String str) {
        try {
            return new DenseMatrix().from(str);
        } catch (Exception e) {
            Logging.log(LoggingLevel.WARNING, "Invalid matrix will be ignored.");
            return null;
        }
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.STREAM_FROM_COMMAND_NAME.toString();
    }

    /**
     * Start to streaming data from file.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of elements.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Load the matrix for the file and change the current stream.
        Stream<Matrix> s = Files
            .lines(Paths.get(fileName))
            .map(this::loadMatrix)
            .filter(Objects::nonNull);
        CurrentStream.getInstance().setCurrentStream(s);
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    @Override
    protected void flush(ObservableStackWrapper<Matrix> elements) {
    }
}
