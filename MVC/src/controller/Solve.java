package controller;

import model.Model;
import view.View;

/**
 * Class Solve executes the command of solving the specified maze.
 * @author Roaa, Wasim
 *
 */
public class Solve implements Command{

	private Model model;
	private View view;
	
	public Solve(Model model, View view) {
		this.model = model;
		this.view = view;
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
