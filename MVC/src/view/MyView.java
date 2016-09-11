package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.Controller;

public class MyView implements View {

	CLI cli;
	Controller controller;
	
	public MyView(BufferedReader in, PrintWriter out, HashMap<String,Command> hm) {
		super();
		this.cli = new CLI(in, out, hm);
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void start() {
		cli.start();
	}

}
