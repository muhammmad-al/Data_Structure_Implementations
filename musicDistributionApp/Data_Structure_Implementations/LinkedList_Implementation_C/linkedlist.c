#include "linkedlist.h"
#include <stdlib.h>
#include <stdio.h>


/**
 * Returns a pointer to the head node of the list, given a pointer to any node 
 * in the list. If the provided pointer is `NULL`, instead returns `NULL`. The
 * head node is the only node with `is_head` set to 1.
 */
cll_node *cll_head(cll_node *list){
    if (list == NULL) {
        return NULL;
    }

    cll_node *current = list;

    do {
        if (current -> is_head) {
            return current;
        }

        current = current -> next;
    } while (current != NULL);

    return NULL;
}


/**
 * Returns a pointer to the "last" node in the list, given a pointer to any node 
 * in the list. If the provided pointer is `NULL`, instead returns `NULL`. The
 * "last" node is the node just before the head node (i.e., the node who's `next`
 * pointer points to a node with `is_head` set to 1.  The head may also be the tail.
 */
cll_node *cll_tail(cll_node *list) {
    if (list != NULL) {
        if (list->next->is_head) // Return the input if the input is the tail node
            return list;
        cll_node* cur = list;

        do {
            cur = cur->next; // Get the next node
        } while (cur != list && cur->next->is_head == 0); // Repeat until we loop or find the tail node

        if (cur != list)
            return cur;
    }
    return NULL; 
}

/**
 * Returns the number of nodes in the list, which is the same for all nodes
 * in the list and 0 for `NULL`.
 */
unsigned long cll_length(cll_node *list) {
    if (list == NULL) {
        return 0;
    }

    unsigned long count = 0;
    cll_node *current = list;

    do {
        count++;
        current = current->next;
    } while (current != list); // Loop until we reach the starting node again

    return count; 
}

/**
 * Given a pointer to a node in a list, returns a pointer to the first node
 * at or *after* that node which has the given `value`. If given `NULL`, or
 * if no such node exists, returns `NULL`.  That is, start looking at `list`'s
 * value and then follow next until you return to the given `list` node.
 */
cll_node *cll_find(cll_node *list, int value) {
    if (list != NULL)  {
        cll_node* cur = list;

        do {
            if (cur->value == value) // If we find the node were looking for we return it
                return cur;
            cur = cur->next; // Advance to the next node
        } while (cur != list); // Repeat until we loop back around
    }
    return NULL; 
}

/**
 * Given a pointer to a node in a list, remove that node from the list,
 * `free`ing its memory in the process. Returns a pointer to the node that now
 * occupies the same position in the list that the removed node used to occupy.
 * 
 * If given `NULL`, this function does nothing and returns `NULL`.
 */
cll_node *cll_remove(cll_node *list) {
    if (list != NULL) {
        cll_node *prev = list->prev; // Get the previous/next nodes
        cll_node *next = list->next;

        prev->next = next;
        next->prev = prev; // Connect the previous/next nodes together

        if (!next->is_head) {
            if (list->is_head)
                next->is_head = 1;
            free(list); // Free the list to delete
            return next;
        }
        if (list->is_head)
            next->is_head = 1;
        free(list); // free the list we are deleting
    }
    return NULL; // Return null if the input is null or the input is the tail
 
}

/**
 * Extend a list by one by adding `value` next to `list`. If `before` is 0,
 * inserts `value` immediately following the node pointed to by `list`;
 * otherwise inserts `value` immediately before that node. If `list` is NULL,
 * the newly inserted node is the entire list and the `is_head` value is set
 * to 1 for this node. If `list` is not NULL, be sure to initialize all
 * fields of the node. In all cases, the new node is allocated using `malloc` 
 * and returned by the function.
 */
cll_node *cll_insert(int value, cll_node *list, int before) {  
    if (list != NULL) {
        cll_node *ret = (cll_node *)malloc(sizeof(cll_node)); // The new node
        ret->value = value;
        ret->is_head = 0;

        cll_node* insert_node;

        if (before) {
            insert_node = list->prev;

            // Update the head node
            if (list->is_head) {
                list->is_head = 0;
                ret->is_head = 1;
            }
        } else {
            insert_node = list;
        }

        ret->prev = insert_node;
        ret->next = insert_node->next;
        insert_node->next->prev = ret;
        insert_node->next = ret;

        return ret; // Return the created node
    }

    // Create an entire new list
    cll_node* ret = (cll_node*) malloc(sizeof(cll_node));
    ret->value = value;
    ret->is_head = 1;
    ret->prev = ret;
    ret->next = ret;
    return ret; // Return the created node
}

void cll_show(cll_node *list) {
    // Find the head of the linked list
    cll_node *head = cll_head(list);
    cll_node *ptr = head;
    // Print opening [
    putchar('[');
    // Loop through list printing values
    if (ptr) {
        do {
            if (!ptr->is_head) 
                printf(", "); // include a comma if not the first element
            if (ptr == list) 
                putchar('*'); // include * surrounding parameter element
            printf("%d", ptr->value);
            if (ptr == list) 
                putchar('*');
            ptr = ptr->next; // next pointer
        } while (ptr != head);
    }
    // Print closing ]
    puts("]");
}



