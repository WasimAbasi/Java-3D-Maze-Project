package algorithms.mazeGenerators;

import java.util.LinkedList;
import java.util.Random;

/**
* Class RandomChooser is a specific chooser that chooses a Position from a list of 
* Positions randomly.
* @author Wasim, Roaa
*
*/
public class RandomChooser implements Chooser {

	@Override
	public Position choose(LinkedList<Position> C) {
		int bound = C.size();
		Random ran = new Random();
		int index = ran.nextInt(bound);
		return C.get(index);
	}

}
