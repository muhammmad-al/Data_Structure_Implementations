package vector;

import java.util.Arrays;

/**
 * A custom implementation of a dynamic array, similar to an ArrayList.
 * Resizes automatically as elements are added or removed.
 *
 * @param <T> the type of elements in this vector
 */
public class Vector<T> implements List<T> {

    private T[] itemArray; //Internal array to store elements
    private int size = 0; //Number of elements currently in the array
    private static final int INITIAL_CAPACITY = 100; //Initial capacity of the vector

    /**
     * Constructs an empty vector with the default initial capacity
     */
    public Vector() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs an empty vector with the specified initial capacity
     *
     * @param capacity the initial capacity of the vector
     */
    @SuppressWarnings("unchecked")
    public Vector(int capacity) {
        this.itemArray = (T[]) new Object[capacity];
        this.size = 0;
    }

    /**
     * Returns the current capacity of the vector (the length of the internal array)
     *
     * @return the current capacity
     */
    public int capacity() {
        return this.itemArray.length;
    }

    /**
     * Resizes the internal array to the specified new capacity
     *
     * @param newCapacity the new capacity
     */
    public void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];

        for(int i = 0; i < itemArray.length; i++) {
            newArray[i] = itemArray[i];

        }
        itemArray = newArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public void insertAtTail(T item) {
        if (size == itemArray.length) {
            resize(itemArray.length * 2);
            itemArray[size] = item;
        }
        else {
            itemArray[size] = item;
        }
        size++;
    }

    @Override
    public void insertAtHead(T item) {
        T[] newArray = (T[]) new Object[itemArray.length + 1];
        newArray[0] = item;
        for (int i = 1; i < itemArray.length; i++) {
            newArray[i] = itemArray[i-1];
        }
        itemArray = newArray;
        size++;
    }

    @Override
    public void insertAt(int index, T item) {
        T[] newArray = (T[]) new Object[itemArray.length + 1];

        for (int i = 0; i < itemArray.length; i++) {
            if (i < index)
                newArray[i] = itemArray[i];
            else if (i == index)
                newArray[i] = item;
            else
                newArray[i] = itemArray[i - 1];
        }

        itemArray = newArray;
        size++;
    }

    @Override
    public T removeAtTail() {
        T removedItem = itemArray[size - 1];
        T[] newArray = (T[]) new Object[itemArray.length - 1];
        for (int i = 0; i < itemArray.length - 1; i++) {
            newArray[i] = itemArray[i];
        }
        itemArray = newArray;
        size--;
        return removedItem;
    }

    @Override
    public T removeAtHead() {
        T removedItem = itemArray[0];
        T[] newArray = (T[]) new Object[itemArray.length - 1];
        for (int i = 0; i < itemArray.length - 1; i++) {
            newArray[i] = itemArray[i + 1];
        }
        itemArray = newArray;
        size--;
        return removedItem;
    }

    @Override
    public int find(T item) {
        for (int i = 0; i < size; i++) {
            if (itemArray[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return itemArray[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(this.itemArray, 0, this.size)); // prints from 0 to size-1
    }

}