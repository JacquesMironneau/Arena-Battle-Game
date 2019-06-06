package fr.iutvalence.projet.battleArenaGame.move;

/**
 * Represent coordinate with a X-axis value and Y-axis value
 * A pawn moves from originCoordinate to destCoordinate
 *
 */
public class Coordinate{


	/**
	 * X value of the coordinate (abscissa)
	 */
	private int coordX;
	/**
	 * Y value of the coordinate (ordinate)
	 */
	private int coordY;
	
	
	/**
	 * Constructor of a coordinate.
	 * @param x represents the value that will be set to coordX
	 * @param y represents the value that will be set to coordY
	 */
	public Coordinate(int x, int y)
	{
		this.coordX = x;
		this.coordY = y;
	}
	
	/**
	 * Add Coordinate value (x and y) to an other coordinate
	 * @param firstCoordinate Coordinate to add
	 * @param secondCoordinate the second one to add
	 * @return a new Coordinate which is the sum of the 2 Coordinate
	 */	
	public static Coordinate addCoordinate(Coordinate firstCoordinate, Coordinate secondCoordinate)
	{
		return new Coordinate(firstCoordinate.coordX+secondCoordinate.coordX, firstCoordinate.coordY + secondCoordinate.coordY);
	}
	
	/*
	 * Getters
	 */

	public int getCoordX()
	{
		return this.coordX;
	}
	
	public int getCoordY()
	{
		return this.coordY;
	}
	
	@Override
	public String toString() {
		return "Coordinate [coordX=" + coordX + ", coordY=" + coordY + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + coordX;
		result = prime * result + coordY;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (coordX != other.coordX)
			return false;
		if (coordY != other.coordY)
			return false;
		return true;
	}
}
