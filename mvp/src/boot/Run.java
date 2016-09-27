package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.MyViewCLI;
import view.MyViewGUI;

public class Run {

	public static void main(String[] args) {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		MyModel model = new MyModel(args);
		

		
		String userInterface = model.getProperties().getUserInterface();
	
		if(userInterface.equals("gui"))
		{
			MyViewGUI view = new MyViewGUI();
			
			Presenter presenter = new Presenter(model, view);
			
			model.addObserver(presenter);
			
			view.addObserver(presenter);
			
			view.start();
		}
		else if(userInterface.equals("cli"))
		{
			MyViewCLI view = new MyViewCLI(in, out);
			
			Presenter presenter = new Presenter(model, view);
			
			model.addObserver(presenter);
			
			view.addObserver(presenter);
			
			view.start();
		}
		
	}

}
