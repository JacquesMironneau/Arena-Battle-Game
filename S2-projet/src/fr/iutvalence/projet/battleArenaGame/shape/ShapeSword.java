package fr.iutvalence.projet.battleArenaGame.shape;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * TODO PUT IN THE SHAPE ENUM
 */
/**
 * 
 * @author durantho
 * This class represents a sword Shape.
 * 
 * Example of a sword Shape:
 * 	|-1,-1| | 0,-1| | 1,-1|
 *  |-1, 0|         | 1, 0|
 *  |-1, 1| | 0, 1| | 1, 1|
 *
 */



public class ShapeSword extends ShapeSpecial implements Serializable{

//Attributes
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = 4498483395472139729L;

	public ShapeSword(String pType) {
		super(pType);
		
		this.effectedCoordinates.add(new Coordinate(-1,-1));
		this.effectedCoordinates.add(new Coordinate(-1,0));
		this.effectedCoordinates.add(new Coordinate(-1,1));
		
		this.effectedCoordinates.add(new Coordinate(0,-1));
		this.effectedCoordinates.add(new Coordinate(0,1));
		
		this.effectedCoordinates.add(new Coordinate(1,-1));
		this.effectedCoordinates.add(new Coordinate(1,0));
		this.effectedCoordinates.add(new Coordinate(1,1));
	}
	
	public void setShape()
	{
		this.damage = ShapeSpecial.SWD_DAMAGE;
		this.damage = ShapeSpecial.SWD_COOLDOWN;
		this.spellCost = ShapeSpecial.SWD_SPELLCOST;
		this.range = ShapeSpecial.SWD_RANGE;
	}

}
