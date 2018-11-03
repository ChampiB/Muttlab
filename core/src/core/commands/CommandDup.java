package core.commands;

import muttlab.helpers.CommandHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import java.util.Stack;

public class CommandDup extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandDup(String command) { setCommand(command); }

    /**
     * Duplicate the last matrix of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check the number of element in the stack.
        CommandHelper.checkAtLeastInTheStack(elements, 1);
        // Duplicate the last matrix of the stack.
        elements.push(elements.peek().copy());
        return false;
    }
}
