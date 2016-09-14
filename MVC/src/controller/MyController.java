package controller;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import model.MyModel;
import view.MyView;

/**
 * Class MyController represents a controller which implements the controller side of the MVC pattern.
 * @author Roaa, Wasim
 *
 */
public class MyController implements Controller {
	
	MyView view;
	MyModel model;
	HashMap<String,Command> commands;
	
	public MyController(BufferedReader in, PrintWriter out) {
		super();
		initiateCommands();
		this.view = new MyView(in, out, this.commands);
		this.model = new MyModel(this);
	}
	
	@Override
	public void initiateCommands() {
		this.commands = new HashMap<String,Command>();
		
		commands.put("dir", new Dir(this.view));	
		commands.put("generate_maze", new GenerateMaze(this.model, this.view));   
		commands.put("display", new Display(this.model, this.view));
		commands.put("display_cross_section", new DisplayCrossSection(this.model, this.view)); 
		commands.put("save_maze", new SaveMaze(this.model, this.view));	
		commands.put("load_maze", new LoadMaze(this.model, this.view));
		commands.put("solve", new Solve(this.model)); 
		commands.put("display_solution", new DisplaySolution(this.model, this.view));
		commands.put("exit", new Exit(this.model));
	}

	@Override
	public void start() {
		view.start();	
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