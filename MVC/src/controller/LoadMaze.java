package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import model.MyModel;

public class LoadMaze implements Command {

	private MyModel m;
	
	public LoadMaze(MyModel m) {
		this.m = m;
	}
	
	@Override
	public void doCommand(String[] st) {
		try {
			m.loadM3d(new FileInputStream(st[1]), st[2]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
