package controller;

import model.Model;

/**
 * Class exits executes the command exit which performs a safe exit from the command line interface.
 * @author Roaa, Wasim
 *
 */
public class Exit implements Command {

	private Model model;
	
	public Exit(Model model) {
		this.model = model;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		model.exit();
	}
}
