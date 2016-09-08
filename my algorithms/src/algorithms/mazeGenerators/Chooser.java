package algorithms.mazeGenerators;

/**
 * Interface Chooser defines the functionality of a Chooser object. 
 * Given a List of Position objects, a chooser chooses one and returns it.
 * @author Wasim, Roaa
 *
 */
import java.util.LinkedList;

public interface Chooser {
	Position choose(LinkedList<Position> C);
}
