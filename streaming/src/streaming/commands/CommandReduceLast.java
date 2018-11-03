package streaming.commands;

import muttlab.math.Element;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class CommandReduceLast extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandReduceLast(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = StreamingDictionary.getInstance().getValue(StreamingKeys.REDUCE_LAST.toString());
        return StreamingDictionary.getInstance()
                .getValue(StreamingKeys.REDUCE_LAST_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Reduce the list of matrices by taking the last.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check if the current stream is present.
        CurrentStream.checkIsPresent();
        // Reduce the list of matrices by taking the last.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            List<Matrix> a = new ArrayList<>();
            a.add(s.reduce(null, (m1, m2) -> m2));
            CurrentStream.getInstance().setCurrentStream(a.stream().filter(Objects::nonNull));
        });
        return false;
    }
}
