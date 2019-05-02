package fr.iutvalence.projet.battleArenaGame.move;

import java.io.Serializable;

/**
 * Represent coordinate with a X-axis value and Y-axis value
 * A pawn moves from originCoordinate to destCoordinate
 * @author charvevi
 *
 */
public class Coordinate implements Serializable {

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -6617054550036231222L;
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
	 * Getter for coordX
	 * @return
	 */
	public int getCoordX()
	{
		return this.coordX;
	}
	
	/**
	 * Getter for coordY
	 * @return
	 */
	public int getCoordY()
	{
		return this.coordY;
	}
	
	@Override
	public String toString() {
		return "Coordinate [coordX=" + coordX + ", coordY=" + coordY + "]";
	}
	
	/**
	 * Add Coordinate value (x and y) to this coordinate
	 * @param pCoordinate Coordinate to add
	 * @return a new Coordinate which is the sum of the 2 Coordinate
	 */
	public Coordinate addCoordinate(Coordinate pCoordinate)
	{
		this.coordX  += pCoordinate.getCoordX();
		this.coordY += pCoordinate.getCoordY();
		return this;
	}
	

}
