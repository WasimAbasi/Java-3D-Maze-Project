package controller;

/**
 * Interface Controller represents the controller side of the MVC pattern. 
 * @author Wasim, Roaa
 *
 */
public interface Controller {

	void initiateCommands();

	void error(String errorMessage);

	void message(String message);
	
}
