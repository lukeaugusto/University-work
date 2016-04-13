public class Sort {
	
	private int K_CONST = 10;  // constant which decides whether insertion sort is 
						  // used or radix sort
	
	public Sort(){
		
	}
	
	public void radixSort(int[] a, int digit){
		this.radixSort(a, digit, 0, a.length);
	}

	public void radixSort(int[] a, int digit, int from, int to) {
		
		if (to-from <= K_CONST) {
			// perform insertion sort
			insertionSort(a, from, to);
			return;
		}
		
		// otherwise perform radix sort on the digit 'digit' 
		// on indices starting from 'from' all the way to 'to'-1
		
		int i;
		int[] new_a;
		int greater = a[0];
		int exp = 1;
		
		new_a = new int[a.length];
		for (i = 0; i < a.length; i++)
			if (a[i] > greater)
				greater = a[i];
			
		
		while (greater/exp > 0) {
			int[] aux = new int[a.length];
			
			for (i = 0; i < a.length; i++)
				aux[(a[i] / exp) % 10]++; 
		 	
			for (i = 1; i < 10; i++)
				aux[i] += aux[i - 1];
	 		
			for (i = a.length - 1; i >= 0; i--)
				new_a[--aux[(a[i] / exp) % 10]] = a[i];
		 	
			for (i = 0; i < a.length; i++)
		 		a[i] = new_a[i];
			
		 	exp *= 10;
		 }
	}

	
	public static void insertionSort(int[] a, int from, int to){
		// perform insertion sort on the array a[from] - a[to]
		
		int number, i, j;
		
		for(i=from; i<to; i++){
			
			number = a[i];
			j = i;
			
			while(j>from && (a[j-1] > number)){
				a[j] = a[j-1];
				j--;
			}
			
			a[j] = number;
		}
		
	}
	
	public static void insertionSort(int[] a){
		insertionSort(a, 0, a.length);
	}
}
