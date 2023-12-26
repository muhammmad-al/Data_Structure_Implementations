public class BinaryTree<T> {
    protected TreeNode<T> root = null;

    public String getInOrder() {
        return getInOrder(root);
    }

    private String getInOrder(TreeNode<T> curNode) {
        String toBeReturned = "";

        if (curNode == null) {
            return toBeReturned;
        }

        toBeReturned = getInOrder(curNode.left);
        toBeReturned += curNode.data.toString() + " ";
        toBeReturned += getInOrder(curNode.right);
        return toBeReturned;

    }

    public String getPreOrder() {
        return getPreOrder(root);
    }

    private String getPreOrder(TreeNode<T> curNode) {
        String toBeReturned = "";

        if (curNode == null) {
            return toBeReturned;
        }

        toBeReturned = curNode.data.toString() + " ";
        toBeReturned += getPreOrder(curNode.left);
        toBeReturned += getPreOrder(curNode.right);
        return toBeReturned;

    }

    public String getPostOrder() {
        return getPostOrder(root);
    }
    private String getPostOrder(TreeNode<T> curNode) {
        String toBeReturned = "";

        if (curNode == null) {
            return toBeReturned;
        }

        toBeReturned = getPostOrder(curNode.left);
        toBeReturned += getPostOrder(curNode.right);
        toBeReturned += curNode.data.toString() + " ";
        return toBeReturned;
    }


    public void printTree() {
        printTree(this.root, 0);
        System.out.println("\n\n");
    }

    private void printTree(TreeNode<T> curNode, int indentLev) {
        if(curNode == null) return;
        for(int i=0; i<indentLev; i++) {
            if(i == indentLev - 1) System.out.print("|-----");
            else System.out.print("      ");
        }
        System.out.println(curNode.data);
        printTree(curNode.left, indentLev+1);
        printTree(curNode.right, indentLev+1);
    }

    public int height() {
        return height(root);
    }

    protected int height(TreeNode<T> node) {
        if(node == null) return 0;
        return node.height;
    }
}
