package deprecated;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * TODO PUT IN THE SHAPE ENUM
 */
/**
 * 
 * @author durantho
 * This class represents a square shape in the game.
 * The coordinate (0,0) represents where the player click to cast his spell.
 * Example of a Square:
 * 
 * |-1,-1| | 0,-1| | 1,-1|
 * |-1, 0| | 0, 0| | 1, 0|
 * |-1, 1| | 0, 1| | 1, 1|
 *
 */
public class ShapeSquare extends ShapeSpecial implements Serializable
{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = 6252285842714764589L;

	/**
	 * Constructor of a shapeSquare
	 * Add the coordinates of a square shape in the effectedCells ArrayList.
	 * And set the correct values for the damage, the cooldown, the range and the spellCost.
	 * @param pType Type of the shape 
	 */
	public ShapeSquare(String pType) {
		super(pType);

		this.effectedCoordinates.add(new Coordinate(0,0));
		this.effectedCoordinates.add(new Coordinate(0,-1));
		this.effectedCoordinates.add(new Coordinate(0,1));
		
		this.effectedCoordinates.add(new Coordinate(-1,0));
		this.effectedCoordinates.add(new Coordinate(-1,-1));
		this.effectedCoordinates.add(new Coordinate(-1,1));
		
		this.effectedCoordinates.add(new Coordinate(1,0));
		this.effectedCoordinates.add(new Coordinate(1,-1));
		this.effectedCoordinates.add(new Coordinate(1,1));
		
	}
	
	public void setShape()
	{
		this.cooldown = ShapeSpecial.SSQ_COOLDOWN;
		this.damage = ShapeSpecial.SSQ_DAMAGE;
		this.range = ShapeSpecial.SSQ_RANGE;
		this.spellCost = ShapeSpecial.SSQ_SPELLCOST;
	}
	
	

}
