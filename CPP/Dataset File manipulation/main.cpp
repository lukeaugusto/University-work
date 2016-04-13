/*
 Author: Lucas A Santos
 Course:  CSE 2050, Spring 2016
 Project: Project 6 - Baby Name Popularity Finder
 
 Files format:
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
 
 Algorithmn:
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
 */

#include <iostream>
#include <vector>
#include <fstream>
#include <string>
#include <sstream>
#define size 12
using namespace std;


// Convert the string in vector
static vector<string> strSplit(string str, char delimiter)
{
    vector<string> vec;
    stringstream stream(str);
    string token;
    
    // Get each line, tokenize and put each token in a vector position
    while(getline(stream, token, delimiter))
    {
        vec.push_back(token);
    }
    
    return vec;
}

static void displayResult(bool fileSave, string result)
{
    // Append the result in the output file
    if(fileSave)
    {
        ofstream output;
        output.open ("log.txt");
        output << result;
        output.close();
    }
    // Print the result
    else
    {
        cout << result;
    }
}


int main(int argc, const char * argv[]) {
    
    string name, line, result = "";
    vector<string> expenses;
    ifstream categoriesFile, expensesFile;
    string *categoriesArray[size], categoriesName[size+1];
    int i, j, expenseQtd = 0, aux = 0, sizes[size];
    float total[size+1];
    
    // Open input files
    expensesFile.open("expenses.txt");
    categoriesFile.open("categories.txt");
    
    // Handle problems with opening
    if (!expensesFile || !categoriesFile)
    {
        cout << "Input files opening failed. Terminating\n";
        return 1;
    }
    else
    {
        // Instantiate the Jagged Array
        while(getline(categoriesFile, line))
        {
            // Expense
            if(line.at(0) == '\t')
                expenseQtd++;
            
            // Category
            else
            {
                if(expenseQtd != 0)
                {
                    // Instantiate the category array
                    categoriesArray[aux] = new string[expenseQtd];
                    // Populate the Class names array
                    sizes[aux] = expenseQtd;
                    aux ++;
                }
                categoriesName[aux] = line;
                expenseQtd = 0;
            }
        }
        // Instantiate the last Category
        categoriesArray[aux] = new string[expenseQtd];
        sizes[aux] = expenseQtd;
        
        // Set the pointer at the begining of the file
        categoriesFile.clear();
        categoriesFile.seekg(0, categoriesFile.beg);

        // Populate the Jagged Array
        aux = -1;
        while(getline(categoriesFile, line))
        {
            // Expense
            if(line.at(0) == '\t')
            {
                // Populate the category array
                categoriesArray[aux][expenseQtd] = line.substr(1);
                expenseQtd++;
            }
            
            // Category
            else
            {
                aux ++;
                expenseQtd = 0;
            }
        }
        
        
        // Calculate the sum of the Categories
        
        // Populate the sum Array with 0's
        // The last element total[size] is the non-categorized elements
        for(i=0;i<size+1; i++)
            total[i] = 0;
        
        while(getline(expensesFile, line))
        {
            expenses = strSplit(line, '\t');
            aux = 0;
            for(i=0;i<size && aux == 0;i++)
            {
            
                for(j=0;j<sizes[i] && aux == 0;j++)
                {
                    if(categoriesArray[i][j] == expenses[1])
                    {
                        total[i] += stod(expenses[2]);
                        aux = 1;
                    }
                }
            }
            
            // Non-categorized element
            if(aux == 0)
                total[size] += stod(expenses[2]);
        }
        
        
        
        // Create the result string
        string value;
        for(i=0;i<=size; i++)
        {
            ostringstream ss;
            ss << total[i];
            value = ss.str();
            if(categoriesName[i] != "") result += categoriesName[i] + "\t $" + value + "\n";
        }
        if(value != "0") result += "Non-categorized expends\t $" + value + "\n";
        
        // Save the output file
        displayResult(true, result);
        // Print the result
        displayResult(false, result);
    }
    
    // Close input file
    expensesFile.close();
    categoriesFile.close();
    
    return 0;
}