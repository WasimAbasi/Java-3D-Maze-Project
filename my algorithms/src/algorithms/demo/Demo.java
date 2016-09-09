package algorithms.demo;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.*;

/**
 * 
 * Class Demo runs a main method that tests algorithms.mazeGenerators and algorithms.search.
 * @author Wasim, Roaa
 *
 */
public class Demo {
	private static void run(Maze3dGenerator mg)
	{
		
		SearchableAdapter maze = new SearchableAdapter(mg.generate(7, 7, 7));
		
		for(int i=0; i<9; i++){
			System.out.println("{");
			for(int j=0; j<9; j++){
				for(int k=0; k<9; k++){
					System.out.print(maze.getM3d().GetMaze3d(new Position(j, k, i)));
				}
				System.out.println();
			}
			System.out.print("}");
			System.out.println();
		}
		
		Searcher<Position> bfs = new BestFirstSearch<Position>();
		bfs.Search(maze);

		System.out.println("BFS:");
		System.out.println(bfs.getNumOfNodesEvaluated());
		
		Searcher<Position> dfs = new DFS<Position>();
		dfs.Search(maze);

		System.out.println("DFS:");
		System.out.println(dfs.getNumOfNodesEvaluated());
	}

	public static void main(String[] args) {
		run(new GrowingTreeGenerator());
		run(new SimpleMaze3dGenerator());
	}
}
