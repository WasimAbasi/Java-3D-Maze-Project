package controller;

import model.MyModel;

public class GenerateMaze implements Command {

	private MyModel m;
	
	public GenerateMaze(MyModel m) {
		this.m = m;
	}
	
	@Override
	public void doCommand(String[] st) {
		m.generateM3d(st[1], Integer.parseInt(st[2]), Integer.parseInt(st[3]), Integer.parseInt(st[4]), st[5]);
	}

}
