package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Presenter;

public class MyViewCLI extends Observable implements View {
	
	PrintWriter out;
	BufferedReader in;
	Presenter presenter;
	ArrayList<String> commands;
	
	public MyViewCLI(BufferedReader in, PrintWriter out) {
		this.out = out;
		this.in = in;
		commands = new ArrayList<String>();
		commands.add("dir");
		commands.add("generate_maze");
		commands.add("display");
		commands.add("display_cross_section");
		commands.add("save_maze");
		commands.add("load_maze");
		commands.add("solve");
		commands.add("display_solution");
		commands.add("exit");
	}

	@Override
	public void start()
	{		 
		new Thread(new Runnable() {
			@Override
			public void run() {	
				String[] command = new String[10]; 
				Scanner scanner;
				try {

					do{
						out.println("Please enter a command:");
						out.println("- dir <path>");
						out.println("- generate_maze <name> <rows> <columns> <floors> <algorithm {Simple,GrowingTree}>");
						out.println("- display <name>");
						out.println("- display_cross_section <index {X,Y,Z}> <name>");
						out.println("- save_maze <name> <file name>");
						out.println("- load_maze <file name> <name>");
						out.println("- solve <name> <algorithm {BFS,DFS}>");
						out.println("- display_solution <name>");
						out.println("- exit");
						out.flush();

						int i = 0;
						try{
							scanner = new Scanner(in.readLine());
							scanner.useDelimiter(" ");
							while(scanner.hasNext())
							{
								command[i] = scanner.next();
								i++;
							}	
							if(commands.contains(command[0]))
							{
								setChanged();
								notifyObservers(command);	
							}
							else
							{
								out.println("Invalid Command. Please try again.");
								out.flush();
							}
						}catch(IOException e)
						{
							e.printStackTrace();
						} catch(Exception e){
							out.println("Invalid Command. Please try again.");
						}
					}while(command[0] != "exit");
				} catch(Exception e){
					message(e.getMessage());
				}
			}

		}).start();
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
