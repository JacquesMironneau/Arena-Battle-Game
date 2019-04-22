package fr.iutvalence.projet.battleArenaGame;

/**
 * 
 * @author durantho
 * This class extends shape, shapeCross represents a cross like shape inn the game.
 */
public class ShapeCross extends ShapeSpecial{

	public ShapeCross(String pName) {
		super(pName);
		this.effectedCells.add(new Coordinate(0,0));
		
		this.effectedCells.add(new Coordinate(0,-1));
		this.effectedCells.add(new Coordinate(0,-2));
		
		this.effectedCells.add(new Coordinate(0,1));
		this.effectedCells.add(new Coordinate(0,2));
		
		this.effectedCells.add(new Coordinate(1,0));
		this.effectedCells.add(new Coordinate(2,0));
		
		this.effectedCells.add(new Coordinate(-1,0));
		this.effectedCells.add(new Coordinate(-2,0));
	}

}
