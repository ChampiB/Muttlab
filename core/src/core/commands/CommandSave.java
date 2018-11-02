package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.DisplayHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class CommandSave extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSave(String command) { setCommand(command); }

    /**
     * Save the last matrix of the list in a file.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Check that there is at least one parameter.
        String[] parameters = getCommand().split(" ");
        if (parameters.length < 1) {
            return DisplayHelper.printErrAndReturn(
                ui, CoreKeys.FILE_NAME_NOT_FOUND_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        }
        // Save the last matrix of the stack.
        FileWriter fileWriter = null;
        try {
            File file = new File(parameters[1]);
            file.createNewFile();
            fileWriter = new FileWriter(file);
            fileWriter.write(elements.peek().asString());
            fileWriter.flush();
        } catch (IOException e) {
            DisplayHelper.printErrAndReturn(
                ui, CoreKeys.CANT_READ_FROM_FILE_ERROR_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
        } finally {
            try { fileWriter.close(); } catch (Exception e) {}
        }
        return false;
    }
}
