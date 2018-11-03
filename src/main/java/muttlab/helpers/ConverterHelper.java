package muttlab.helpers;

import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabKeys;


public class ConverterHelper {
    /**
     * Return the number contains in the string.
     * @param s: the string.
     * @return the number contains in the string.
     * @throws Exception if the string is malformed.
     */
    public static int toInt(String s) throws Exception {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            throw new UserException(MuttLabKeys.NOT_VALID_INT.toString());
        }
    }

    /**
     * Return the number contains in the string.
     * @param s: the string.
     * @return the number contains in the string.
     * @throws Exception if the string is malformed.
     */
    public static float toFloat(String s) throws Exception {
        try {
            return Float.valueOf(s);
        } catch (Exception e) {
            throw new UserException(MuttLabKeys.NOT_VALID_FLOAT.toString());
        }
    }
}