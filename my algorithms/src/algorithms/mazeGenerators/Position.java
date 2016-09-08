package algorithms.mazeGenerators;

/**
 * Class Position represents a position in a three dimensional maze.
 * @author Wasim, Roaa
 *
 */

public class Position {
	private int x;
	private int y;
	private int z;
	
	private Position passage;
	
	public Position getPassage() {
		return passage;
	}

	public void setPassage(Position passage) {
		this.passage = passage;
	}

	public Position(int x, int y, int z){
		setPosition(x, y, z);
	}
	
	public Position(Position copy) {
		this.x = copy.getX();
		this.y = copy.getY();
		this.z = copy.getZ();
	}

	public void setPosition(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//getters
	public int getX() {return x;}
	public int getY() {return y;}
	public int getZ() {return z;}
	//setters
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setZ(int z) {this.z = z;}
	
	public boolean isInternal(int x, int y, int z){
		return this.x>0 && this.x<x-1 && this.y>0 && this.y<y-1 && this.z>0 && this.z<z-1;
	}

	public boolean onSideSectionByX(int x) {
		if (this.x == 0 || this.x == x-1)
			return true;
		return false;
	}

	public boolean onSideSectionByY(int y) {
		if (this.y == 0 || this.y == y-1)
			return true;
		return false;
	}

	public boolean onSideSectionByZ(int z) {
		if (this.z == 0 || this.z == z-1)
			return true;
		return false;
	}
	
	@Override
	public boolean equals(Object obj){ // Compare method
		Position p = (Position) obj;
		return x == p.getX() && y == p.getY() && z == p.getZ();
	}
	
	@Override
	public String toString(){ // Prints a position
		return "{"+x+","+y+","+z+"}";
	}
}
