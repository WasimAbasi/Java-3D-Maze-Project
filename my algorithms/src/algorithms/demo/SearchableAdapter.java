package algorithms.demo;

import java.util.ArrayList;

import algorithms.search.Searchable;
import algorithms.search.State;
import algorithms.mazeGenerators.*;

/**
 * 
 * Class SearchableAdapter is an Object Adapter that adapts Maze3d to a searchable problem.
 * @author Wasim, Roaa
 *
 */
public class SearchableAdapter implements Searchable<Position> {
	private Maze3d maze3d;
	
	public Maze3d getM3d() {
		return maze3d;
	}
	public void setM3d(Maze3d maze3d) {
		this.maze3d = maze3d;
	}
	public SearchableAdapter(Maze3d maze3d)
	{
		this.maze3d = maze3d;
	}
	@Override
	public State<Position> getStartState()
	{
		return new State<Position>(this.maze3d.getStartPosition());
	}
	@Override
	public State<Position> getGoalState()
	{
		return new State<Position>(this.maze3d.getGoalPosition());
	}
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> sp)
	{
		ArrayList<Position> ap =  this.maze3d.getAllPossibleMoves(sp.getValue());
		ArrayList<State<Position>> asp = new ArrayList<State<Position>>() ;
		if(ap == null) return null ;
		for(Position p : ap)
		{
			asp.add(new State<Position>(p));
	    }
		
		return asp ;
	}
	@Override
	public double getMoveCost(State<Position> currState, State<Position> neighbor) {
		return 10;
	}

}