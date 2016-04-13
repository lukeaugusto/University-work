#include <iostream>
#include "customStack.cpp"

using namespace std;

// Error Message
void invalidExpressionMessage(){
    cout << "Invalid RPN expression" << endl;
}

bool calculateRPN(string expression)
{
    
    double num;
    int i;
    char cr;
    string aux;
    customStack stack;
    
    // Stop condition
    if(expression == "$")
        return false;
    else
    {
        
        // Iterate over the expression
        for(i=0; i<expression.length(); i++)
        {
            
            cr = expression[i];
            
            // Get only valid characters
            if(cr != ' ')
            {
                // Numbers
                if(isdigit(cr))
                {
                    // Get decimal part of the numbers
                    aux = "";
                    do
                    {
                        aux = aux + cr;
                        i++;
                        cr = expression[i];
                        
                        // Handles invalid characters after numbers
                        if(cr != '.' && cr != ' ' && !isdigit(cr))
                        {
                            invalidExpressionMessage();
                            return true;
                        }
                        
                    }while(isdigit(cr) || cr == '.');
                    
                    num = stod(aux);
                    stack.push(num);
                }
                
                // Operators
                else
                {
                    // Handles invalid operators order
                    if(stack.size() < 2)
                    {
                        invalidExpressionMessage();
                        return true;
                    }
                    switch (cr)
                    {
                        case '*':
                            num = stack.pop() * stack.pop();
                            stack.push(num);
                            break;
                            
                        case '/':
                            num = stack.pop();
                            
                            // Handles division by 0
                            if (stack.top() == 0)
                            {
                                cout << "Division by 0" << endl;
                                return true;
                            }
                            
                            num = num / stack.pop();
                            stack.push(num);
                            break;
                            
                        case '+':
                            num = stack.pop() + stack.pop();
                            stack.push(num);
                            break;
                            
                        case '-':
                            num = stack.pop() - stack.pop();
                            stack.push(num);
                            break;
                            
                        default:
                            invalidExpressionMessage();
                            return true;
                            break;
                            
                    }
                }
                
            }
        }

    }
    
    // Handles too much numbers
    if(stack.size() > 1)
    {
        invalidExpressionMessage();
        return true;
    }
    cout << "Result: " << stack.pop() << endl;
    return true;
}

int main()
{
    string expression;
    
    do
    {
        // Prompt the user the expression
        cout << "Enter a RPN expression ('$' to end)" << endl;
        
        // Get the expression
        getline(cin, expression);
    
        // Calculate the RPN
    }while(calculateRPN(expression));
    
    return 0;
}
