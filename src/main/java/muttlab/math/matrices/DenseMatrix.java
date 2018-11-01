package muttlab.math.matrices;

import muttlab.exceptions.InvalidMatrixSize;
import muttlab.exceptions.MatrixElementDoesNotExist;

import java.util.Arrays;

public class DenseMatrix extends Matrix {

    private Float[][] data = null;
    private Integer numberOfRows = 0;
    private Integer numberOfColumns = 0;

    /**
     * Default constructor.
     */
    public DenseMatrix() {}

    /**
     * Constructor.
     * @param numberOfRows : The matrix's number of rows.
     * @param numberOfColumns : The matrix's number of columns.
     * @throws InvalidMatrixSize if the matrix size is not valid.
     */
    public DenseMatrix(Integer numberOfRows, Integer numberOfColumns) throws InvalidMatrixSize {
        if (numberOfRows < 0 || numberOfColumns < 0)
            throw new InvalidMatrixSize();
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        data = new Float[numberOfRows][numberOfColumns];
        for (int i = 0; i < data.length; i++)
            Arrays.fill(data[i], 0F);
    }

    /**
     * Getter method.
     * @return the internal buffer.
     */
    private Float[][] getData() { return data; }

    /**
     * Check if the position passed as parameter is valid.
     * @param row : The row.
     * @param column : The column.
     * @return true if the position passed as parameter is valid and false otherwise.
     */
    private boolean isValid(Integer row, Integer column) {
        return data != null && data.length != 0 && data[0].length != 0 &&
               row >= 0 && row < data.length &&
               column >= 0 && column < data[0].length;
    }

    /**
     * Setter method.
     * @param row : The row that need to be modify.
     * @param column : The column that need to be modify.
     * @param value : The new value.
     * @throws MatrixElementDoesNotExist if the position is not valid.
     */
    public void set(Integer row, Integer column, Float value) throws MatrixElementDoesNotExist {
        if (!isValid(row, column)) throw new MatrixElementDoesNotExist(row, column);
        data[row][column] = value;
    }

    /**
     * Getter method.
     * @param row : The row.
     * @param column : The column.
     * @return the element of this that correspond to position passed as parameters.
     * @throws MatrixElementDoesNotExist if the position is not valid.
     */
    public Float get(Integer row, Integer column) throws MatrixElementDoesNotExist {
        if (!isValid(row, column)) throw new MatrixElementDoesNotExist(row, column);
        return data[row][column];
    }

    /**
     * Create an empty matrix.
     * @param numberOfRows : The number of row of the new matrix.
     * @param numberOfColumns : The number of column of the new matrix.
     * @return The matrix build.
     * @throws InvalidMatrixSize if the size is not valid.
     */
    public DenseMatrix newEmptyMatrix(Integer numberOfRows, Integer numberOfColumns) throws InvalidMatrixSize {
        return new DenseMatrix(numberOfRows, numberOfColumns);
    }

    /**
     * Getter method.
     * @return the number of columns.
     */
    public Integer getNumberOfColumns() { return numberOfColumns; }

    /**
     * Getter method.
     * @return the number of rows.
     */
    public Integer getNumberOfRows() { return numberOfRows; }

    /**
     * Load this.data from string.
     * @param s : The string containing the data.
     * @return the data.
     * @throws Exception if string is malformed.
     */
    private Float[][] dataFrom(String s) throws Exception {
        if (s.charAt(0) != '[')
            throw new Exception("Invalid string format.");
        s = s.substring(1);
        if (s.charAt(s.length() - 1) == ']')
            s = s.substring(0, s.length() - 1);
        String[] rows = s.split(";");
        if (rows.length == 0)
            throw new Exception("Invalid string format.");
        Float[][] matrix = new Float[rows.length][];
        int rowsLength = -1;
        for (int i = 0; i < rows.length; i++) {
            String[] columnsOfRowI = rows[i].trim().replaceAll(" +", " ").split(" ");
            if (columnsOfRowI.length == 0)
                throw new Exception("Invalid string format.");
            matrix[i] = Arrays.stream(columnsOfRowI).map(Float::valueOf).toArray(Float[]::new);
            if (i == 0) rowsLength = matrix[i].length;
            if (rowsLength != matrix[i].length)
                throw new Exception("Invalid string format.");
        }
        return matrix;
    }

