package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.demo.SearchableAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Solution;
import controller.MyController;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * Class MyModel implements Interface Model
 * @author Wasim, Roaa
 *
 */
public class MyModel implements Model{

	private HashMap<String, Maze3d> nameToMazeMap;
	private HashMap<String, Solution<Position>> nameToSolutionMap;

	private MyCompressorOutputStream out;
	private MyDecompressorInputStream in;

	MyController controller;

	public MyModel(){
		this.nameToMazeMap = new HashMap<String, Maze3d>();
		this.nameToSolutionMap = new HashMap<String, Solution<Position>>();
	}
	
	@Override
	public void SetController(MyController controller){
		this.controller = controller;
	}

	@Override
	public void generateMaze(String name, int rows, int columns, int floors, String algorithm) {
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				Maze3d maze;
				if(algorithm.toLowerCase().equals("growingtree")){
					maze = new GrowingTreeGenerator().generate(rows, columns, floors);
					controller.message("Maze " + name + " is ready");
				}
				else{
					maze = new SimpleMaze3dGenerator().generate(rows, columns, floors);
					controller.message("Maze " + name + " is ready");
				}
				nameToMazeMap.put(name, maze);
			}
		}).start();
	}

	@Override
	public void saveMaze(String name, String fileName){
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(nameToMazeMap.get(name).toByteArray());
			out.close();

		} catch (FileNotFoundException e) {
			controller.error(e.getMessage());
		}
		catch(IOException e){
			controller.error(e.getMessage());
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				controller.error(e.getMessage());
			}
		}

	}

	@Override
	public void loadMaze(String fileName, String name)throws IOException, FileNotFoundException {
		try{
			ArrayList<Byte> mazeList = new ArrayList<Byte>();
			byte[] byteStream = new byte[9000];
			in = new MyDecompressorInputStream(new FileInputStream(fileName));

			while( in.read(byteStream) > 0 ){
				for (byte b : byteStream) {
					mazeList.add(b);
				}
			}
			in.close();

			byte[] mazeArray = new byte[mazeList.size()];
			for (int i = 0; i < mazeArray.length; i++) {
				mazeArray[i] = (byte) mazeList.get(i);
			}

			Maze3d maze = new Maze3d(mazeArray);
			nameToMazeMap.put(name, maze);	
		}
		catch(FileNotFoundException e){
			controller.error(e.getMessage());
		}
		catch(IOException e){
			controller.error(e.getMessage());
		}

	}

	@Override
	public void solveMaze(String name, String algorithm) {
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				Maze3d maze = nameToMazeMap.get(name);
				SearchableAdapter searchableMaze = new SearchableAdapter(maze);

				Solution<Position> solution = null;
				if( algorithm.toLowerCase().equals("bfs") ){
					solution = new BestFirstSearch<Position>().Search(searchableMaze);
				}
				else if( algorithm.toLowerCase().equals("dfs") ){
					solution = new DFS<Position>().Search(searchableMaze);
				}
				controller.message("Solution for " + name + " is ready");	
				nameToSolutionMap.put(name, solution);
			}
		}).start();
	}

	@Override
	public void exit() {
		if(out != null){
			try {
				out.close();
			} catch (IOException e) {
				controller.error(e.getMessage());
			}
		}
		System.exit(0);
	}

	@Override
	public Maze3d getMaze(String name) {
		Maze3d maze = nameToMazeMap.get(name);
		if(maze != null){
			return maze;
		}
		else{
			controller.error("Maze Not Found!");
			return null;
		}
	}

	@Override
	public int[][] getMazeSection(String name, char section, int index) {
		Maze3d maze = nameToMazeMap.get(name);
		int[][] mazeSection = null;

		if(maze != null){

			try {
				switch (section) {
				case 'x': 
					mazeSection = maze.getCrossSectionByX(index); 
					break;
				case 'y': 
					mazeSection = maze.getCrossSectionByY(index); 
					break;
				case 'z':
					mazeSection = maze.getCrossSectionByZ(index);
					break;
				default:
					controller.error("Invalid Section!");
					return null;
				}
			} catch (IndexOutOfBoundsException e) {
				controller.error(e.getMessage());
			}
			return mazeSection;
		}
		else{
			controller.error("Maze Not Found!");
			return null;
		}
	}

	@Override
	public int getMazeSectionLength(String name, char section) {
		Maze3d maze = nameToMazeMap.get(name);
		if(maze != null){
				switch (section) {
				case 'x': 
					return maze.getY();
				case 'y': 
					return maze.getX();
				case 'z':
					return maze.getX();
				}
		}
		return -1;
	}

	@Override
	public int getMazeSectionWidth(String name, char section) {
		Maze3d maze = nameToMazeMap.get(name);
		if(maze != null){
				switch (section) {
				case 'x': 
					return maze.getZ();
				case 'y': 
					return maze.getZ();
				case 'z':
					return maze.getY();
				}
		}
		return -1;
	}

	@Override
	public Solution<Position> getSolution(String name) {
		return nameToSolutionMap.get(name);
	}
}
