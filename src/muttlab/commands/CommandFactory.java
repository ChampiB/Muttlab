package muttlab.commands;

import muttlab.plugins.Plugin;
import muttlab.plugins.PluginsManager;

import java.util.Iterator;

public class CommandFactory {
    /**
     * Create the command corresponding to the command line.
     * @param line: The command line.
     * @return the command.
     */
    public static Command create(String line) {
        String[] cmdAndArgs = line.split(" ");
        if (cmdAndArgs.length < 1)
            return new UnknownCommand();
        String cmd = cmdAndArgs[0];
        Iterator<Plugin> availablePlugins = PluginsManager.get().getAvailablePlugins();
        while (availablePlugins.hasNext()) {
            Plugin plugin = availablePlugins.next();
            try {
                Command command = plugin.getCommand(cmd, line);
                if (command != null) return command;
            } catch (Exception e) {
                // Do nothing.
            }
        }
        return new UnknownCommand();
    }
}