    /**
     * Load this from array of float.
     * @param d : The array of float.
     * @return this.
     */
    private Matrix from(Float[][] d) {
        data = d;
        numberOfRows = data.length;
        numberOfColumns = data[0].length;
        return this;
    }

    /**
     * Load this from string.
     * @param s : The string containing the matrix.
     * @return this.
     * @throws Exception if string is malformed.
     */
    public Matrix from(String s) throws Exception {
        data = dataFrom(s);
        numberOfRows = data.length;
        numberOfColumns = data[0].length;
        return this;
    }

    /**
     * Convert this to string.
     * @return the string representation of this.
     */
    public String asString() {
        StringBuilder result = new StringBuilder();
        try {
            result.append("[");
            for (Integer row = 0; row < getNumberOfRows(); row++) {
                if (row != 0) result.append(";");
                for (Integer column = 0; column < getNumberOfColumns(); column++) {
                    result.append(" ");
                    result.append(get(row, column));
                }
            }
            result.append(" ]");
        } catch (Exception e) {
            return "";
        }
        return result.toString();
    }

    /**
     * Element wise multiplication between this and another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public void pointWiseMul(Matrix matrix) throws Exception {
        if (!hasSameSizeAs(matrix)) throw new InvalidMatrixSize();
        for (Integer column = 0; column < matrix.getNumberOfColumns(); column++) {
            for (Integer row = 0; row < matrix.getNumberOfRows(); row++) {
                set(row, column, get(row, column) * matrix.get(row, column));
            }
        }
    }

    /**
     * Add this with another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public void add(Matrix matrix) throws Exception {
        if (!hasSameSizeAs(matrix)) throw new InvalidMatrixSize();
        for (Integer column = 0; column < matrix.getNumberOfColumns(); column++) {
            for (Integer row = 0; row < matrix.getNumberOfRows(); row++) {
                set(row, column, get(row, column) + matrix.get(row, column));
            }
        }
    }

    /**
     * Multiply this with another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public void mul(Matrix matrix) throws Exception {
        if (!hasCompatibleSizeWith(matrix)) throw new InvalidMatrixSize();
        DenseMatrix result = newEmptyMatrix(getNumberOfRows(), matrix.getNumberOfColumns());
        for (int r = 0; r < getNumberOfRows(); r++) {
            for (int c = 0; c < matrix.getNumberOfColumns(); c++) {
                Float value = 0.0F;
                for (int k = 0; k < getNumberOfColumns(); k++)
                    value += get(r, k) * matrix.get(k, c);
                result.set(r, c, value);
            }
        }
        from(result.getData());
    }

    /**
     * Multiply this with a scalar.
     * @param scalar : The scalar.
     * @throws Exception if an error occurred.
     */
    public void mul(Float scalar) throws Exception {
        for (Integer column = 0; column < getNumberOfColumns(); column++) {
            for (Integer row = 0; row < getNumberOfRows(); row++) {
                set(row, column, get(row, column) * scalar);
            }
        }
    }

    /**
     * Subtract this with another matrix.
     * @param matrix : The other matrix.
     * @throws Exception if an error occurred.
     */
    public void sub(Matrix matrix) throws Exception {
        if (!hasSameSizeAs(matrix)) throw new InvalidMatrixSize();
        for (Integer column = 0; column < matrix.getNumberOfColumns(); column++) {
            for (Integer row = 0; row < matrix.getNumberOfRows(); row++) {
                set(row, column, get(row, column) - matrix.get(row, column));
            }
        }
    }

    /**
     * Create a copy of the this.
     * @return the copy.
     */
    public Matrix copy() {
        Float[][] data_copy = new Float[data.length][];
        for (int i = 0; i < data.length; i++) {
            data_copy[i] = Arrays.copyOf(data[i], data[i].length);
        }
        return new DenseMatrix().from(data_copy);
    }
}
