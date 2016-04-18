/*
 Author: Lucas A Santos
 Course: CSE 4001, Spring 2016
 Project: Assignment 2 - Exercise 8
 */

#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char *argv[]){
    
    char sending[] = "Sending throught pipe\n";
    char receiving[50];
    
    // Create the pipe
    int pp[2];
    if(pipe(pp)){
        // Pipe failed. Terminate
        fprintf(stderr, "Pipe failed. Terminating.\n");
        exit(1);
    }
    
    // Create the child process
    int rc = fork();
    
    if (rc < 0){
        // Fork failed. Terminate
        fprintf(stderr, "Fork failed. Terminating.\n");
        exit(1);
    }else if (rc == 0){
        // child (new process)
        
        close(pp[0]);
        
        // Send the string
        write(pp[1], sending, strlen(sending)+1);
        exit(0);
    }else{
        // parent goes down this path (original process)
        
        close(pp[1]);
        
        // Receive the string
        read(pp[0], receiving, sizeof(receiving));
        printf("Received string: %s", receiving);
    }
    return 0;
}
