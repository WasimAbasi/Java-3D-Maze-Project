package algorithms.mazeGenerators;

/**
* A Chooser object that chooses a Position from a list of Positions by taking the last
* one added to the list.
* @author Wasim, Roaa
*
*/
import java.util.LinkedList;

public class LastCellChooser implements Chooser {

	@Override
	public Position choose(LinkedList<Position> C) {
		return C.getLast();
	}

}
