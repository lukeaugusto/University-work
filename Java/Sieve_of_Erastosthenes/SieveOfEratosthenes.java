/*
 * Author:  Guilherme Pereira Gribeler, gpereiragrib2015@my.fit.edu
 * Course:  CSE 2010, Section 02, Fall 2015
 * Project: Lab1, Primes
 */

import java.util.ArrayList;
import java.util.Arrays;

public class SieveOfEratosthenes extends PrimeOptimized {
	
	ArrayList<Integer> primes; // array list of primes
	
	
    public SieveOfEratosthenes(){
    	this.primes = new ArrayList<Integer>();
    }
    
    public SieveOfEratosthenes(int upto){
    	this.primes = new ArrayList<Integer>();
    	this.findAllPrimes(upto);
    }
    
    // find all primes up to the number 'upTo' and adds them into 'primes'
    private void findAllPrimes(int upTo){
    	
    	boolean are_primes[] = new boolean[upTo];
    	
    	Arrays.fill(are_primes, true);
    	
		//checks if i is prime. If so, calculates all numbers that i divides 
		//and set them as not prime
    	for (int i = 2; i < upTo; i++)	{
    		if (are_primes[i] == true)	{
    			primes.add(i);
    			int j = i*2;
    			while (j < upTo)	{
    				are_primes[j] = false;
    				j += i;
    			}
    		}
    		
    	}

    }
    @Override
    // checks if the number z is in the ArrayList. If so, then it is prime
    public boolean isPrime(int z) {
       if (primes.indexOf(z) == -1)	{
    	   return false;
       }
       return true;
    } 
}

