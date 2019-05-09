package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * This exception is raised when the player cast a spell out of its range
 * @author durantho
 *
 */
public class SpellOutOfRangeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5603739569361930671L;

	public SpellOutOfRangeException() {
		super("Spell is out of range");
	}
	
	
}
