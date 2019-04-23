package fr.iutvalence.projet.battleArenaGame;
import java.lang.Math;

/**
 * Represents one move of a pawn, with a distance, origin coordinates and destination coordinates.
 * @author durantho
 */
public class Movement {
	
	/**
	 * distance between origin and destination
	 */
	private int distance;
	
	/**
	 * coordinate of pawns
	 */
	private Coordinate originCoordinate;
	
	/**
	 * destination of pawns
	 */
	private Coordinate destCoordinate;
	
	/**
	 * Constructor of a movement
	 * set all the parameters to null by default.
	 */
	public Movement()
	{
		this.distance = 0;
		this.originCoordinate = null;
		this.destCoordinate = null;
	}
	
	/**
	 * Create a movement with a origin position and a destination 
	 * and set the distance
	 * @param pOrigin coordinate of the pawn. 
	 * @param pDest coordinate of the destination
	 */
	public Movement(Coordinate pOrigin,Coordinate pDest)
	{
		this.distance = calculateDistance(pOrigin,pDest);
		this.originCoordinate = pOrigin;
		this.destCoordinate = pDest;
	}
	

	/**
	 * @return the distance of the move in number of cells.
	 */
	public int getDistance()
	{
		return this.distance;
	}
	
	/**
	 * Calculates the distance of the move in number of cells
	 * @param pOrigin coordinate of the pawn.
	 * @param pDest coordinate of the destination.
	 * @return the distance between origin and destination
	 */
	public int calculateDistance(Coordinate pOrigin,Coordinate pDest) 
	{
		return Math.abs((pOrigin.getCoordX() - pDest.getCoordY())+ (pOrigin.getCoordX()-pDest.getCoordY()));
	}
}
