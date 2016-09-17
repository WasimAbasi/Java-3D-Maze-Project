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
	BufferedReader in;
	HashMap<String, Command> commands;
	
	public MyView(BufferedReader in, PrintWriter out) {
		this.out = out;
		this.in = in;
	}
	
	@Override
	public void setCommands(HashMap<String, Command> commands){
		this.commands = commands;
	}

	@Override
	public void start() {
		this.cli = new CLI(in, out, commands);
		cli.start();
	}

	@Override
	public void dir(String path) {
		File f = new File(path);
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {
			if( (file.isFile()) || (file.isDirectory()) ){
				out.println(file.getName());
			}
			else{
				out.println("Error with path");
				out.flush();
			}
		}	
	}

	@Override
	public void display(Maze3d maze) {
		for (int i = 0; i < maze.getZ(); i++) {
			for (int j = 0; j < maze.getX(); j++) {
				for (int k = 0; k < maze.getY(); k++) {
					out.print(maze.GetMaze3d(new Position(j, k, i)));
					out.flush();
				}
				out.println();
				out.flush();
			}
			out.println();
			out.flush();
		}
		out.println("Maze entrance at: " + maze.getStartPosition());
		out.println("Maze exit at: " + maze.getGoalPosition());
	}

	@Override
	public void displayCrossSection(int[][] section, int length, int width) {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {	
				out.print(section[i][j]);
				out.flush();
			}
			out.println();
			out.flush();
		}
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		out.print(solution.toString());
		out.flush();
		
	}

	@Override
	public void error(String errorMessage) {
		out.println(errorMessage);
		out.flush();
		
	}

	@Override
	public void message(String message) {
		out.println(message);
		out.flush();
	}

}
