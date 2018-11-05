package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.ConverterHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public class CommandMul extends Command {
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
     * @throws Exception
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        String[] args = getCommand().split(" ");
        // Check the number of element in the stack.
        CommandHelper.checkAtLeastInTheStack(elements, 2);
        // Compute the multiplication.
        Matrix e1 = elements.pop();
        if (args.length > 1) {
            e1.mul(ConverterHelper.toFloat(args[1]));
            elements.push(e1);
            String message = e1.asString() + "\n";
            out.write(message.getBytes());
        } else {
            Matrix e2 = elements.pop();
            e2.mul(e1);
            elements.push(e2);
            String message = e2.asString() + "\n";
            out.write(message.getBytes());
        }
    }
}
