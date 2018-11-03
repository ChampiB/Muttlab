package muttlab.math.matrices;

import muttlab.exceptions.InvalidMatrixSize;
import muttlab.exceptions.MatrixElementDoesNotExist;

public abstract class Matrix {
    /**
     * Create an empty matrix.
     * @param numberOfRows : The number of row of the new matrix.
     * @param numberOfColumns : The number of column of the new matrix.
     * @return The matrix build.
     * @throws InvalidMatrixSize if the size is not valid.
     */
    public abstract Matrix newEmptyMatrix(Integer numberOfRows, Integer numberOfColumns) throws InvalidMatrixSize;

    /**
     * Add columns on the left side of the matrix.
     * @param n: the number of columns to add.
     * @param value: the value used to fill the columns.
     * @return true if the columns have been correctly added false otherwise.
     */
    public abstract boolean addColumnsOnTheLeft(int n, float value);

    /**
     * Add columns on the right side of the matrix.
     * @param n: the number of columns to add.
     * @param value: the value used to fill the columns.
     * @return true if the columns have been correctly added false otherwise.
     */
    public abstract boolean addColumnsOnTheRight(int n, float value);

    /**
     * Setter method.
     * @param row : The row that need to be modify.
     * @param column : The column that need to be modify.
     * @param value : The new value.
     * @throws MatrixElementDoesNotExist if the position is not valid.
     */
    public abstract void set(Integer row, Integer column, Float value) throws MatrixElementDoesNotExist;

    /**
     * Getter method.
     * @param row : The row.
     * @param column : The column.
     * @return the element of this that correspond to position passed as parameters.
     * @throws MatrixElementDoesNotExist if the position is not valid.
     */
    public abstract Float get(Integer row, Integer column) throws MatrixElementDoesNotExist;

    /**
     * Getter method.
     * @return the number of columns.
     */
    public abstract Integer getWidth();

    /**
     * Getter method.
     * @return the number of rows.
     */
    public abstract Integer getHeight();

    /**
     * Create a copy of the this.
     * @return the copy.
     */
    public abstract Matrix copy();

    /**
     * Return the sum of all the elements of the matrix.
     * @return the sum.
     */
    public abstract Float sum();

    /**
     * Return the smallest element of the matrix.
     * @return the smallest element.
     */
    public abstract Float min();

    /**
     * Return the biggest element of the matrix.
     * @return the biggest element.
     */
    public abstract Float max();

    /**
     * Load this from string.
     * @param s : The string containing the matrix.
     * @return this.
     * @throws Exception if string is malformed.
     */
    public abstract Matrix from(String s) throws Exception;

    /**
     * Convert this to string.
     * @return the string representation of this.
     */
    public abstract String asString();

    /**
     * Multiply this with a scalar.
     * @param scalar : The scalar.
     * @throws Exception if an error occurred.
     */
    public abstract void mul(Float scalar) throws Exception;

    /**
     * Element wise multiplication between this and another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public abstract void pointWiseMul(Matrix matrix) throws Exception;

    /**
     * Add this with another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public abstract void add(Matrix matrix) throws Exception;

    /**
     * Multiply this with another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public abstract void mul(Matrix matrix) throws Exception;

    /**
     * Subtract this with another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public abstract void sub(Matrix matrix) throws Exception;

    /**
     * Check if this has the same size as another matrix.
     * @param matrix : The other matrix.
     * @return true if the matrix passed as parameter have the same size as this.
     */
    public boolean hasSameSizeAs(Matrix matrix) {
        return getWidth().equals(matrix.getWidth())
                && getHeight().equals(matrix.getHeight());
    }

    /**
     * Check if the size of this is compatible with the size of another matrix.
     * @param matrix : The other matrix.
     * @return true if the size of this is compatible with the size of another matrix and false otherwise.
     */
    public boolean hasCompatibleSizeWith(Matrix matrix) {
        return getWidth().equals(matrix.getHeight());
    }
}
