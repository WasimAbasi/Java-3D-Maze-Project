package presenter;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * Class Display displays the maze ordered by the user.
 * @author Roaa, Wasim
 *
 */
public class Display implements Command {

	private Model model;
	private View view;
	
	public Display(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		Maze3d maze = model.getMaze(commandParameters[1]);
		if(maze != null){
			view.display(maze);
		}
		else{
			view.error("Error with displaying the maze!");
		}
	}

}
