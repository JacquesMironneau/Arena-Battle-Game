package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * this exception is raised when a player launch a spell that have a cost greater than the amount of action points of the player
 * @author durantho
 *
 */
public class NotEnoughPointsException extends Exception{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -800349406020778690L;

	public NotEnoughPointsException() {
		super("Spell cost is higher than pawn Action Points");
	}
	
	

}
