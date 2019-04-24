package fr.iutvalence.projet.battleArenaGame.shape;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * 
 * @author durantho
 * This class represents a square shape in the game.
 * 
 * |-1,-1| |-1, 0| |-1, 1|
 * | 0,-1| | 0, 0| | 0, 1|
 * | 1,-1| | 1, 0| | 1, 1|
 *
 */
public class ShapeSquare extends ShapeSpecial{

	/**
	 * 
	 * @param pName Name of the shape 
	 * Constructor of a shapeSquare
	 * Add the coordinates of a square shape in the effectedCells ArrayList.
	 * And set the correct values for the damage, the cooldown, the range and the spellCost.
	 */
	public ShapeSquare(String pName) {
		super(pName);

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
	
	//@override
	public void setShape()
	{
		this.cooldown = Shape.SSQ_COOLDOWN;
		this.damage = Shape.SSQ_DAMAGE;
		this.range = Shape.SSQ_RANGE;
		this.spellCost = Shape.SSQ_SPELLCOST;
	}
	
	

}
