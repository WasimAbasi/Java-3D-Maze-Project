package controller;

import java.util.HashMap;
import model.MyModel;
import view.MyView;

public class MyController implements Controller {
	private MyView mv;
	private MyModel mm;
	private HashMap<String,Command> hm;

	public MyView getMv() {
		return mv;
	}
	
	public MyModel getModel(){
		return mm;
	}
	
	public HashMap<String, Command> getHm() {
		return hm;
	}

	public MyController(MyView mv, MyModel mm, HashMap<String, Command> hm) {
		super();
		this.mv = mv;
		this.mm = mm;
		this.hm = hm;
	}
	
	
	
}