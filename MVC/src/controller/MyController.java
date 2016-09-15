package controller;

import java.util.HashMap;

import model.Model;
import view.View;

/**
 * Class MyController represents a controller which implements the controller side of the MVC pattern.
 * @author Roaa, Wasim
 *
 */
public class MyController implements Controller {
	
	View view;
	Model model;
	HashMap<String,Command> commands;
	
	public MyController(View view, Model model) {
		this.view = view;
		this.model = model;
		initiateCommands();
		this.view.setCommands(commands);
	}
	
	@Override
	public void initiateCommands() {
		commands = new HashMap<String,Command>();
		
		commands.put("dir", new Dir(this.view));	
		commands.put("generate_maze", new GenerateMaze(this.model, this.view));   
		commands.put("display", new Display(this.model, this.view));
		commands.put("display_cross_section", new DisplayCrossSection(this.model, this.view)); 
		commands.put("save_maze", new SaveMaze(this.model, this.view));	
		commands.put("load_maze", new LoadMaze(this.model, this.view));
		commands.put("solve", new Solve(this.model, this.view)); 
		commands.put("display_solution", new DisplaySolution(this.model, this.view));
		commands.put("exit", new Exit(this.model));
	}

	@Override
	public void error(String errorMessage) {
		view.error(errorMessage);
		
	}

	@Override
	public void message(String message) {
		view.message(message);
		
	}

}