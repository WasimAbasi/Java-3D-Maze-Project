package controller;

import model.Model;
import view.View;

public class DisplayCrossSection implements Command {

	private Model model;
	private View view;
	
	public DisplayCrossSection(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		try {
			int index = Integer.parseInt(commandParameters[1]);
			char section = commandParameters[2].toLowerCase().charAt(0);
			String name = commandParameters[3].toLowerCase();
			
			int[][] maze = model.getMazeSection(name, section, index);
			int length = model.getMazeSectionLength(name, section);
			int width = model.getMazeSectionWidth(name, section);
			if(maze != null){
				view.displayCrossSection(maze, length, width);
			}
			else{
				view.error("Error occured while displaying a cross section!");
			}
		} catch (NumberFormatException e) {
			view.error("Invalid Arguments!");
		}
	}

}
