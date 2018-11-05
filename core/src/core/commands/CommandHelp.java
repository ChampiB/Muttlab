package core.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.plugins.PluginsManager;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
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
        return MuttLabStrings.HELP_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.HELP_COMMAND_KEY.toString());
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
        return MuttLabStrings.UNKNOWN_COMMAND_MESSAGE.toString();
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.HELP_COMMAND_NAME.toString();
    }

    /**
     * Print out some help information.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of matrix.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check the number of parameters.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 1, 2);
        if (args.length == 1) { // 'help' was entered.
            DisplayHelper.println(out, MuttLabStrings.GLOBAL_HELP_MESSAGE.toString());
            DisplayHelper.println(out, getAvailableCommandsName());
        } else { // 'help <command_name>' was entered.
            DisplayHelper.println(out, getHelpOfCommand(args[1]));
        }
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    protected void flush(ObservableStackWrapper<Matrix> elements) {
    }
}
