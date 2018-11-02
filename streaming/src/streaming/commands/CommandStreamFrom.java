package streaming.commands;

import muttlab.helpers.DisplayHelper;
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

import java.io.IOException;
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
        // Load the matrix for the file and change the current stream.
        try {
            Stream s = Files
                .lines(Paths.get(parameters[1]))
                .map(this::loadMatrix)
                .filter(Objects::nonNull);
            CurrentStream.getInstance().setCurrentStream(s);
        } catch (IOException e) {
            return DisplayHelper.printErrAndReturn(
                ui, StreamingKeys.CANT_STREAM_THE_FILE.toString(), StreamingDictionary.getInstance(), false
            );
        }
        return false;
    }
}
