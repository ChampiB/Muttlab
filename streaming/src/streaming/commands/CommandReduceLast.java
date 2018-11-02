package streaming.commands;

import muttlab.math.Element;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class CommandReduceLast extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandReduceLast(String command) { setCommand(command); }

    /**
     * Reduce the list of matrices by taking the last.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Reduce the list of matrices by taking the last.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            List<Matrix> a = new ArrayList<>();
            a.add(s.reduce(null, (m1, m2) -> m2));
            CurrentStream.getInstance().setCurrentStream(a.stream().filter(Objects::nonNull));
        });
        return false;
    }
}
