package muttlab.exceptions;

public class MatrixElementDoesNotExist extends Exception {

    Integer row;
    Integer column;
    public MatrixElementDoesNotExist(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }
    @Override
    public String getMessage() {
        return "The matrix element (row:" + row + ", column:" + column + ") does not exist.";
    }
}
