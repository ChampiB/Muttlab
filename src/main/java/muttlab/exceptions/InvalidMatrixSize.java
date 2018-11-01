package muttlab.exceptions;

public class InvalidMatrixSize extends Exception {
    @Override
    public String getMessage() { return "Invalid matrix size."; }
}
