package muttlab.plugins;

import muttlab.exceptions.UnknownCommand;
import muttlab.languages.Language;

public abstract class Plugin {

    /**
     * Set the language used by the plugin.
     * @param language : The new language.
     * @return true of the language is supported by the plugin and false otherwise.
     */
    public abstract boolean setLanguage(Language language);

    /**
     * Return command corresponding to name.
     * @param name : The command's name.
     * @param command : The command line.
     * @return The command corresponding to the command's name.
     * @throws UnknownCommand if the command is not supported by the plugin.
     */
    public abstract Command getCommand(String name, String command) throws UnknownCommand;

    /**
     * Getter method.
     * @return the plugin's name
     */
    public abstract String getName();
}
