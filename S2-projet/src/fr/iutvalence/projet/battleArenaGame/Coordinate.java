package fr.iutvalence.projet.battleArenaGame;

/**
 * Represent coordinate with a X-axis value and Y-axis value
 * @author charvevi
 *
 */
public class Coordinate {

	/**
	 * X value of the coordinate (absciss)
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
	
	

}
