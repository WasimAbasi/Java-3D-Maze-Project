package controller;

import model.MyModel;

public class Exit implements Command {

	private MyModel m;
	
	public Exit(MyModel m) {
		this.m = m;
	}

	@Override
	public void doCommand(String[] st) {
		m.exit();
	}

}
