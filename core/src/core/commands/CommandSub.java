package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabKeys;
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
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Check that there is at least one parameter.
        if (elements.size() < 2) {
            return DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.NOT_ENOUGH_ELEMENT_IN_QUEUE.toString(), CoreDictionary.getInstance(), false
            );
        }
        // Execute the subtraction between the two last element in the queue.
        try {
            Element e1 = elements.pop();
            Element e2 = elements.pop();
            Element res = e2.sub(e1);
            elements.push(res);
            ui.println(res.asString());
        } catch (Exception e) {
            return DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.INVALID_OPERATION_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        }
        return false;
    }
}
