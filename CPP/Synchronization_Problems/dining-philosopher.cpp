/*
 
 Author: Lucas A Santos
 
 Course:  CSE 4001, Spring 2016
 
 Project: Assignment 4 - Dining-Philosopher Solution
 
 */



/* Includes */

#include <stack>

#include "semaphore_class.h"

/* prototype for thread routine */

void *thread (void *);


// Linked List

struct node {
    int x;
    node *next;
};


/* global vars */

Semaphore* forks[5];

Semaphore footman(4); // Handle all philophers holding 1 fork deadlock


int main()

{
    int a,b,c,d,e;
    
    a=0;
    b=1;
    c=2;
    d=3;
    e=4;
    
    pthread_t thread_a;
    pthread_t thread_b;
    pthread_t thread_c;
    pthread_t thread_d;
    pthread_t thread_e;
    
    for(int i=0;i<5; i++)
        forks[i] = new Semaphore(1);
    
    pthread_create (&thread_a, NULL, thread, (void *) &a);
    pthread_create (&thread_c, NULL, thread, (void *) &c);
    pthread_create (&thread_d, NULL, thread, (void *) &d);
    pthread_create (&thread_b, NULL, thread, (void *) &b);
    pthread_create (&thread_e, NULL, thread, (void *) &e);
    
    (void) pthread_join(thread_e, NULL);
    (void) pthread_join(thread_c, NULL);
    (void) pthread_join(thread_a, NULL);
    (void) pthread_join(thread_b, NULL);
    (void) pthread_join(thread_d, NULL);
    
    cout << "\n";
    
    exit(0);
}


// Philosopher
void *thread (void  *ptr){
    int i = *((int *) ptr);
    
    // Get forks
    footman.wait();
    forks[(i+1)%5]->wait(); // Right fork
    forks[i]->wait(); // Left fork
    
    cout << "Philosopher " << i << " is eating\n";
    sleep(1);
    
    // Get forks
    forks[i]->signal(); // Left fork
    forks[(i+1)%5]->signal(); // Reft fork
    footman.signal();
    
    cout << "Philosopher " << i << " finished his meal\n";
    
    pthread_exit(0);
}