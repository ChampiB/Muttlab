package muttlab.helpers;

import muttlab.languages.DictionaryLoader;
import muttlab.ui.UserInterface;

public class DisplayHelper {
    /**
     * Print a message on the error output.
     * @param ui: The user interface.
     * @param key: The key corresponding to the error message.
     * @param dic: The dictionary to use for converting the key into the value.
     * @param r: the return value.
     * @return 'r'.
     */
    public static boolean printErrAndReturn(UserInterface ui, String key, DictionaryLoader dic, boolean r) {
        ui.printlnErr(dic.getValue(key));
        return r;
    }

    /**
     * Print a message on the standard output.
     * @param ui: The user interface.
     * @param key: The key corresponding to the error message.
     * @param dic: The dictionary to use for converting the key into the value.
     * @param r: the return value.
     * @return 'r'.
     */
    public static boolean printAndReturn(UserInterface ui, String key, DictionaryLoader dic, boolean r) {
        ui.println(dic.getValue(key));
        return r;
    }
}