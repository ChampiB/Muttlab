package muttlab.commands;

import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;

import java.io.OutputStream;

public abstract class Command {

    private String command;

    /**
     * Execute the command.
     * @param output: The output of the command.
     * @param elements: The stack of element.
     * @throws Exception if an error occurred.
     */
    protected abstract void execute(OutputStream output, ObservableStackWrapper<Matrix> elements) throws Exception;

    /**
     * Getter.
     * @return the help message to display to the user.
     */
    public abstract String getHelpMessage();

    /**
     * Getter.
     * @return the command name.
     */
    public abstract String getName();

    /**
     * Getter method.
     * @return the line command.
     */
    protected String getCommand() {
        return command;
    }

    /**
     * Setter method.
     * @param command : The line command.
     */
    protected void setCommand(String command) {
        this.command = command;
    }

}