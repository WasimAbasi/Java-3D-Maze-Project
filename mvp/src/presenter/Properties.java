package presenter;

import java.io.Serializable;

public class Properties implements Serializable {

	private static final long serialVersionUID = 5730626894017213933L;
	
	private int maxNumOfThreads;
	private String searchAlgorithm;
	private String generateAlgorithm;
	private String userInterface;
	
	public Properties() {
		super();
	}
	
	/**
	 * Constructor for Properties
	 * @param maxNumOfThreads: maximum number of simultaneously running threads.
	 * @param searchAlgorithm: default algorithm to use when searching for a solution to a maze.
	 * @param generateAlgorithm: default algorithm for generating a maze.
	 * @param userInterface: CLI or GUI.
	 */
	public Properties(int maxNumOfThreads, String searchAlgorithm, String generateAlgorithm,String userInterface) 
	{
		super();
		this.maxNumOfThreads = maxNumOfThreads;
		this.searchAlgorithm = searchAlgorithm;
		this.generateAlgorithm = generateAlgorithm;
		this.userInterface = userInterface;
	}
	
	public int getMaxNumOfThreads() {
		return maxNumOfThreads;
	}

	public void setMaxNumOfThreads(int maxNumOfThreads) {
		this.maxNumOfThreads = maxNumOfThreads;
	}

	public String getSearchAlgorithm() {
		return searchAlgorithm;
	}

	public void setSearchAlgorithm(String searchAlgorithm) {
		this.searchAlgorithm = searchAlgorithm;
	}

	public String getGenerateAlgorithm() {
		return generateAlgorithm;
	}

	public void setGenerateAlgorithm(String generateAlgorithm) {
		this.generateAlgorithm = generateAlgorithm;
	}

	public String getUserInterface() {
		return userInterface;
	}

	public void setUserInterface(String userInterface) {
		this.userInterface = userInterface;
	}
}