package fr.iutvalence.projet.battleArenaGame.view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * This class reads inputs from an input Stream
 * Be carefull of not enter an String when you want to read an int :)
 * @author durantho
 *
 */
public class CheapScanner {
	
	/**
	 * Allows the class to read a line from an input Stream
	 */
	private BufferedReader in;
	
	/**
	 * Constructor of a CheapScanner
	 * It reads from System.in by default
	 */
	public CheapScanner() {
		this.in= new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Constructor of a Cheap Scanner it allows the user to read from a specified InputStream
	 * @param is an InputStream to read from
	 */
	public CheapScanner(InputStream is) {
		this.in = new BufferedReader(new InputStreamReader(is));
	}
	
	/**
	 * @return An int value from the InputStream
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public int getInt() throws NumberFormatException, IOException{
		int result = 0;
			result = Integer.valueOf(this.in.readLine());
		return result;
	}
	
	/**
	 * @return The String from the bufferedReader
	 * @throws IOException
	 */
	public String getStr(){
		try {
			return this.in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Close the InputStream
	 * @throws IOException
	 */
	public void close(){
		try {
			this.in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
