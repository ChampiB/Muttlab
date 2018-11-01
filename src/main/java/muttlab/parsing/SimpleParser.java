package muttlab.parsing;

import muttlab.exceptions.UnknownCommand;
import muttlab.plugins.Command;
import muttlab.plugins.Plugin;
import muttlab.exceptions.InvalidCommand;

import java.io.InputStream;
import java.util.Iterator;

public class SimpleParser extends Parser {

    /**
     * Constructor.
     * @param is : The input stream to read from.
     */
    public SimpleParser(InputStream is) { super(is); }

    /**
     * Return the next command to execute.
     * @param availablePlugins : The list of available plugin.
     * @return the next command from the input stream.
     * @throws Exception if the command is not supported.
     */
    public Command getNextCommand(Iterator<Plugin> availablePlugins) throws Exception
    {
        String line = getInputReader().nextLine();
        String[] cmdAndArgs = line.split(" ");
        if (cmdAndArgs.length < 1)
            throw new InvalidCommand();
        String cmd = cmdAndArgs[0];
        while (availablePlugins.hasNext()) {
            Plugin plugin = availablePlugins.next();
            try {
                Command command = plugin.getCommand(cmd, line);
                if (command != null) return command;
            } catch (Exception e) {}
        }
        throw new UnknownCommand();
    }
}
