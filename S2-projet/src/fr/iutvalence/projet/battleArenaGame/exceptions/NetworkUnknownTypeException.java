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

	/**
	 *serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object 
	 */
	private static final long serialVersionUID = 4743338760453254983L;

	public NetworkUnknownTypeException(Object transferedObject)
	{
		super(new String("This type :" + transferedObject.getClass() + " is not supported by the network..."));
	}
}
