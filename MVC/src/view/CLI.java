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
	
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String,Command> commands;

	//the user supplies an input stream, output stream and a list of commands
	public CLI(BufferedReader in, PrintWriter out, HashMap<String,Command> commands) {
		this.in = in;
		this.out = out;
		this.commands = commands;
	}

	public void start(){
		new Thread(new Runnable(){
			@Override
			public void run()
			{
				Scanner scanner;
				String [] command = new String[10]; //The largest command including parameters is less than 10 strings.

				do{
					out.println("Please enter a command:");
					out.println("- dir <path>");
					out.println("- generate_maze <name> <rows> <columns> <floors> <algorithm {Simple,GrowingTree}>");
					out.println("- display <name>");
					out.println("- display_cross_section <index {X,Y,Z}> <name>");
					out.println("- save_maze <name> <file name>");
					out.println("- load_maze <file name> <name>");
					out.println("- solve <name> <algorithm {BFS,DFS}>");
					out.println("- display_solution <name>");
					out.println("- exit");
					out.flush();

					int i = 0;
					try{
						scanner = new Scanner(in.readLine());
						scanner.useDelimiter(" ");
						while(scanner.hasNext())
						{
							command[i] = scanner.next();
							i++;
						}
						if(commands.containsKey(command[0]))
						{
							commands.get(command[0]).doCommand(command);
						}
						else
						{
							out.println("Invalid Command. Please try again.");
							out.flush();
						}
					}catch(IOException e)
					{
						e.printStackTrace();
					} catch(Exception e){
						out.println("Invalid Command. Please try again.");
					}
				}while(command[0] != "exit");

			}
		}).start();
	}
}
