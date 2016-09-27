package presenter;

import model.Model;

public class LoadXML implements Command {
	
	Model model;
	
	public LoadXML(Model model){
		this.model = model;
	}

	@Override
	public void doCommand(String[] commandParameters) {
		String[] path = new String[1];
		path[0] = commandParameters[1];
		model.loadXML(path);
	}
}
