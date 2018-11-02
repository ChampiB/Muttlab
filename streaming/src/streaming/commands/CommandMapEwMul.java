package streaming.commands;

import muttlab.helpers.DisplayHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.Objects;
import java.util.Stack;

public class CommandMapEwMul extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandMapEwMul(String command) { setCommand(command); }

    /**
     * Multiply element wise all the matrix of the stream.
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
                ui, StreamingKeys.NOT_ENOUGH_PARAMETERS.toString(), StreamingDictionary.getInstance(), false
            );
        }
        try {
            // Multiply element wise all the matrix of the stream.
            final Float k = Float.valueOf(parameters[1]);
            CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
                s = s.map(m -> {
                    try {
                        m.mul(k);
                        return m;
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull);
                CurrentStream.getInstance().setCurrentStream(s);
            });
        } catch (Exception e) {
            DisplayHelper.printErrAndReturn(
                ui, StreamingKeys.NOT_VALID_INT.toString(), StreamingDictionary.getInstance(), false
            );
        }
        return false;
    }
}
