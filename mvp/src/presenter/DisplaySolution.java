package presenter;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import model.Model;
import view.View;

/**
 * Class DisplaySolution displays the solution to a specific maze.
 * @author Roaa, Wasim
 *
 */
public class DisplaySolution implements Command {

	private Model model;
	private View view;
	
	public DisplaySolution(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		if(model.getMaze(commandParameters[1]) != null){
			Solution<Position> solution = model.getSolution(commandParameters[1]);
			if(solution != null){
				view.displaySolution(solution);
			}else{
				view.error("No Solution Available for maze " + commandParameters[1] + "!");
			}
		}else{
			view.error("Maze " + commandParameters[1] +" was not found!");
		}
	}
}
