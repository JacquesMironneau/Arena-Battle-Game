package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * This exception is raised when the player want to cast a spell that is currently on cooldown
 * @author durantho
 *
 */
public class SpellOnCooldownException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9112019511377977915L;

	public SpellOnCooldownException() {
		super("Spell is currently on cooldown");
	}
	

}
