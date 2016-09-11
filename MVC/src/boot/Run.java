package boot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.demo.SearchableAdapter;
import algorithms.search.Solution;
import model.MyModel;
import view.MyView;
import view.CLI;
import controller.*;

public class Run {

	public static void main(String[] args) throws IOException
	{
		new File("MyMaze");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		MyModel mm = new MyModel();
		HashMap<String,Command> hm = new HashMap<String,Command>();
		
		hm.put("Dir", new Dir(mm));
		hm.put("Generate3DMaze", new GenerateMaze(mm));
		hm.put("Display", new Display(mm));
		hm.put("DisplayCrossSectionBy", new DisplayCrossSection(mm));
		hm.put("SaveMaze", new SaveMaze(mm));
		hm.put("LoadMaze", new LoadMaze(mm));
		hm.put("Solve", new Solve(mm));
		hm.put("DisplaySolution", new DisplaySolution(mm));
		hm.put("Exit", new Exit(mm));
		
		MyView mv = new MyView(in, out, hm);
		
		MyController c = new MyController(mv, mm, hm);
		mv.setController(c);
		
		c.getMv().start();
		
	}
}


