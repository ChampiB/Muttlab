package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.util.Stack;

public class CommandPointWiseMul extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandPointWiseMul(String command) { setCommand(command); }

    /**
     * Multiply point-wise the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        if (elements.size() < 2) {
            String errorMessage = CoreKeys.NOT_ENOUGH_ELEMENT_IN_THE_QUEUE_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
            return false;
        }
        try {
            Element e1 = elements.pop();
            Element e2 = elements.pop();
            Element res = e2.pointWiseMul(e1);
            elements.push(res);
            ui.println(res.asString());
        } catch (Exception e) {
            String errorMessage = CoreKeys.INVALID_OPERATION_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
        }
        return false;
    }
}
