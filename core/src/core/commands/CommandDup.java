package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabKeys;
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
        // Check the number of element in the stack.
        if (elements.size() == 0) {
            return DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.NOT_ENOUGH_ELEMENT_IN_QUEUE.toString(), CoreDictionary.getInstance(), false
            );
        }
        // Duplicate the last matrix of the stack.
        try {
            elements.push(elements.peek().copy());
        } catch (Exception e) {
            return DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.INVALID_OPERATION_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        }
        return false;
    }
}
