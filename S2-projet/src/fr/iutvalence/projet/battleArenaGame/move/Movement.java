package fr.iutvalence.projet.battleArenaGame.move;
import java.lang.Math;

/**
 * Represents one move of a pawn, with a distance, origin coordinates and destination coordinates.
 * Might also be a movement of a spell (the ground traveled )
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
	 * 
	 * TODO: remove this or add setters
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
		this.originCoordinate = pOrigin;
		this.destCoordinate = pDest;
		this.distance = calculateDistance();
	}
	

	/**
	 * @return the distance of the move in number of cells.
	 */
	public int getDistance()
	{
		return this.distance;
	}
	
	public Coordinate getOriginCoordinate()
	{
		return this.originCoordinate;
	}
	
	public Coordinate getDestCordinate()
	{
		return this.destCoordinate;
	}
	
	/**
	 * Calculates the distance of the move in number of cells
	 * @return the distance between origin and destination
	 */
	public int calculateDistance() 
	{
		return Math.abs(this.originCoordinate.getCoordX() - this.destCoordinate.getCoordX())+ Math.abs(this.originCoordinate.getCoordY()-this.destCoordinate.getCoordY());
	}
	
	/**
	 * Sum up the movement
	 */
	public String toString()
	{
		return "Movement [distance=" + distance + ", originCoordinate=" + originCoordinate + ", destCoordinate="
				+ destCoordinate + "]";
	}
	
	
}
