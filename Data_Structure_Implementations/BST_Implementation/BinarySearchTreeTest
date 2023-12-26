import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tree.BinarySearchTree;


public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> bst;

    @Before
    public void setUp() {
        bst = new BinarySearchTree<>();
    }

    @Test
    public void testInsertAndFind() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(2);
        bst.insert(4);

        assertTrue(bst.find(3));
        assertFalse(bst.find(6));
    }

    @Test
    public void testFindMax() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(9);
        bst.insert(1);

        assertEquals(Integer.valueOf(9), bst.findMax());
    }

    @Test
    public void testRemove() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(2);
        bst.insert(6);

        assertTrue(bst.find(7));
        bst.remove(7);
        assertFalse(bst.find(7));

        assertTrue(bst.find(3));
        bst.remove(3);
        assertFalse(bst.find(3));
    }

    @Test
    public void testTreeStructureAfterRemoval() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(1);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);

        bst.remove(7);
        assertEquals("1 3 4 5 6 8 ", bst.getInOrder());
    }
}
