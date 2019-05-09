package fr.iutvalence.projet.battleArenaGame.exceptions;

/**
 * This exception is raised when the player create an invalid movement
 * @author durantho
 *
 */
public class InvalidMoveException extends Exception{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -3682828203591517564L;
	
	public InvalidMoveException(String exception) {
		super(exception);
	}


}
