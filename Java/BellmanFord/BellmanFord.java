/**
 * Lucas Augusto dos Santos
 * CSE2010
 * Lab7 - Arbitrage
*/

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class BellmanFord {
	
	private Graph graph;
	private Integer source;
	private double[] dist;
	private Integer[] predecessor;
	private LinkedList<Integer>[] paths;
	
	/**
	 * Constructor which initializes graph, source, dist and predecessor
	 * arrays
	 * @param g Graph to use
	 * @param source vertex for which calculations have to be performed
	 */
	public BellmanFord(Graph g, int source){
		graph=g;
		this.dist=new double[g.V()];
		
		// Instantiate predecessor Array
		this.predecessor = new Integer[g.V()];
		for (int i = 0; i < dist.length; i++) {
			this.predecessor[i] = null;
		}

		// Instantiate path Array
		paths = new LinkedList[g.V()];
		for(int v=0;v<g.V();v++){
			paths[v]=new LinkedList<Integer>();
		}
		
		this.source = source;
	}
	
	public double getShortestPath(int to){
		return dist[to];
	}
	
	
	/**
	 * Perform Bellman-Form algorithm.
	 */
	public void findShortestPath(){
		
		Stack<Integer> aux = new Stack<Integer>();
		
		// Initialize the distance and the predecessor 
		for(int i=0; i<dist.length; i++)  dist[i] = Double.MIN_VALUE;
		for(int i=0; i<predecessor.length; i++) predecessor [i] = null;

		// Set the first distance
		dist[source] = 1.0;
		
		// Iterate over all edges
		for(int i=1; i<graph.V()-1; i++){
			for(int j=0; j<graph.V(); j++){
				aux.addAll(graph.adj(j));
				while(!aux.isEmpty() && aux.peek() != j)
					// Perform the modified relax method
					relaxEdge(j, aux.pop()); 
			}
		}
		
		buildPaths();
	}
	/**
	 * Build all paths from source to every element in the BST
	 * If the element does not have any connection with the source it creates a new track
	 * path[0] is designed to inform if there are any cycle in the path
	 * 		-1: there is a cycle in the path.
	 * 		0: there isn't cycles in the path.
	 */
	private void buildPaths(){
		for(int i=0; i<predecessor.length; i++){
			
			Integer pred = i;
			paths[i].addFirst(i);
			int count = 0;
			
			// Start with the element at the end of the track and go until find the source
			while(pred != source && pred != null){
				
				pred = predecessor[pred];
				 
				// If there is a cycle in the path break the loop  
				if(paths[i].indexOf(pred) != -1) count ++;
				paths[i].addFirst(pred);
				if(count > 0)
					break;
			}
			
			if(i==0) paths[i].addFirst(null); 
			
			// Set the flag at path[0] for cycles
			if(count >0 ) paths[i].addFirst(-1);
			else paths[i].addFirst(0);
		}
	}
	
	public LinkedList<Integer>[] getPaths(){
		return paths;
	}
	/**
	 * Relaxation method for Bellman-Ford algorithm
	 * @param u	vertex from
	 * @param v vertex to
	 */
	public void relaxEdge(int u, int v){
		if (dist[u] == Double.MIN_VALUE ) return;
		if (dist[u] * graph.getWeight(u, v) > dist[v]) {
			dist[v] = dist[u] * graph.getWeight(u, v);
			predecessor[v] = u;
		}
		
	}
	
	@Override
	public String toString() {
		
		String NEWLINE = System.getProperty("line.separator");
		StringBuilder s = new StringBuilder();
		
		// Print the weight and predecessor for each end of track
		String dest = "Dest: [";
		String prec = "Prec: [";
		for(int i=0; i<predecessor.length; i++){
			dest = dest + (i>0 ? ", " : "") + dist[i];
			prec = prec + (i>0 ? ", " : "") + predecessor[i];
		}
		dest = dest + "]";
		prec = prec + "]";
		s.append(dest + NEWLINE + prec + NEWLINE);
		
		// Print the path for every track and put one note if there is a cycle in the path
		for(int i=0; i<predecessor.length; i++){
			String path = "";
			if(paths[i].get(0) == -1) path = path + "Multiple-Cycle between ("+ paths[i].get(1) + ") and (" +  paths[i].get(1) + "): ";

			path = path + "[";
			for(int j=1; j<paths[i].size(); j++){
				path = path + paths[i].get(j) + (j == paths[i].size()-1 ? "" : ", ");
			}
			s.append(path + "]" +NEWLINE);
		}
		
		return s.toString();
	}

}
