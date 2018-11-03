package streaming.languages;

/**
 * List of available message.
 */
public enum StreamingKeys {
    CURRENT_STREAM_IS_NOT_PRESENT,
    REDUCER_NAME_FIRST,
    REDUCER_NAME_LPAD,
    REDUCER_NAME_RPAD,
    STREAM_FROM,
    REDUCE_ADD,
    REDUCE_FIRST,
    REDUCE_LAST,
    SORT_BY,
    BY_SUM,
    BY_MIN,
    BY_MAX,
    FILTER_WIDTH,
    MAP_EW_MUL,
    SAVE_STACK,
    SAVE_FILE;

    /**
     * Transform the command name write by the user into the corresponding key.
     * @param cmdName : The command name.
     * @return the enumeration corresponding to the command name (write by the user).
     */
    public static StreamingKeys fromString(String cmdName) {
        StreamingKeys[] values = StreamingKeys.values();

        String commandName = StreamingDictionary.getInstance().getKey(cmdName);
        for (StreamingKeys value : values) {
            if (value.toString().equals(commandName)) {
                return value;
            }
        }
        return null;
    }
}
