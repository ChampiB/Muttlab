package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.FileHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.HomeView;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.FileWriter;
import java.io.OutputStream;

public class CommandSave extends Command {

    private String fileName;
    private String result = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSave(String command) {
        setCommand(command);
        // Get the file name.
        String[] args = getCommand().split(" ");
        fileName = (args.length < 2) ? HomeView.get().pickFile() : args[1];
    }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.SAVE_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.SAVE_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.SAVE_COMMAND_NAME.toString();
    }

    /**
     * Save the last matrix of the list in a file.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        result = elements.peek().asString();
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) throws Exception {
        // Save the last matrix of the stack.
        final FileWriter fileWriter = FileHelper.openWriter(fileName);
        fileWriter.write(result);
        fileWriter.flush();
    }
}
