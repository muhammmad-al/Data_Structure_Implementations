#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_MSG_LENGTH 4096

int main(){
    // Retrieve the current user's ID from environment variables
    char *my_id = getenv("USER");

    // Construct the path to the user's mailbox file
    char mailbox_path [4096];
    snprintf(mailbox_path, 4096, "/p/cso1/mailbox/%s.chat", my_id);

    // Attempt to open the mailbox file for reading
    FILE *mailbox = fopen(mailbox_path, "r");
    if (mailbox == NULL){
        // Notify the user if the mailbox file does not exist (no messages)
        printf("You have no messages. \n");
    } else {
        // Read and display the messages from the mailbox
        printf("You have received the following messages:\n\n");
        char msg[MAX_MSG_LENGTH];
        while (fgets(msg, MAX_MSG_LENGTH, mailbox) != NULL) {
            printf("%s", msg);
        }
        // Close the mailbox file after reading the messages
        fclose(mailbox);

        // Open and immediately close the mailbox file for writing to empty it
        FILE *empty_mailbox = fopen(mailbox_path, "w");
        fclose(empty_mailbox);
    }

    // Prompt the user to send a message
    char recipient_id[128];
    printf("\nDo you want to send a message? Enter the recipient's ID (or press enter to skip):  ");
    fgets(recipient_id, 128, stdin);
    // Remove the newline character from the input
    strtok(recipient_id, "\n"); 

    // Check if the user entered a recipient ID
    if (strlen(recipient_id) > 0){
        // Construct the path to the recipient's mailbox file
        char recipient_mailbox_path[4096];
        snprintf(recipient_mailbox_path, 4096, "/p/cso1/mailbox/%s.chat", recipient_id);

        // Prompt the user for a message
        char message [MAX_MSG_LENGTH];
        printf("Enter your message: ");
        fgets(message, MAX_MSG_LENGTH, stdin);

        // Prepare the full message with sender's ID and the message
        char full_message[MAX_MSG_LENGTH + 128];
        snprintf(full_message, MAX_MSG_LENGTH + 128, "%s: %s", my_id, message);

        // Attempt to open the recipient's mailbox file for appending
        FILE *recipient_mailbox = fopen(recipient_mailbox_path, "a");
        if(recipient_mailbox == NULL){
            // Handle the error if the mailbox file cannot be opened
            printf("Error, couldn't open recipient's mailbox");
            return 1;
        }
        // Write the message to the recipient's mailbox
        fputs(full_message, recipient_mailbox);
        // Close the recipient's mailbox file
        fclose(recipient_mailbox);

        // Construct a command to set permissions on the recipient's mailbox file
        char set_permissions_command[4096];
        snprintf(set_permissions_command, 4096, "chmod 666 %s", recipient_mailbox_path);
        // Execute the command to change file permissions
        system(set_permissions_command);

        // Notify the user that the message has been sent
        printf("Message sent to %s.\n", recipient_id);
    }
}
