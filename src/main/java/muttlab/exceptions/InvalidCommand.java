package muttlab.exceptions;

public class InvalidCommand extends Exception {
    @Override
    public String getMessage() { return "Command is not valid."; }
}
