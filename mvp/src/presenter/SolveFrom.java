package presenter;

import model.Model;
import view.View;

public class SolveFrom implements Command {
	
	Model model;
	View view;
	
	public SolveFrom(Model model, View view){
		this.model = model;
		this.view = view;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		
		model.solveFrom(commandParameters[1], commandParameters[2], Integer.parseInt(commandParameters[3]),
				Integer.parseInt(commandParameters[4]), Integer.parseInt(commandParameters[5]));

	}

}
