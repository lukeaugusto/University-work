Author: Lucas A Santos

## Synopsis

Project: Midterm Project - RPN Calculator
For this assignment, you will design, develop, and test a program that simulates a Reverse Polish Notation (RPN) calculator.

## Motivation

Course: CSE 2050 - Program in a Second Language, Spring 2016

## Installation

Source codes of a C++ Project. 
    Import the .cpp files in the src folder.
    Compile and run.

## Tests

A print of the usage is in the root folder


## Algorithmn

1. Prompt the user the expression
2. Get the expression
3. Check if it is the stop token
    -> Yes: terminate
    -> No: Calculate the expression
4. Iterate over the expression
5. Ignore the spaces
6. If it is a number
    -> get its decimal and fractional parts 
    -> convert to a double
    -> push in the stack
7. If it is an operator
    -> If there arent two elements in the stacks fail the calculation
    -> Switch the operator
    -> Proceed with the operation by poping two elements in the stack
    -> Push the result in the stack
8. Repeat 5 - 7 until the expression is done
9. Pop the stack and print it.