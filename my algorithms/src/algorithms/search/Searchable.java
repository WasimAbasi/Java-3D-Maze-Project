package algorithms.search;

import java.util.ArrayList;

/**
 * Interface Searchable defines the functionality of a generic searchable problem
 * @author Wasim, Roaa
 *
 */
public interface Searchable<T> {
	
	/**
	 * 
	 * @return the start state
	 */
	public State<T> getStartState(); 
	
	/**
	 * 
	 * @return the goal state
	 */
	public State<T> getGoalState(); 
	
	/**
	 * @param s {@link State}
	 * @return <T>: returns all next possible states from this state s
	 */
	public ArrayList<State<T>> getAllPossibleStates(State<T> s);
	
	/**
	 * 
	 * @param currState {@link State}
	 * @param neighbor {@link State}
	 * @return <T>: returns the move cost from current state to a neighboring state
	 */
	public double getMoveCost(State<T> currState, State<T> neighbor); 
	

}
