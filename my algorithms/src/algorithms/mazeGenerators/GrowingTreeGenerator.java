package algorithms.mazeGenerators;
/**
* Generator to launch a 3d maze using a growing tree algorithm
* @author Wasim, Roaa
*
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GrowingTreeGenerator extends CommonMaze3dGenerator{
	
	Chooser chooser;
	
	public GrowingTreeGenerator(){
		chooser = new LastCellChooser();
	}
	
	public GrowingTreeGenerator(Chooser chooser){
		this.chooser = chooser;
	}
	
	@Override
	public Maze3d generate(int x, int y, int z){
		
		Maze3d maze = new Maze3d(x, y, z);
		x = x + 2;
		y = y + 2;
		z = z + 2;
		LinkedList<Position> C = new LinkedList<Position>();
		//add one cell to C at random
		Random ran = new Random();
		int myX  = ran.nextInt(x-2)+1;
		int myY = ran.nextInt(y-2)+1;
		//int myZ = ran.nextInt(z-2)+1;
		Position cell = new Position(myX,myY,1);
		C.addLast(cell);
		maze.setMaze3d(cell, 0);
		while(! C.isEmpty()){
			//choose a cell c from C
			Position c = chooser.choose(C);
			ArrayList<Position> unvisitedNeighbors = maze.unvisitedNeighbors(c);
			if(!unvisitedNeighbors.isEmpty()){
				Collections.shuffle(unvisitedNeighbors); //to choose random neighbor
				Position n = unvisitedNeighbors.get(0);
				maze.setMaze3d(n.getPassage(), 0);
				maze.setMaze3d(n, 0);
				C.addLast(n); // add n to C
			} else {
				C.remove(c);
			}
		}
		
		/*for(int i=0; i<z; i++){
			System.out.println("{");
			for(int j=0; j<x; j++){
				for(int k=0; k<y; k++){
					System.out.print(maze.GetMaze3d(new Position(j, k, i)));
				}
				System.out.println();
			}
			System.out.print("}");
			System.out.println();
		}*/
		//Set entrance at ground floor randomly
		LinkedList<Position> openCells = maze.openCells(1); //return open cells at first floor
		Collections.shuffle(openCells);
		Position randomPosition = openCells.get(0);
		randomPosition.setZ(0); //ground floor
		maze.setMaze3d(randomPosition, 0);
		maze.setEntrance(randomPosition);

		//set exit randomly at the ceiling
		openCells = maze.openCells(z-2); //return open cells at highest floor
		Collections.shuffle(openCells);
		randomPosition = openCells.get(0);
		randomPosition.setZ(z-1); // exit at the ceiling
		maze.setMaze3d(randomPosition, 0);
		maze.setExit(randomPosition);

		return maze;
	}
	
}
