package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class MyDecompressor decompresses the data it gets from the input stream
 * @author Wasim, Roaa
 *
 */
public class MyDecompressorInputStream extends InputStream {
	
	protected InputStream in;
	protected int currByte;
	protected int count;

	/**
	 * constructor
	 * @param in input stream source
	 */
	public MyDecompressorInputStream(InputStream in) {
		super();
		this.in = in;

	}

	/**
	 * Method read reads data from input stream, decompresses and returns it
	 */
	@Override
	public int read() throws IOException 
	{
		if(count<=0)
		{
			if((currByte=in.read()) == -1) //input stream is empty or finished reading
			{
				return -1;
			}
			if((count=in.read()) == -1)//There is a problem with the sequence of bytes
			{
				throw (new IOException("Expected count, invalid byte array!"));
			}
			if(count<=0) //count is negative
			{
				throw (new IOException("Invalid count"));
			}
		}
		//return currByte count times
		count--;
		return currByte;
	}
}
