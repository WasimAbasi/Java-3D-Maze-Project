
package algorithms.search;

import java.io.Serializable;

/**
 * Class State represents a generic state in a searchable problem
 * @author Wasim, Roaa
 * 
 * @param <T>
 */
public class State<T> implements Serializable, Comparable<State<T>> {
	
	private static final long serialVersionUID = -335412955882326284L;
	private T value; // represents the state
	private double cost = 0;  // cost to reach this state
	private State<T> cameFrom;  // the state we came from to reach this state
	
	public State(T value){ // State Constructor
		this.value = value;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {  // override equals
		State<T> s = (State<T>)obj;
		return s.value.equals(this.value);
	}
	
/**
 * @return value (which represents the state)
 */
	public T getValue() {
		return value;
	}

	/**
	 * 
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return the state we came from
	 */
	public State<T> getCameFrom() {
		return cameFrom;
	}

	/**
	 * set the state we came from
	 * @param cameFrom {@link State}
	 */
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	/**
	 *  set value
	 * @param value
	 */
	public void setValue(T value) { 
		this.value = value;
	}

	/**
	 * set cost
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * @return an integer >0 if this.cost>s.cost, <0 if this.const<s.cost and =0 if their 
	 * costs are equal.
	 */
	@Override
	public int compareTo(State<T> s) {
		return (int) (this.getCost()-s.getCost()); // return >0 if this>s //  <0 if this<s 
	}
	
	
	
	
	

}
