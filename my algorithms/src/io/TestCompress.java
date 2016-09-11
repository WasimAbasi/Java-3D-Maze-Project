package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;

public class TestCompress {

	public static void main(String[] args) {
		Maze3d maze = new GrowingTreeGenerator().generate(5, 5, 5);
		// save it to a file
		OutputStream out;
		try {
			out = new MyCompressorOutputStream(
			new FileOutputStream("1.maz"));
			out.write(maze.toByteArray());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		InputStream in;
		try {
			in = new MyDecompressorInputStream(
			new FileInputStream("1.maz"));
			byte b[]=new byte[maze.toByteArray().length];
			in.read(b);
			in.close();
			Maze3d loaded = new Maze3d(b);
			System.out.println(loaded.equals(maze));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
