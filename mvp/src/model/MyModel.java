package model;

import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import algorithms.demo.SearchableAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.DFS;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import presenter.Presenter;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * Class MyModel implements Interface Model
 * @author Wasim, Roaa
 *
 */
public class MyModel extends Observable implements Model{

	private HashMap<String, Maze3d> nameToMazeMap;
	//private HashMap<String, Solution<Position>> nameToSolutionMap;
	private HashMap<Maze3d, Solution<Position>> mazeToSolutionMap;

	private MyCompressorOutputStream out;
	private MyDecompressorInputStream in;

	Presenter presenter;

	private ExecutorService threadPool;

	public MyModel(){
		this.nameToMazeMap = new HashMap<String, Maze3d>();
		//this.nameToSolutionMap = new HashMap<String, Solution<Position>>();
		this.mazeToSolutionMap = new HashMap<Maze3d, Solution<Position>>();
		this.threadPool = Executors.newFixedThreadPool(7);
	}

	@Override
	public void generateMaze(String name, int rows, int columns, int floors, String algorithm) {
		String[] command = new String[2];
		Future<Maze3d> future = threadPool.submit(new Callable<Maze3d>() 
		{
			@Override
			public Maze3d call() throws Exception
			{
				Maze3dGenerator generator;
				if(algorithm.equals("growingtree")){
					generator = new GrowingTreeGenerator();
				}
				else{
					generator = new SimpleMaze3dGenerator();
				}
				Maze3d maze = generator.generate(rows, columns, floors);
				return maze;
			}
		});

		//We chose to run future.get() in a thread because it might take time
		threadPool.execute(new Runnable() {
			@Override
			public void run() {

				try 
				{
					Maze3d maze = future.get();
					nameToMazeMap.put(name, maze);
					command[0] = "message";
					command[1] = "Maze " + name + " is ready";
					setChanged();
					notifyObservers(command);
				}

				catch (InterruptedException e) 
				{
					command[0] = "error";
					command[1] = e.getMessage();
					setChanged();
					notifyObservers(command);
				} 
				catch (ExecutionException e) 
				{
					command[0] = "error";
					command[1] = e.getMessage();
					setChanged();
					notifyObservers(e.getMessage());
				}
			}
		});
	}

	@Override
	public void saveMaze(String name, String fileName){
		String[] command = new String[2];
		command[0] = "error";
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			out.write(nameToMazeMap.get(name).toByteArray());
			out.close();

		} catch (FileNotFoundException e) {
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);

		}
		catch(IOException e){
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);
		}
		finally {
			try {
				out.close();
			} catch (IOException e) {
				command[1] = e.getMessage();
				setChanged();
				notifyObservers(command);
			}
		}

	}

	@Override
	public void loadMaze(String fileName, String name)throws IOException, FileNotFoundException {
		String[] command = new String[2];
		command[0] = "error";
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
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);
		}
		catch(IOException e){
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);
		}

	}

	@Override
	public void solveMaze(String name, String algorithm) {
		String[] command = new String[2];
		Future<Solution<Position>> future = threadPool.submit(new Callable<Solution<Position>>() 
		{
			@Override
			public Solution<Position> call() throws Exception
			{
				Maze3d maze = nameToMazeMap.get(name);
				SearchableAdapter searchableMaze = new SearchableAdapter(maze);
				Searcher<Position> searcher;
				if( algorithm.toLowerCase().equals("bfs") ){
					searcher = new BestFirstSearch<Position>();
				}
				else{
					searcher = new DFS<Position>();
				}
				Solution<Position> solution = searcher.Search(searchableMaze);
				if(solution == null){
					System.out.println("solution is null!");
				}
				return solution;
			}
		});

		//We chose to run future.get() in a thread because it might take time
		threadPool.execute(new Runnable() {
			@Override
			public void run() {

				try 
				{
					Solution<Position> solution = future.get();
					mazeToSolutionMap.put(nameToMazeMap.get(name), solution);
					command[0] = "message";
					command[1] = "Solution for " + name + " is ready";
					setChanged();
					notifyObservers(command);
				}

				catch (InterruptedException e) 
				{
					command[0] = "error";
					command[1] = e.getMessage();
					setChanged();
					notifyObservers(command);
				} 
				catch (ExecutionException e) 
				{
					command[0] = "error";
					command[1] = e.getMessage();
					setChanged();
					notifyObservers(e.getMessage());
				}
			}
		});
	}

	@Override
	public void exit() {
		String[] command = new String[2];
		command[0] = "error";
		if(out != null){
			try {
				out.close();
			} catch (IOException e) {
				command[1] = e.getMessage();
				setChanged();
				notifyObservers(command);
			}
		}
		System.exit(0);
	}

	@Override
	public Maze3d getMaze(String name) {
		String[] command = new String[2];
		command[0] = "error";
		Maze3d maze = nameToMazeMap.get(name);
		if(maze != null){
			return maze;
		}
		else{
			command[1] = "Maze not found!";
			setChanged();
			notifyObservers(command);
			return null;
		}
	}

	@Override
	public int[][] getMazeSection(String name, char section, int index) {
		String[] command = new String[2];
		command[0] = "error";
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
					command[1] = "Maze not found!";
					setChanged();
					notifyObservers(command);
					return null;
				}
			} catch (IndexOutOfBoundsException e) {
				command[1] = e.getMessage();
				setChanged();
				notifyObservers(command);
			}
			return mazeSection;
		}
		else{
			command[1] = "Maze not found!";
			setChanged();
			notifyObservers(command);
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
		Maze3d maze = nameToMazeMap.get(name);
		return mazeToSolutionMap.get(maze);
	}
}
