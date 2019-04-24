package fr.iutvalence.projet.battleArenaGame.shape;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

//import java.util.ArrayList;

/**
 * 
 * @author durantho
 * This class extends shape, shapeCross represents a cross like shape in the game.
 * 
 *               |-2,0|    
 *               |-1,0|
 * |0,-2| |0,-1| |0 ,0| |0 ,1| |0 ,2|
 *               |1 ,0|
 *               |2, 0|
 */
public class ShapeCross extends ShapeSpecial{

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
