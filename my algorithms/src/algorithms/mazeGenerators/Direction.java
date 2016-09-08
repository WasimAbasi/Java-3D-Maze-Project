package algorithms.mazeGenerators;

/**
 * 
 * Helper Class Direction represents all possible directions within a specific floor:
 * Left, Right, Forward, Back.
 * @author Wasim, Roaa
 *
 */

public class Direction {
	int direction;
	Direction(int direction){
		this.direction = direction;
	}
	public String toString(){
		switch (direction)
		{
		case 0: return "Left";
		case 1: return "Right";
		case 2: return "Forward";
		case 3: return "Back";
		default: return "";
		}
	}
	public void nextDirection() {
		direction = (direction + 1) % 4;
		
	}
}
