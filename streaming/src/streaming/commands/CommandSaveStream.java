package streaming.commands;

import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.Stack;

public class CommandSaveStream extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSaveStream(String command) { setCommand(command); }

    /**
     * Add the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // TODO
        return false;
    }
}
