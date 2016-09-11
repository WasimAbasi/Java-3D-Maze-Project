package controller;

import model.MyModel;

public class DisplaySolution implements Command {

	private MyModel m;
	
	public DisplaySolution(MyModel m) {
		this.m = m;
	}

	@Override
	public void doCommand(String[] st) {
		m.displaySolution(st[1]);
	}

}
