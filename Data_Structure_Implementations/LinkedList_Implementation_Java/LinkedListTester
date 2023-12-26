import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTester {

    private LinkedList<Integer> linkedList;

    @BeforeEach
    void setUp() {
        linkedList = new LinkedList<>();
    }

    @Test
    void testIsEmptyOnNewList() {
        assertEquals(0, linkedList.size());
    }

    @Test
    void testInsertAtHead() {
        linkedList.insertAtHead(1);
        linkedList.insertAtHead(2);
        assertEquals(2, linkedList.size());
        assertEquals(2, linkedList.get(0));
    }

    @Test
    void testInsertAtTail() {
        linkedList.insertAtTail(1);
        linkedList.insertAtTail(2);
        assertEquals(2, linkedList.size());
        assertEquals(1, linkedList.get(0));
        assertEquals(2, linkedList.get(1));
    }

    @Test
    void testRemoveAtHead() {
        linkedList.insertAtHead(1);
        linkedList.insertAtHead(2);
        assertEquals(2, linkedList.removeAtHead());
        assertEquals(1, linkedList.size());
    }

    @Test
    void testRemoveAtTail() {
        linkedList.insertAtTail(1);
        linkedList.insertAtTail(2);
        assertEquals(2, linkedList.removeAtTail());
        assertEquals(1, linkedList.size());
    }

    @Test
    void testInsertAt() {
        linkedList.insertAtHead(1);
        linkedList.insertAtHead(3);
        linkedList.insertAt(1, 2);
        assertEquals(3, linkedList.size());
        assertEquals(2, linkedList.get(1));
    }

    @Test
    void testFind() {
        linkedList.insertAtTail(1);
        linkedList.insertAtTail(2);
        linkedList.insertAtTail(3);
        assertEquals(1, linkedList.find(2));
        assertEquals(-1, linkedList.find(4));
    }

    @Test
    void testGetInvalidIndex() {
        linkedList.insertAtTail(1);
        assertThrows(IndexOutOfBoundsException.class, () -> linkedList.get(2));
    }

    @Test
    void testClear() {
        linkedList.insertAtTail(1);
        linkedList.clear();
        assertEquals(0, linkedList.size());
    }

    @Test
    void testIterator() {
        linkedList.insertAtTail(1);
        linkedList.insertAtTail(2);
        ListIterator<Integer> iterator = linkedList.front();
        assertTrue(iterator.value().equals(1));
        iterator.moveForward();
        assertTrue(iterator.value().equals(2));
        assertFalse(iterator.isPastEnd());
    }
}
