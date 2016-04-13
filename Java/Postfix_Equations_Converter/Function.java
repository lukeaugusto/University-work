import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;;

public class Function {

	private String stringExpression;    // string representation of the function
	private boolean notation;			// if true - infix, postfix otherwise
	ArrayList<String> variables;		// list of variables the function depends on
										// For example: if stringExpression = "2 * x ^ y"
										// then variables =["x","y"]
	
	private double precision = 0.00001; // precision when finding root of the equation
										
	
	// Since there may be 0 or more variables in the expression 
	// we use syntax 'String... vars'
	// See more: http://stackoverflow.com/questions/2330942/java-variable-number-or-arguments-for-a-method
	public Function(String inputExpression,  String... vars) {
		this.stringExpression = inputExpression;
		this.notation = true;
		
		variables = new ArrayList<String>(vars.length);
		for (String var : vars) {
			variables.add(var);
		}

	}

	public void setExpression(String expression) {
		this.stringExpression = expression;
	}

	public void setNotation(boolean notation) {
		this.notation = notation;
	}

	public String getExpression() {
		return stringExpression;
	}

	public boolean getNotation() {
		return notation;
	}
	
	public void infixToPostfix() {
		// convert from infix to postfix notation
		
		Stack<String> aux = new Stack<String>();    
		String postfix = "";
		char letter;
		boolean verif;
		
		notation = false;
		
		for(int i=0; i<stringExpression.length(); i++){
			
			letter = stringExpression.charAt(i);
			
			if(letter != ' '){
				
				// Print operands as they arrive
				if( (letter >= '0' && letter <= '9') || 
					(letter >= 'a' && letter <= 'z') ||
					(letter >= 'A' && letter <= 'Z') ||
					letter == '.' || 
					( (letter == '+' || letter == '-') && i+1 < stringExpression.length() && stringExpression.charAt(i+1) != ' ' )
				){
					postfix = postfix + letter;
				}
				else{
					if(letter == '+' || letter == '-' || letter == '/' || letter == '*' || letter == '^'){
						verif = true;
						while(verif){
							// If the stack is empty or contains a left parenthesis on top, push the incoming operator onto the stack
							if(aux.isempty() || aux.peek().equals("(")){
								aux.push(letter + "");
								verif = false;
							}
							else{
								// If the incoming symbol has higher precedence than the top of the stack, push it on the stack
								if(
									( letter == '^'  && !aux.peek().equals("^")) || 
									( (letter == '*' || letter == '/') && (aux.peek().equals("+") || aux.peek().equals("-")) )
								){
									aux.push(letter + "");
									verif = false;
								}
								else{
									// If the incoming symbol has equal precedence with the top of the stack, use association
									if(
										( letter == '^' && aux.peek().equals("^") ) || 
										( (letter == '*' || letter == '/') && (aux.peek().equals("*") || aux.peek().equals("/")) ) ||
										( (letter == '+' || letter == '-') && (aux.peek().equals("+") || aux.peek().equals("-")) )
									){
										postfix = postfix + " " + aux.pop();
										aux.push(letter + "");
										verif = false;
									}
									// If the incoming symbol has lower precedence than the symbol on the top of the stack, pop the stack and print the top operator. 
									else{
											if(
												( ( letter == '+' || letter == '-' ) && (!aux.peek().equals("+") && !aux.peek().equals("-")) ) || 
												( (letter == '*' || letter == '/') && aux.peek().equals("^") ) 
											){
												postfix = postfix + " " + aux.pop();
	
											}
											else{
												verif = false;
												aux.push(letter + "");
											}
										}
									}		
									
								}
							
							}
						
							postfix = postfix + " ";
						}
					}
				}
				// If the incoming symbol is a left parenthesis, push it on the stack
				if(letter == '(')
					aux.push(letter + "");
				
				// If the incoming symbol is a right parenthesis, pop the stack and print the operators until you see a left parenthesis. Discard the pair of parentheses
				if(letter == ')'){
					while(!aux.peek().equals("("))
						postfix = postfix + " " + aux.pop();
					
					aux.pop();
				}
		}
		
		
		while(!aux.isempty())
			postfix = postfix + " " +aux.pop();
		
		stringExpression = postfix;
	}

