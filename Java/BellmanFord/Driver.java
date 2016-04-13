public class Driver {
	
	public static void main(String[] args) {
		
		int n = 6;
		CurrencyExchange currency = new CurrencyExchange(n);

		currency.addExchange("USD", "EUR", 0.4);
		currency.addExchange("EUR", "CYN", 0.5);
		currency.addExchange("EUR", "RUB", 1);
		currency.addExchange("USD", "CYN", 0.2);
		currency.addExchange("RUB", "CZK", 1.1);
		currency.addExchange("EUR", "CYN", 0.4);
		currency.addExchange("CYN", "BRL", 0.3);
		currency.addExchange("BRL", "RUB", 0.4);
		
		Graph graph = currency.getGraph();
		
		System.out.println("Graph: \n"+currency.getGraph());
		BellmanFord bf = new BellmanFord(graph, 0);
		
		bf.findShortestPath();
		
		System.out.println(bf);
		
		Arbitrage arbitrage;
		arbitrage = currency.findArbitrage();
		System.out.println(arbitrage);	
		
		currency.addExchange("CZK", "EUR", 0.95);
		arbitrage = currency.findArbitrage();
		System.out.println(arbitrage);
	}
}
