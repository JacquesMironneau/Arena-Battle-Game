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
	
	//@override
	public void setShape()
	{
		this.cooldown = ShapeSpecial.SSQ_COOLDOWN;
		this.damage = ShapeSpecial.SSQ_DAMAGE;
		this.range = ShapeSpecial.SSQ_RANGE;
		this.spellCost = ShapeSpecial.SSQ_SPELLCOST;
	}
	
	

}
