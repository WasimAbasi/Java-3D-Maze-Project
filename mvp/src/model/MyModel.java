package model;

import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import presenter.Properties;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * Class MyModel implements Interface Model
 * @author Wasim, Roaa
 *
 */
public class MyModel extends Observable implements Model{
	
	private String generateAlgorithm;
	private String searchAlgorithm;

	private HashMap<String, Maze3d> nameToMazeMap;
	private HashMap<Maze3d, Solution<Position>> mazeToSolutionMap;
	private HashMap<Maze3d, Solution<Position>> mazeToHalfSolutionMap;

	private MyCompressorOutputStream out;
	private MyDecompressorInputStream in;

	Presenter presenter;

	private ExecutorService threadPool;

	private Properties properties;

	public MyModel(String[] propertiesPath){
		loadXML(propertiesPath);
		this.nameToMazeMap = new HashMap<String, Maze3d>();
		this.mazeToHalfSolutionMap = new HashMap<Maze3d, Solution<Position>>();
		loadMazeToSolutionMap();
		this.threadPool = Executors.newFixedThreadPool(properties.getMaxNumOfThreads());
	}

	@Override
	public Properties getProperties(){
		return properties;
	}
	
	@Override
	public void loadXML(String[] propertiesPath) {
		
		StringBuilder sb = new StringBuilder();

		if(propertiesPath == null || propertiesPath.length == 0 || propertiesPath[0].intern() == "null")
		{
			sb.append("./resources/properties.xml");
		}
		else
		{
			for(int i=0;i<propertiesPath.length;i++)
			{
				if(i==propertiesPath.length-1)
				{
					sb.append(propertiesPath[i]);
				}
				else
				{
					sb.append(propertiesPath[i]+" ");
				}
			}
		}
		try
		{
			File f = new File(sb.toString());
			//If the file doesn't exist generate a default one.
			if(!f.exists())
			{
				XMLEncoder xmlE = new XMLEncoder(new FileOutputStream(sb.toString()));
				xmlE.writeObject(new Properties(10, "dfs", "growingtree","gui"));
				xmlE.close();
			}
			
			//the file exist -> load it
			XMLDecoder xmlD = new XMLDecoder(new FileInputStream(sb.toString()));
			properties = (Properties) xmlD.readObject();
			xmlD.close();
		} 
		catch (FileNotFoundException e) 
		{
			String[] command = new String[2];
			command[0]="error";
			command[1]=e.getMessage();
			setChanged();
			notifyObservers(command);
		}

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
				if(algorithm == null){ //user didn't supply a specific algorithms so we're using the default.
					generateAlgorithm = properties.getGenerateAlgorithm();
				}
				else{
					generateAlgorithm = algorithm;
				}
				if(generateAlgorithm.toLowerCase().equals("growingtree")){
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
				if(algorithm == null){
					searchAlgorithm = properties.getSearchAlgorithm();
				} else{
					searchAlgorithm = algorithm;
				}
				if( searchAlgorithm.toLowerCase().equals("bfs") ){
					searcher = new BestFirstSearch<Position>();
				}
				else{
					searcher = new DFS<Position>();
				}
				Solution<Position> solution = searcher.Search(searchableMaze);
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
	public void solveFrom(String name, String algorithm, int x, int y, int z) {
		String[] command = new String[2];
		Future<Solution<Position>> future = threadPool.submit(new Callable<Solution<Position>>() 
		{
			@Override
			public Solution<Position> call() throws Exception
			{
				Maze3d maze = nameToMazeMap.get(name);
				maze.setEntrance(new Position(x, y, z));
				SearchableAdapter searchableMaze = new SearchableAdapter(maze);
				Searcher<Position> searcher;
				if(algorithm == null){
					searchAlgorithm = properties.getSearchAlgorithm();
				} else{
					searchAlgorithm = algorithm;
				}
				if( searchAlgorithm.toLowerCase().equals("bfs") ){
					searcher = new BestFirstSearch<Position>();
				}
				else{
					searcher = new DFS<Position>();
				}
				Solution<Position> solution = searcher.Search(searchableMaze);
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
					mazeToHalfSolutionMap.put(nameToMazeMap.get(name), solution);
					command[0] = "message";
					command[1] = "Half Solution for " + name + " is ready";
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
		try {
			saveMazeToSolutionMap(); //to use in future sessions
		} catch (FileNotFoundException e) {
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);
		} catch (IOException e) {
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);
		}
		System.exit(0);
	}

	private void saveMazeToSolutionMap() throws FileNotFoundException, IOException{
		//there are no solutions to save
		if(mazeToSolutionMap.isEmpty()){
			return;
		}
		String[] command = new String[2];
		command[0] = "error";
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions")));
			outFile.writeObject(mazeToSolutionMap);
			outFile.flush();
			outFile.close();
		}
		catch (IOException e) {
			command[1] = e.getMessage();
			setChanged();
			notifyObservers(command);
		}

	}

	@SuppressWarnings("unchecked")
	private void loadMazeToSolutionMap() {
		File file = new File("solutions");
		if(!file.exists()){
			this.mazeToSolutionMap = new HashMap<Maze3d, Solution<Position>>();
		}
		else{
			String[] command = new String[2];
			command[0] = "error";
			try {
				ObjectInputStream inFile = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions")));
				mazeToSolutionMap = (HashMap<Maze3d, Solution<Position>>) inFile.readObject();
				inFile.close();
			}
			catch (  IOException e) {
				command[1] = e.getMessage();
				setChanged();
				notifyObservers(command);
			} catch (ClassNotFoundException e) {
				command[1] = e.getMessage();
				setChanged();
				notifyObservers(command);
			}
		}
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
	
	@Override
	public Solution<Position> getHalfSolution(String name) {
		Maze3d maze = nameToMazeMap.get(name);
		return mazeToHalfSolutionMap.get(maze);
	}
}
