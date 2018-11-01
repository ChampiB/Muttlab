package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
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
     * Multiply the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    private void mul(UserInterface ui, Stack<Element> elements) {
        try {
            Element e1 = elements.pop();
            Element e2 = elements.pop();
            Element res = e2.mul(e1);
            elements.push(res);
            ui.println(res.asString());
        } catch (Exception e) {
            String errorMessage = CoreKeys.INVALID_OPERATION_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
        }
    }

    /**
     * If command == "* N" add a scalar in the list of element.
     * Call multiplication method.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        String[] cmdAndArgs = getCommand().split(" ");
        if (cmdAndArgs.length > 1) {
            try {
                elements.push(new ScalarWrapper().from(cmdAndArgs[1]));
            } catch (Exception e) {
                ui.printlnErr(e.getMessage());
                return false;
            }
        }
        if (elements.size() < 2) {
            String errorMessage = CoreKeys.NOT_ENOUGH_ELEMENT_IN_THE_QUEUE_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
            return false;
        }
        mul(ui, elements);
        return false;
    }
}
