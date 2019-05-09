package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * this exception is raised when a player launch a spell that have a cost greater than the amount of action points of the player
 * @author durantho
 *
 */
public class NotEnoughPointsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -800349406020778690L;

	public NotEnoughPointsException() {
		super("Spell cost is higher than pawn Action Points");
	}
	
	

}
