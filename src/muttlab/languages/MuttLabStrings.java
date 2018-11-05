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
    STREAM_FROM_COMMAND_KEY("stream_from"),
    SORT_COMMAND_KEY("sort"),
    SAVE_STACK_COMMAND_KEY("save_stack"),
    SAVE_FILE_COMMAND_KEY("save_file"),
    REDUCE_LAST_COMMAND_KEY("reduce_last"),
    REDUCE_FIRST_COMMAND_KEY("reduce_first"),
    REDUCE_ADD_COMMAND_KEY("reduce_add"),
    MAP_EW_MUL_COMMAND_KEY("map_ew_mul"),
    FILTER_WIDTH_COMMAND_KEY("filter_width"),

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
    STREAM_FROM_COMMAND_NAME("stream from file"),
    SORT_COMMAND_NAME("sort"),
    SAVE_STACK_COMMAND_NAME("save stream in stack"),
    SAVE_FILE_COMMAND_NAME("save stream in file"),
    REDUCE_LAST_COMMAND_NAME("reduce last"),
    REDUCE_FIRST_COMMAND_NAME("reduce first"),
    REDUCE_ADD_COMMAND_NAME("reduce add"),
    MAP_EW_MUL_COMMAND_NAME("map element wise multiplication"),
    FILTER_WIDTH_COMMAND_NAME("filter width"),

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
    UNKNOWN_COMMAND_MESSAGE("Command is unknown. (Use the help command for further information)"),
    STREAM_FROM_HELP_MESSAGE("Synopsis: COMMAND_NAME file_name\nDescription: Create a new stream of matrix from the file passed as parameter.\nExample: 'stream_from file.txt'"),
    SORT_HELP_MESSAGE("Synopsis: COMMAND_NAME <+|<m|<M\nDescription: Sort the stream of matrix. There is three strategy for comparing the matrices:\n(1) take the sum of the element\n(2) take the smallest element\n(3) take the biggest element.\nExample: 'sort <+'"),
    SAVE_STACK_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Terminal operation that save the stream in the stack.\nExample: 'save_stack'"),
    SAVE_FILE_HELP_MESSAGE("Synopsis: COMMAND_NAME file_name\nDescription: Terminal operation that save the stream in the file specified as parameter.\nExample: 'save_file file.txt'"),
    REDUCE_LAST_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Reduce the stream of matrix, return the last matrix.\nExample: 'reduce_last'"),
    REDUCE_FIRST_HELP_MESSAGE("Synopsis: COMMAND_NAME\nDescription: Reduce the stream of matrix, return the first matrix.\nExample: 'reduce_first'"),
    REDUCE_ADD_HELP_MESSAGE("Synopsis: COMMAND_NAME first|pad_r|pad_l\nDescription: Reduce the stream of matrix by summing them. Three strategy are available to deal with the different size of matrix:\n(1) Only sum the matrix with the same size as the first.\n(2) Apply right padding\n(3) Apply left padding.\nExample: 'reduce_add first'"),
    MAP_EW_MUL_HELP_MESSAGE("Synopsis: COMMAND_NAME k\nDescription: Multiply all the matrices element wise the number passed as parameter.\nExample: 'map_ew_mul 42'"),
    FILTER_WIDTH_HELP_MESSAGE("Synopsis: COMMAND_NAME width\nDescription: Keep only the matrix that the width specified as parameter.\nExample: 'filter_width 3'"),





    // TODO clean it
    CURRENT_STREAM_IS_NOT_PRESENT("There is currently no stream available."),
    REDUCER_NAME_FIRST("first"),
    REDUCER_NAME_LPAD("pad_l"),
    REDUCER_NAME_RPAD("pad_r"),
    TASK_DONE("Done."),
    BY_SUM("<+"),
    BY_MIN("<m"),
    BY_MAX("<M"),

    WELCOME_MESSAGE("\n╱▔╲╱▔▔▔▔╲╱▔╲\n▏ ╱  ▂ ▂ ╲ ▕\n╲╳▏ ┏▅┐ ┏▅┐╳╱ \n  ▏╭╰━╯╰━┻━━╮\n╱▔▏▕     ▕▔▔▔▏\n▏ ▏▕      ╲▂╱▏\n▏ ▏ ╲   ▕╲ ┃▕╱▏\n▏ ╲  ▔▔▔▔▔▔▔▔▔▏   Welcome to MuttLab!\n▏ ▏╲▂▂▂▂▂▂▂▂▂╱\n▏ ▏        ▕\n▏ ▏(textart4u.blogspot.com)\n\nMuttLab is an amazing new, matrix calculator.\nType 'help' if you need help.\n"),
    GOODBYE_MESSAGE("Thank you for using MuttLab\n\n╱▔╲╱▔▔▔▔╲╱▔╲\n▏ ╱  ▂ ▂ ╲ ▕\n╲╳▏ ┏▅┐ ┏▅┐╳╱\n  ▏╭╰━╯╰━┻━━╮\n╱▔▏▕     ▕▔▔▔▏\n▏ ▏▕      ╲▂╱▏\n▏ ▏ ╲   ▕╲ ┃▕╱▏\n▏ ╲  ▔▔▔▔▔▔▔▔▔▏     hee, hee, hee!\n▏ ▏╲▂▂▂▂▂▂▂▂▂╱\n▏ ▏        ▕\n▏ ▏        ▕\nThank you for using MuttLab.  Good bye.\n"),
    NOT_ENOUGH_ELEMENT_IN_QUEUE("There is not enough matrix in the queue."),
    INVALID_OPERATION_ERROR_MESSAGE("The operation is not valid."),
    FAIL_TO_WRITE_IN_FILE("Can't write in file."),
    BAD_NUMBER_OF_PARAMETERS("The number of parameters is not correct."),
    NOT_VALID_FLOAT("Float is not valid."),
    NOT_VALID_INT("Integer is not valid."),
    NOT_VALID_MATRIX("Matrix is not valid."),
    COMMAND_FAILED_CHECK_LOG("The command has failed. (Check the logs for further information)"),
    IO_EXCEPTION("IOException: Impossible to open, create, read or write into the file. (Check the logs for further information)"),
    UNSUPPORTED_COMMAND_PARAMETER("Command's parameter is not supported. (Use the help command for further information)"),
    UNSUPPORTED_OPERATION("Operation is not supported."),

    FAIL_TO_FLUSH_COMMAND_OUTPUT("Fail to flush command output."),
    COMMAND_PROMPT_HELP_MESSAGE("Write the command, press enter and follow it execution in the other tabs."),
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
