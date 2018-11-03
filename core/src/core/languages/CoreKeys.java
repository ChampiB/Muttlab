package core.languages;
/*
NOT_ENOUGH_ELEMENT_IN_QUEUE
INVALID_OPERATION_ERROR_MESSAGE
FAIL_TO_WRITE_IN_FILE
BAD_NUMBER_OF_PARAMETERS
NOT_VALID_INT
NOT_VALID_FLOAT

CANT_STREAM_THE_FILE,
HELP_MESSAGE,
SAVE_CANT_WRITE_IN_FILE_ERROR_MESSAGE
QUIT_ERROR_MESSAGE,
NEW_MATRIX_ERROR_MESSAGE,
FILE_NAME_NOT_FOUND_ERROR_MESSAGE,
*/

/**
 * List of available message.
 */
public enum CoreKeys {
    HELP_MESSAGE,
    QUIT_ERROR_MESSAGE,
    NEW_MATRIX_ERROR_MESSAGE,
    FILE_NAME_NOT_FOUND_ERROR_MESSAGE,
    SAVE_CANT_WRITE_IN_FILE_ERROR_MESSAGE,
    NEW_MATRIX,
    ADD,
    SUB,
    MUL,
    MUL_ELEMENT_WISE,
    DUP,
    SAVE,
    HELP,
    QUIT,
    SCRIPT;

    /**
     * Transform the command name write by the user into the corresponding key.
     * @param cmdName : The command name.
     * @return the enumeration corresponding to the command name (write by the user).
     */
    public static CoreKeys fromString(String cmdName) {
        CoreKeys[] values = CoreKeys.values();

        String commandName = CoreDictionary.getInstance().getKey(cmdName);
        for (CoreKeys value : values) {
            if (value.toString().equals(commandName)) {
                return value;
            }
        }
        return null;
    }
}
