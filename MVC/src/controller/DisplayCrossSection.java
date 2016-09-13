package controller;

import model.MyModel;
import view.MyView;

public class DisplayCrossSection implements Command {

	private MyModel model;
	private MyView view;
	
	public DisplayCrossSection(MyModel model, MyView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		int index = Integer.parseInt(commandParameters[1]);
		char section = commandParameters[2].toLowerCase().charAt(0);
		String name = commandParameters[3].toLowerCase();
		
		int[][] maze = model.getMazeSection(name, section, index);
		int length = model.getMazeSectionLength(name, section, index);
		int width = model.getMazeSectionWidth(name, section, index);
		if(maze != null){
			view.displayCrossSection(maze, length, width);
		}
		else{
			view.error("Error occured while displaying a cross section!");
		}
	}

}
