package muttlab.plugins;

import muttlab.math.Element;
import muttlab.ui.UserInterface;

import java.util.List;
import java.util.Stack;

public abstract class Command {

    private String command;

    /**
     * Execute the command.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    public abstract boolean execute(UserInterface ui, Stack<Element> elements);

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
