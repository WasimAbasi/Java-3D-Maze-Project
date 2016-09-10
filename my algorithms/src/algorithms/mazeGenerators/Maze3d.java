package algorithms.mazeGenerators;

/**
* Class Maze3d represents a three dimensional maze.
* @author Wasim, Roaa
*
*/
import java.util.ArrayList;
import java.util.LinkedList;

public class Maze3d {
	
	private int[][][] maze3d;
	private int x;
	private int y;
	private int z;
	private Position entrance;
	private Position exit;
	
	//Maze3d Constructor
	public Maze3d(int x, int y, int z){
		//building walls all around the maze + ground floor and a ceiling
		this.x = x + 2;
		this.y = y + 2;
		this.z = z + 2; 
		maze3d = new int[this.x][this.y][this.z];
		for(int i=0; i<this.x; i++){ //initializing the maze with walls
			for(int j=0; j<this.y; j++){
				for(int k=0; k<this.z; k++){
					maze3d[i][j][k] = 1;
				}
			}
		}
	}

	/**
	 * Constructor to create a new maze from a byte array
	 */
	public Maze3d(byte[] arr){
		int index = 9;
		int z = arr[0];
		int y = arr[1];
		int x = arr[2];
		Position entrancePos = new Position(z,y,x);
		setEntrance(entrancePos);
		
		z = arr[3];
		y = arr[4];
		x = arr[5];
		Position exitPos = new Position(z,y,x);
		
		setExit(exitPos);
		
		this.z = arr[6];
		this.x = arr[7];
		this.y = arr[8];
		
		maze3d = new int[z][x][y];
		
		for(int i=0; i<z;i++){
			for(int j=0;j<x;j++){
				for(int k=0;k<y;k++){
					maze3d[i][j][k] = arr[index];
					index++;
					
				}
			}
		}
		
	}
	
	public void setEntrance(Position p){
		entrance = p;
		setMaze3d(p, 0);
	}
	
	public Position getStartPosition(){
		return entrance;
	}
	
	public void setExit(Position p){
		exit = p;
		setMaze3d(p, 0);
	}
	
	public Position getGoalPosition(){
		return exit;
	}
	
	public void setMaze3d(Position p, int num){
		maze3d[p.getX()][p.getY()][p.getZ()] = num;
	}

