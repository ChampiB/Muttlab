package core;

import core.commands.*;
import muttlab.commands.Command;
import muttlab.languages.MuttLabStrings;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

class CommandFactory {

    private static final HashMap<MuttLabStrings, Function<String, Command>> commands = createMap();

    /**
     * Create the map between commands' name and commands' constructor.
     * @return the map build.
     */
    private static HashMap<MuttLabStrings, Function<String, Command>> createMap() {
        HashMap<MuttLabStrings, Function<String, Command>> commands = new HashMap<>();
        commands.put(MuttLabStrings.NEW_MATRIX_COMMAND_KEY, CommandNewMatrix::new);
        commands.put(MuttLabStrings.ADD_COMMAND_KEY, CommandAdd::new);
        commands.put(MuttLabStrings.SUB_COMMAND_KEY, CommandSub::new);
        commands.put(MuttLabStrings.MUL_COMMAND_KEY, CommandMul::new);
        commands.put(MuttLabStrings.MUL_ELEMENT_WISE_COMMAND_KEY, CommandPointWiseMul::new);
        commands.put(MuttLabStrings.DUP_COMMAND_KEY, CommandDup::new);
        commands.put(MuttLabStrings.SAVE_COMMAND_KEY, CommandSave::new);
        commands.put(MuttLabStrings.HELP_COMMAND_KEY, CommandHelp::new);
        commands.put(MuttLabStrings.QUIT_COMMAND_KEY, CommandQuit::new);
        commands.put(MuttLabStrings.SCRIPT_COMMAND_KEY, CommandScript::new);
        return commands;
    }

    /**
     * Getter method.
     * @return the list of the available commands' name
     */
    static Stream<String> getAvailableCommandsName() {
        return commands.keySet().stream().map(Enum::toString);
    }

    /**
     * Create the command corresponding to the command key.
     * @param key : The command key corresponding to the command.
     * @param command : The command line.
     * @return The command corresponding to the key.
     * @throws Exception if the the command is not supported.
     */
    static Command create(MuttLabStrings key, String command) throws Exception {
        return commands.get(key).apply(command);
    }
}
