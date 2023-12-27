# Simple Processor Simulator

This Python script serves as a basic computer simulator of an Instruction Set Architecture (ISA). The project demonstrates the simulation of a simple processor with a limited set of instructions and memory management. It allows you to visualize how a computer's Central Processing Unit (CPU) executes instructions, modifies registers, and interacts with memory, offering a practical insight into the functioning of an ISA.

## Key Features

- **Instruction Execution**: The script processes a sequence of instructions, demonstrating their execution within the simulated processor.

- **Register and Memory Handling**: The simulator maintains registers (`R`) and memory (`M`) to mimic the storage and data manipulation aspects of a computer.

- **Clock Cycle Simulation**: Each clock cycle is simulated to show the step-by-step execution of instructions.

- **Interactive Display**: The script provides an interactive command-line interface to visualize the processor's state after each clock cycle.

## How to Use

To use this script, follow these steps:

1. **Execution**: The script can be executed by running it with Python from the command line. You can either provide a memory file as an argument or pass a list of bytes as command-line arguments.

    ```bash
    python processor_simulation.py memory.txt
    ```

    OR

    ```bash
    python processor_simulation.py byte1 byte2 byte3 ...
    ```

2. **Memory File**: If you provide a memory file, it should be a text file containing a set of bytes in hexadecimal format, separated by spaces. These bytes will be loaded into memory before running the simulation.

3. **Command-Line Arguments**: If you provide bytes as command-line arguments, they will be loaded into memory before running the simulation.

4. **Simulation**: The script simulates the processor by executing a sequence of clock cycles. You can specify the number of steps you want the simulation to take. If you enter '0', the simulation will exit.

5. **Display**: The script displays the processor state, including the last executed instruction, register values, and memory contents, after each clock cycle.

