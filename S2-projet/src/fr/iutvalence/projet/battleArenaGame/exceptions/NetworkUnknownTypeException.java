package fr.iutvalence.projet.battleArenaGame.exceptions;
/**
 * This exception is raised if the user try to send a non valid information: something that is not handled by the
 * network class.
 * 
 * @author pashmi
 *
 */
public class NetworkUnknownTypeException extends Exception
{

	public NetworkUnknownTypeException(Object transferedObject)
	{
		super(new String("This type :" + transferedObject.getClass() + " is not supported by the network..."));
	}
}
