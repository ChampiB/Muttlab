package streaming.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandReduceFirst extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandReduceFirst(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.REDUCE_FIRST_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.REDUCE_FIRST_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.REDUCE_FIRST_COMMAND_NAME.toString();
    }

    /**
     * Reduce the list of matrices by taking the first.
     * @param out : The output stream to use for displaying messages.
     * @param elements : The current stack of elements.
     */
    @Override
    public void execute(OutputStream out, ObservableStackWrapper<Matrix> elements) throws Exception {
        // Check if the current stream is present.
        CurrentStream.checkIsPresent();
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Reduce the list of matrices by taking the first.
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            List<Matrix> a = new ArrayList<>();
            a.add(s.findFirst().orElse(null));
            CurrentStream.getInstance().setCurrentStream(a.stream().filter(Objects::nonNull));
        });
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    @Override
    protected void flush(ObservableStackWrapper<Matrix> elements) {}
}
