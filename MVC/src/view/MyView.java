package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;

/**
 * Class MyView implements the View functionality as a part of the MVC pattern.
 * @author Wasim, Roaa
 *
 */
public class MyView implements View {

	CLI cli;
	PrintWriter out;
	
	public MyView(BufferedReader in, PrintWriter out, HashMap<String,Command> commands) {
		super();
		this.out = out;
		this.cli = new CLI(in, out, commands);
	}

	@Override
	public void start() {
		cli.start();
	}

	@Override
	public void dir(String path) {
		File f = new File(path);
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {
			if( (file.isFile()) || (file.isDirectory()) ){
				this.out.println(file.getName());
			}
			else{
				this.out.println("Error with path");
			}
		}	
	}

	@Override
	public void display(Maze3d maze) {
		for (int i = 0; i < maze.getZ(); i++) {
			for (int j = 0; j < maze.getX(); j++) {
				for (int k = 0; k < maze.getY(); k++) {
					out.print(maze.GetMaze3d(new Position(j, k, i)));
				}
				out.println();
			}
			out.println();
		}
	}

	@Override
	public void displayCrossSection(int[][] section, int length, int width) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {	
				System.out.print(section[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		solution.toString();
		
	}

	public void error(String string) {
		// TODO Auto-generated method stub
		
	}

	public void message(String string) {
		// TODO Auto-generated method stub
		
	}

}
