package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.util.List;
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
        if (elements.size() < 2) {
            String errorMessage = CoreKeys.NOT_ENOUGH_ELEMENT_IN_THE_QUEUE_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
            return false;
        }
        try {
            Element tmp = elements.get(elements.size() - 2).add(elements.get(elements.size() - 1));
            elements.remove(elements.size() - 1);
            elements.remove(elements.size() - 1);
            elements.add(tmp);
            ui.println(tmp.asString());
        } catch (Exception e) {
            String errorMessage = CoreKeys.INVALID_OPERATION_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
        }
        return false;
    }
}
