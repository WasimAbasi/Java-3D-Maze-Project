package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class Presenter implements Observer {
	
	View view;
	Model model;
	HashMap<String, Command> commands;
	
	public Presenter(Model model,  View view) {
		this.model = model;
		this.view = view;
		commands = new HashMap<String, Command>();
		commands.put("dir", new Dir(this.view));	
		commands.put("generate_maze", new GenerateMaze(this.model, this.view));   
		commands.put("display", new Display(this.model, this.view));
		commands.put("display_cross_section", new DisplayCrossSection(this.model, this.view)); 
		commands.put("save_maze", new SaveMaze(this.model, this.view));	
		commands.put("load_maze", new LoadMaze(this.model, this.view));
		commands.put("solve", new Solve(this.model, this.view)); 
		commands.put("display_solution", new DisplaySolution(this.model, this.view));
		commands.put("exit", new Exit(this.model));
	}

	@Override
	public void update(Observable observable, Object object) {
		String[] command = (String[])object;
		
		if (observable == model)
		{
			switch(command[0])
			{
			case "message":
				view.message(command[1]);
			break;
			case "error":
				view.error(command[1]);
				break;	
			}
		}
		
		if (observable == view)
		{
			commands.get(command[0]).doCommand(command);
		}
	}
}
