package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.HomeController;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class CommandScript extends Command {

    private String fileName = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandScript(String command) {
        setCommand(command);
    }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.SCRIPT_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.SCRIPT_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.SCRIPT_COMMAND_NAME.toString();
    }

    /**
     * Execute the script contains in the file passed as (command) parameter.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        fileName = args[1];
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) throws Exception {
        Scanner input = new Scanner(new FileInputStream(new File(fileName)));
        while (input.hasNextLine()) {
            HomeController.get().executeCommand(input.nextLine());
        }
    }
}
