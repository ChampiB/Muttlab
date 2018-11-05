package core;

import muttlab.commands.Command;
import muttlab.commands.UnknownCommand;
import muttlab.languages.MuttLabStrings;

import java.util.stream.Stream;

public class Plugin extends muttlab.plugins.Plugin {

    /**
     * Getter method.
     * @return the list of the available commands' name
     */
    @Override
    public Stream<String> getAvailableCommandsName() {
        return CommandFactory.getAvailableCommandsName();
    }

    /**
     * Return command corresponding to name.
     * @param name : The command's name.
     * @param command : The command line.
     * @return The command corresponding to the command's name.
     */
    @Override
    public Command getCommand(String name, String command)
    {
        try {
            MuttLabStrings commandName = MuttLabStrings.fromString(name);
            return CommandFactory.create(commandName, command);
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }

    /**
     * Getter method.
     * @return the plugin's name
     */
    @Override
    public String getName() {
        return "core";
    }
}
