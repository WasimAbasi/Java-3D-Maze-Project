package algorithms.search;

import java.util.ArrayList;

/**
 * Class Solution represents a generic solution for a generic searchable problem
 * @author Wasim, Roaa
 *
 * @param <T>
 */
public class Solution<T> {
	
	//solution is represented by a list of states that make up the solution
	private ArrayList<State<T>> statesList = new ArrayList<State<T>>();

	/**
	 * @return the states list
	 */
	public ArrayList<State<T>> getStatesList() { 
		return statesList;
	}

	/**
	 * set the states list
	 * @param statesList
	 */
	public void setStatesList(ArrayList<State<T>> statesList) {
		this.statesList = statesList;
	}

	@Override
	public String toString() { // prints the solution
		StringBuilder sb = new StringBuilder();
		for(State<T> s :statesList){
			sb.append(s.toString()).append(" ");
		}
		return sb.toString();
	}
	
	
	
	

}
