package fr.iutvalence.projet.battleArenaGame.exceptions;


/**
 * This exception is raised if the player select an index for a spell that is not existing
 * @author pashmi
 *
 */
public class SpellIndexException extends Exception
{
	public SpellIndexException(int index)
	{
		super(new String("This index "+ index + "is not allowed, please select one between 0 and 2"));
	}
}
