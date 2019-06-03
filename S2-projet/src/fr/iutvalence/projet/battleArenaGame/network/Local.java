package fr.iutvalence.projet.battleArenaGame.network;

import fr.iutvalence.projet.battleArenaGame.Game;
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
	
	private Game myGame;
	
	public Local(Game pGame)
	{
		this.myNetwork = new Network(pGame);
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

	@Override
	public void broadcast(int id, Object o)
	{
		// TODO Auto-generated method stub
		
	}

}
