package fr.iutvalence.projet.battleArenaGame;

/**
 * Represents one move of a pawn, with a distance, origin coordinates and destination coordinates.
 * @author durantho
 */
public class Movement {
	private int distance;
	
	private Coordinate originCoordinate;
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
	 *  Move the pawn to wanted coordinates
	 * @param pCoordinate the destination coordinates
	 */
	public void move(Coordinate pCoordinate) {
		
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
	 */
	public void calculateDistance() 
	{
		
	}
}
