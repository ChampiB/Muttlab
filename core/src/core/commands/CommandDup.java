package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public class CommandDup extends Command {

    private Matrix result = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandDup(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.DUP_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.DUP_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.DUP_COMMAND_NAME.toString();
    }

    /**
     * Duplicate the last matrix of the list.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check the number of element in the stack.
        CommandHelper.checkAtLeastInTheStack(elements, 1);
        // Duplicate the last matrix of the stack.
        result = elements.peek().copy();
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) {
        elements.push(result);
    }
}
