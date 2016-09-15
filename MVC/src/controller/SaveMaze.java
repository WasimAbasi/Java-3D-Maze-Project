package controller;

import model.Model;
import view.View;

/**
 * Class SaveMaze executes the command of saving the specified maze to a file.
 * @author Roaa, Wasim
 *
 */
public class SaveMaze implements Command {

	private Model model;
	private View view;
	
	public SaveMaze(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void doCommand(String[] commandParameters) {
		model.saveMaze(commandParameters[1], commandParameters[2]);
		view.message("Maze "+ commandParameters[1] + " was successfully saved to file " + commandParameters[2] + "!");
	}

}
