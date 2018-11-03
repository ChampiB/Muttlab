package core.commands;

import muttlab.math.Element;
import muttlab.math.elements.MatrixWrapper;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

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
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Add a matrix in the stack.
        elements.push(new MatrixWrapper().from(getCommand()));
        return false;
    }
}
