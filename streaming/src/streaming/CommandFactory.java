package streaming;

import muttlab.commands.Command;
import muttlab.languages.MuttLabStrings;
import streaming.commands.*;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class CommandFactory {

    private static final HashMap<MuttLabStrings, Function<String, Command>> commands = createMap();

    /**
     * Create the map between commands' name and commands' constructor.
     * @return the map build.
     */
    private static HashMap<MuttLabStrings, Function<String, Command>> createMap() {
        HashMap<MuttLabStrings, Function<String, Command>> commands = new HashMap<>();
        commands.put(MuttLabStrings.STREAM_FROM_COMMAND_KEY, CommandStreamFrom::new);
        commands.put(MuttLabStrings.FILTER_WIDTH_COMMAND_KEY, CommandFilterWidth::new);
        commands.put(MuttLabStrings.SAVE_STACK_COMMAND_KEY, CommandSaveStack::new);
        commands.put(MuttLabStrings.SAVE_FILE_COMMAND_KEY, CommandSaveFile::new);
        commands.put(MuttLabStrings.MAP_EW_MUL_COMMAND_KEY, CommandMapEwMul::new);
        commands.put(MuttLabStrings.REDUCE_ADD_COMMAND_KEY, CommandReduceAdd::new);
        commands.put(MuttLabStrings.REDUCE_FIRST_COMMAND_KEY, CommandReduceFirst::new);
        commands.put(MuttLabStrings.REDUCE_LAST_COMMAND_KEY, CommandReduceLast::new);
        commands.put(MuttLabStrings.SORT_COMMAND_KEY, CommandSort::new);
        return commands;
    }

    /**
     * Getter method.
     * @return the list of the available commands' name
     */
    public static Stream<String> getAvailableCommandsName() {
        return commands.keySet().stream().map(Enum::toString);
    }

    /**
     * Create the command corresponding to the command key.
     * @param key : The command key corresponding to the command.
     * @param command : The command line.
     * @return The command corresponding to the key.
     * @throws Exception if the the command is not supported.
     */
    public static Command create(MuttLabStrings key, String command) throws Exception {
        return commands.get(key).apply(command);
    }
}
