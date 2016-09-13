package controller;

import model.MyModel;

/**
 * Class exits executes the command exit which performs a safe exit from the command line interface.
 * @author Roaa, Wasim
 *
 */
public class Exit implements Command {

	private MyModel model;
	
	public Exit(MyModel model) {
		this.model = model;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		model.exit();
	}
}
