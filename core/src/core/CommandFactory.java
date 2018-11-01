package core;

import core.languages.CoreKeys;
import muttlab.plugins.Command;
import core.commands.*;

import java.util.HashMap;
import java.util.function.Function;

public class CommandFactory {

    private static final HashMap<CoreKeys, Function<String, Command>> commands = createMap();

    /**
     * Create the map between commands' name and commands' constructor.
     * @return the map build.
     */
    private static HashMap<CoreKeys, Function<String, Command>> createMap() {
        HashMap<CoreKeys, Function<String, Command>> commands = new HashMap<>();
        commands.put(CoreKeys.NEW_MATRIX, CommandNewMatrix::new);
        commands.put(CoreKeys.ADD, CommandAdd::new);
        commands.put(CoreKeys.SUB, CommandSub::new);
        commands.put(CoreKeys.MUL, CommandMul::new);
        commands.put(CoreKeys.MUL_ELEMENT_WISE, CommandPointWiseMul::new);
        commands.put(CoreKeys.DUP, CommandDup::new);
        commands.put(CoreKeys.SAVE, CommandSave::new);
        commands.put(CoreKeys.HELP, CommandHelp::new);
        commands.put(CoreKeys.QUIT, CommandQuit::new);
        commands.put(CoreKeys.SCRIPT, CommandScript::new);
        return commands;
    }

    /**
     * Create the command corresponding to the command key.
     * @param key : The command key corresponding to the command.
     * @param command : The command line.
     * @return The command corresponding to the key.
     * @throws Exception if the the command is not supported.
     */
    public static Command create(CoreKeys key, String command) throws Exception {
        return commands.get(key).apply(command);
    }
}
