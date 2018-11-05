package streaming.commands;

import muttlab.commands.Command;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class CommandSaveStack extends Command {

    private List<Matrix> matrices = null;

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSaveStack(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.SAVE_STACK_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.SAVE_STACK_COMMAND_KEY.toString());
    }

    /**
     * Save all the matrices of the stream into the stack.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of elements.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check if the current stream is present.
        CurrentStream.checkIsPresent();
        // Save all the matrix of the stream in the stack.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> matrices = s.collect(Collectors.toList()));
        CurrentStream.getInstance().setCurrentStream(null);
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.SAVE_STACK_COMMAND_NAME.toString();
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    @Override
    protected void flush(ObservableStackWrapper<Matrix> elements) {
        for (Matrix m : matrices) {
            elements.push(m);
        }
    }
}
