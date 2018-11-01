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
     * Check the rest of the command to see whether we really quit the editor.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        if (getCommand().split(" ").length > 1) {
            String errorMessage = CoreKeys.QUIT_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
            return false;
        }
        return true; // signal that we want to quit
    }
}
