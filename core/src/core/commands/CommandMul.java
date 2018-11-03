package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabKeys;
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
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Add scalar in the stack if needed.
        String[] parameters = getCommand().split(" ");
        if (parameters.length > 1) {
            try {
                elements.push(new ScalarWrapper().from(parameters[1]));
            } catch (Exception e) {
                return DisplayHelper.printErrAndReturn(
                    ui, MuttLabKeys.NOT_VALID_FLOAT.toString(), CoreDictionary.getInstance(), false
                );
            }
        }
        // Check the number of element in the stack.
        if (elements.size() < 2) {
            return DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.NOT_ENOUGH_ELEMENT_IN_QUEUE.toString(), CoreDictionary.getInstance(), false
            );
        }
        // Compute the multiplication.
        try {
            Element e1 = elements.pop();
            Element e2 = elements.pop();
            Element res = e2.mul(e1);
            elements.push(res);
            ui.println(res.asString());
        } catch (Exception e) {
            return DisplayHelper.printAndReturn(
                ui, MuttLabKeys.INVALID_OPERATION_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        }
        return false;
    }
}
