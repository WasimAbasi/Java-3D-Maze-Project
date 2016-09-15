package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Generator for creating a simple maze
 * @author Wasim, Roaa
 *
 */
public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	@Override
	public Maze3d generate(int x, int y, int z) {

		Maze3d simpleMaze = new Maze3d(x, y, z);
		//Update the size of rows columns and floors
		x = x + 2;
		y = y + 2;
		z = z + 2;
		PositionGenerator pg = new PositionGenerator();

		//Random Entrance at ground floor : z = 0
		Position entrance = pg.specificFloorRandomPosition(x, y, 0);
		simpleMaze.setMaze3d(entrance, 0);
		simpleMaze.setEntrance(entrance);

		Position position = new Position(entrance.getX(), entrance.getY(), entrance.getZ());
		//Random path through all internal floors
		for(int floor = 1; floor<z-1; floor++){

			position.setZ(floor); //next floor
			simpleMaze.setMaze3d(position, 0); //set start point at floor z
			Position targetPosition = pg.specificFloorRandomPosition(x, y, floor);
			//find random path from start position to target position on each floor
			findRandomPath(simpleMaze, position, targetPosition);
			//initialize next floor's starting point
			position = targetPosition;

		}

		//Random Exit at ceiling
		position.setZ(z-1);
		simpleMaze.setMaze3d(position, 0);
		simpleMaze.setExit(position);

		Position p;
		//Tearing Down additional walls randomly, up to 1/4 of the walls can be torn down
		Random ran = new Random();
		int repeat = ran.nextInt((x*y*z)/4); 
		for(int i=0; i<repeat; i++){
			p = pg.randomPosition(x, y, z);
			if(legal(simpleMaze, p)){ //if tearing down wall within rules of maze
				simpleMaze.setMaze3d(p, 0);
			}
		}

		return simpleMaze;
	}


	/*rules: no paths on walls, bottom floor or ceiling
	 * and no internal 'squares' of zeros*/
	private boolean legal(Maze3d simpleMaze, Position p) {

		if(simpleMaze.GetMaze3d(p) == 0) //not a wall - already torn down
			return false;

		//if on internal floor and there's a potential for illegal maze
		if(p.isInternal(simpleMaze.getX(), simpleMaze.getY(), simpleMaze.getZ())){
			if(potentialSquare(simpleMaze, p)){
				return false;
			}

		} else if(potentialIllegalMove(simpleMaze, p)) // on side wall, ground floor or ceiling
			return false;

		return true;
	}

	//Check for potential squares
	private boolean potentialSquare(Maze3d simpleMaze, Position p) {

		//check potential for illegal squares on (x,y) axis
		int[][] maze2dz=simpleMaze.getCrossSectionByZ(p.getZ());
		if(checkSquares(maze2dz, p.getX(), p.getY()))
			return true;

		return false;
	}


	private boolean checkSquares(int[][] maze2d, int coordinate1, int coordinate2) {
		//top left square
		if( maze2d[coordinate1][coordinate2+1] + maze2d[coordinate1-1][coordinate2]
				+ maze2d[coordinate1-1][coordinate2+1] == 0)
			return true;
		//top right square
		if( maze2d[coordinate1][coordinate2+1] + maze2d[coordinate1+1][coordinate2]
				+ maze2d[coordinate1+1][coordinate2+1] == 0)
			return true;
		//bottom left square
		if( maze2d[coordinate1-1][coordinate2] + maze2d[coordinate1-1][coordinate2-1]
				+ maze2d[coordinate1][coordinate2-1] == 0)
			return true;
		//bottom right square
		if( maze2d[coordinate1][coordinate2-1] + maze2d[coordinate1+1][coordinate2]
				+ maze2d[coordinate1+1][coordinate2-1] == 0)
			return true;

		return false;
	}

	//Potential illegal move on a side wall, ground floor or ceiling - no traveling allowed
	private boolean potentialIllegalMove(Maze3d simpleMaze, Position p) {

		if(p.onSideSectionByX(simpleMaze.getX())){
			int[][] maze2dx=simpleMaze.getCrossSectionByX(p.getX());
			if(checkNeighbors(maze2dx, p.getY(), p.getZ(), simpleMaze.getY(), simpleMaze.getZ()))
				return true;
		}
		if(p.onSideSectionByY(simpleMaze.getY())){
			int[][] maze2dy=simpleMaze.getCrossSectionByY(p.getY());
			if(checkNeighbors(maze2dy, p.getX(), p.getZ(), simpleMaze.getX(), simpleMaze.getZ()))
				return true;
		}
		if(p.onSideSectionByZ(simpleMaze.getZ())){
			int[][] maze2dz=simpleMaze.getCrossSectionByZ(p.getZ());
			if(checkNeighbors(maze2dz, p.getX(), p.getY(), simpleMaze.getX(), simpleMaze.getY()))
				return true;
		}
		return false;
	}

	private boolean checkNeighbors(int[][] maze2d, int coordinate1, int coordinate2, 
			int rows, int cols) {
		//right side neighbor has no wall
		if(validIndex(coordinate1+1, coordinate2, rows, cols) && 
				maze2d[coordinate1+1][coordinate2] == 0){
			return true;
		}
		//left side neighbor has no wall
		if(validIndex(coordinate1-1, coordinate2, rows, cols) && 
				maze2d[coordinate1-1][coordinate2] == 0){
			return true;
		}
		//up side neighbor has no wall
		if(validIndex(coordinate1, coordinate2+1, rows, cols) && 
				maze2d[coordinate1][coordinate2+1] == 0){
			return true;
		}
		//down side neighbor has no wall
		if(validIndex(coordinate1, coordinate2-1, rows, cols) && 
				maze2d[coordinate1][coordinate2-1] == 0){
			return true;
		}
		return false;
	}	


	private boolean validIndex(int coordinate1, int coordinate2, int rows, int cols) {
		if(coordinate1<0 || coordinate1>rows-1 || coordinate2<0 ||coordinate2>cols-1)
			return false;
		return true;
	}

	//helper method: randomly find path in a single floor from start position to target position
	private boolean findRandomPath(Maze3d simpleMaze, Position startPosition,
			Position targetPosition) {

		if(startPosition.equals(targetPosition)){ //reached our destination
			return true;
		}
		if(!startPosition.isInternal(simpleMaze.getX(), simpleMaze.getY(), 
				simpleMaze.getZ())){ //dead end
			return false;
		}
		boolean success;
		
		//List of all possible directions
		ArrayList<String> directions = new ArrayList<String>();
		directions.add("Left");
		directions.add("Right");
		directions.add("Forward");
		directions.add("Back");
		while(!directions.isEmpty()){
			Collections.shuffle(directions);
			Position nextMove = new Position(startPosition);
			if(directions.get(0).equals("Left")){
				nextMove.setX(startPosition.getX()-1);
			}
			if(directions.get(0).equals("Right")){
				nextMove.setX(startPosition.getX()+1);
			}
			if(directions.get(0).equals("Forward")){
				nextMove.setY(startPosition.getY()+1);
			}
			if(directions.get(0).equals("Back")){
				nextMove.setY(startPosition.getY()-1);
			}
			if(legal(simpleMaze, nextMove)){
				simpleMaze.setMaze3d(nextMove, 0);
				success = findRandomPath(simpleMaze, nextMove, targetPosition);
				if(success)
					return true;
				else
					simpleMaze.setMaze3d(nextMove, 1);
			}
			directions.remove(0);
		}
		return false;
	}

}