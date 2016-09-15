package controller;

import model.Model;
import view.View;

/**
 * Class GenerateMaze executes the command of generating a new maze using
 * the parameters specified by the user.
 * @author Roaa, Wasim
 *
 */
public class GenerateMaze implements Command{

	private Model model;
	private View view;
	String[] commandParameters;	
	Thread thread;
	
	public GenerateMaze(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {

		try {
			model.generateMaze(commandParameters[1], Integer.parseInt(commandParameters[2]), Integer.parseInt(commandParameters[3]), 
					Integer.parseInt(commandParameters[4]), commandParameters[5]);
		} catch (ArrayIndexOutOfBoundsException e) {
			view.error("Incorrect Parameters!");
		}
	}
}
