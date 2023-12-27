# C Client-Server Communication

This C program demonstrates simple client-server communication using socket programming. It provides a basic framework for exchanging messages between a client and a server over a network. The client and server can run on the same machine or different machines, allowing for remote communication.

## Key Features

- **Client-Server Model**: The project follows a client-server architecture where the server listens for incoming connections, and the client establishes a connection to communicate.

- **Socket Programming**: Utilizes socket programming in C to establish communication channels between the client and server.

- **Polling**: The code uses the `poll()` function to efficiently handle I/O operations for both sockets and standard input.

- **Port Selection**: The server can automatically select an available random port or accept a specified port number. This flexibility enables dynamic or predefined port configurations.

## Usage
1. **Compile the Code:** 
   - Open a terminal window and navigate to the source code directory.
   - Compile the code using the gcc compiler.

2. **Run the Server:** 
   - Start the server mode with either a randomly selected port or a specified custom port.

3. **Run the Client:** 
   - Initiate client mode by providing the server's IP address and port as arguments.

4. **Chatting:** 
   - Once both server and client are running, messaging can begin.
   - Messages are sent from the client's terminal and received on the server's terminal.

5. **Terminating the Program:** 
   - Exit the program by pressing Ctrl+C in either the server or client terminal.
