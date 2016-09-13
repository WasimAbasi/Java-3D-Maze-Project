package controller;

/**
 * Interface Command defines the functionality of a command which is 
 * to do a command using parameter given by the user.
 * @author Roaa, Wasim
 *
 */
public interface Command {
	
	void doCommand(String[] commandParameters);

}
