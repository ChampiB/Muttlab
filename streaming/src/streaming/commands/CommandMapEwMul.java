package streaming.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.ConverterHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.OutputStream;
import java.util.Objects;

public class CommandMapEwMul extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandMapEwMul(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.MAP_EW_MUL_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.MAP_EW_MUL_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.MAP_EW_MUL_COMMAND_NAME.toString();
    }

    /**
     * Multiply element wise all the matrix of the stream.
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
        // Multiply element wise all the matrix of the stream.
        final Float k = ConverterHelper.toFloat(args[1]);
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
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    @Override
    protected void flush(ObservableStackWrapper<Matrix> elements) {}
}
