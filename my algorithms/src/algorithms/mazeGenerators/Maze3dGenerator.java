package algorithms.mazeGenerators;

/**
 * Interface Maze3dGenerator defines the functionality of a three dimensional maze generator
 * which is generating a new 3d maze and measuring the time it takes for the generating
 * algorithm to finish its task.
 * @author Wasim, Roaa
 *
 */
public interface Maze3dGenerator {
	/**
	 * Method generate generates a new maze 3d and returns it.
	 * @param x : number of rows
	 * @param y : number of columns
	 * @param z : number of floors
	 * @return Maze3d : returns a Maze3d object
	 */
	public Maze3d generate(int x, int y, int z);
	
	/**
	 * @param x : number of rows
	 * @param y : number of columns
	 * @param z : number of floors
	 * @return String : returns a string that represents the time in milliseconds that the
	 * algorithm takes to generate the maze
	 */
	public String measureAlgorithmTime(int x, int y, int z);

}

