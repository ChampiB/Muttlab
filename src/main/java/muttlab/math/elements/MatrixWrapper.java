package muttlab.math.elements;

import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabKeys;
import muttlab.math.Element;
import muttlab.math.matrices.DenseMatrix;
import muttlab.math.matrices.Matrix;

public class MatrixWrapper extends Element {

    private Matrix m;

    /**
     * Default constructor.
     */
    public MatrixWrapper() {
        m = new DenseMatrix();
    }

    /**
     * Constructor.
     * @param matrix : The matrix to wrap.
     */
    public MatrixWrapper(Matrix matrix) {
        m = matrix;
    }

    /**
     * Convert Element to string.
     * @return The string representation of this.
     */
    public String asString() {
        return m.asString();
    }

    /**
     * Getter method.
     * @return the wrapped matrix.
     */
    public Matrix getMatrix() {
        return m;
    }

    /**
     * Load matrix from String.
     * @param s : The string containing the element.
     * @return The loaded matrix.
     * @throws Exception if an error occurred.
     */
    public Element from(String s) throws Exception {
        m.from(s);
        return this;
    }

    /**
     * Point wise multiplication between this and the element passed as parameter.
     * @param element : The element that will be point wise multiply with this.
     * @return the result of the point wise multiplication.
     * @throws Exception if an error occurred.
     */
    public Element pointWiseMul(Element element) throws Exception {
        if (element instanceof MatrixWrapper) {
            m.pointWiseMul(((MatrixWrapper) element).m);
        } else {
            throw new UserException(MuttLabKeys.UNSUPPORTED_OPERATION.toString());
        }
        return this;
    }

    /**
     * Multiply this with another element.
     * @param element : The element that will be multiply with this.
     * @return the result of the multiplication.
     * @throws Exception if an error occurred.
     */
    public Element mul(Element element) throws Exception {
        if (element instanceof MatrixWrapper) {
            m.mul(((MatrixWrapper) element).getMatrix());
        } else if (element instanceof ScalarWrapper) {
            m.mul(((ScalarWrapper) element).getScalar());
        } else {
            throw new UserException(MuttLabKeys.UNSUPPORTED_OPERATION.toString());
        }
        return this;
    }

    /**
     * Subtract this with the element passed as parameter.
     * @param element : The element that will be subtract with this.
     * @return the result of the subtraction.
     * @throws Exception if an error occurred.
     */
    public Element sub(Element element) throws Exception {
        if (element instanceof MatrixWrapper) {
            m.sub(((MatrixWrapper) element).getMatrix());
        } else {
            throw new UserException(MuttLabKeys.UNSUPPORTED_OPERATION.toString());
        }
        return this;
    }

    /**
     * Add this with the element passed as parameter.
     * @param element : The element that will be add with this.
     * @return the result of the addition.
     * @throws Exception if an error occurred.
     */
    public Element add(Element element) throws Exception {
        if (element instanceof MatrixWrapper) {
            m.add(((MatrixWrapper) element).getMatrix());
        } else {
            throw new UserException(MuttLabKeys.UNSUPPORTED_OPERATION.toString());
        }
        return this;
    }

    /**
     * Create a copy of this.
     * @return the copy.
     */
    public Element copy() {
        return new MatrixWrapper(m.copy());
    }
}
