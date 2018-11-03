package muttlab.plugins;

import muttlab.exceptions.UserException;
import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabDictionary;
import muttlab.languages.MuttLabKeys;
import muttlab.loggers.Logging;
import muttlab.loggers.LoggingLevel;
import muttlab.math.Element;
import muttlab.ui.UserInterface;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public abstract class Command {

    private String command;

    /**
     * Execute the command.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     * @throws Exception if the command failed.
     */
    protected abstract boolean execute(UserInterface ui, Stack<Element> elements) throws Exception;

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public abstract String getHelpMessage();

    /**
     * Execute the command and handle the errors.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    public boolean exec(UserInterface ui, Stack<Element> elements) {
        try {
            return execute(ui, elements);
        } catch (UserException e) {
            return DisplayHelper.printlnErr(ui, e.getMessage(), MuttLabDictionary.getInstance(), false);
        } catch (IOException e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
            return DisplayHelper.printlnErr(ui, MuttLabKeys.IO_EXCEPTION.toString(), MuttLabDictionary.getInstance(), false);
        } catch (Exception e) {
            Logging.log(LoggingLevel.ERROR, e.getMessage());
            Logging.log(LoggingLevel.ERROR, Arrays.toString(e.getStackTrace()));
            return DisplayHelper.printlnErr(ui, MuttLabKeys.COMMAND_FAILED_CHECK_LOG.toString(), MuttLabDictionary.getInstance(), false);
        }
    }

    /**
     * Getter method.
     * @return the line command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Setter method.
     * @param command : The line command.
     */
    public void setCommand(String command) {
        this.command = command;
    }
}
