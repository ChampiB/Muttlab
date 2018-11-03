package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.FileHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.io.FileWriter;
import java.util.Stack;

public class CommandSave extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSave(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = CoreDictionary.getInstance().getValue(CoreKeys.SAVE.toString());
        return CoreDictionary.getInstance()
                .getValue(CoreKeys.SAVE_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Save the last matrix of the list in a file.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Save the last matrix of the stack.
        final FileWriter fileWriter = FileHelper.openWriter(args[1]);
        fileWriter.write(elements.peek().asString());
        fileWriter.flush();
        return false;
    }
}
