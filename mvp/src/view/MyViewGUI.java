package view;

import java.util.HashMap;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MyViewGUI extends Observable implements View {
	
	String[] message;
	HashMap<String, Listener> listeners;
	MazeWindow mainWindow;
	GenericWindow genericWindow;
	boolean isSolved;
	private Position characterPosition;
	private Maze3d maze;
	KeyListener canvasArrowKeyListener;
	
	private String path;
	private Boolean isBeingSolved;
	
	public MyViewGUI(){
		
		listeners = new HashMap<String, Listener>();
		initListeners();
		this.mainWindow = new MazeWindow("Minion Maze",600,400,listeners,canvasArrowKeyListener);
		isSolved = false;
		this.isBeingSolved = false;
	}

	private void initListeners() {
		listeners.put("exit",new Listener() 
		{
			 public void handleEvent(Event event) 
			 {
				 MessageBox messageBox = new MessageBox(mainWindow.getShell(),  SWT.ICON_QUESTION| SWT.YES | SWT.NO);
				 messageBox.setMessage("Do you really want to exit?");
				 messageBox.setText("Exiting Application");
				 if(messageBox.open()==SWT.YES)
				 {
					 message[0] = "exit";
					 setChanged();
					 notifyObservers(message);
					 event.doit=true;

				 }
				 else
				 {
					 event.doit=false;
				 }
			 }
		}); 
		
		listeners.put("file dialog",new Listener() 
		{
			 public void handleEvent(Event event) 
			 {
				 FileDialog fd = new FileDialog(mainWindow.getShell(),SWT.OPEN);
				 fd.setText("xml loader");
				 fd.setFilterPath("./");
				 String[] filterExt = {  "*.xml" };
				 fd.setFilterExtensions(filterExt);
				 path = fd.open();
				 message[0] = "load_xml";
				 message[1] = path;
				 setChanged();
				 notifyObservers(message);
			 }
		}); 
		listeners.put("start", new Listener() {
			
			@Override
			public void handleEvent(Event event) {

				genericWindow=new GenericWindow(200,200,listeners,new MazeProperties());
				genericWindow.run();
				MazeProperties mp=(MazeProperties)genericWindow.getObj();
				if(mp==null)
				{
					return;
				}
				int x=mp.getHeight();
				int y=mp.getWidth();
				int z=mp.getDepth();
				
				String[] command = new String[5];
				command[0] = "generate_maze";
				command[1] = "m"; //arbitrary name for the maze
				command[2] = Integer.toString(x);
				command[3] = Integer.toString(y);
				command[4] = Integer.toString(z);
				
				setChanged();
				notifyObservers(command);
				
				
			}
		});
		listeners.put("solve", new Listener() {
			
			@Override
			public void handleEvent(Event event) 
			{
				isBeingSolved=true;
				int x=characterPosition.getX();
				int y=characterPosition.getY();
				int z=characterPosition.getZ();
				
				//TO BE CORRECTED!!!
				message[0]="solve from m "+x+" "+y+" "+z;
				
				isSolved=true;
				setChanged();
				notifyObservers(message);
			}
		});
		listeners.put("hint", new Listener() {
			
			@Override
			public void handleEvent(Event event) 
			{

				int x=characterPosition.getX();
				int y=characterPosition.getY();
				int z=characterPosition.getZ();
				
				message[0]="solve from m "+x+" "+y+" "+z;
				isSolved=false;
				setChanged();
				notifyObservers(message);
			}
		});
		listeners.put("reset", new Listener() {
			
			@Override
			public void handleEvent(Event event) 
			{
				characterPosition.setPosition(maze.getStartPosition().getX(), maze.getStartPosition().getY(), maze.getStartPosition().getZ());
				mainWindow.moveCharacterInCanvas(maze.getStartPosition().getX(), maze.getStartPosition().getY(), maze.getStartPosition().getZ(),canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
				
			}
		});
		canvasArrowKeyListener=new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent event) 
			{
				if(isBeingSolved)
				{
					return;
				}
				int x=characterPosition.getX();
				int y=characterPosition.getY();
				int z=characterPosition.getZ();
				int[][][] mazeArr=maze.getMaze();
				if((event.keyCode==SWT.ARROW_RIGHT)||(event.keyCode==SWT.KEYPAD_6))
				{
					
					if((z<mazeArr[0][0].length-1)&&(mazeArr[x][y][z+1]==0))
					{
						characterPosition.setPosition(x, y, z+1);
						mainWindow.moveCharacterInCanvas(x, y, z+1,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				else if((event.keyCode==SWT.ARROW_LEFT)||(event.keyCode==SWT.KEYPAD_4))
				{
					if((z>0)&&(mazeArr[x][y][z-1]==0))
					{
						characterPosition.setPosition(x, y, z-1);
						mainWindow.moveCharacterInCanvas(x, y, z-1,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				else if((event.keyCode==SWT.ARROW_UP)||(event.keyCode==SWT.KEYPAD_8))
				{
					if((y>0)&&(mazeArr[x][y-1][z]==0))
					{
						characterPosition.setPosition(x, y-1, z);
						mainWindow.moveCharacterInCanvas(x, y-1, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}

				}
				else if((event.keyCode==SWT.ARROW_DOWN)||(event.keyCode==SWT.KEYPAD_2))
				{
					if((y<mazeArr[0].length-1)&&(mazeArr[x][y+1][z]==0))
					{
						characterPosition.setPosition(x, y+1, z);
						mainWindow.moveCharacterInCanvas(x, y+1, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				else if(event.keyCode==SWT.PAGE_DOWN)
				{
					if((x>0)&&(mazeArr[x-1][y][z]==0))
					{
						characterPosition.setPosition(x-1, y, z);
						mainWindow.moveCharacterInCanvas(x-1, y, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				else if(event.keyCode==SWT.PAGE_UP)
				{
					if((x<mazeArr.length-1)&&(mazeArr[x+1][y][z]==0))
					{
						characterPosition.setPosition(x+1, y, z);
						mainWindow.moveCharacterInCanvas(x+1, y, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent event) {}
		};
	}
	
	public boolean canBeMovedDown()
	{
		int x=characterPosition.getX();
		int y=characterPosition.getY();
		int z=characterPosition.getZ();

		//check the exit!!!!!!
		if(maze!=null)
		{
			int[][][] mazeData=maze.getMaze();
			if((characterPosition.equals(maze.getStartPosition()))||(characterPosition.equals(maze.getGoalPosition())))
			{
				return false;
			}
			if((x>0)&&(mazeData[x-1][y][z]==0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}

	public boolean canBeMovedUp()
	{
		int x=characterPosition.getX();
		int y=characterPosition.getY();
		int z=characterPosition.getZ();
		
		if(maze!=null)
		{
			int[][][] mazeData=maze.getMaze();
			if((characterPosition.equals(maze.getStartPosition()))||(characterPosition.equals(maze.getGoalPosition())))
			{
				return false;
			}
			if((x<mazeData.length-1)&&(mazeData[x+1][y][z]==0))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}

	@Override
	public void start() {
		mainWindow.run();
	}

	@Override
	public void dir(String path) {}

	@Override
	public void display(Maze3d maze) {
		characterPosition = new Position(maze.getStartPosition().getX(),maze.getStartPosition().getY(),maze.getStartPosition().getZ());
		mainWindow.setMazeInCanvas(maze);
		mainWindow.moveCharacterInCanvas(maze.getStartPosition().getX(),maze.getStartPosition().getY(),maze.getStartPosition().getZ(),canBeMovedUp(),canBeMovedDown(),true,isBeingSolved);
	}

	@Override
	public void displayCrossSection(int[][] section, int length, int width) {}

	@Override
	public void displaySolution(Solution<Position> solution) {}

	@Override
	public void error(String errorMessage) {
		
		mainWindow.showMessageBox(errorMessage);

	}

	@Override
	public void message(String message) {
		
		mainWindow.showMessageBox(message);

	}

}
