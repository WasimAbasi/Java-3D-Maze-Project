package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * Interface View represents the functionality of a View as a part of the MVC pattern.
 * @author Wasim, Roaa
 *
 */
public interface View {

	/**
	 * Method start starts the interaction with the user via the command line interface.
	 */
	void start();

	/**
	 * Method dir displays all files and directories under path.
	 * @param path
	 */
	public void dir(String path);



	/**
	 * Method display displays the maze with the name specified by the user.
	 * @param maze: the maze to be displayed.
	 */
	public void display(Maze3d maze);

	/**
	 * Method displayCrossSection displays a two dimensional maze that represents
	 * the specified cross section by the user.
	 * @param section: the two dimensional maze to be displayed.
	 */
	public void displayCrossSection(int[][] section, int length, int width);
	
	
	/**
	 * Method displaySolution displays a solution for a maze specified by the user.
	 */
	public void displaySolution(Solution<Position> solution);
}
