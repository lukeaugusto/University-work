
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class MazeGraph extends Graph{
	private Integer columns, rows;

	public MazeGraph(boolean[][] maze){
		// Create a graph from two-dimensional maze matrix.
		super(maze.length*maze[0].length);
		
		this.rows = maze.length;
		this.columns = maze[0].length;
		
		for (int i=0; i<maze.length; i++){
		    for (int j=0; j<maze[i].length; j++){	
		        if(maze[i][j]){
		        	// Add an Edge with the element up
		        	if(i-1 >= 0  && maze[i-1][j])
		        		this.addEdge(findVertex(i, j), findVertex(i-1,j));
		        	// Add an Edge with the element in the right
		        	if(j+1 < maze[i].length && maze[i][j+1])
		        		this.addEdge(findVertex(i, j), findVertex(i,j+1));	
		        }
		    }
		}
	}
	
	public static void printPath(boolean[][] maze, LinkedList<Pair<Integer, Integer>> path) {
		// Print maze where for location which is accessible print "x", but 
		// for one which is not - "o"; for locations which are part of the path
		// print "*"
		
		for (int i=0; i<maze.length; i++){
		    for (int j=0; j<maze[i].length; j++){
		    	Pair<Integer, Integer> position = new Pair<Integer, Integer>(i, j);
		    	if(path.indexOf(position) != -1) System.out.print("*");
		    	else{
			        if(maze[i][j]) System.out.print("x");
			        else System.out.print("o");
		    	}
		    }
	    	System.out.println();
		}
	}
	
	private Integer findVertex(Integer first, Integer second){
		return (this.columns * first) + second;
	}
	
	private Integer findVertex(Pair<Integer, Integer> Position){
		return (this.columns * Position.getFirst()) + Position.getSecond();
	}
	
	private Pair<Integer, Integer> findPosition(Integer Vertex){
		Integer first, second;
		first = Vertex / this.columns;
		second = Vertex - (first * this.columns); 
		Pair<Integer, Integer> Position = new Pair<Integer, Integer>(first, second);
		return Position;
	}
	
	
	public LinkedList<Pair<Integer, Integer>> findPath(Pair<Integer, Integer> startingPosition,
		Pair<Integer, Integer> targetPosition){
		// Find a path from starting to the target position in a maze and
		// return it.
		// DFS
		
		int i;
		LinkedList<Pair<Integer, Integer>> Path = new LinkedList<Pair<Integer, Integer>>();
		Integer actual, startingPoint, targetPoint;
		Stack<Integer> peers = new Stack<Integer>();
		Boolean[] verif = new Boolean[this.V()];
		
		// Set all elements as non visited
		for(i=0;i<this.V();i++)
			verif[i] = false;
		
		// Find the starting and target Vertex in the Graph
		startingPoint = findVertex(startingPosition);
		targetPoint = findVertex(targetPosition);
		
		peers.push(startingPoint);
		verif[startingPoint] = true;

		Path.add(startingPosition);
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
				Path.add(findPosition(actual_peers.peek()));
				// If find the element interrupt the search
				if(actual_peers.peek() == targetPoint) break;
				verif[actual_peers.peek()] = true;
				peers.push(actual_peers.peek());
			}
			else{
				peers.pop();
				Path.removeLast();
			}
		}

		Collections.reverse(Path);
		return Path;
	}
}
