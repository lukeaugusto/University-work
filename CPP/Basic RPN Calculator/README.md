## Synopsis

Project: Project 6 - Baby Name Popularity Finder
Manipulation of strings and files.

## Motivation

Course: CSE 2050 - Program in a Second Language, Spring 2016, Florida Institute of Technology

## Installation

Source codes of a C++ Project. 
Import the .cpp files in the src folder.
Compile and run.

## Tests

Prints of the usage is in the root folder

## Format of the input files

Category:
Category Name
(\t)Expense
(\t)Expense
Category Name
(\t)Expense

How to decode: To decode this file we must check the first char of each line and if it is an '\t' it is an Expense and if not it is an Category Name


Expenses:
Date(\t)Expense Name(\t)Cost
Date(\t)Expense Name(\t)Cost
Date(\t)Expense Name(\t)Cost
Date(\t)Expense Name(\t)Cost
Date(\t)Expense Name(\t)Cost

How to decode: Slipt every line using '\t' as its delimiter and parse or use the information returned
tokens[0] = Date - String
tokens[1] = Expense Name - String
tokens[2] = Cost - String | Able to Parse directly with stod



## Algorithmn

1. Open two input files
2. Handle problems with opening
    -> If doesn't open the file, terminate.
3. Instantiate the Jagged Array, counting the number of expenses in each category in category file
4. Set the pointer at the begining of the category file to read it again
5. Populate the Jagged Array with the expenses
6. Populate the sum Array with 0's
7. Check each expense in expense file and search for a match in the Jagged Array
    -> If found: sum in the same position in the sum Array
    -> Not found: sum in the non-categorized items in the sum Array (last position)
7. Create the result string
8. Save the result in a file
9. Print the result