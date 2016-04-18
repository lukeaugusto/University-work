/*
 Author: Lucas A Santos
 Course:  CSE 4001, Spring 2016
 Project: Assignment 4 - Readers-Writers Solution
 */


/* Includes */
#include <stack>
#include "semaphore_class.h"

/* prototype for thread routine */
void *threadB (void *);
void *threadA (void *);

// Linked List
struct node {
    int x;
    node *next;
};

/* global vars */
node *head, *tail;// End of the list
Semaphore roomEmpty(1); // Semaphore for exclusive writer access to the Linked List
Semaphore buffempty(0); // Semaphore for preventing the consumer to read empty buffer
Semaphore readerFlag(1); // Semaphore for exclusive access to the readers variable


Semaphore turnstile(1); // Semaphore for preventing the consumer to read empty buffer
int readers;


int main()
{
    pthread_t thread_a;
    pthread_t thread_b;
    
    head = nullptr;
    tail = nullptr;
    
    for(int j=0; j<10; j++){
        pthread_create (&thread_a, NULL, threadA, (void *) &j);
        pthread_create (&thread_b, NULL, threadB, (void *) &j);
        
        (void) pthread_join(thread_a, NULL);
        (void) pthread_join(thread_b, NULL);
    }
    
    cout << "\n";
    exit(0);
}

// Writer
void *threadA (void  *ptr)
{
    int i = *((int *) ptr);
    
    turnstile.wait();
    
    roomEmpty.wait();
    
    node *item = new node;
    item->x = i;
    item->next = nullptr;
    
    if(!head)
        head = item;
    else
        tail->next = item;
    
    tail = item;
    
    roomEmpty.signal();
    
    buffempty.signal();
    turnstile.signal();
    
    pthread_exit(0);
}

// Reader
void *threadB ( void *ptr)
{
    
    turnstile.wait();
    turnstile.signal();
    
    buffempty.wait();
    
    // Reader start in and flag the empty room for the writer
    readerFlag.wait();
    readers++;
    if (readers == 1){
        roomEmpty.wait();
    }
    readerFlag.signal();
    
    cout << tail->x << " ";
    
    readerFlag.wait();
    // Reader start in and flag the free room for the writer, if there is no other reader
    readers--;
    if(readers == 0){
        roomEmpty.signal();
    }
    readerFlag.signal();
    
    pthread_exit(0);
}
