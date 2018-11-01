package muttlab.math;

/**
 * Abstract class representing an element e.g. matrix, vector, scalar.
 */
public abstract class Element {
    /**
     * Load element from String.
     * @param s : The string containing the element.
     * @return The loaded element.
     * @throws Exception if an error occurred.
     */
    public abstract Element from(String s) throws Exception;

    /**
     * Convert Element to string.
     * @return The string representation of this.
     */
    public abstract String asString();

    /**
     * Multiply this with another element.
     * @param element : The element that will be multiply with this.
     * @return the result of the multiplication.
     * @throws Exception if an error occurred.
     */
    public abstract Element mul(Element element) throws Exception;

    /**
     * Point wise multiplication between this and the element passed as parameter.
     * @param element : The element that will be point wise multiply with this.
     * @return the result of the point wise multiplication.
     * @throws Exception if an error occurred.
     */
    public abstract Element pointWiseMul(Element element) throws Exception;

    /**
     * Add this with the element passed as parameter.
     * @param element : The element that will be add with this.
     * @return the result of the addition.
     * @throws Exception if an error occurred.
     */
    public abstract Element add(Element element) throws Exception;

    /**
     * Subtract this with the element passed as parameter.
     * @param element : The element that will be subtract with this.
     * @return the result of the subtraction.
     * @throws Exception if an error occurred.
     */
    public abstract Element sub(Element element) throws Exception;

    /**
     * Create a copy of this.
     * @return the copy.
     */
    public abstract Element copy();
}
