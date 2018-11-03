package streaming.commands;

import muttlab.helpers.DisplayHelper;
import muttlab.languages.MuttLabKeys;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.Stack;

public class CommandFilterWidth extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandFilterWidth(String command) { setCommand(command); }

    /**
     * Filter all the matrices which have not the width specified in the commands' parameters.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Check that there is at least one parameter.
        String[] parameters = getCommand().split(" ");
        if (parameters.length < 2) {
            return DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.NOT_ENOUGH_PARAMETERS.toString(), StreamingDictionary.getInstance(), false
            );
        }
        try {
            // Keep only matrices where the width asked.
            final int width = Integer.valueOf(parameters[1]);
            CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
                s = s.filter(m -> m.getWidth() == width);
                CurrentStream.getInstance().setCurrentStream(s);
            });
        } catch (Exception e) {
            DisplayHelper.printErrAndReturn(
                ui, MuttLabKeys.NOT_VALID_INT.toString(), StreamingDictionary.getInstance(), false
            );
        }
        return false;
    }
}
