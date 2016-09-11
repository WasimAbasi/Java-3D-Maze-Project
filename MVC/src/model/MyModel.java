package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;

public class MyModel implements Model{

	@Override
	public void dir(File dir) {
		String [] arr;
		 if(dir.exists())
		 {
			 arr = dir.list();
		 }
		 else
		 {
//			 "The path " + dir.getPath() + " is not exists";
		 }
	}

	@Override
	public void getCrossBY(String name, int index, char place) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateM3d(String name, int x, int y, int z, String algo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveM3d(String name, FileOutputStream fos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadM3d(FileInputStream fis, String name) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void solveM3d(String name, String algo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySolution(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}
}
