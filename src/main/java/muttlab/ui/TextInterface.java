package muttlab.ui;

import java.io.InputStream;

public class TextInterface extends UserInterface {

    /**
     * Getter method.
     * @return the input stream of the user interface.
     */
    public InputStream getInputStream() { return System.in; }

    /**
     * Print out one message on the standard output (without '\n' at the end).
     * @param message : The message to display.
     */
    public void printStd(String message) { System.out.print(message); }

    /**
     * Print out one message on the error output (without '\n' at the end).
     * @param message : The message to display.
     */
    public void printErr(String message) { System.err.print(message); }
}