	// Substitute each variables in the expression with values from
	// vars_values and evaluate it.
	public double evaluate(Double... args) {
		
		Stack<Double> numbers = new Stack<Double>();
		String num_temp;
		char letter;

		// Postfix
		if (!this.getNotation()) {

			for(int i=0; i<stringExpression.length(); i++){
				// Print operands
				letter = stringExpression.charAt(i);
				
				// Push value of variables instead of letters
				if(variables.indexOf(letter + "") != -1){
					numbers.push(args[variables.indexOf(letter + "") ]);
				}
				
				if( (letter >= '0' && letter <= '9') ||
					 letter == '.' || 
					 ( (letter == '+' || letter == '-') && i+1 < stringExpression.length() && stringExpression.charAt(i+1) != ' ')
				){

					num_temp = "";
				
					// Get decimal numbers
					while(i+1 <= stringExpression.length() && stringExpression.charAt(i) != ' '){
						num_temp = num_temp + stringExpression.charAt(i);
						i++;
					}
					
					numbers.push(Double.parseDouble(num_temp));
				}
				else{
					// Build the operations
					if(letter == '+' || letter == '-' || letter == '/' || letter == '*' || letter == '^'){
						
						Double result = 0.0;
						Double first;
						Double second;
						
						second = numbers.pop();
						first = numbers.pop();
						
						switch(letter){
							case '+':
								result = first + second;
							break;
							case '-':
								result = first - second;
							break;
							case '/':
								result = first / second;
							break;
							case '*':
								result = first * second;
							break;
							case '^':
								result = Math.pow(first, second);
							break;
						}
						
						numbers.push(result);
					}
				}
				
			}
			return numbers.pop();
		}
		
		// Infix
		if(this.getNotation()){
			// Since the algorithm of infix evaluation is complex, because of the parenthesis rules, the best option is to convert to postfix, evaluate it and then convert to infix again.
			double value;
			
			this.infixToPostfix();
			value = this.evaluate(args);
			this.postfixToInfix();
			
			return value;
		}
		
		return 0;
	}

	// convert back from postfix to infix, if the expression is already in 
	// infix do nothing
	public void postfixToInfix() {
		
		Stack<String> numbers = new Stack<String>();    
		String infix = "";
		char letter;
		String num_temp = "";
		notation = true;
		
		
		for(int i=0; i<stringExpression.length(); i++){
			
			letter = stringExpression.charAt(i);
			// Print operands as they arrive
			if( (letter >= '0' && letter <= '9') || 
				(letter >= 'a' && letter <= 'z') ||
				(letter >= 'A' && letter <= 'Z') ||
				letter == '.' || 
				( (letter == '+' || letter == '-') && i+1 < stringExpression.length() && stringExpression.charAt(i+1) != ' ')
			){
				num_temp = "";
			
				// Get decimal numbers
				while(i+1 <= stringExpression.length() && stringExpression.charAt(i) != ' '){
					num_temp = num_temp + stringExpression.charAt(i);
					i++;
				}
				numbers.push(num_temp);
			}
			else{
				// Build the operations
				if(letter == '+' || letter == '-' || letter == '/' || letter == '*' || letter == '^'){
					
					String operation_temp = "";
					String first = "";
					String second = "";
					
					second = numbers.pop();
					first = numbers.pop();
					
					operation_temp = "(" + " " + first + " " + letter + " " + second + " " + ")";
					
					numbers.push(operation_temp);
				}
			}
		}
		

		while(!numbers.isempty())
			infix = infix + " " + numbers.pop();
		
		stringExpression = infix;
	}

	public String toString(){
		String notation ="infix";
		if (!this.getNotation()) {
			notation="postfix";
		}
		String res="[ Notation: "+notation+" ] Expression: "+this.stringExpression;
		return res;
	}
	
	public double partialDerivative(int location, double... args){
		// Calculate partial derivative for variable variable.get(location) around the 
		// point given by vector args.
		// More on derivatives: https://en.wikipedia.org/wiki/Derivative
		// You may set h = 0.000001
		
		Double[] args_new = new Double[args.length];
		Double[] args_old = new Double[args.length];
		
		for(int i=0; i<args.length; i++){
			if(i == location)
				args_new[i] = args[i] + precision;
			else
				args_new[i] = args[i];
			args_old[i] = args[i];
		}
		
		return (evaluate(args_new) - evaluate(args_old)) / precision;
	}
	
	public double findRoot(double x0){
		// find root using Newton's method.
		// https://en.wikipedia.org/wiki/Newton%27s_method
		//You can assume that if this function
		// is called variables array list has only 1 element.
		boolean verif = false;
		double xn = x0;
		while(!verif){
			xn = x0 - (evaluate(x0) / partialDerivative(0,x0));
			if(Math.abs(xn - x0) <= precision )
				verif = true;
			x0 = xn;
		}
		
		return xn;
		
	}
		
	public double[] gradient(double... args){
		// calculate gradient, i.e. calculate vector of partial derivatives for 
		// all variables

		double[] grad = new double[args.length];
		
		for(int j=0; j <args.length; j++){
			Double[] args_new = new Double[args.length];
			Double[] args_old = new Double[args.length];
			
			for(int i=0; i<args.length; i++){
				if(i == j)
					args_new[i] = args[i] + precision;
				else
					args_new[i] = args[i];
				args_old[i] = args[i];
			}
		
			grad[j] = (evaluate(args_new) - evaluate(args_old)) / precision;
		}
		return grad;
	}

	// taken from http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
