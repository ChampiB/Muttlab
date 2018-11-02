package muttlab.parsing;

import muttlab.plugins.Command;
import muttlab.plugins.Plugin;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public abstract class Parser {

    private Scanner inputReader;

    /**
     * Constructor.
     * @param is : The input stream to read from.
     */
    public Parser(InputStream is) {
        inputReader = new Scanner(is);
    }

    /**
     * Return the next command to execute.
     * @param availablePlugins : The list of available plugin.
     * @return the next command from the input stream.
     * @throws Exception if the command is not supported.
     */
    public abstract Command getNextCommand(Iterator<Plugin> availablePlugins) throws Exception;

    /**
     * Getter method.
     * @return the input stream.
     */
    protected Scanner getInputReader() { return this.inputReader; }

    /**
     * Setter method.
     * @param is : the new input stream.
     * @return this
     */
    protected Parser setInputStream(InputStream is) {
        this.inputReader = new Scanner(is);
        return this;
    }
}
