## Overview
This repository contains the implementation of a circular doubly linked list in C. A circular doubly linked list is a more complex variant of the standard linked list, allowing for efficient operations at both ends of the list. This implementation includes functions for insertion, deletion, searching, and traversing the list.

## Features
- **Head and Tail Retrieval**: Functions to find the head (`cll_head`) and tail (`cll_tail`) of the list.
- **Length Calculation**: Determines the number of nodes in the list (`cll_length`).
- **Node Search**: Finds a node with a specific value (`cll_find`).
- **Node Insertion and Removal**: Adds (`cll_insert`) and removes (`cll_remove`) nodes from the list.
- **Display**: Displays the list in a formatted way (`cll_show`).

## Usage
Here's an example of how to use the functions in the linked list:

```c
#include "linkedlist.h"

int main() {
    cll_node *list = NULL;

    // Insert elements
    list = cll_insert(10, list, 0);
    list = cll_insert(20, list, 0);

    // Find and remove an element
    cll_node *node = cll_find(list, 20);
    if (node) {
        list = cll_remove(node);
    }

    // Display the list
    cll_show(list);

    // Free the list
    while (list != NULL) {
        list = cll_remove(list);
    }

    return 0;
}
