#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <poll.h>
#include <fcntl.h>

#define MAX_MSG_SIZE 4096
void server(int port);
void client(char *ip_address, int port);

// createAddr: Constructs a sockaddr_in structure with the provided IP and port.
// Parameters:
// - char* ip: IP address in string format. If NULL, INADDR_ANY is used.
// - int port: Port number.
// Returns:
// - struct sockaddr_in: The constructed address structure.
struct sockaddr_in createAddr(char* ip, int port) {
    struct sockaddr_in ret;
    memset(&ret, 0, sizeof(struct sockaddr_in));
    ret.sin_family = AF_INET;
    ret.sin_port = htons(port);

    if (ip != NULL) {
        inet_pton(AF_INET, ip, &(ret.sin_addr));
    } else {
        ret.sin_addr.s_addr = htonl(INADDR_ANY);
    }

    return ret;
}

// randomPort: Generates a random port number.
// Returns:
// - int: A random port number within a specific range.
int randomPort() {
    return 0xc000 | (random()&0x3fff);
}


// connection: Manages the sending and receiving of messages over a socket.
// Parameters:
// - int socket: The socket file descriptor.
// This function continuously polls for data on the socket and standard input,
// handles reading from the socket, and sending data to the socket.
void connection(int socket) {
    char *message = (char*) malloc((MAX_MSG_SIZE + 2) * sizeof(char));
    struct pollfd fds[2];
    fds[0].fd = socket;
    fds[0].events = POLLIN;
    fds[1].fd = STDIN_FILENO;
    fds[1].events = POLLIN;

    for (;;) {
        int ret = poll(fds, 2, 60000);

        if (fds[0].revents & POLLIN) {
            if (read(socket, message, MAX_MSG_SIZE) <= 0) break;

            printf("%s", message);
            fflush(stdout);
        }

        // Write data if there is data to write
        if (fds[1].revents & POLLIN) {
            char c = getchar();
            int i;
            for (i = 0; i < MAX_MSG_SIZE; i++) {
                if (c == EOF || c == '\n') break;
                message[i] = c;
                c = getchar();
            }
            if (i <= 0)
                break;
            message[i] = '\0';
            strcat(message, "\r\n");
            send(socket, message, i + 3, 0);
        }
    }
    close(socket);
}

// main: The entry point of the program. Depending on the arguments, it starts
// either a server or a client.
// Parameters:
// - int argc: Argument count.
// - char *argv[]: Argument vector.
// The function handles different scenarios based on the number of arguments
// provided and calls either the server or client function accordingly.
int main(int argc, char *argv[]) {
    if (argc == 1) {
        // Server mode
        server(0);
    } else if (argc == 2) {
        // Server mode with specified port
        int port = atoi(argv[1]);
        server(port);
    } else if (argc == 3) {
        // Client mode
        char *ip_address = argv[1];
        int port = atoi(argv[2]);
        client(ip_address, port);
    } else {
        fprintf(stderr, "Usage: %s [IP_ADDRESS PORT]\n", argv[0]);
        exit(1);
    }

    return 0;
}

// server: Sets up and runs the server.
// Parameters:
// - int port: The port number on which the server will run. If zero, a random
// port is selected.
// This function creates a socket, binds it to the specified port (or a random
// one), listens for incoming connections, accepts a connection, and then
// handles the connection.
void server(int port) {
    
    port = port == 0 ? randomPort() : port;
    struct sockaddr_in ipOfServer = createAddr(NULL, port);

    
    int listener = socket(AF_INET, SOCK_STREAM, 0);
    if (bind(listener, (struct sockaddr*) &ipOfServer, sizeof(ipOfServer)) == -1)
        exit(1);

    
    listen(listener , 1);
    printf("Waiting for connection on port %d\n", port); 

    int sock = accept(listener, (struct sockaddr*)NULL, NULL);
    close(listener);

    connection(sock);
}

// client: Sets up and runs the client.
// Parameters:
// - char *ip: IP address of the server.
// - int port: Port number of the server.
// This function creates a socket, connects to the server at the given IP and
// port, and then handles the connection.
void client(char *ip, int port) {
    struct sockaddr_in serverAddr = createAddr(ip, port);
    int sock = socket(AF_INET, SOCK_STREAM, 0);

    
    if (connect(sock, (struct sockaddr*) &serverAddr, sizeof(serverAddr)) == -1)
        exit(1);

    connection(sock);
}
