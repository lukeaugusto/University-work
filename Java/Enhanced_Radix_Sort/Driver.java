import java.util.Arrays;
import java.util.Random;

public class Driver {
	public static void main(String[] args) {

		int n = 1000;  // number of elements

		int[] l = new int[n];
		int seed = 1;
		Random rnd = new Random(seed);

		for (int i = 0; i < n; i++) {
			l[i] = rnd.nextInt(n);
		}
		
		// make a copy of l
		int[] l_copy=Arrays.copyOf(l, l.length);

		// sort the copy using java's built in method
		Arrays.sort(l_copy);

		Sort sort = new Sort();
		
		int digit=3;
		sort.radixSort(l, digit-1);
		
		System.out.println("My sort \t Java Sort");
		for (int i = 0; i < 10; i++) {
			System.out.println(l[i]+"\t-----      "+l_copy[i]);
		}
		System.out.println("\t...");
		
		System.out.println("Are arrays equal? " +Arrays.equals(l, l_copy));

	}
}

