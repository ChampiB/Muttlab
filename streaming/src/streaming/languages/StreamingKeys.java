package streaming.languages;

/**
 * List of available message.
 */
public enum StreamingKeys {
    NOT_ENOUGH_ELEMENT_IN_QUEUE,
    INVALID_OPERATION_ERROR_MESSAGE,
    CANT_STREAM_THE_FILE,
    STREAM_FROM,
    FAIL_TO_WRITE_IN_FILE,
    NOT_ENOUGH_PARAMETERS,
    NOT_VALID_INT,
    REDUCE_ADD,
    REDUCE_MIN,
    REDUCE_MAX,
    SORT_BY,
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
