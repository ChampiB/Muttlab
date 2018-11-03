package core.commands;

import muttlab.helpers.CommandHelper;
import muttlab.math.Element;
import muttlab.math.elements.ScalarWrapper;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import java.util.Stack;

public class CommandMul extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandMul(String command) { setCommand(command); }

    /**
     * Execute the multiplication between the two last elements of the stack.
     * Call multiplication method.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Add scalar in the stack if needed.
        String[] args = getCommand().split(" ");
        if (args.length > 1) {
            elements.push(new ScalarWrapper().from(args[1]));
        }
        // Check the number of element in the stack.
        CommandHelper.checkAtLeastInTheStack(elements, 2);
        // Compute the multiplication.
        Element e1 = elements.pop();
        Element e2 = elements.pop();
        Element res = e2.mul(e1);
        elements.push(res);
        ui.println(res.asString());
        return false;
    }
}
