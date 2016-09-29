package view;

import java.util.ArrayList;
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
import algorithms.search.State;

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
					 message = new String[1];
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
				
				String[] command = new String[6];
				command[0] = "generate_maze";
				command[1] = "m1"; //arbitrary name for the maze
				command[2] = Integer.toString(x);
				command[3] = Integer.toString(y);
				command[4] = Integer.toString(z);
				command[5] = null; //the generating algorithm will be taken from the xml file
				
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
				
				String[] command = new String[6];
				command[0] = "solve_from";
				command[1] = "m1";
				command[2] = null;
				command[3] = Integer.toString(x);
				command[4] = Integer.toString(y);
				command[5] = Integer.toString(z);
				
				isSolved=true;
				setChanged();
				notifyObservers(command);
			}
		});
		listeners.put("hint", new Listener() {
			
			@Override
			public void handleEvent(Event event) 
			{

				int x=characterPosition.getX();
				int y=characterPosition.getY();
				int z=characterPosition.getZ();
				
				String[] command = new String[6];
				command[0] = "solve_from";
				command[1] = "m1";
				command[2] = null;
				command[3] = Integer.toString(x);
				command[4] = Integer.toString(y);
				command[5] = Integer.toString(z);
				
				isSolved=false;
				setChanged();
				notifyObservers(command);
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
					
					if((x<mazeArr.length-1)&&(mazeArr[x+1][y][z]==0))
					{
						characterPosition.setPosition(x+1, y, z);
						mainWindow.moveCharacterInCanvas(x+1, y, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				else if((event.keyCode==SWT.ARROW_LEFT)||(event.keyCode==SWT.KEYPAD_4))
				{
					if((x>0)&&(mazeArr[x-1][y][z]==0))
					{
						characterPosition.setPosition(x-1, y, z);
						mainWindow.moveCharacterInCanvas(x-1, y, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
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
					if((z>0)&&(mazeArr[x][y][z-1]==0))
					{
						characterPosition.setPosition(x, y, z-1);
						mainWindow.moveCharacterInCanvas(x, y, z-1,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
					}
				}
				else if(event.keyCode==SWT.PAGE_UP)
				{
					if((z<mazeArr.length-1)&&(mazeArr[x][y][z+1]==0))
					{
						characterPosition.setPosition(x, y, z+1);
						mainWindow.moveCharacterInCanvas(x, y, z+1,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
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
		this.maze = maze;
		characterPosition = new Position(maze.getStartPosition().getX(),maze.getStartPosition().getY(),maze.getStartPosition().getZ());
		mainWindow.setMazeInCanvas(maze);
		mainWindow.moveCharacterInCanvas(maze.getStartPosition().getX(),maze.getStartPosition().getY(),maze.getStartPosition().getZ(),canBeMovedUp(),canBeMovedDown(),true,isBeingSolved);
	}

	@Override
	public void displayCrossSection(int[][] section, int length, int width) {}

	@Override
	public void displaySolution(Solution<Position> solution) {
		if(isSolved)
		{	
			
			ArrayList<State<Position>> al=solution.getStatesList();
			
			
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() 
				{
					for(State<Position> s:al)
					{
						if(mainWindow.IsDisposed())
						{
							return;
						}
						
						Position p=s.getValue();
						if(p.equals(maze.getGoalPosition()))
						{
							isBeingSolved=false;
						}
						int x=p.getX();
						int y=p.getY();
						int z=p.getZ();
						
						
						characterPosition.setPosition(x, y, z);
						
						mainWindow.moveCharacterInCanvas(x,y,z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							
							//e.printStackTrace();
						}
					}
					
				}
			});
			thread.start();
			
		}
		else//if hint
		{
			ArrayList<State<Position>> al=solution.getStatesList();
			if(al.isEmpty())
			{
				return;
			}
			if(al.size()<2)
			{
				mainWindow.showMessageBox("You are already in the goal Position,can't show hint");
				return;
			}
			Position p=al.get(1).getValue();
			
			if(p!=null)
			{
				int x=p.getX();
				int y=p.getY();
				int z=p.getZ();
				characterPosition.setPosition(x, y, z);
				mainWindow.moveCharacterInCanvas(x, y, z,canBeMovedUp(),canBeMovedDown(),false,isBeingSolved);
	
			}
		}
	}

	@Override
	public void error(String errorMessage) {
		
		mainWindow.showMessageBox(errorMessage);

	}

	@Override
	public void message(String message) {}



}
