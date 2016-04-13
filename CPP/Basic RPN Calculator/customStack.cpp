#include <iostream>
using namespace std;

// Maximum numbers in the stack
#define max 500

// Custom Stack Class
class customStack
{
    
protected:
    double arr[max];
    int lenght;
    
public:
    // Constructor
    customStack()
    {
        lenght = 0;
    }
    
    // Remove and return the top element
    double pop()
    {
        if (lenght == 0)
        {
            cout << "Empty Stack";
            return false;
        }
        
        int num = arr[lenght-1];
        lenght--;
        return num;
    }
    
    // Return the top element
    double top()
    {
        if (lenght == 0)
        {
            cout << "Empty Stack";
            return false;
        }
        
        return arr[lenght-1];
    }
    
    
    // Add an element
    void push(double element)
    {
        arr[lenght] = element;
        lenght++;
    }
    
    // Return the size of the stack
    int size()
    {
        return lenght;
    }
    
    // Return if the stack is empty or not
    bool isEmpty()
    {
        if(lenght > 0) return false;
        return true;
    }
    
    // Reset the stack
    void reset()
    {
        lenght = 0;
    }
};

