package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.MuttLab;
import muttlab.helpers.CommandHelper;
import muttlab.math.Element;
import muttlab.parsing.Parser;
import muttlab.parsing.SimpleParser;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;

import java.io.File;
import java.io.FileInputStream;
import java.util.Stack;

public class CommandScript extends Command {
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
        String commandName = CoreDictionary.getInstance().getValue(CoreKeys.SCRIPT.toString());
        return CoreDictionary.getInstance()
                .getValue(CoreKeys.SCRIPT_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Execute the script contains in the file passed as (command) parameter.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Execute the script command.
        FileInputStream fileReader = new FileInputStream(new File(args[1]));
        Parser parser = new SimpleParser(fileReader);
        return MuttLab.executeUserCommands(parser, ui, elements, true);
    }
}
