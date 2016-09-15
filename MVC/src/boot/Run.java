package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import controller.*;
import model.MyModel;
import view.MyView;

public class Run {

	public static void main(String[] args){
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
	
		MyView view = new MyView(in, out);
		
		MyModel model = new MyModel();
		
		MyController controller = new MyController(view, model);
		
		model.SetController(controller);
		
		view.start();
		
	}
}


