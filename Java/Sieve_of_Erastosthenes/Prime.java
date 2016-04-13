/*
 * Author:  Guilherme Pereira Gribeler, gpereiragrib2015@my.fit.edu
 * Course:  CSE 2010, Section 02, Fall 2015
 * Project: Lab1, Primes
 */

public class Prime {
	
	// checks if p is a prime number
	public boolean isPrime(int p){
		int n = 2;
		boolean ans = false;
		
		if (p == 2)	{
			ans = true;
		}
		
		// checks if any number less than p and grater than 1 divides p
		while (n < p && ans == false)	{
			if(isDivisible(p, n))	{
				ans = true;
			}	
			n++;
		}
		
		if (n == p)	{
			return true;
		}	else	{
			return false;
		}
		
		
	}
	
	// checks if n divides p
	public boolean isDivisible(int p, int n){
		if (p%n == 0) {
			return true;
		}
		return false;
	}
}