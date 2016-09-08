package algorithms.mazeGenerators;

/**
* 
* Abstract Class CommonMaze3dGenerator implements the functionality measureAlgorithmTime
* that is common to all Maze3dGenerators.
* @author Wasim, Roaa
*
*/ 

public abstract class CommonMaze3dGenerator implements Maze3dGenerator {

	@Override
	public abstract Maze3d generate(int x, int y, int z);

	@Override
	public String measureAlgorithmTime(int x, int y, int z) {
		long start = System.currentTimeMillis();
		generate(x, y, z);
		long finish = System.currentTimeMillis();
		return Long.toString(finish-start);
	}

}
