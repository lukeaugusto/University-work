public class Driver {
	public static void main(String[] args) {
		
		int n = 100000;
		
		Prime prime = new Prime();
		PrimeOptimized prime_optimized = new PrimeOptimized();
		
		int[] c = {0, 0, 0};
		long[] times = {0, 0, 0};
		long t1 = System.currentTimeMillis();
		SieveOfEratosthenes sieve = new SieveOfEratosthenes(n);
		
		times[2] = System.currentTimeMillis() - t1;
		
		for (int i = 2; i < n; i++) {
			t1 = System.currentTimeMillis();
			if (prime.isPrime(i)) c[0]++;
			times[0] += System.currentTimeMillis() - t1;
			t1 = System.currentTimeMillis();
			if (prime_optimized.isPrime(i)) c[1]++;
			times[1] += System.currentTimeMillis() - t1;
			t1 = System.currentTimeMillis();
			if (sieve.isPrime(i)) c[2]++;
			times[2] += System.currentTimeMillis() - t1;
		}
		// Output primes and the time it took to find them.
		for (int i = 0; i < c.length; i++) {
			System.out.println("Number of primes: " + c[i]);
			System.out.println("Time: " + times[i]);
		}
	}
}