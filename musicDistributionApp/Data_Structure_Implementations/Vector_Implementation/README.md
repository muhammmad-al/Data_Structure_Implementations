# Vector Implementation

## Overview
This project contains a custom implementation of the Vector data structure, similar to an ArrayList in Java. It's designed to demonstrate proficiency in understanding and implementing dynamic arrays. The Vector class provides basic functionalities such as adding, removing, and retrieving elements, as well as dynamically resizing itself as needed.

## Features
- **Dynamic Resizing:** Automatically resizes itself when needed.
- **Generic Implementation:** Can hold elements of any reference type.
- **Basic Operations:** Includes methods for adding, removing, and accessing elements.
- **JUnit Testing:** Comprehensive test cases to validate functionality.

## Usage
To use the `Vector` class, include it in your Java project and instantiate it with the desired type. Here is a basic example:
```java
Vector<Integer> vector = new Vector<>();
vector.insertAtTail(1);
vector.insertAtTail(2);
int size = vector.size(); // size will be 2
