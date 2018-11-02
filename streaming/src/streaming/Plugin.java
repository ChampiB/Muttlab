package streaming;

import muttlab.exceptions.UnknownCommand;
import muttlab.languages.Language;
import muttlab.plugins.Command;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

public class Plugin extends muttlab.plugins.Plugin {

    /**
     * Set the language used by the plugin.
     * @param language : The new language.
     * @return true of the language is supported by the plugin and false otherwise.
     */
    @Override
    public boolean setLanguage(Language language)
    {
        StreamingDictionary.getInstance().setLanguage(language);
        return true;
    }

    /**
     * Return command corresponding to name.
     * @param name : The command's name.
     * @param command : The command line.
     * @return The command corresponding to the command's name.
     * @throws UnknownCommand if the command is not supported by the plugin.
     */
    @Override
    public Command getCommand(String name, String command) throws UnknownCommand
    {
        try {
            StreamingKeys commandName = StreamingKeys.fromString(name);
            return CommandFactory.create(commandName, command);
        } catch (Exception e) {
            throw new UnknownCommand();
        }
    }

    /**
     * Getter method.
     * @return the plugin's name
     */
    @Override
    public String getName() {
        return "streaming";
    }
}
