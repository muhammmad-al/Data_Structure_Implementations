package vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for the Vector implementation
 */
public class VectorTest {

    private Vector<Integer> vector;

    /**
     * Initializes a new Vector for each class
     */
    @BeforeEach
    void setUp() {
        vector = new Vector<>();
    }

    @Test
    void testInsertAtTailAndSize() {
        vector.insertAtTail(1);
        vector.insertAtTail(2);
        assertEquals(2, vector.size());
    }

    @Test
    void testClear() {
        vector.insertAtTail(1);
        vector.clear();
        assertEquals(0, vector.size());
    }

    @Test
    void testInsertAtHead() {
        vector.insertAtHead(1);
        vector.insertAtHead(2);
        assertEquals(2, vector.size());
        assertEquals(2, vector.get(0));
    }

    @Test
    void testInsertAt() {
        vector.insertAtTail(1);
        vector.insertAtTail(3);
        vector.insertAt(1, 2);
        assertEquals(3, vector.size());
        assertEquals(2, vector.get(1));
    }

    @Test
    void testRemoveAtTail() {
        vector.insertAtTail(1);
        vector.insertAtTail(2);
        assertEquals(2, vector.removeAtTail());
        assertEquals(1, vector.size());
    }

    @Test
    void testRemoveAtHead() {
        vector.insertAtTail(1);
        vector.insertAtTail(2);
        assertEquals(1, vector.removeAtHead());
        assertEquals(1, vector.size());
    }

    @Test
    void testFind() {
        vector.insertAtTail(1);
        vector.insertAtTail(2);
        assertEquals(1, vector.find(2));
        assertEquals(-1, vector.find(3));
    }

    @Test
    void testGetValidIndex() {
        vector.insertAtTail(1);
        vector.insertAtTail(2);
        assertNotNull(vector.get(1));
    }

    @Test
    void testGetInvalidIndex() {
        vector.insertAtTail(1);
        assertThrows(IndexOutOfBoundsException.class, () -> vector.get(2));
    }
}
