/*
 * Author:  Guilherme Pereira Gribeler, gpereiragrib2015@my.fit.edu
 * Course:  CSE 2010, Section 02, Fall 2015
 * Project: Lab1, Primes
 */

public class PrimeOptimized extends Prime {
	
	// checks if p is a prime number
	public boolean isPrime (int p)	{
		
		int n = 3;
		
		if (p == 2)	{
			return true;
		}	
		
		// checks if 2 divides p
		if (p % 2 == 0)	{
			return false;
		}
		
		// if 2 does not divide p so any even number divides p and it does not check them
		while (n < p)	{
			if(isDivisible(p, n) == true)	{
				return false;
			}	
			n += 2;
		}
		
		return true;
		
	}
}
	