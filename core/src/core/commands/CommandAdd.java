package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.util.Stack;

public class CommandAdd extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandAdd(String command) { setCommand(command); }

    /**
     * Add the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Check the number of element in the stack.
        if (elements.size() < 2) {
            return DisplayHelper.printErrAndReturn(
                ui, CoreKeys.NOT_ENOUGH_ELEMENT_IN_QUEUE.toString(), CoreDictionary.getInstance(), false
            );
        }
        // Compute the addition between the two last elements of the stack.
        try {
            Element e1 = elements.pop();
            Element e2 = elements.pop();
            Element res = e2.add(e1);
            elements.push(res);
            ui.println(res.asString());
        } catch (Exception e) {
            return DisplayHelper.printErrAndReturn(
                ui, CoreKeys.INVALID_OPERATION_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        }
        return false;
    }
}
