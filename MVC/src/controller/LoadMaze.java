package controller;

import java.io.IOException;

import model.MyModel;
import view.MyView;

/**
 * Class LoadMaze executes the command for loading the maze from a specific file.
 * @author Roaa, Wasim
 *
 */
public class LoadMaze implements Command {

	private MyModel model;
	private MyView view;
	
	public LoadMaze(MyModel model, MyView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] commandParameters) {
		try{
			model.loadMaze(commandParameters[1] , commandParameters[2]);
			view.message("Maze " + commandParameters[2] + " has been succefully loaded!");
		}catch(IOException e){
			view.error("Error occured while loading the file!");
		}
	}

}
