package fr.iutvalence.projet.battleArenaGame.network;

import fr.iutvalence.projet.battleArenaGame.exceptions.NetworkUnknownTypeException;
/**
 * Local class is a  way of communication in a local way.
 * Players are on one computer and application is working on only one node
 * @author mironnej
 *
 */
public class Local implements Communication
{
	/**
	 * Translate class
	 */
	private Network myNetwork;
	
	public Local()
	{
		this.myNetwork = new Network();
	}
	
	/**
	 * TODO javadoc
	 */
	public void init()
	{
		
	}

	/**
	 * Send the object to the other player (basically just translate it)
	 */
	public void sendToOther(Object o)
	{
		try
		{
			this.myNetwork.Transform(o);
		} catch (NetworkUnknownTypeException e)
		{
			e.printStackTrace();
		}
	}

}
