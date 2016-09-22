package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;
/**
 * Class ConstructProperties construsts an xml file with default properties for the program, including user interface and 
 * algorithm types.
 * @author Wasim, Roaa
 *
 */
public class ConstructProperties {

	public static void main(String[] args) 
	{		
		try 
		{
			XMLEncoder xmlE = new XMLEncoder(new FileOutputStream("./src/resources/properties.xml"));
			xmlE.writeObject(new Properties(10, "dfs", "growingtree","gui"));
			xmlE.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
