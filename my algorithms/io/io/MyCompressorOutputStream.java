package io;

import java.io.IOException;
import java.io.OutputStream;
/**
 * Compress a Byte Array byte array into file
 * @author Wasim, Roaa
 */
public class MyCompressorOutputStream extends OutputStream {
	
private OutputStream out;
	
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
	}
	
	@Override
	public void write(int b) throws IOException{
		out.write(b);
	}
	
	@Override
	public void write(byte[] arr) throws IOException {
		byte currByte = arr[0];
		int count = 1;
		
		for(int i = 1; i<arr.length;i++){
			if(arr[i] != currByte || count == 255){
				write(count);
				write(currByte);
				currByte = arr[i];
				count = 1;
			}
			else
				count++;
		}
		write(count);
		write(currByte);
	}
	
	/**
	 * Writes an integer bigger than 255
	 * @param b
	 * @throws IOException
	 */
	public void writeSize(int b) throws IOException {
		while(b > 255){
			out.write(255);
			b-=255;
		}
		out.write(b);
	}
	}