package algorithms.mazeGenerators;

public class MyMazeGenerator extends CommonMaze3dGenerator{

	@Override
	public Maze3d generate(int x, int y, int z) {

		Maze3d simpleMaze = new Maze3d(x, y, z);
		//Update the size of rows columns and floors
		x = x + 2;
		y = y + 2;
		z = z + 2;
		
		//set entrance
		Position entrance = new Position(1, 1, 0);
		simpleMaze.setMaze3d(entrance, 0);
		simpleMaze.setEntrance(entrance);
		
		for(int floor = 1; floor<z-1; floor +=2){
			for(int i=1; i<z-1; i++){
				simpleMaze.setMaze3d(new Position(1, i, floor), 0);
				simpleMaze.setMaze3d(new Position(i, z-2, floor), 0);
			}
		}
		
		int flag = 0;
		for(int floor = 2; floor<z-1; floor +=2){
			if(flag == 0){
				simpleMaze.setMaze3d(new Position(z-2,z-2,floor), 0);
				flag = 1;
			}
			else{
				simpleMaze.setMaze3d(new Position(1,1,floor), 0);
				flag = 0;
			}
		}

		//set exit
		Position exit = new Position(z-2, z-2, z-1);
		simpleMaze.setMaze3d(exit, 0);
		simpleMaze.setExit(exit);

		return simpleMaze;
	}
}