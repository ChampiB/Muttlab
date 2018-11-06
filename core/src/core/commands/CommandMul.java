package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.ConverterHelper;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public class CommandMul extends Command {

    private Matrix result = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandMul(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.MUL_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.MUL_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.MUL_COMMAND_NAME.toString();
    }

    /**
     * Execute the multiplication between the two last elements of the stack. Call multiplication method.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     * @throws Exception if an error occurred.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        String[] args = getCommand().split(" ");
        if (args.length > 1) {
            // Check the number of element in the stack.
            CommandHelper.checkAtLeastInTheStack(elements, 1);
            // Multiplication with a scalar.
            result = elements.peek(0).copy();
            result.mul(ConverterHelper.toFloat(args[1]));
            DisplayHelper.println(out, result.asString());
        } else {
            // Check the number of element in the stack.
            CommandHelper.checkAtLeastInTheStack(elements, 2);
            // Multiplication with a matrix.
            Matrix e1 = elements.peek(0);
            result = elements.peek(1).copy();
            result.mul(e1);
            DisplayHelper.println(out, result.asString());
        }
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) {
        if (getCommand().split(" ").length == 1)
            elements.pop();
        elements.pop();
        elements.push(result);
    }
}
