package model;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface Model represents the functionality of a model as a component of the MVC pattern.
 * @author Wasim, Roaa
 *
 */
public interface Model
{
	/**
	 * Method generateMaze generates a three dimensional maze using the algorithm specified by the user
	 * @param name
	 * @param x: number of rows
	 * @param y: number of columns
	 * @param z: number of floors
	 * @param algorithm: SimpleMaze3dGenerator or GrowingTreeGenerator
	 */
	public void generateMaze(String name, int x, int y, int z, String algorithm);
	
	/**
	 * method display displays the maze with the name specified by the user.
	 * @param name: the name of the maze to display
	 */
	public void display(String name);
	
	/**
	 * Method displayCrossSection displays a cross section by axis in a specific maze.
	 * @param index: index to cross by
	 * @param section: X, Y or Z
	 * @param name: the name of the maze
	 */
	public void displayCrossSection(int index, char section, String name);

	/**
	 * Method saveMaze save the maze to a file
	 * @param name: the name of the maze to save
	 * @param fileName: file's name to save the maze in
	 */
	public void saveMaze(String name, String fileName);
	
	/**
	 * Method loadMaze loads a maze from an input file
	 * @param fileName: file's name to load from
	 * @param name: the name of the maze to load
	 */
	public void loadMaze(String fileName, String name)throws IOException, FileNotFoundException;
	
	/**
	 * Method solveMaze solves the specified maze using the specified algorithm.
	 * @param name: the name of the maze to solve
	 * @param algorithm: BFS or DFS
	 */
	public void solveMaze(String name, String algorithm);
	
	/**
	 * Method displaySolution displays the solution for the specified maze
	 * @param name: the name of the specified maze
	 */
	public void displaySolution(String name);
	
	/**
	 * Method exit performs a safe exit from the user interface.
	 */
	public void exit();
}
