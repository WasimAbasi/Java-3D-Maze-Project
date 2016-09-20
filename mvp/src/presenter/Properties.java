package presenter;

import java.io.Serializable;

public class Properties implements Serializable {
	
	private static final long serialVersionUID = 1L;
		
	private String[] defaultMaze;
	
	private int maxNumOfThread;
	
	private String solutionsFilePath;
	
	private String LogFilePath;
	
	private String mazeGenerate;
	
	private String ProgramPath;
	
	private String defaultSolve;
	 private String viewInterface;
	
	public String getDefaultSolve() {
		return defaultSolve;
	}

	public void setDefaultSolve(String defaultSolve) {
		this.defaultSolve = defaultSolve;
	}
	
	public String[] getDefaultMaze() {
		return defaultMaze;
	}

	public void setDefaultMaze(String[] defaultMaze) {
		this.defaultMaze = defaultMaze;
	}

	public String getProgramPath() {
		return ProgramPath;
	}

	public void setProgramPath(String programPath) {
		ProgramPath = programPath;
	}

	public Properties(int maxNumOfThread, String solutionsFilePath, String mazeGenerate, String LogFilePath, String[] defaultMaze, String defaultSolve,String viewInterface) {
		super();
		this.maxNumOfThread = maxNumOfThread;
		this.solutionsFilePath = solutionsFilePath;
		this.mazeGenerate = mazeGenerate;
		this.LogFilePath = LogFilePath;
		this.defaultMaze = defaultMaze;
		this.defaultSolve = defaultSolve;
		this.viewInterface =viewInterface;
	}
	
	public Properties() {
		super();
	}
	

	public int getMaxNumOfThread() {
		return maxNumOfThread;
	}

	public void setMaxNumOfThread(int maxNumOfThread) {
		this.maxNumOfThread = maxNumOfThread;
	}

	public String getSolutionsFilePath() {
		return solutionsFilePath;
	}

	public void setSolutionsFilePath(String solutionsFilePath) {
		this.solutionsFilePath = solutionsFilePath;
	}

	public String getMazeGenerate() {
		return mazeGenerate;
	}

	public void setMazeGenerate(String mazeGenerate) {
		this.mazeGenerate = mazeGenerate;
	}

	public String getLogFilePath() {
		return LogFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		LogFilePath = logFilePath;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("maxNumOfThread: " + getMaxNumOfThread() + "\n" );
		sb.append("solutionsFilePath: " + getSolutionsFilePath() + "\n");
		sb.append("LogFilePath: " + getLogFilePath() + "\n" );
		sb.append("mazeGenerate: " + getMazeGenerate() + "\n");
		sb.append("Default solve: " + getDefaultSolve() + "\n");
		sb.append("Program path: " + getProgramPath() + "\n");
		sb.append("view interface: " +getViewInterface()+"\n");
		sb.append("Default maze values " + getDefaultMaze());
		return sb.toString();
	}

	public String getViewInterface() {
		return viewInterface;
	}
	public void setViewInterface(String viewInterface) {
		 this.viewInterface = viewInterface;
		
	}
}