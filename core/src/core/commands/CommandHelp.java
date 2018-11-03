package core.commands;

import core.languages.CoreDictionary;
import core.languages.CoreKeys;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabDictionary;
import muttlab.languages.MuttLabKeys;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.plugins.PluginsManager;
import muttlab.ui.UserInterface;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CommandHelp extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandHelp(String command) {
        setCommand(command);
    }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        String commandName = CoreDictionary.getInstance().getValue(CoreKeys.HELP.toString());
        return CoreDictionary.getInstance()
                .getValue(CoreKeys.HELP_HELP_MESSAGE.toString())
                .replaceAll("COMMAND_NAME", commandName);
    }

    /**
     * Getter method.
     * @return the list of available commands' name.
     */
    private String getAvailableCommandsName() {
        Iterator<muttlab.plugins.Plugin> plugins = PluginsManager.get().getAvailablePlugins();
        return StreamSupport
            .stream(Spliterators.spliteratorUnknownSize(plugins, Spliterator.ORDERED), false)
            .flatMap(muttlab.plugins.Plugin::getAvailableCommandsName)
            .collect(Collectors.joining(", "));
    }

    /**
     * Get the help message to display to the final user.
     * @param commandName: the command name.
     * @return the help message.
     */
    private String getHelpOfCommand(String commandName) {
        Iterator<muttlab.plugins.Plugin> plugins = PluginsManager.get().getAvailablePlugins();
        while (plugins.hasNext()) {
            try {
                return plugins.next().getCommand(commandName, commandName).getHelpMessage();
            } catch (Exception e) {
                // Do nothing.
            }
        }
        return MuttLabDictionary.getInstance().getValue(MuttLabKeys.UNKNOWN_COMMAND_MESSAGE.toString());
    }

    /**
     * Print out some help information.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check the number of parameters.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 1, 2);

        if (args.length == 1) { // 'help' was entered.
            DisplayHelper.println(
                ui, CoreKeys.GLOBAL_HELP_MESSAGE.toString(), CoreDictionary.getInstance(), false
            );
            ui.println(getAvailableCommandsName());
        } else { // 'help <command_name>' was entered.
            ui.println(getHelpOfCommand(args[1]));
        }
        return false;
    }
}
