package fr.iutvalence.projet.battleArenaGame;

/**
 * 
 * @author durantho
 * This class represents a square shape in the game.
 * 
 * |-1,-1| |-1,0| |-1,1|
 * 
 * |0,-1| |0,0| |0,1|
 * 
 * |1,-1| |1,0| |1,1|
 *
 */
public class ShapeSquare extends ShapeSpecial{

	/**
	 * 
	 * @param pName Name of the shape 
	 * Constructor of a shapeSquare
	 * Add the coordinates of a square shape in the effectedCells ArrayList
	 */
	public ShapeSquare(String pName) {
		super(pName);
		

		this.effectedCells.add(new Coordinate(0,0));
		this.effectedCells.add(new Coordinate(0,-1));
		this.effectedCells.add(new Coordinate(0,1));
		
		this.effectedCells.add(new Coordinate(-1,0));
		this.effectedCells.add(new Coordinate(-1,-1));
		this.effectedCells.add(new Coordinate(-1,1));
		
		this.effectedCells.add(new Coordinate(1,0));
		this.effectedCells.add(new Coordinate(1,-1));
		this.effectedCells.add(new Coordinate(1,1));
	}

}
