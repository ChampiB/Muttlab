package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import java.util.Stack;

public class CommandHelp extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandHelp(String command) {
        setCommand(command);
    }

    /**
     * Print out some help information.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        return DisplayHelper.println(
            ui, CoreKeys.HELP_MESSAGE.toString(), CoreDictionary.getInstance(), false
        );
    }
}
