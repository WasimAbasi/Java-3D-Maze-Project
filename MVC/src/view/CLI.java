package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import controller.Command;

/**
 * Class CLI defines a command line interface.
 * @author Wasim, Roaa
 *
 */
public class CLI{
	
	protected BufferedReader in;
	protected PrintWriter out;
	protected HashMap<String,Command> commands;

	//the user supplies an input stream, output stream and a list of commands
	public CLI(BufferedReader in, PrintWriter out, HashMap<String,Command> commands) {
		this.in = in;
		this.out = out;
		this.commands = new HashMap<String,Command>();
		this.commands.putAll(commands);
	}

	public void start(){
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				Scanner s;
				String [] choice = new String[10]; //The largest string is less than 10 characters.
				int i = 0;

				do{
					System.out.println("Please enter a command:");
					System.out.println("- dir <path>");
					System.out.println("- generate_maze <name> <rows> <columns> <floors> <algorithm {Simple,GrowingTree}>");
					System.out.println("- display <name>");
					System.out.println("- display_cross_section <index {X,Y,Z}> <name>");
					System.out.println("- save_maze <name> <file name>");
					System.out.println("- load_maze <file name> <name>");
					System.out.println("- solve <name> <algorithm {BFS,DFS}>");
					System.out.println("- display_solution <name>");
					System.out.println("- exit");

					try{
						s= new Scanner(in.readLine());
						s.useDelimiter(" ");
						while(s.hasNext())
						{
							choice[i] = s.next();
							i++;
						}

						if(commands.containsKey(choice[0]))
						{
							commands.get(choice[0]).doCommand(choice);
						}
					}catch(IOException e)
					{
						out.println("Invalid Command!");
					}
					i=0;
				}while(choice[0] != "exit");

			}
		}).start();
	}
}
