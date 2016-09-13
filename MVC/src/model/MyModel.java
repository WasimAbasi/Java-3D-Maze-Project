package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.MyController;

/**
 * Class MyModel implements Interface Model
 * @author Wasim, Roaa
 *
 */
public class MyModel implements Model{
	
	MyController controller;
	
	public MyModel(MyController controller){
		this.controller = controller;
	}
	
	@Override
	public void generateMaze(String name, int x, int y, int z, String algorithm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String name) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void displayCrossSection(int index, char section, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveMaze(String name, String fileName){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMaze(String fileName, String name)throws IOException, FileNotFoundException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solveMaze(String name, String algorithm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	public Maze3d getMaze(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public int[][] getMazeSection(String name, char section, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMazeSectionLength(String name, char section, int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMazeSectionWidth(String name, char section, int index) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Solution<Position> getSolution(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}
