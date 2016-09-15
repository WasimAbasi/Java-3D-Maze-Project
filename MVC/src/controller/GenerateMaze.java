package controller;

import model.MyModel;
import view.MyView;

/**
 * Class GenerateMaze executes the command of generating a new maze using
 * the parameters specified by the user.
 * @author Roaa, Wasim
 *
 */
public class GenerateMaze implements Command{

	private MyModel model;
	private MyView view;
	String[] commandParameters;	
	Thread thread;
	
	public GenerateMaze(MyModel model, MyView view) {
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
