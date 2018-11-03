package streaming.languages;

/**
 * List of available message.
 */
public enum StreamingKeys {
    CURRENT_STREAM_IS_NOT_PRESENT,
    REDUCER_NAME_FIRST,
    REDUCER_NAME_LPAD,
    REDUCER_NAME_RPAD,
    BY_SUM,
    BY_MIN,
    BY_MAX,
    STREAM_FROM,
    REDUCE_ADD,
    REDUCE_FIRST,
    REDUCE_LAST,
    SORT,
    FILTER_WIDTH,
    MAP_EW_MUL,
    SAVE_STACK,
    SAVE_FILE,
    STREAM_FROM_HELP_MESSAGE,
    REDUCE_ADD_HELP_MESSAGE,
    REDUCE_FIRST_HELP_MESSAGE,
    REDUCE_LAST_HELP_MESSAGE,
    SORT_HELP_MESSAGE,
    FILTER_WIDTH_HELP_MESSAGE,
    MAP_EW_MUL_HELP_MESSAGE,
    SAVE_STACK_HELP_MESSAGE,
    SAVE_FILE_HELP_MESSAGE;

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
