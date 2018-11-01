package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.math.Element;
import muttlab.math.elements.MatrixWrapper;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.util.List;
import java.util.Stack;

public class CommandNewMatrix extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandNewMatrix(String command) { setCommand(command); }

    /**
     * Parse the text representation of the matrix and add it in the stack.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        try {
            elements.push(new MatrixWrapper().from(getCommand()));
        } catch (Exception e) {
            String errorMessage = CoreKeys.NEW_MATRIX_ERROR_MESSAGE.toString();
            ui.printlnErr(CoreDictionary.getInstance().getValue(errorMessage));
        }
        return false;
    }
}
