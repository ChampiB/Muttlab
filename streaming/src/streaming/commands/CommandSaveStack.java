package streaming.commands;

import muttlab.math.Element;
import muttlab.math.elements.MatrixWrapper;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CommandSaveStack extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSaveStack(String command) { setCommand(command); }

    /**
     * Add the two last elements of the list.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) {
        // Save all the matrix of the stream in the stack.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
                List<Matrix> matrices = s.collect(Collectors.toList());
                for (Matrix m : matrices) {
                    elements.push(new MatrixWrapper(m));
                }
            }
        );
        CurrentStream.getInstance().setCurrentStream(null);
        return false;
    }
}
