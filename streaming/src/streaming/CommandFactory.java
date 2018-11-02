package streaming;

import muttlab.plugins.Command;
import streaming.commands.*;
import streaming.languages.StreamingKeys;

import java.util.HashMap;
import java.util.function.Function;

public class CommandFactory {

    private static final HashMap<StreamingKeys, Function<String, Command>> commands = createMap();

    /**
     * Create the map between commands' name and commands' constructor.
     * @return the map build.
     */
    private static HashMap<StreamingKeys, Function<String, Command>> createMap() {
        HashMap<StreamingKeys, Function<String, Command>> commands = new HashMap<>();
        commands.put(StreamingKeys.STREAM_FROM, CommandStreamFrom::new);
        commands.put(StreamingKeys.FILTER_WIDTH, CommandFilterWidth::new);
        commands.put(StreamingKeys.SAVE_STACK, CommandSaveStack::new);
        commands.put(StreamingKeys.SAVE_FILE, CommandSaveFile::new);
        commands.put(StreamingKeys.MAP_EW_MUL, CommandMapEwMul::new);
        commands.put(StreamingKeys.REDUCE_ADD, CommandReduceAdd::new);
        commands.put(StreamingKeys.REDUCE_FIRST, CommandReduceFirst::new);
        commands.put(StreamingKeys.REDUCE_LAST, CommandReduceLast::new);
        commands.put(StreamingKeys.SORT_BY, CommandSortBy::new);
        return commands;
    }

    /**
     * Create the command corresponding to the command key.
     * @param key : The command key corresponding to the command.
     * @param command : The command line.
     * @return The command corresponding to the key.
     * @throws Exception if the the command is not supported.
     */
    public static Command create(StreamingKeys key, String command) throws Exception {
        return commands.get(key).apply(command);
    }
}
