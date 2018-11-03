package streaming.commands;

import muttlab.helpers.CommandHelper;
import muttlab.helpers.ConverterHelper;
import muttlab.math.Element;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;

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
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
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
        return false;
    }
}
