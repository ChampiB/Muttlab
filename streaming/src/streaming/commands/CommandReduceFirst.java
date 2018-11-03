package streaming.commands;

import muttlab.helpers.CommandHelper;
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

public class CommandReduceFirst extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandReduceFirst(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = StreamingDictionary.getInstance().getValue(StreamingKeys.REDUCE_FIRST.toString());
        return StreamingDictionary.getInstance()
                .getValue(StreamingKeys.REDUCE_FIRST_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Reduce the list of matrices by taking the first.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check if the current stream is present.
        CurrentStream.checkIsPresent();
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Reduce the list of matrices by taking the first.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            List<Matrix> a = new ArrayList<>();
            a.add(s.findFirst().orElse(null));
            CurrentStream.getInstance().setCurrentStream(a.stream().filter(Objects::nonNull));
        });
        return false;
    }
}
