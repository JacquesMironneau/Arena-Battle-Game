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

	public ShapeCross(String pName) {
		
		super(pName);
		
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
		this.damage = Shape.SC_DAMAGE;
		this.cooldown = Shape.SC_COOLDOWN;
		this.range = Shape.SC_RANGE;
		this.spellCost = Shape.SC_SPELLCOST;
	}

}
