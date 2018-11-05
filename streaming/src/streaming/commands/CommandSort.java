package streaming.commands;

import muttlab.commands.Command;
import muttlab.exceptions.UserException;
import muttlab.helpers.CommandHelper;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;
import streaming.CurrentStream;

import java.io.OutputStream;
import java.util.*;

public class CommandSort extends Command {

    private final Map<String, Comparator<Matrix>> mapping = createMapping();

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSort(String command) { setCommand(command); }

    /**
     * Getter method.
     * @return the help message to display to the user.
     */
    public String getHelpMessage() {
        return MuttLabStrings.SORT_HELP_MESSAGE.toString()
                .replaceAll("COMMAND_NAME", MuttLabStrings.SORT_COMMAND_KEY.toString());
    }

    /**
     * Create the mapping between the reducers' name and the reducers.
     * @return the mapping.
     */
    private Map<String, Comparator<Matrix>> createMapping() {
        Map<String, Comparator<Matrix>> mapping = new HashMap<>();
        // Add key-value for summing all matrices which have the same size as the first matrix in the stream.
        String first = MuttLabStrings.BY_SUM.toString();
        mapping.put(first, Comparator.comparing(Matrix::sum));
        // Add key-value for summing all matrices with left padding.
        String lpad = MuttLabStrings.BY_MIN.toString();
        mapping.put(lpad, Comparator.comparing(Matrix::min));
        // Add key-value for summing all matrices with right padding.
        String rpad = MuttLabStrings.BY_MAX.toString();
        mapping.put(rpad, Comparator.comparing(Matrix::max));
        return mapping;
    }

    /**
     * Return the sort function (sorter) corresponding to the key.
     * @param sorterName: the key of the sorter.
     * @return the sorter.
     */
    private Comparator<Matrix> getSorter(String sorterName) throws Exception {
        Comparator<Matrix> sorter = mapping.get(sorterName);
        if (sorter == null)
            throw new UserException(MuttLabStrings.UNSUPPORTED_COMMAND_PARAMETER.toString());
        return sorter;
    }

    /**
     * Add the two last elements of the list.
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
        // Reduce all the matrix of the stream by summing them.
        final Comparator<Matrix> sorter = getSorter(args[1]);
        CurrentStream.getInstance().getCurrentStream().ifPresent(s ->
            CurrentStream.getInstance().setCurrentStream(s.sorted(sorter))
        );
    }

    /**
     * Getter.
     * @return the command name.
     */
    public String getName() {
        return MuttLabStrings.SORT_COMMAND_NAME.toString();
    }

    /**
     * Flush the command output.
     * @param elements: The stack of element.
     */
    @Override
    protected void flush(ObservableStackWrapper<Matrix> elements) {}
}
