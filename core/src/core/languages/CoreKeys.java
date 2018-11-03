package core.languages;

/**
 * List of available message.
 */
public enum CoreKeys {
    HELP_MESSAGE,
    QUIT_ERROR_MESSAGE,
    NEW_MATRIX_ERROR_MESSAGE,
    FILE_NAME_NOT_FOUND_ERROR_MESSAGE,
    SAVE_CANT_WRITE_IN_FILE_ERROR_MESSAGE,
    GLOBAL_HELP_MESSAGE,
    NEW_MATRIX,
    ADD,
    SUB,
    MUL,
    MUL_ELEMENT_WISE,
    DUP,
    SAVE,
    HELP,
    QUIT,
    SCRIPT,
    NEW_MATRIX_HELP_MESSAGE,
    ADD_HELP_MESSAGE,
    SUB_HELP_MESSAGE,
    MUL_HELP_MESSAGE,
    MUL_ELEMENT_WISE_HELP_MESSAGE,
    DUP_HELP_MESSAGE,
    SAVE_HELP_MESSAGE,
    HELP_HELP_MESSAGE,
    QUIT_HELP_MESSAGE,
    SCRIPT_HELP_MESSAGE,
    ;

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
