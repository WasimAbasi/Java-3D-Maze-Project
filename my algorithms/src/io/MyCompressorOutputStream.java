package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class MyCompressorOutputStream compresses data using the following algorithm:
 * count the number of consecutive zeros/ones, write it and then write the digit.
 * @author Wasim, Roaa
 */
public class MyCompressorOutputStream extends OutputStream 
{
	protected OutputStream out;
	protected int previousByte;
	protected int count;

	/**
	 * constructor
	 * @param out output stream source
	 * 
	 */
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
		this.count = 0;

	}

	/**
	 * Method write compresses data and writes it to the out stream
	 */
	@Override
	public void write(int num) throws IOException 
	{
		if(count==0) //first byte
		{
			this.previousByte = num;
			this.count = 1;
			return;
		}
		if(num == this.previousByte) //Same digit
		{
			count++;
			//if there are more than 255 bytes from the same type write byte and restart count
			if(count == 256)
			{
				out.write(previousByte);
				out.write(255);
				count = 1;
			}
		}
		else //Different digit detected, write previous one and continue
		{
			out.write(previousByte);
			out.write(count);
			this.previousByte = num;
			this.count = 1; 
		}
	}
	
	/**
	 * Compress and write a byte array to the out stream
	 * @param byteArr: array for compression and writing
	 */
	@Override
	public void write(byte[] byteArr) throws IOException 
	{
		/*the super's write method writes all bytes except the last one 
		because of the way we implemented the write(int) method*/
		super.write(byteArr);
		if(count>0)//The last byte wasn't written
		{
			out.write((byte)previousByte);
			out.write((byte)count);
		}

		//Reset count and previousByte for future uses of the compressor object
		count = 0;
		previousByte = 0;
	}

}
