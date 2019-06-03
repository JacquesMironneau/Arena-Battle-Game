package fr.iutvalence.projet.battleArenaGame.network;
/**
 * Represent the way of communication between the players
 * 
 * @author mironnej
 *
 */
public interface Communication
{
	
	/**
	 * Prepare and set the communication
	 */
	public  void  init();
	
	/**
	 * Send to the other people an object
	 * @param o the object sended
	 */
	public void sendToOther(Object o);
	
	
	public void Broadcast(int id, Object o);
	
}
