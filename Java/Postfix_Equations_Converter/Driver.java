import java.math.BigDecimal;
import java.math.RoundingMode;

public class Driver {

	public static boolean testConversions(String expression){
		Function exp = new Function(expression);
		
		// print out the expression making sure it was parsed correctly
		System.out.println(exp.toString());
		
		// convert to postfix
		exp.infixToPostfix();
		
		//print out
		System.out.println(exp.toString());
		
		// evaluate expression in the postfix form
		double postfixEvaluate = exp.evaluate();
		
		// convert back to infix
		exp.postfixToInfix();
		System.out.println(exp.toString());
		
		// evaluate again
		double infixEvaluate=exp.evaluate();
		System.out.println("Infix value: "+infixEvaluate+"  Postfix value: "+postfixEvaluate);

		return (Math.abs(postfixEvaluate-infixEvaluate)<=0.0000000001);
	}
	
	// taken from http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public static void main(String[] args) {
		boolean isConversionsCorrect;
		String e1,e2,e3,e4,e5;
		e1 = "( 1 + ( ( 2 + 3.2 ) / 5 ) ) * ( 1 - 4 )";
		e2= "( 2 + 41 ) * -8.1 / ( -1 + 3 )";
		e3="3 * ( ( 7.9 - 2 ^ ( 4 * 1 - 2 ) ) / 2 )";
		e4="( 8 / ( 2 ^ 3 ) ) - ( 3 - 2 * 4.2 ) ^ ( 5 - 3 / 1 ) + ( -2 * 3 )";
		e5="2 ^ ( 8 - ( 6 / 3 + ( 1 ^ 2 ) ) ) + ( 0 - 2 ) * ( 0 - 3 )";
		
		String[] input={e1,e2,e3,e4,e5};
		
		System.out.println("Part 1");
		for (int i = 0; i < input.length; i++) {
			isConversionsCorrect=testConversions(input[i]);
			if (!isConversionsCorrect){
				System.out.println("Conversion was not correct");
			}
			
			System.out.println();
		}
		
		
		System.out.println("Part 2");
		String expression = "5 ^ x - 10 * y";
		Function f1 = new Function(expression, "x", "y");
		int round_digits = 5;

		
		// d(f1)/d(x)|(1,5)
		System.out.println("d(f1)/d(x) at (1,5) = " +round(f1.partialDerivative(0, 1.0, 5), round_digits));
		
		// d(f1)/d(y)|(1,5)
		System.out.println("d(f1)/d(y) at (1,5) = " +round(f1.partialDerivative(1, 1.0, 5), round_digits));
		
		String expression1 = " 8 - z ^ ( 2 * 2 )";
		Function f2 = new Function(expression1, "z");
		double root =f2.findRoot(10);
		
		System.out.println("Root of the equation " + expression1 +"=0  is " +round(root, 5));
		
		System.out.println("f2(root)=" + round(f2.evaluate(root),5));
	}
}
