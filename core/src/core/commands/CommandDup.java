package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
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
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        if (elements.size() == 0) {
            String errorMessage = CoreKeys.NOT_ENOUGH_ELEMENT_IN_THE_QUEUE_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
            return false;
        }
        try {
            elements.push(elements.peek().copy());
        } catch (Exception e) {
            String errorMessage = CoreKeys.INVALID_OPERATION_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
        }
        return false;
    }
}
