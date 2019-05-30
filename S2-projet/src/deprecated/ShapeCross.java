package deprecated;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

//import java.util.ArrayList;
/**
 * TODO PUT IN THE SHAPE ENUM
 */

/**
 * 
 * @author durantho
 * This class extends shape, shapeCross represents a cross like shape in the game.
 * The coordinate (0,0) represents where the player has clicked to cast his spell.
 * Example of a cross:
 * 
 * 				 |0,-2|    
 *               |0,-1|
 * |-2,0| |-1,0| |0 ,0| |1 ,0| |2, 0|
 * 				 |0 ,1|  
 * 				 |0 ,2|
 *               
 *               
 */
public class ShapeCross extends ShapeSpecial implements Serializable
{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -1605060884000608785L;
	/**
	 * Constructor of a cross shape
	 * Add the coordinates of a cross shape in the effectedCells ArrayList.
	 * And set the correct values for the damage, the cooldown, the range and the spellCost.
	 * @param pType Type of the shape (in this case cross)
	 */
	public ShapeCross(String pType) {
		
		super(pType);
		
		this.effectedCoordinates.add(new Coordinate(0,0));
		
		this.effectedCoordinates.add(new Coordinate(-2,0));
		this.effectedCoordinates.add(new Coordinate(-1,0));
		this.effectedCoordinates.add(new Coordinate(1,0));
		this.effectedCoordinates.add(new Coordinate(2,0));
		
		this.effectedCoordinates.add(new Coordinate(0,-2));
		this.effectedCoordinates.add(new Coordinate(0,-1));
		this.effectedCoordinates.add(new Coordinate(0,1));
		this.effectedCoordinates.add(new Coordinate(0,2));
		
	}
	//@override
	public void setShape()
	{
		this.damage = ShapeSpecial.SC_DAMAGE;
		this.cooldown = ShapeSpecial.SC_COOLDOWN;
		this.range = ShapeSpecial.SC_RANGE;
		this.spellCost = ShapeSpecial.SC_SPELLCOST;
	}

}
