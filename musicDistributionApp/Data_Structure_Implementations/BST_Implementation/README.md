## Project Overview
This project contains a Java implementation of a Binary Search Tree (BST), a fundamental data structure used in computer science. The BST allows for efficient data storage, retrieval, and manipulation. The implementation includes core functionalities such as insertion, deletion, and searching for elements. Additionally, the project includes JUnit tests to ensure the correctness and reliability of the implementation.

## Features
- **Insertion**: Adds a new element to the tree while maintaining the BST properties.
- **Search**: Efficiently finds an element in the tree.
- **Deletion**: Removes an element from the tree, adjusting the structure accordingly.
- **Finding Maximum Value**: Retrieves the maximum value in the tree.
- **Traversal Methods**: Includes in-order, pre-order, and post-order traversal of the tree.
- **JUnit Tests**: Comprehensive test cases to validate the functionality of the BST.

## Usage
Here's a basic example of how to use the Binary Search Tree:

```java
tree.BinarySearchTree<Integer> bst = new tree.BinarySearchTree<>();
bst.insert(5);
bst.insert(3);
bst.insert(7);

System.out.println("Is 3 in the tree? " + bst.find(3));
bst.remove(3);
System.out.println("Is 3 in the tree after removal? " + bst.find(3));
