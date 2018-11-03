package muttlab.exceptions;

/**
 * This error is throws when the exception must be display to the user.
 */
public class UserException extends Exception {

    private String message;

    /**
     * Constructor.
     * @param msg: the key corresponding to the message to display.
     */
    public UserException(String msg) {
        message = msg;
    }

    @Override
    public String getMessage() { return message; }
}