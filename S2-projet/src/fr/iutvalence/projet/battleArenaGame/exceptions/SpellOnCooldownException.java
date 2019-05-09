package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * This exception is raised when the player want to cast a spell that is currently on cooldown
 * @author durantho
 *
 */
public class SpellOnCooldownException extends Exception{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -9112019511377977915L;

	public SpellOnCooldownException() {
		super("Spell is currently on cooldown");
	}
	

}
