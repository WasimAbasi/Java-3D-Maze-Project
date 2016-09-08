package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/* Answers:
 * 
 * 1. The advantage of BFS is that it finds the shortest solution from the start position
 * to the end. It does so by continually adjusting the priority of new and better states
 * which lead to shorter paths.
 * The advantage of DFS is that it is less time consuming. DFS tries a specific route all the
 * way to the end only once. If it reaches the goal position it is through. If it doesn't 
 * it retracts to the last possible cross road and continues from there.
 * 
 * 2. We chose generic programming to code BFS so we wouldn't have to perform casting to the
 * problem's specific position type. It makes the algorithm more generic, capable of supporting
 * different types of problems and easier for future changes.
 *
 */


/**
 * Best First Search Algorithm
 * @author Wasim, Roaa
 *
 * @param <T>
 */
public class BestFirstSearch<T> extends CommonSearcher<T> {
	
	private PriorityQueue<State<T>> openList = new PriorityQueue<State<T>>(); // open list for attending states
	private HashSet<State<T>> closedSet = new HashSet<State<T>>(); // closed set for attended states
	

	
	@Override
	/**
	 * Method Search finds a solution for a searchable type using best first search algorithm
	 */
	public Solution<T> Search(Searchable<T> s) {
		State<T> startState = s.getStartState();
		openList.add(startState); // define the start state
		
		while(!openList.isEmpty()){
			State<T> currState = openList.poll(); // move state from open list to closed set
			closedSet.add(currState);
			if(currState.equals(s.getGoalState())){ // if the state equals the goal state back trace the path
				return backTrace(currState);
			}
			ArrayList<State<T>> neighbors = s.getAllPossibleStates(currState); // get all possible moves
			for(State<T> neighbor : neighbors){
				if(!openList.contains(neighbor)&&!closedSet.contains(neighbor)){
					neighbor.setCameFrom(currState); // if the neighbor is not in open and not in close add it to open
					neighbor.setCost(currState.getCost() + s.getMoveCost(currState, neighbor));
					openList.add(neighbor);
					evaluatedNodes++; // add a node when going to a new state
				}
				else {
					double newPathCost = currState.getCost() + s.getMoveCost(currState, neighbor); // calculate new path cost
					if(neighbor.getCost() > newPathCost){ // if the new path is better
						neighbor.setCost(newPathCost);
						neighbor.setCameFrom(currState);
						if(!openList.contains(neighbor))
							openList.add(neighbor);
							else{ // adjust the priority queue
								openList.remove(neighbor);
								openList.add(neighbor);
						}
					}
				}
			}
		}
		return null;
		}
		
	}


