package muttlab.math.elements;

import muttlab.exceptions.UserException;
import muttlab.languages.MuttLabKeys;
import muttlab.math.Element;

public class ScalarWrapper extends Element {

    private Float scalar;

    /**
     * Default constructor.
     */
    public ScalarWrapper() {
        scalar = 0.0F;
    }

    /**
     * Constructor.
     * @param scalar : The scalar to wrap.
     */
    public ScalarWrapper(Float scalar) {
        this.scalar = scalar;
    }

    /**
     * Getter method.
     * @return the scalar.
     */
    public Float getScalar() {
        return scalar;
    }

    /**
     * Load element from String.
     * @param string : The string containing the element.
     * @return The loaded element.
     * @throws Exception if an error occurred.
     */
    public Element from(String string) throws Exception {
        try {
            scalar = Float.valueOf(string);
        } catch (Exception e) {
            throw new UserException(MuttLabKeys.NOT_VALID_FLOAT.toString());
        }
        return this;
    }

    /**
     * Convert Element to string.
     * @return The string representation of this.
     */
    public String asString() {
        return scalar.toString();
    }

    /**
     * Multiply this with another element.
     * @param element : The element that will be multiply with this.
     * @return the result of the multiplication.
     * @throws Exception if an error occurred.
     */
    public Element mul(Element element) throws Exception {
        if (element instanceof ScalarWrapper) {
            scalar *= ((ScalarWrapper) element).getScalar();
        } else if (element instanceof MatrixWrapper) {
            MatrixWrapper mw = ((MatrixWrapper) element);
            mw.getMatrix().mul(scalar);
            return mw;
        } else {
            throw new UserException(MuttLabKeys.UNSUPPORTED_OPERATION.toString());
        }
        return this;
    }

    /**
     * Point wise multiplication between this and the element passed as parameter.
     * @param element : The element that will be point wise multiply with this.
     * @return the result of the point wise multiplication.
     * @throws Exception if an error occurred.
     */
    public Element pointWiseMul(Element element) throws Exception {
        if (element instanceof ScalarWrapper) {
            scalar *= ((ScalarWrapper) element).getScalar();
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
        if (element instanceof ScalarWrapper) {
            scalar += ((ScalarWrapper) element).getScalar();
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
        if (element instanceof ScalarWrapper) {
            scalar -= ((ScalarWrapper) element).getScalar();
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
        return new ScalarWrapper(scalar);
    }
}
