package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * This exception is raised when the player cast a spell out of its range
 * @author durantho
 *
 */
public class SpellOutOfRangeException extends Exception{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = 5603739569361930671L;

	public SpellOutOfRangeException() {
		super("Spell is out of range");
	}
	
	
}
