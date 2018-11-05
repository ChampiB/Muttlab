package muttlab.helpers;

import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabStrings;


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
            throw new UserException(MuttLabStrings.NOT_VALID_INT.toString());
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
            throw new UserException(MuttLabStrings.NOT_VALID_FLOAT.toString());
        }
    }

    /**
     * Return the float contains in the string.
     * @param s: the string.
     * @return the float contains in the string or null if the string is malformed.
     */
    public static Float toFloatOrNull(String s) {
        try {
            return Float.valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }
}