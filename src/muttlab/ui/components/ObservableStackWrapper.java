package muttlab.ui.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.EmptyStackException;

public class ObservableStackWrapper<T> {

    private ObservableList<T> list;

    /**
     * Create the observable stack.
     */
    public ObservableStackWrapper() {
        list = FXCollections.observableArrayList();
    }

    /**
     * Getter.
     * @return the observable list.
     */
    public ObservableList<T> get() {
        return list;
    }

    /**
     * Return the number of element in the stack.
     * @return the stack' size.
     */
    public int size() {
        return list.size();
    }

    /**
     * Check if the stack is empty.
     * @return true if the stack is empty and false otherwise.
     */
    public boolean empty() {
        return list.size() == 0;
    }

    /**
     * Return the top element of the stack without consuming it.
     * @return the top element.
     */
    public T peek() {
        if (empty())
            throw new EmptyStackException();
        return list.get(0);
    }

    /**
     * Return the element of the stack corresponding to the index without consuming it.
     * @return the element corresponding to the index.
     */
    public T peek(int index) {
        if (empty())
            throw new EmptyStackException();
        return list.get(index);
    }

    /**
     * Return the top element of the stack and consuming it.
     * @return the top element.
     */
    public T pop() {
        if (empty())
            throw new EmptyStackException();
        T e = list.get(0);
        list.remove(0);
        return e;
    }

    /**
     * Add an element on the top of stack.
     * @param item: The element to add in the stack.
     * @return the item argument.
     */
    public T push(T item) {
        list.add(0, item);
        return item;
    }

    /**
     * Search for the object in the stack.
     * @param o: The object researched.
     * @return the index of 'o' in the stack.
     */
    public int search(Object o) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(o))
                return i;
        }
        return -1;
    }
}