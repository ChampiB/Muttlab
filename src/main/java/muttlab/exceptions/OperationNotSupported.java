package muttlab.exceptions;

public class OperationNotSupported extends Exception {
    @Override
    public String getMessage() { return "Operation is not valid."; }
}
