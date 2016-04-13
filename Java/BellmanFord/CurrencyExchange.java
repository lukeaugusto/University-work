/**
 * Lucas Augusto dos Santos
 * CSE2010
 * Lab7 - Arbitrage
*/

import java.util.LinkedList;

public class CurrencyExchange {
	private int totalCurrencies = 0;
	private LinkedList<String> currencies;
	private Graph graph;
	
	public CurrencyExchange(int totalNumberCurrencies){
		// implement constructor
		graph = new Graph(totalNumberCurrencies);
		currencies = new LinkedList<String>();
	}
	
	
	public void addExchange(String currencyFrom, String currencyTo,
			double price){
		// add a weighted edge from the currencyFrom to the currencyTo, 
		// if the edge exists, replace it if new price is larger.
		int indexFrom, indexTo;
		
		indexFrom = currencies.indexOf(currencyFrom);
		indexTo = currencies.indexOf(currencyTo);
		
		// If the edge does not exist, or if the weight is lower than the new: update the value.
		if(indexFrom == -1 || indexTo == -1 || graph.getWeight(indexFrom, indexTo) < price){
			
			// Create a new currency if it does not exist
			if(indexFrom == -1){
				indexFrom = currencies.size();
				currencies.add(currencyFrom);
			}
			if(indexTo == -1){
				indexTo = currencies.size();
				currencies.add(currencyTo);
			}
			// Update the weight value
			graph.addWeightedEdge(indexFrom, indexTo, price);
		}
	}
	
	public Graph getGraph(){
		return this.graph;
	}
	
	/**
	 * Find the highest cycle in the BST.
	 */
	public LinkedList<Integer> findCycle(BellmanFord bf){
		
		LinkedList<Integer>[] paths = bf.getPaths();
		LinkedList<Integer>[] cycles = new LinkedList[paths.length];
		
		double highestWeight = 0.0;
		Integer highestCycle = null;
		for(int i=0; i<paths.length; i++){
			cycles[i]=new LinkedList<Integer>();
		}
		
		for(int i=0; i<paths.length; i++){
			
			// If the path contains a multiple cycle, save it in cycles.
			if(paths[i].get(0) == -1){
				cycles[i].add(paths[i].get(1));
				for(int j=2; j< paths[i].size(); j++){
					if(paths[i].get(j) != paths[i].get(1))
						cycles[i].add(paths[i].get(j));
					else break;
				}
				cycles[i].add(paths[i].get(1));
				
				// Check the weight of the cycles and chose the highest.
				if(getPathWeight(cycles[i]) > highestWeight) highestCycle = i; 
			}
		}
		if(highestCycle != null) return cycles[highestCycle];
		return null;
	}
	
	/**
	 * Return the weight of the path.
	 */
	public double getPathWeight(LinkedList<Integer> path){
		if(path != null){
			double weight = 1.0;
			for(int i=0; i<path.size()-1; i++)
				weight *= graph.getWeight(path.get(i), path.get(i+1));
			
			return weight;
		}
		return 0;
	}
	
	public Arbitrage findArbitrage(){
		// find arbitrage, if more than 1 cycle exists return arbitrage of 
		// the highest cost. If none cycles exist return null.
		BellmanFord bf;
		Arbitrage arb = null;
		bf = new BellmanFord(graph, 0);
		
		bf.findShortestPath();
		bf.getPaths();
		
		LinkedList<Integer> cycle = findCycle(bf);
		
		// Create the arbitrage for the highest cycle and check it
		if(cycle != null){
			String from = currencies.get(cycle.getFirst());
			double cost = getPathWeight(cycle);
			
			arb = new Arbitrage(from, cost);
			
			for(int i=0; i<cycle.size(); i++)
				arb.addLink(currencies.get(cycle.get(i)));
			
			if(!arb.isRealArbitrage()) arb = null;
		}
		return arb;
	}
	
}
