package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

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
	 * @param rows: number of rows
	 * @param columns: number of columns
	 * @param floors: number of floors
	 * @param algorithm: SimpleMaze3dGenerator or GrowingTreeGenerator
	 */
	public void generateMaze(String name, int rows, int columns, int floors, String algorithm);
	
	/**
	 * method getMaze returns the maze of the name specified by the user.
	 * @param name: the name of the maze to return
	 */
	public Maze3d getMaze(String name);

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
	 * Method getSolution returns the solution for the specified maze
	 * @param name: the name of the specified maze
	 */
	public Solution<Position> getSolution(String name);
	
	/**
	 * Method exit performs a safe exit from the user interface.
	 */
	public void exit();

	/**
	 * Method getMazeSection
	 * @param name: the name of the maze to cross
	 * @param section: the section of the maze to cross: x, y or z
	 * @param index: index to cross by
	 * @return a two dimensional cross section of the maze
	 */
	int[][] getMazeSection(String name, char section, int index);

	/**
	 * Method getMazeSectionWidth returns the number of columns in the specified section.
	 * @param name
	 * @param section
	 */
	int getMazeSectionWidth(String name, char section);

	/**
	 * Method getMazeSectionLength returns the number of rows in the specified section.
	 * @param name
	 * @param section
	 */
	int getMazeSectionLength(String name, char section);
}
