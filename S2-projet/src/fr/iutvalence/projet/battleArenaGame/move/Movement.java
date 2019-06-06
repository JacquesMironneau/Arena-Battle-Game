 package fr.iutvalence.projet.battleArenaGame.move;

/**
 * Represents the cells traveled between two coordinates
 * Used for move of pawn and move of spells
 */

public class Movement {
	
	/**
	 * distance between origin and destination
	 */
	private int distance;
	
	/**
	 * first and origin coordinate
	 */
	private Coordinate originCoordinate;
	
	/**
	 * destination and second coordinate
	 */
	private Coordinate destCoordinate;
	
	/**
	 * Constructor of a movement
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
	 * Calculates the distance of the move in number of cells
	 * @return the distance between origin and destination
	 */
	private int calculateDistance() 
	{
		return Math.abs(this.originCoordinate.getCoordX() - this.destCoordinate.getCoordX())+ Math.abs(this.originCoordinate.getCoordY()-this.destCoordinate.getCoordY());
	}
	
	/*
	 * Getters for Movement
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
	 * Sum up the movement
	 */
	public String toString()
	{
		return "Movement [distance=" + distance + ", originCoordinate=" + originCoordinate + ", destCoordinate="
				+ destCoordinate + "]";
	}
	
	
}
