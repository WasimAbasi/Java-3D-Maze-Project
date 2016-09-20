package gZip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <h1>GZipArchiver</h1>
 * The GZipArchiver class which pack and unpack Data Base HashMap to and from a Gzip file
 *
 * @author  Wasim, Roaa
 */
public class GZipArchiver {
	public GZipArchiver(){}

	/**
	 * compressToGZipFile() Method compresses the current "DB" of mazes and their solutions to GZIP file
	 * 
	 * @param map is a ConcurrentHashMap<String, String> object
	 */
    public void compressToGZipFile(ConcurrentHashMap<String, String> map) throws IOException{
    	//Create a serializable Data object
    	ConcurrentHashMap<String, String> serMap = new ConcurrentHashMap<>();
    	//migrating data 
    	for(String key: map.keySet())
    		serMap.put(key, map.get(key));
    		
    	FileOutputStream fos = new FileOutputStream("map.gz");
    	GZIPOutputStream gz = new GZIPOutputStream(fos);
    	ObjectOutputStream oos = new ObjectOutputStream(gz);
    	oos.writeObject(serMap);
    	oos.close();
    }
	 
	/**
	 * deCompressFromGZip() Method de-compresses the GZIP file to current "DB" of mazes and their solutions
	 * 
	 * @param f is a File object
	 * @return the ConcurrentHashMap<String, String> which is our "DB"
	 */
	public ConcurrentHashMap<String, String> deCompressFromGZip(File f) throws IOException, ClassNotFoundException{
		  	FileInputStream fin = new FileInputStream(f);
	    	GZIPInputStream gzis = new GZIPInputStream(fin);
	    	ObjectInputStream ois = new ObjectInputStream(gzis);
	    	@SuppressWarnings("unchecked")
			ConcurrentHashMap<String, String> newMap = ((ConcurrentHashMap<String, String>)ois.readObject());
	    	
    	ois.close();
    	return newMap;
	}
}
