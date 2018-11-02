package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
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
     * Check the rest of the command to see whether we really quit the editor.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Check that there is no command's parameters.
        if (getCommand().split(" ").length > 1) {
            return DisplayHelper.printErrAndReturn(
                ui, CoreKeys.QUIT_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        }
        return true; // Signal that we want to quit.
    }
}
