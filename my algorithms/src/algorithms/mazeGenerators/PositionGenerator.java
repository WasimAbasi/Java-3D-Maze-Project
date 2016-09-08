package algorithms.mazeGenerators;

/**
* Class PositionGenerator generates and returns a new position either on a specific
* floor or anywhere in the maze.
* @author Wasim, Roaa
*
*/
import java.util.Random;

public class PositionGenerator {
	
	private Random ran;
	
	PositionGenerator(){
		ran = new Random();
	}
	
	public Position randomPosition(int x, int y, int z){
		Position p = new Position(ran.nextInt(x-2)+1, ran.nextInt(y-2)+1, ran.nextInt(z-2)+1);
		return p;
	}
	
	public Position specificFloorRandomPosition(int x, int y, int floor){
		Position p = new Position(ran.nextInt(x-2)+1,  ran.nextInt(y-2)+1, floor);
		return p;
	}
}