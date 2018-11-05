package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;


public class CommandAdd extends Command {

    private Matrix result = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandAdd(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.ADD_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.ADD_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.ADD_COMMAND_NAME.toString();
    }

    /**
     * Add the two last elements of the list.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check the number of element in the stack.
        CommandHelper.checkAtLeastInTheStack(elements, 2);
        // Compute the addition between the two last elements of the stack.
        Matrix e1 = elements.peek(-1);
        Matrix e2 = elements.peek(-2).copy();
        e2.add(e1);
        result = e2;
        DisplayHelper.println(out, e2.asString());
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) {
        elements.pop();
        elements.pop();
        elements.push(result);
    }
}
