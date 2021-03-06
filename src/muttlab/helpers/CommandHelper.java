package muttlab.helpers;

import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabStrings;
import muttlab.math.Matrix;
import muttlab.ui.components.ObservableStackWrapper;


public class CommandHelper {
    /**
     * Check if the number of parameters is between min and max (included).
     * @param args: the parameters.
     * @param min: min boundary.
     * @param max: max boundary.
     * @throws Exception if the number of parameter is not valid.
     */
    public static void checkNumberOfParameters(String[] args, int min, int max) throws Exception {
        if (args.length < min || max < args.length) {
            throw new UserException(MuttLabStrings.BAD_NUMBER_OF_PARAMETERS.toString());
        }
    }

    /**
     * Check that there is at least n element in the stack.
     * @param elements: the stack.
     * @param n: the minimum number of elements needed.
     * @throws Exception if there is not enough elements in the stack.
     */
    public static void checkAtLeastInTheStack(ObservableStackWrapper<Matrix> elements, int n) throws Exception {
        if (elements.size() < n) {
            throw new UserException(MuttLabStrings.NOT_ENOUGH_ELEMENT_IN_QUEUE.toString());
        }
    }
}