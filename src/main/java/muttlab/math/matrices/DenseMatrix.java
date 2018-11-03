package muttlab.math.matrices;

import muttlab.MuttLab;
import muttlab.exceptions.InvalidMatrixSize;
import muttlab.exceptions.MatrixElementDoesNotExist;
import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabKeys;

import java.util.Arrays;

public class DenseMatrix extends Matrix {

    private Float[][] data = null;
    private Integer height = 0;
    private Integer width = 0;

    /**
     * Default constructor.
     */
    public DenseMatrix() {}

    /**
     * Constructor.
     * @param height : The matrix's number of rows.
     * @param width : The matrix's number of columns.
     * @throws InvalidMatrixSize if the matrix size is not valid.
     */
    public DenseMatrix(Integer height, Integer width) throws InvalidMatrixSize {
        if (height < 0 || width < 0)
            throw new InvalidMatrixSize();
        this.width = width;
        this.height = height;
        data = new Float[height][width];
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
    public Integer getWidth() { return width; }

    /**
     * Getter method.
     * @return the number of rows.
     */
    public Integer getHeight() { return height; }

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
     * Load the matrix's data from a csv row.
     * @param s: one row of the csv file.
     * @return the matrix's data.
     * @throws Exception if the row is malformed.
     */
    private Float[][] dataFromCSV(String s) throws Exception {
        // Parse CSV row.
        Float[] row = Arrays.stream(s.split(","))
            .map(String::trim)
            .map(str -> {
                try {
                    return Float.valueOf(str);
                } catch (Exception e) {
                    return null;
                }
            })
            .toArray(Float[]::new);
        // Check that the matrix is valid.
        for (Float elem: row) {
            if (elem == null) {
                throw new Exception("Invalid string format");
            }
        }
        // Return the matrix's data.
        Float[][] data = new Float[1][];
        data[0] = row;
        return data;
    }

    /**
     * Return the sum of all the elements of the matrix.
     * @return the sum.
     */
    public Float sum() {
        Float sum = 0F;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                sum += data[i][j];
            }
        }
        return sum;
    }

    /**
     * Return the smallest element of the matrix.
     * @return the smallest element.
     */
    public Float min() {
        Float min = Float.MAX_VALUE;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                min = Float.min(min, data[i][j]);
            }
        }
        return min;
    }

    /**
     * Return the biggest element of the matrix.
     * @return the biggest element.
     */
    public Float max() {
        Float max = Float.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                max = Float.max(max, data[i][j]);
            }
        }
        return max;
    }

    /**
     * Load this from array of float.
     * @param d : The array of float.
     * @return this.
     */
    private Matrix from(Float[][] d) {
        data = d;
        height = data.length;
        width = data[0].length;
        return this;
    }

    /**
     * Load the matrix from all the different formats supported.
     * @param s: the string representation of the matrix.
     * @return the data of the matrix.
     * @throws Exception if the loading failed.
     */
    private Float[][] fromFormats(String s) throws Exception {
        try {
            return dataFrom(s);
        } catch (Exception e) {
            return dataFromCSV(s);
        }
    }

    /**
     * Load this from string.
     * @param s : The string containing the matrix.
     * @return this.
     * @throws Exception if string is malformed.
     */
    public Matrix from(String s) throws Exception {
        try {
            data = fromFormats(s);
            height = data.length;
            width = data[0].length;
        } catch (UserException e) {
            throw e;
        } catch (Exception e) {
            throw new UserException(MuttLabKeys.NOT_VALID_MATRIX.toString());
        }
        return this;
    }

    /**
     * Add columns on the left side of the matrix.
     * @param n: the number of columns to add.
     * @param value: the value used to fill the columns.
     * @return true if the columns have been correctly added false otherwise.
     */
    public boolean addColumnsOnTheLeft(int n, float value) {
        if (n < 0)
            return false;
        this.width += n;
        for (int i = 0; i < data.length; i++) {
            Float[] newC = new Float[data[i].length + n];
            for (int j = 0; j < n; j++) {
                newC[j] = value;
            }
            System.arraycopy(data[i], 0, newC, n, data[i].length);
            data[i] = newC;
        }
        return true;
    }

    /**
     * Add columns on the right side of the matrix.
     * @param n: the number of columns to add.
     * @param value: the value used to fill the columns.
     * @return true if the columns have been correctly added false otherwise.
     */
    public boolean addColumnsOnTheRight(int n, float value) {
        if (n < 0)
            return false;
        this.width += n;
        for (int i = 0; i < data.length; i++) {
            Float[] newC = new Float[data[i].length + n];
            System.arraycopy(data[i], 0, newC, 0, data[i].length);
            for (int j = data[i].length; j < data[i].length + n; j++) {
                newC[j] = value;
            }
            data[i] = newC;
        }
        return true;
    }

    /**
     * Convert this to string.
     * @return the string representation of this.
     */
    public String asString() {
        StringBuilder result = new StringBuilder();
        try {
            result.append("[");
            for (Integer row = 0; row < getHeight(); row++) {
                if (row != 0) result.append(";");
                for (Integer column = 0; column < getWidth(); column++) {
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
        for (Integer column = 0; column < matrix.getWidth(); column++) {
            for (Integer row = 0; row < matrix.getHeight(); row++) {
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
        for (Integer column = 0; column < matrix.getWidth(); column++) {
            for (Integer row = 0; row < matrix.getHeight(); row++) {
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
        DenseMatrix result = newEmptyMatrix(getHeight(), matrix.getWidth());
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < matrix.getWidth(); c++) {
                Float value = 0.0F;
                for (int k = 0; k < getWidth(); k++)
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
        for (Integer column = 0; column < getWidth(); column++) {
            for (Integer row = 0; row < getHeight(); row++) {
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
        for (Integer column = 0; column < matrix.getWidth(); column++) {
            for (Integer row = 0; row < matrix.getHeight(); row++) {
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
