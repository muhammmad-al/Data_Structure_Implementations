import java.util.NoSuchElementException;

/**
 *
 * A custom built linked list
 * T here is the type the list stores
 */
public class LinkedList<T> implements List<T> {
    private ListNode<T> head, tail;
    private int size;

    public LinkedList() {
        head = new ListNode<T>(null);
        tail = new ListNode<T>(null);
        head.next = tail;
        head.prev = null;
        tail.next = null;
        tail.prev = head;
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    /**
     * Clears out the entire list
     */
    public void clear() {
        head.next = tail;
        head.prev = null;
        tail.next = null;
        tail.prev = head;
        this.size = 0;
    }

    /**
     * Inserts new data at the end of the list (i.e., just before the dummy tail node)
     * @param data
     */
    public void insertAtTail(T data) {
        ListNode<T> insertedTailNode = new ListNode<T>(data);
        insertedTailNode.next = tail;
        insertedTailNode.prev = tail.prev;
        tail.prev.next = insertedTailNode;
        tail.prev = insertedTailNode;
        this.size++;
    }

    /**
     * Inserts data at the front of the list (i.e., just after the dummy head node
     * @param data
     */
    public void insertAtHead(T data) {
        ListNode<T> insertedHeadNode = new ListNode<T>(data);
        insertedHeadNode.prev = head;
        insertedHeadNode.next = head.next;
        head.next.prev = insertedHeadNode;
        head.next = insertedHeadNode;
        this.size++;
    }

    /**
     * Inserts node such that index becomes the position of the newly inserted data
     * @param data
     * @param index
     */
    public void insertAt(int index, T data) {
        ListNode<T> indexValue = head.next;
        ListNode<T> insertedNode = new ListNode<T>(data);
        int count = 1;
        while (indexValue.next != null && count <= index) {
            indexValue = indexValue.next;
            count++;
        }
        insertedNode.next = indexValue;
        insertedNode.prev = indexValue.prev;
        indexValue.prev.next = insertedNode;
        indexValue.prev = insertedNode;
        this.size++;
    }

    /**
     * Inserts data after the node pointed to by iterator
     */
    public void insert(ListIterator<T> it, T data) {
        if(it.isPastEnd()) {
            return;
        }
        ListNode<T> insertedValue = new ListNode<T>(data);
        ListNode<T> prev = it.curNode;
        ListNode<T> next = it.curNode.next;
        prev.next = insertedValue;
        insertedValue.prev = prev;
        insertedValue.next = next;
        next.prev = insertedValue;
        this.size++;
    }

    public T removeAtTail(){
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }

        ListNode<T> removeNode = tail.prev;
        removeNode.prev.next = tail;
        tail.prev = removeNode.prev;
        size--;

        return removeNode.getData();
    }

    public T removeAtHead(){
        T toBeReturned = head.next.getData();
        head.next = head.next.next;
        head.next.prev = head;
        this.size--;
        return toBeReturned;
    }

    /**
     * Remove based on Iterator position
     * Sets the iterator to the node after the one removed
     */
    public T remove(ListIterator<T> it) {
        if(it.isPastEnd()) {
            return null;
        }
        it.moveForward();
        ListNode<T> prev = it.curNode.prev.prev;
        ListNode<T> next = it.curNode;
        T removedNode = it.curNode.getData();
        prev.next = next;
        next.prev = prev;
        size--;
        return removedNode;
    }

    /**
     * Returns index of first occurrence of the data in the list, or -1 if not present
     * @param data
     * @return
     */
    public int find(T data) {
        ListNode<T> test = head.next;
        int count = 0;
        while (test.next != null) {
            if(test.getData().equals(data)) {
                return count;
            }
            count++;
            test = test.next;
        }
        return -1;
    }

    /**
     * Returns the data at the given index, throws an error if anything goes wrong (index out of bounds, empty list, etc.)
     * @param index
     * @return
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " , Size: " + size);
        }

        ListNode<T> currentNode = head.next;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode.getData();
    }

    /**
     * Returns the list as space separated values
     */
    public String toString() {
        String toRet = "[";

        ListNode<T> curNode = head.next;
        while(curNode != tail) {
            toRet += curNode.getData() + ", ";
            curNode = curNode.next;
        }

        return toRet + "]";
    }

    /* Return iterators at front and end of list */
    public ListIterator<T> front(){
        ListIterator<T> frontIterator = new ListIterator<T>(head.next);
        return frontIterator;
    }

    public ListIterator<T> back(){
        ListIterator<T> backIterator = new ListIterator<T>(tail.prev);
        return backIterator;
    }
}
