package streaming.commands;

import muttlab.exceptions.UserException;
import muttlab.helpers.CommandHelper;
import muttlab.languages.MuttLabKeys;
import muttlab.math.Element;
import muttlab.math.matrices.Matrix;
import muttlab.plugins.Command;
import muttlab.ui.UserInterface;
import streaming.CurrentStream;
import streaming.languages.StreamingDictionary;
import streaming.languages.StreamingKeys;

import java.util.*;
import java.util.function.BinaryOperator;

public class CommandReduceAdd extends Command {

    private final Map<String, BinaryOperator<Matrix>> mapping = createMapping();

    /**
     * Constructor.
     * @param command : The command line.
     */
    public CommandReduceAdd(String command) { setCommand(command); }

    /**
     * Reduce the matrices by summing them.
     * The matrices are only summed if they have the same size else the first one is returned.
     * @param m1: The first matrix.
     * @param m2: The second matrix.
     * @return: The reduced matrix.
     */
    private static Matrix firstReducer(Matrix m1, Matrix m2) {
        try {
            if (m1 != null && m1.hasSameSizeAs(m2)) m1.add(m2);
            if (m1 == null) m1 = m2;
            return m1;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Reduce the matrices by summing them.
     * If the matrices don't have the same size, apply a left padding.
     * @param m1: The first matrix.
     * @param m2: The second matrix.
     * @return: The reduced matrix.
     */
    private static Matrix padLeftReducer(Matrix m1, Matrix m2) {
        try {
            if (m1 != null) {
                int diff = m1.getWidth() - m2.getWidth();
                if (diff < 0) {
                    m1.addColumnsOnTheLeft(-diff, 0);
                } else if (diff > 0) {
                    m2.addColumnsOnTheLeft(diff, 0);
                }
                m1.add(m2);
            }
            if (m1 == null) m1 = m2;
            return m1;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Reduce the matrices by summing them.
     * If the matrices don't have the same size, apply a right padding.
     * @param m1: The first matrix.
     * @param m2: The second matrix.
     * @return: The reduced matrix.
     */
    private static Matrix padRightReducer(Matrix m1, Matrix m2) {
        try {
            if (m1 != null) {
                int diff = m1.getWidth() - m2.getWidth();
                if (diff < 0) {
                    m1.addColumnsOnTheRight(-diff, 0);
                } else if (diff > 0) {
                    m2.addColumnsOnTheRight(diff, 0);
                }
                m1.add(m2);
            }
            if (m1 == null) {
                m1 = m2;
            }
            return m1;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create the mapping between the reducers' name and the reducers.
     * @return the mapping.
     */
    private Map<String, BinaryOperator<Matrix>> createMapping() {
        Map<String, BinaryOperator<Matrix>> mapping = new HashMap<>();
        // Add key-value for summing all matrices which have the same size as the first matrix in the stream.
        String first = StreamingDictionary.getInstance().getValue(StreamingKeys.REDUCER_NAME_FIRST.toString());
        mapping.put(first, CommandReduceAdd::firstReducer);
        // Add key-value for summing all matrices with left padding.
        String lpad = StreamingDictionary.getInstance().getValue(StreamingKeys.REDUCER_NAME_LPAD.toString());
        mapping.put(lpad, CommandReduceAdd::padLeftReducer);
        // Add key-value for summing all matrices with right padding.
        String rpad = StreamingDictionary.getInstance().getValue(StreamingKeys.REDUCER_NAME_RPAD.toString());
        mapping.put(rpad, CommandReduceAdd::padRightReducer);
        return mapping;
    }

    /**
     * Return the reduce function (reducer) corresponding to the key.
     * @param reducerName: the key of the reducer.
     * @return the reducer.
     */
    private BinaryOperator<Matrix> getReducer(String reducerName) throws Exception {
        BinaryOperator<Matrix> reducer = mapping.get(reducerName);
        if (reducer == null)
            throw new UserException(MuttLabKeys.UNSUPPORTED_COMMAND_PARAMETER.toString());
        return mapping.get(reducerName);
    }

    /**
     * Reduce the list of matrices by summing them.
     * @param ui : The user interface to use for displaying messages.
     * @param elements : The current stack of elements.
     * @return true if the session must be closed and false otherwise.
     */
    @Override
    public boolean execute(UserInterface ui, Stack<Element> elements) throws Exception {
        // Check that there is at least one parameter.
        String[] args = getCommand().split(" ");
        CommandHelper.checkNumberOfParameters(args, 2, 2);
        // Reduce all the matrix of the stream by summing them.
        final BinaryOperator<Matrix> reducer = getReducer(args[1]);
        CurrentStream.getInstance().getCurrentStream().ifPresent(s -> {
            List<Matrix> a = new ArrayList<>();
            a.add(s.reduce(null, reducer));
            CurrentStream.getInstance().setCurrentStream(a.stream().filter(Objects::nonNull));
        });
        return false;
    }
}
