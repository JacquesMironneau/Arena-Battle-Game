package fr.iutvalence.projet.battleArenaGame.exceptions;


/**
 * This exception is raised if the player select an index for a spell that is not existing
 * @author pashmi
 *
 */
public class SpellIndexException extends Exception
{
	/**
	 *serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object 
	 */
	private static final long serialVersionUID = 1681062945384740590L;

	public SpellIndexException(int index)
	{
		super(new String("This index "+ index + "is not allowed, please select one between 0 and 2"));
	}
}
