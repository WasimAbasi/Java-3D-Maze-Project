package controller;

import model.MyModel;

public class Display implements Command {

	private MyModel m;
	
	public Display(MyModel m) {
		this.m = m;
	}

	@Override
	public void doCommand(String[] st) {
		m.display(st[1]);
	}

}
