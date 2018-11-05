package muttlab.commands;

import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public class UnknownCommand extends Command {
    /**
     * Execute the command.
     * @param output: The output of the command.
     * @param elements: The stack of element.
     * @throws Exception if an error occurred.
     */
    protected void execute(OutputStream output, ObservableStackWrapper<Matrix> elements) throws Exception {
        DisplayHelper.println(output, MuttLabStrings.UNKNOWN_COMMAND_MESSAGE.toString());
    }

    /**
     * Getter.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return "";
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.UNKNOWN_COMMAND_NAME.toString();
    }
}