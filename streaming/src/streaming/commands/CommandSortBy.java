package streaming.commands;

import muttlab.helpers.DisplayHelper;
import muttlab.math.Element;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.*;

public class CommandSortBy extends Command {

    private final Map<String, Comparator<Matrix>> mapping = createMapping();

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandSortBy(String command) { setCommand(command); }

    /**
     * Create the mapping between the reducers' name and the reducers.
     * @return the mapping.
     */
    private Map<String, Comparator<Matrix>> createMapping() {
        Map<String, Comparator<Matrix>> mapping = new HashMap<>();
        // Add key-value for summing all matrices which have the same size as the first matrix in the stream.
        String first = StreamingDictionary.getInstance().getValue(StreamingKeys.BY_SUM.toString());
        mapping.put(first, Comparator.comparing(Matrix::sum));
        // Add key-value for summing all matrices with left padding.
        String lpad = StreamingDictionary.getInstance().getValue(StreamingKeys.BY_MIN.toString());
        mapping.put(lpad, Comparator.comparing(Matrix::min));
        // Add key-value for summing all matrices with right padding.
        String rpad = StreamingDictionary.getInstance().getValue(StreamingKeys.BY_MAX.toString());
        mapping.put(rpad, Comparator.comparing(Matrix::max));
        return mapping;
    }

    /**
     * Return the sort function (sorter) corresponding to the key.
     * @param sorterName: the key of the sorter.
     * @return the sorter.
     */
    private Comparator<Matrix> getSorter(String sorterName) {
        return mapping.get(sorterName);
    }

    /**
     * Add the two last elements of the list.
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
            // Reduce all the matrix of the stream by summing them.
            final Comparator<Matrix> sorter = getSorter(parameters[1]);
            if (sorter == null)
                throw new Exception(StreamingKeys.INVALID_OPERATION_ERROR_MESSAGE.toString());
            CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
                CurrentStream.getInstance().setCurrentStream(s.sorted(sorter));
            });
        } catch (Exception e) {
            DisplayHelper.printErrAndReturn(
                ui, e.getMessage(), StreamingDictionary.getInstance(), false
            );
        }
        return false;
    }
}
