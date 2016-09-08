package algorithms.search;

/**
 *  Interface Searcher defines the functionality of a searcher algorithm that searches
 *  for a solution to a specific searchable problem.
 * @author Wasim, Roaa
 *
 * @param <T>
 */
public interface Searcher<T> {
	
	/**
	 * Method Search returns the solution of a searchable problem s
	 * @param s {@link Searchable}
	 * @return {@link Solution}
	 */
	public Solution<T> Search(Searchable<T> s);
	
	/**
	 * 
	 * @return how many nodes the algorithm evaluated
	 */
	public int getNumOfNodesEvaluated();

}
