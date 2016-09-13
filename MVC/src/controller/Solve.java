package controller;

import model.MyModel;
import view.MyView;

/**
 * Class Solve executes the command of solving the specified maze.
 * @author Roaa, Wasim
 *
 */
public class Solve implements Command, Runnable{

	private MyModel model;
	private MyView view;
	Thread thread;
	String[] commandParameters;
	
	public Solve(MyModel model) {
		this.model = model;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		if( (model.getMaze(commandParameters[1]) != null) && (commandParameters[2].equals("bfs") || commandParameters[2].equals("dfs")) ){
			this.commandParameters = commandParameters;
			thread = new Thread(this);
			thread.start();
		}
		else{
			view.error("Invalid Parameters!");
		}
	}
	
	@Override
	public void run() {
		model.solveMaze(commandParameters[1] , commandParameters[2]);
		view.message("Solution for maze " + commandParameters[1] + "- is ready!");	
	}

}
