
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Driver {
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		
		// Part 1: BFS & DFS
		Graph g = new Graph(10);

		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(0, 7);

		g.addEdge(1, 4);

		g.addEdge(2,5);
		g.addEdge(2,6);

		g.addEdge(6, 3);
		g.addEdge(6, 9);
		g.addEdge(8, 4);
		g.addEdge(8, 9);

		g.addEdge(9, 7);

		// print out the graph
		System.out.println(g.toString());

		// fix source node
		int source=0;


		System.out.println("BFS graph:");
		Graph bfs=g.bfs(source);
		System.out.println(bfs.toString());

		System.out.println("DFS graph:");
		Graph dfs=g.dfs(source);
		System.out.println(dfs.toString());
		
		
		// Part 2: Maze
		
		
		// dimension of the maze
		int n = 4;
		int m = 6;
		boolean[][] maze = new boolean[n][m];
		for (int i = 0; i < maze.length; i++) {
			Arrays.fill(maze[i], true);
		}
		
		// make some locations unaccessible 
		for (int i = 1; i < maze.length-1; i++) {
			for (int j = 1; j < maze[0].length-1; j++) {
				maze[i][j] = false;
			}
		}
		
		MazeGraph mazeGraph = new MazeGraph(maze);

		Pair<Integer, Integer> starting = new Pair<Integer, Integer>(0, 0);
		Pair<Integer, Integer> target = new Pair<Integer, Integer>(n-1, m-1);
		
		
		System.out.println("Original maze:");
		mazeGraph.printPath(maze, new LinkedList<Pair<Integer,Integer>>());
		
		
		
		LinkedList<Pair<Integer, Integer>> path = mazeGraph.findPath(starting, target);
		
		System.out.println("Path:");
		// path is in reversed order, to reverse it in time proportional to 
		// it's size use descrendingIterator()
		Iterator<Pair<Integer, Integer>> reverseIterator = path.descendingIterator();
		while (reverseIterator.hasNext()) {
			Pair<Integer, Integer> pair = (Pair<Integer,Integer>) reverseIterator
					.next();
			
			System.out.print(pair);
			if (reverseIterator.hasNext()) {
				System.out.print(" -> ");
			}
			
			
		}
		System.out.println();
		
		// Path with solution
		System.out.println("Maze with solution:");
		mazeGraph.printPath(maze, path);
	}

}
