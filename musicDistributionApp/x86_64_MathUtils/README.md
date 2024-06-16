# Assembly Math Utilities

## Overview
This project consists of a series of math utility functions implemented in x86-64 assembly language. The primary focus of these utilities is to demonstrate fundamental arithmetic operations and algorithms at the assembly level, providing an insight into low-level programming and optimization techniques.

## Key Features

### Modulo Routine (`modulo`)
- **Purpose**: Calculates the modulo (`mod`) of two numbers using addition and subtraction.
- **Implementation**: This routine continuously subtracts the divisor from the dividend until the remainder is less than the divisor, effectively calculating the `mod`.

### Greatest Common Divisor Routine (`gcd`)
- **Purpose**: Calculates the Greatest Common Divisor (GCD) of two numbers using the modulo method.
- **Implementation**: Implements the GCD algorithm recursively by calling the `modulo` routine, ensuring the calculation adheres to the mathematical definition of GCD.

### Prime Number Routine (`prime`)
- **Purpose**: Determines if a number is prime by using the GCD method.
- **Implementation**: Checks if the only divisor that a number has is 1 (aside from itself), employing the GCD function to verify this condition.
