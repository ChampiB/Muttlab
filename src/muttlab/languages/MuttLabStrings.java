package muttlab.languages;

/**
 * List of available message.
 */
public enum MuttLabStrings {

    // Tab's name.
    RUNNING_TASKS_TAB_NAME("Running tasks"),
    MATRICES_STACK_TAB_NAME("Matrices stack"),
    COMMAND_PROMPT_TAB_NAME("Command prompt"),
    HISTORY_TAB_NAME("History"),

    // Command's key.
    NEW_MATRIX_COMMAND_KEY("["),
    ADD_COMMAND_KEY("+"),
    SUB_COMMAND_KEY("-"),
    MUL_COMMAND_KEY("*"),
    MUL_ELEMENT_WISE_COMMAND_KEY(".*"),
    DUP_COMMAND_KEY("dup"),
    SAVE_COMMAND_KEY("save"),
    HELP_COMMAND_KEY("help"),
    QUIT_COMMAND_KEY("quit"),
    SCRIPT_COMMAND_KEY("script"),

    // Command's name.
    UNKNOWN_COMMAND_NAME("unknown"),
    ADD_COMMAND_NAME("+"),
    DUP_COMMAND_NAME("duplicate"),
    HELP_COMMAND_NAME("help"),
    MUL_COMMAND_NAME("*"),
    NEW_MATRIX_COMMAND_NAME("new matrix"),
    MUL_ELEMENT_WISE_COMMAND_NAME(".*"),
    QUIT_COMMAND_NAME("quit"),
    SAVE_COMMAND_NAME("save"),
    SCRIPT_COMMAND_NAME("script"),
    SUB_COMMAND_NAME("-"),

    // Command's help message.
    NEW_MATRIX_HELP_MESSAGE("Synopsis: COMMAND_NAME content_of_matrix\nDescription: Create a new matrix and add it on the top of the stack.\nExample: '[ 1 2; 3 4 ]'"),
    ADD_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Add the two matrices on the top of the stack.\nExample: '+'"),
    SUB_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Subtract the two matrices on the top of the stack.\nExample: '-'"),
    MUL_HELP_MESSAGE("Synopsis: COMMAND_NAME [k]\nDescription: If 'k' is present multiply the first matrix on the top of the stack by 'k', otherwise multiply the two matrices on the top of the stack.\nExample: '* 3'"),
    MUL_ELEMENT_WISE_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Multiply element wise the two matrices on the top of the stack.\nExample: '.*'"),
    DUP_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Duplication the matrix on the top of the stack.\nExample: 'dup'"),
    SAVE_HELP_MESSAGE("Synopsis: COMMAND_NAME output_file_name\nDescription: Save the matrix on the top of the stack in the file passed as parameter.\nExample: 'save file.out'"),
    HELP_HELP_MESSAGE("Synopsis: COMMAND_NAME [command_name]\nDescription: Print the general help message or the help message of the command requested.\nExample: 'help ['"),
    QUIT_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Allow to quit the program.\nExample: 'quit'"),
    SCRIPT_HELP_MESSAGE("Synopsis: COMMAND_NAME script_file_name\nDescription: Execute the script passed as parameter.\nExample: 'script file.txt'"),
    UNKNOWN_COMMAND_MESSAGE("Command is unknown."),






    // TODO clean it
    FAIL_TO_FLUSH_COMMAND_OUTPUT("Fail to flush command output."),
    COMMAND_PROMPT_HELP_MESSAGE("Write the command, press enter and follow it execution in the other tabs."),
    GOODBYE_MESSAGE(""),
    WELCOME_MESSAGE(""),
    NOT_ENOUGH_ELEMENT_IN_QUEUE(""),
    UNSUPPORTED_COMMAND_PARAMETER(""),
    UNSUPPORTED_OPERATION(""),
    INVALID_OPERATION_ERROR_MESSAGE(""),
    COMMAND_FAILED_CHECK_LOG(""),
    IO_EXCEPTION(""),
    FAIL_TO_WRITE_IN_FILE(""),
    BAD_NUMBER_OF_PARAMETERS(""),
    NOT_VALID_FLOAT("Float is not valid."),
    NOT_VALID_INT("Integer is not valid."),
    NOT_VALID_MATRIX("Matrix is not valid."),
    NOT_VALID_MATRIX_SIZE("Size of matrix is not valid."),
    MATRIX_ELEMENT_DOES_NOT_EXIST("The matrix's element does not exist."),

    HELP_MESSAGE(""),

    COMMAND_NAME_NOT_FOUND_HELP_MESSAGE("You are using MatBench.\n\nYour command words are:\n   show [ + - * .* dup help script quit"),
    GLOBAL_HELP_MESSAGE("The following commands are available (use 'help <command_name>' to get further information):"),
    QUIT_ERROR_MESSAGE("Quit what?"),
    NEW_MATRIX_ERROR_MESSAGE("what's the matrix?"),
    FILE_NAME_NOT_FOUND_ERROR_MESSAGE("File not found."),
    SAVE_CANT_WRITE_IN_FILE_ERROR_MESSAGE("Can't write the matrix in the file."),
    ;

    private final String str;

    /**
     * Create the enum.
     * @param str: The string corresponding to the enumeration.
     */
    MuttLabStrings(final String str) {
        this.str = str;
    }

    /**
     * Convert the enum into a string.
     * @return the string representation.
     */
    @Override
    public String toString() {
        // TODO load the string from dictionary.
        return str;
    }

    /**
     * Transform the command name write by the user into the corresponding key.
     * @param commandName : The command name.
     * @return the enumeration corresponding to the command name (write by the user).
     */
    public static MuttLabStrings fromString(String commandName) {
        MuttLabStrings[] values = MuttLabStrings.values();
        for (MuttLabStrings value : values) {
            if (value.toString().equals(commandName)) {
                return value;
            }
        }
        return null;
    }
}
