/*
 Author: Lucas A Santos
 Course:  CSE 4001, Spring 2016
 Project: Assignment 4 - Consumer-Producer Solution
*/


/* Includes */
#include <stack>
#include "semaphore_class.h"

#define maxBuffer 2

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
Semaphore mux(1); // Mutex for exclusive read/write
Semaphore items(0); // Semaphore for preventing the consumer to read empty buffer
Semaphore spaces(maxBuffer); // Semaphore for producer to respect the buffer limit


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

// Producer
void *threadA (void  *ptr)
{
    int i = *((int *) ptr);
    
    spaces.wait();
    mux.wait();
        
    node *item = new node;
    item->x = i;
    item->next = nullptr;
    
    if(!head)
        head = item;
    else
        tail->next = item;
    
    tail = item;
    
    mux.signal();
    items.signal();
    
    pthread_exit(0);
}

// Consumer
void *threadB ( void *ptr)
{
    
    items.wait();
    mux.wait();
    
    cout << head->x << " ";
    node *aux = head;
    head = head->next;
    free(aux);
    
    mux.signal();
    spaces.signal();
    
    pthread_exit(0);
}
 