	public int GetMaze3d(Position p) {
		return maze3d[p.getX()][p.getY()][p.getZ()];
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getZ(){
		return z;
	}

	public int[][] getCrossSectionByX(int x) throws IndexOutOfBoundsException {
		if (x<0 || x>=this.x)
			throw new IndexOutOfBoundsException();
		int[][] maze2d = new int[y][z];
		for(int i=0; i<y; i++){
			for(int j=0; j<z; j++){
				maze2d[i][j] = maze3d[x][i][j];
			}
		}
		return maze2d;
	}

	public int[][] getCrossSectionByY(int y) throws IndexOutOfBoundsException {
		if (y<0 || y>=this.y)
			throw new IndexOutOfBoundsException();
		int[][] maze2d = new int[x][z];
		for(int i=0; i<x; i++){
			for(int j=0; j<z; j++){
				maze2d[i][j] = maze3d[i][y][j];
			}
		}
		return maze2d;
	}

	public int[][] getCrossSectionByZ(int z) throws IndexOutOfBoundsException {
		if (z<0 || z>=this.z)
			throw new IndexOutOfBoundsException();
		int[][] maze2d = new int[x][y];
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				maze2d[i][j] = maze3d[i][j][z];
			}
		}
		return maze2d;
	}

	public ArrayList<Position> getAllPossibleMoves(Position p) {
		ArrayList<Position> moves = new ArrayList<Position>();
		Position position;
		if(p.getX()-1 >= 0 && GetMaze3d(p)!= 1){
			position = new Position(p.getX()-1, p.getY(), p.getZ());
			moves.add(position);
		}
		if(p.getX()+1 < x && GetMaze3d(p)!= 1){
			position = new Position(p.getX()+1, p.getY(), p.getZ());
			moves.add(position);
		}
		if(p.getY()-1 >= 0 && GetMaze3d(p)!= 1){
			position = new Position(p.getX(), p.getY()-1, p.getZ());
			moves.add(position);
		}
		if(p.getY()+1 < y && GetMaze3d(p)!= 1){
			position = new Position(p.getX(), p.getY()+1, p.getZ());
			moves.add(position);
		}
		if(p.getZ()-1 >= 0 && GetMaze3d(p)!= 1){
			position = new Position(p.getX(), p.getY(), p.getZ()-1);
			moves.add(position);
		}
		if(p.getZ()+1 < z && GetMaze3d(p)!= 1){
			position = new Position(p.getX(), p.getY(), p.getZ()+1);
			moves.add(position);
		}
		return moves;
	}
	
	public String[] getPossibleMoves(Position p) {
		ArrayList<String> moves = new ArrayList<String>();
		ArrayList<Position> positions = getAllPossibleMoves(p);
		for(Position position : positions){
			moves.add(position.toString());
		}
		return (String[]) moves.toArray(new String[moves.size()]);
	}
	
	public ArrayList<Position> unvisitedNeighbors(Position c) {
		ArrayList<Position> unvisitedNeighbors = new ArrayList<Position>();
		Position neighbor = up(c);
		if(neighbor != null){
			unvisitedNeighbors.add(neighbor);
		}
		neighbor = down(c);
		if(neighbor != null){
			unvisitedNeighbors.add(neighbor);
		}
		neighbor = left(c);
		if(neighbor != null){
			unvisitedNeighbors.add(neighbor);
		}
		neighbor = right(c);
		if(neighbor != null){
			unvisitedNeighbors.add(neighbor);
		}
		neighbor = back(c);
		if(neighbor != null){
			unvisitedNeighbors.add(neighbor);
		}
		neighbor = forward(c);
		if(neighbor != null){
			unvisitedNeighbors.add(neighbor);
		}
		return unvisitedNeighbors;
	}
	
	public Position up(Position c){
		if (c.getZ()+1 >= z-1 || maze3d[c.getX()][c.getY()][c.getZ()+1] != 1){
			return null;
		}
		/*if (c.getZ()+2 >= z-1 || maze3d[c.getX()][c.getY()][c.getZ()+2] != 1){
			return null;
		}*/
		Position up = new Position(c.getX(), c.getY(), c.getZ()+1);
		//up.setPassage(new Position(c.getX(), c.getY(), c.getZ()+1));
		return up;
	}
	
	public Position down(Position c){
		if (c.getZ()-1 <= 0 || maze3d[c.getX()][c.getY()][c.getZ()-1] != 1){
			return null;
		}
		/*if (c.getZ()-2 <= 0 || maze3d[c.getX()][c.getY()][c.getZ()-2] != 1){
			return null;
		}*/
		Position down = new Position(c.getX(), c.getY(), c.getZ()-1);
		//down.setPassage(new Position(c.getX(), c.getY(), c.getZ()-1));
		return down;
	}
	
	public Position left(Position c){
		if (c.getX()-1 <= 0 || maze3d[c.getX()-1][c.getY()][c.getZ()] != 1){
			return null;
		}
		if (c.getX()-2 <= 0 || maze3d[c.getX()-2][c.getY()][c.getZ()] != 1){
			return null;
		}
		Position left = new Position(c.getX()-2, c.getY(), c.getZ());
		left.setPassage(new Position(c.getX()-1, c.getY(), c.getZ()));
		return left;
	}
	
	public Position right(Position c){
		if (c.getX()+1 >= x-1 || maze3d[c.getX()+1][c.getY()][c.getZ()] != 1){
			return null;
		}
		if (c.getX()+2 >= x-1 || maze3d[c.getX()+2][c.getY()][c.getZ()] != 1){
			return null;
		}
		Position right = new Position(c.getX()+2, c.getY(), c.getZ());
		right.setPassage(new Position(c.getX()+1, c.getY(), c.getZ()));
		return right;
	}
	
	public Position back(Position c){
		if (c.getY()-1 <= 0 || maze3d[c.getX()][c.getY()-1][c.getZ()] != 1){
			return null;
		}
		if (c.getY()-2 <= 0 || maze3d[c.getX()][c.getY()-2][c.getZ()] != 1){
			return null;
		}
		Position back = new Position(c.getX(), c.getY()-2, c.getZ());
		back.setPassage(new Position(c.getX(), c.getY()-1, c.getZ()));
		return back;
	}
	
	public Position forward(Position c){
		if (c.getY()+1 >= y-1 || maze3d[c.getX()][c.getY()+1][c.getZ()] != 1){
			return null;
		}
		if (c.getY()+2 >= y-1 || maze3d[c.getX()][c.getY()+2][c.getZ()] != 1){
			return null;
		}
		Position forward = new Position(c.getX(), c.getY()+2, c.getZ());
		forward.setPassage(new Position(c.getX(), c.getY()+1, c.getZ()));
		return forward;
	}

	public LinkedList<Position> openCells(int floor) {
		LinkedList<Position> openCells = new LinkedList<Position>();
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				Position p = new Position(i, j, floor);
				if(GetMaze3d(p) == 0){ //open cell
					openCells.add(p);
					//System.out.println(p);
				}
			}
		}
		//System.out.println("Open Cells are: "+openCells.size());
		return openCells;
	}
	
	/**
	 * Method to return the maze information as byte array
	 * @return byte[]
	 */
	public byte[] toByteArray(){
		ArrayList<Byte> list = new ArrayList<Byte>();
		list.add((byte)entrance.getZ()); // add entrance position
		list.add((byte)entrance.getX());
		list.add((byte)entrance.getY());
		list.add((byte)exit.getZ()); // add exit position
		list.add((byte)exit.getX());
		list.add((byte)exit.getY());
		list.add((byte)z); // add floors
		list.add((byte)x); // add rows
		list.add((byte)y); // add columns
		
		// add the maze integers to the list
		for(int i=0; i<z;i++){
			for(int j=0;j<x;j++){
				for(int k=0;k<y;k++){
					list.add((byte)maze3d[i][j][k]);
				}
			}
		}
	
		byte[] bytes = new byte[list.size()];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)list.get(i);
		}
		return bytes;
	}

	
}
