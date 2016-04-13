
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Graph {
	private int V; // number of vertices
	private int E; // number of edges
	public ArrayList<Integer>[] adj; // adjacency list representation 
	

	public Graph(int V){
		// constructor
		adj = (ArrayList<Integer>[])new ArrayList[V];
		
		for(int i=0;i<V;i++)
			adj[i] = new ArrayList<Integer>();
		
		this.V = V;
	}


	public int V(){
		return V;
	}

	public int E(){
		return E;
	}
	
	// Perform Bread-First search and return a graph representing it. 
	public Graph bfs(int s) {
		int i;
		Integer actual, next;
		Graph that = new Graph(V);
		Queue<Integer> peers = new LinkedList<Integer>();
		Boolean[] verif = new Boolean[V];
		
		// Set all elements as non visited
		for(i=0;i<V;i++)
			verif[i] = false;
		
		peers.add(s);
		verif[s] = true;
		
		while(!peers.isEmpty()){
			actual = peers.peek();
			Queue<Integer> actual_peers = new PriorityQueue<Integer>();
			
			// Push the non-visited peers of the position to a Priority Queue
			for(i=0; i<this.adj[actual].size(); i++)
				if(!verif[this.adj[actual].get(i)]) actual_peers.add(this.adj[actual].get(i));
			
			peers.poll();
			peers.addAll(actual_peers);
			
			// Get the next row of non-visited peers and add them to the Queue
			while(!actual_peers.isEmpty()){
				next = actual_peers.peek();
				Stack<Integer> next_peers = new Stack<Integer>();
				
				that.addEdge(actual, next);
				verif[next] = true;
				
				for(i=0; i<this.adj[next].size(); i++)
					if(!verif[this.adj[next].get(i)]) next_peers.push(this.adj[next].get(i));
				
				Collections.reverse(next_peers);
				actual_peers.poll();
				peers.addAll(next_peers);
			}
		}
		return that;
	}

	// Perform Depth-First search and return a graph representing it. 
	public Graph dfs(int s){
		
		int i;
		Integer actual;
		Graph that = new Graph(V);
		Stack<Integer> peers = new Stack<Integer>();
		Boolean[] verif = new Boolean[V];
		
		// Set all elements as non visited
		for(i=0;i<V;i++)
			verif[i] = false;
		
		peers.push(s);
		verif[s] = true;
		
		while(!peers.isEmpty()){
			actual = peers.peek();
			Stack<Integer> actual_peers = new Stack<Integer>();
			
			// Push the edges of the position to a stack
			for(i=0; i<this.adj[actual].size(); i++)
				actual_peers.push(this.adj[actual].get(i));
			
			// Get the biggest non visited peer
			Collections.sort(actual_peers);
			while(!actual_peers.isEmpty()){
				if(verif[actual_peers.peek()])
					actual_peers.pop();
				else
					break;
			}
			// Check if all elements were visited
			if(!actual_peers.isEmpty()){
				that.addEdge(actual, actual_peers.peek());
				verif[actual_peers.peek()] = true;
				peers.push(actual_peers.peek());
			}
			else
				peers.pop();
		}
		return that;
	}
	
	public void addEdge(int v, int w){
		// add edge between vertex v and vertex w
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}

	public ArrayList<Integer> adj(int v){
		return adj[v];
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (int w : adj[v]) {
				s.append(w+" ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}


	/**Override equals methods for graphs: compare number of edges, vertices and the
	 * adjacency lists correspondence. Nothing to implement here ( will be used for grading)
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Graph)) {
			return false;
		}

		Graph that = (Graph) other;

		boolean isAdjSame=true;

		// iterate over adjacency list to check if they are the same
		try{
			for (int i = 0; i < Math.max(this.adj.length, that.adj.length); i++) {
				// sort so that order doesn't matter
				Collections.sort(this.adj[i]);
				Collections.sort(that.adj[i]);
				for (int j = 0; j < Math.max(this.adj[i].size(), that.adj[i].size()); j++) {
					if (this.adj[i].get(j)!=that.adj[i].get(j)){
						isAdjSame=false;
						// once at least one is found there is no need to continue
						break;
					}
				}
				if (!isAdjSame) break;
			}

		}catch(ArrayIndexOutOfBoundsException e){
			isAdjSame=false;
		}

		// if graphs are the same all should match
		return this.V==(that.V)&& this.E==(that.E)&&isAdjSame;
	}

}
