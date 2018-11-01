package muttlab.exceptions;

public class UnknownCommand extends Exception {
    @Override
    public String getMessage() { return "Command is unknown."; }
}
