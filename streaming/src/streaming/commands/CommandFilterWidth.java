package streaming.commands;

import muttlab.helpers.CommandHelper;
import muttlab.helpers.ConverterHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.Stack;

public class CommandFilterWidth extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandFilterWidth(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = StreamingDictionary.getInstance().getValue(StreamingKeys.FILTER_WIDTH.toString());
        return StreamingDictionary.getInstance()
                .getValue(StreamingKeys.FILTER_WIDTH_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Filter all the matrices which have not the width specified in the commands' parameters.
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
        // Keep only matrices where the width asked.
        final int width = ConverterHelper.toInt(args[1]);
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            s = s.filter(m -> m.getWidth() == width);
            CurrentStream.getInstance().setCurrentStream(s);
        });
        return false;
    }
}
