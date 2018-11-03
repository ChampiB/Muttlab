package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import java.util.Stack;

public class CommandQuit extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandQuit(String command) {
        setCommand(command);
    }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = CoreDictionary.getInstance().getValue(CoreKeys.QUIT.toString());
        return CoreDictionary.getInstance()
                .getValue(CoreKeys.QUIT_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Check the rest of the command to see whether we really quit the editor.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check that there is no parameter.
        if (getCommand().split(" ").length > 1) {
            throw new Exception(CoreKeys.QUIT_ERROR_MESSAGE.toString());
        }
        // Signal that we want to quit.
        return true;
    }
}
