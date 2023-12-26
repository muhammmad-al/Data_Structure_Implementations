/**
 * Simple BST Node. Contains the data in this node, and pointers to left and right children
 * @param <T>
 */
public class TreeNode<T>{
    // Every TreeNode has a left and right reference (pointer), and a data item (currently null)
    protected TreeNode<T> left = null;
    protected TreeNode<T> right = null;
    protected int height = 1;
    protected T data = null;

    public TreeNode(T data) { // Constructor
        this.data = data;
    }

    public TreeNode(T data, int height) { // Another Constructor
        this(data);
        this.height = height;
    }

}
