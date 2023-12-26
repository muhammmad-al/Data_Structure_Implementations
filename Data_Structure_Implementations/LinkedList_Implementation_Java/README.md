# LinkedList Implementation in Java

## Overview
This project contains a custom implementation of a LinkedList in Java. The LinkedList is a fundamental data structure in computer science, used for storing collections of data in a sequential manner. This implementation provides basic functionalities such as adding, removing, and retrieving elements, while maintaining the order of insertion.

## Features
- **Doubly Linked List**: Each node in the list maintains a reference to both the next and the previous node.
- **Generic Implementation**: Can store elements of any type.
- **Dynamic Operations**: Supports operations like insertion and deletion at both head and tail, as well as at any given index.
- **Iterator Support**: Custom iterator implementation for traversing the list.

## Usage
To use the LinkedList class, include it in your Java project. Here's a basic example:

```java
LinkedList<Integer> list = new LinkedList<>();
list.insertAtHead(10);
list.insertAtTail(20);
int firstElement = list.get(0); // Returns 10
int size = list.size(); // Returns 2
