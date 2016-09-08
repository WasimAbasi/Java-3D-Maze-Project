package algorithms.mazeGenerators;

/**
* Class Run is a tester class which runs a main method and tests the maze generator algorithms.
* @author Wasim, Roaa
*
*/
public class Run {
	
	private static void testMazeGenerator(Maze3dGenerator mg){
		// prints the time it takes the algorithm to run
		System.out.println(mg.measureAlgorithmTime(9, 9, 9));
		// generate another 3d maze
		Maze3d maze=mg.generate(9, 9, 9);
		// get the maze entrance
		Position p=maze.getStartPosition();
		// print the position
		System.out.println(p); // format "{x,y,z}"
		// get all the possible moves from a position
		String[] moves=maze.getPossibleMoves(p);
		// print the moves
		for(String move : moves)
			System.out.println(move);
		// prints the maze exit position
		System.out.println(maze.getGoalPosition());
		try{
			// get 2d cross sections of the 3d maze
			int[][] maze2dx=maze.getCrossSectionByX(2);
			for(int i=0; i<11; i++){
				for(int j=0; j<11; j++){
					System.out.print(maze2dx[i][j]);
				}
				System.out.println();
			}
			int[][] maze2dy=maze.getCrossSectionByY(5);
			for(int i=0; i<11; i++){
				for(int j=0; j<11; j++){
					System.out.print(maze2dy[i][j]);
				}
				System.out.println();
			}
			int[][] maze2dz=maze.getCrossSectionByZ(0);
			for(int i=0; i<11; i++){
				for(int j=0; j<11; j++){
					System.out.print(maze2dz[i][j]);
				}
				System.out.println();
			}
			// this should throw an exception!
			maze.getCrossSectionByX(-1);
		} catch (IndexOutOfBoundsException e){
			System.out.println("good!");
		}
	}
	public static void main(String[] args) {
		testMazeGenerator(new SimpleMaze3dGenerator());
		testMazeGenerator(new GrowingTreeGenerator());
	}
}
