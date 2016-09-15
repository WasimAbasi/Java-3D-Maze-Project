package controller;

import model.MyModel;
import view.MyView;

/**
 * Class Solve executes the command of solving the specified maze.
 * @author Roaa, Wasim
 *
 */
public class Solve implements Command{

	private MyModel model;
	private MyView view;
	
	public Solve(MyModel model) {
		this.model = model;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		try {
			if( (model.getMaze(commandParameters[1]) != null) && 
					(commandParameters[2].toLowerCase().equals("bfs") || commandParameters[2].toLowerCase().equals("dfs")) ){
				model.solveMaze(commandParameters[1] , commandParameters[2]);
			}
			else{
				view.error("Invalid Parameters!");
			}
		} catch (Exception e) {
			view.error("Invalid Parameters!");
		}
	}

}
