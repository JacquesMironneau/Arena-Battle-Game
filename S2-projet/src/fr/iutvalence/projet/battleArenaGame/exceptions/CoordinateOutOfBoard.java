package fr.iutvalence.projet.battleArenaGame.exceptions;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
/**
 * Exception for coordinate: if the player try to move one of his pawn outside of the board, this exception is raised.
 * @author pashmi
 *
 */
public class CoordinateOutOfBoard extends Exception
{

	public CoordinateOutOfBoard(Coordinate pCoordinates)
	{
		super(new String("Coordonnées incorrectes " + pCoordinates));
	}
}
