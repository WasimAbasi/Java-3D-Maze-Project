package algorithms.mazeGenerators;

/**
 * Generator for creating a simple maze
 * @author Wasim, Roaa
 *
 */

import java.util.Random;

public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	@Override
	public Maze3d generate(int x, int y, int z) {
		
		Maze3d simpleMaze = new Maze3d(x, y, z);
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
			simpleMaze.setMaze3d(targetPosition, 0); //set finish point at floor z
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
		if(p.isInternal(simpleMaze.getX(), simpleMaze.getY(), simpleMaze.getZ()) 
				&& potentialSquare(simpleMaze, p)){
			return false;
				
		} else if(potentialIllegalMove(simpleMaze, p)) // on side wall, ground floor or ceiling
				return false;
		
		return true;
	}
		
	//Check for potential squares on specific cross sections
	private boolean potentialSquare(Maze3d simpleMaze, Position p) {
		
		//check potential for illegal squares on (y,z) axis
		int[][] maze2dx=simpleMaze.getCrossSectionByX(p.getX());
		if(checkSquares(maze2dx, p.getY(), p.getZ()))
			return true;
		
		//check potential for illegal squares on (x,z) axis
		int[][] maze2dy=simpleMaze.getCrossSectionByY(p.getY());
		if(checkSquares(maze2dy, p.getX(), p.getZ()))
			return true;
		
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

		//Potential illegal move on a side wall, ground floor or ceiling
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

	//helper method: randomly find path in a single floor from start position to target
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
		Random ran = new Random();
		Direction direction = new Direction(ran.nextInt(4)); //random direction
		for(int i=0; i<4; i++){ //used in case random direction isn't legal and a new direction is needed
			Position nextMove = new Position(startPosition);
			if(direction.toString().equals("Left")){
				nextMove.setX(startPosition.getX()-1);
			}
			if(direction.toString().equals("Right")){
				nextMove.setX(startPosition.getX()+1);
			}
			if(direction.toString().equals("Forward")){
				nextMove.setX(startPosition.getY()+1);
			}
			if(direction.toString().equals("Back")){
				nextMove.setX(startPosition.getY()-1);
			}
			if(legal(simpleMaze, nextMove)){
				simpleMaze.setMaze3d(nextMove, 0);
				success = findRandomPath(simpleMaze, nextMove, targetPosition);
				if(success)
					return true;
				else
					simpleMaze.setMaze3d(nextMove, 1);
			}
			direction.nextDirection();
		}
		return false;
	}

}