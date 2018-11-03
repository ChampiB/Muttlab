package core.commands;

import muttlab.helpers.CommandHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.util.Stack;

public class CommandSub extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSub(String command) { setCommand(command); }

    /**
     * Subtract the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check the number of element in the stack.
        CommandHelper.checkAtLeastInTheStack(elements, 2);
        // Execute the subtraction between the two last element in the queue.
        Element e1 = elements.pop();
        Element e2 = elements.pop();
        Element res = e2.sub(e1);
        elements.push(res);
        ui.println(res.asString());
        return false;
    }
}
