package muttlab.helpers;

import java.io.OutputStream;

public class DisplayHelper {
    /**
     * Print the message with a '\n' on the stream.
     * @param out: The output stream.
     * @param message: The message to write.
     * @return false if an error occurred and true otherwise.
     */
    public static boolean println(OutputStream out, String message) {
        try {
            String msg = message + "\n";
            out.write(msg.getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}