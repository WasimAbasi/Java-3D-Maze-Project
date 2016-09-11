package controller;

import java.io.File;
import model.MyModel;

public class Dir implements Command {

	private MyModel m;
	
	public Dir(MyModel m) {
		this.m = m;
	}

	@Override
	public void doCommand(String[] st) {
		m.dir(new File(st[1]));
	}

}
