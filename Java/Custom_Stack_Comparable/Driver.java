import java.util.Random;

public class Driver {
	public static void main(String[] args) {
			
		final int seed = 1;
		final int n = 15;
		final int max_integer_to_generate = 100000;
		final Random rnd = new Random(seed);

		System.out.println("Starting browser session...");
		BackButtonBackend browser_session = new BackButtonBackend();

		for (int i = 0; i < n; i++) { 
			
			int random_integer = rnd.nextInt(max_integer_to_generate); 
			String url = "http://tiffzhang.com/startup/index.html?s=" + random_integer;

			browser_session.add(url, random_integer);
		} 
		
		System.out.println(browser_session); 
		System.out.println("Pressing back, foward OR backMostTimeSpent..."); 
		System.out.println("Back: \n" +browser_session.back()); 
		System.out.println("Back to the most time spent: \n" + browser_session.backMostTimeSpent()); 
		System.out.println(browser_session); 
		System.out.println("Forward: \n" +browser_session.forward()); 
		System.out.println("Forward: \n" +browser_session.forward()); 
		System.out.println(browser_session); 
		System.out.println("Back to the most time spent: \n" + browser_session.backMostTimeSpent()); 
		System.out.println("Back to the most time spent: \n" + browser_session.backMostTimeSpent()); 
		System.out.println("Back: \n" +browser_session.back()); 
		System.out.println("Back: \n" +browser_session.back()); 
		System.out.println(browser_session); 
		System.out.println("Back to the most time spent: \n" + browser_session.backMostTimeSpent());

	} 
}