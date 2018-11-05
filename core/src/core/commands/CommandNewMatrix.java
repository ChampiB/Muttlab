package core.commands;

import muttlab.commands.Command;
import muttlab.languages.MuttLabStrings;
import muttlab.math.DenseMatrix;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public class CommandNewMatrix extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandNewMatrix(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.NEW_MATRIX_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.NEW_MATRIX_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.NEW_MATRIX_COMMAND_NAME.toString();
    }

    /**
     * Parse the text representation of the matrix and add it in the stack.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Add a matrix in the stack.
        elements.push(new DenseMatrix().from(getCommand()));
    }
}
