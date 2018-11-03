package streaming.commands;

import muttlab.helpers.CommandHelper;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.Element;
import muttlab.math.matrices.DenseMatrix;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Stream;

public class CommandStreamFrom extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandStreamFrom(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = StreamingDictionary.getInstance().getValue(StreamingKeys.STREAM_FROM.toString());
        return StreamingDictionary.getInstance()
                .getValue(StreamingKeys.STREAM_FROM_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
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
     * Start to streaming data from file.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Load the matrix for the file and change the current stream.
        Stream<Matrix> s = Files
            .lines(Paths.get(args[1]))
            .map(this::loadMatrix)
            .filter(Objects::nonNull);
        CurrentStream.getInstance().setCurrentStream(s);
        return false;
    }
}
