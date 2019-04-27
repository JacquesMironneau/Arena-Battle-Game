package fr.iutvalence.projet.battleArenaGame.exceptions;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
/**
 * Exception for coordinate: if the player try to move one of his pawn outside of the board, this exception is raised.
 * @author pashmi
 *
 */
public class CoordinateOutOfBoard extends Exception
{

	/**
	 *serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object 
	 */
	private static final long serialVersionUID = 2559108415411891289L;

	public CoordinateOutOfBoard(Coordinate pCoordinates)
	{
		super(new String("Coordonnées incorrectes " + pCoordinates));
	}
}
