package streaming.commands;

import muttlab.commands.Command;
import muttlab.helpers.CommandHelper;
import muttlab.helpers.ConverterHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.OutputStream;

public class CommandFilterWidth extends Command {
    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandFilterWidth(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.FILTER_WIDTH_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.FILTER_WIDTH_COMMAND_KEY.toString());
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.FILTER_WIDTH_COMMAND_NAME.toString();
    }

    /**
     * Filter all the matrices which have not the width specified in the commands' parameters.
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
        // Keep only matrices where the width asked.
        final int width = ConverterHelper.toInt(args[1]);
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            s = s.filter(m -> m.getWidth() == width);
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
