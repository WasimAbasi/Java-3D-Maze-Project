package controller;

import model.MyModel;
import view.MyView;

/**
 * Class SaveMaze executes the command of saving the specified maze to a file.
 * @author Roaa, Wasim
 *
 */
public class SaveMaze implements Command {

	private MyModel model;
	private MyView view;
	
	public SaveMaze(MyModel model, MyView view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] commandParameters) {
		model.saveMaze(commandParameters[1], commandParameters[2]);
		view.message("Maze "+ commandParameters[1] + " was successfully saved to file " + commandParameters[2] + "!");
	}

}
