package algorithms.search;

import java.util.ArrayList;
import java.util.List;

/**
 * DFS Algorithm
 * @author Wasim, Roaa
 *
 * @param <T>
 */
public class DFS<T> extends CommonSearcher<T> {
	
	private ArrayList<State<T>> closedList = new ArrayList<State<T>>();
	
	@Override
	public Solution<T> Search(Searchable<T> s) {
		State<T> startState = s.getStartState();
		RecursiveDFS(s, startState);
		return backTrace(closedList.get(closedList.size()-1));
	}
	
	/**
	 * Method recursiveDFS recursively searches the end point of a Searchable object
	 * beginning from the start point of the object, meaning, it searches for an exit
	 * beginning from the entrance of a searchable problem.
	 * @param s: a Searchable object
	 * @param currentState: State that is being currently examined
	 * @return Boolean: return true if solution was found
	 */
	public boolean RecursiveDFS(Searchable<T> s, State<T> currentState){
		
		closedList.add(currentState);
		
		if (currentState.equals(s.getGoalState())) {
			return true;
		}
			
		List<State<T>> neighbors = s.getAllPossibleStates(currentState);

		for (State<T> neighbor : neighbors){
			if(!closedList.contains(neighbor)){
				evaluatedNodes++;
				neighbor.setCameFrom(currentState);
				if(neighbor==s.getGoalState())
					System.out.println("SCREAMING!!!");
				neighbor.setCost(currentState.getCost() + s.getMoveCost(currentState, neighbor));
				
               if(RecursiveDFS(s, neighbor) == true)
            	   return true;
               }
			}
		return false;
	}
}
