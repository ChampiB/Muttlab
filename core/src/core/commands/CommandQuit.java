package core.commands;

import muttlab.commands.Command;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public class CommandQuit extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandQuit(String command) {
        setCommand(command);
    }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.QUIT_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.QUIT_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.QUIT_COMMAND_NAME.toString();
    }

    /**
     * Check the rest of the command to see whether we really quit the editor.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check that there is no parameter.
        if (getCommand().split(" ").length > 1) {
            throw new Exception(MuttLabStrings.QUIT_ERROR_MESSAGE.toString());
        }
        // Signal that we want to quit.
        System.exit(0);
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) {
    }
}
