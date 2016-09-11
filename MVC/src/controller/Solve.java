package controller;

import model.MyModel;

public class Solve implements Command {

	private MyModel m;
	
	public Solve(MyModel m) {
		this.m = m;
	}

	@Override
	public void doCommand(String[] st) {
		m.solveM3d(st[1], st[2]);
	}

}
