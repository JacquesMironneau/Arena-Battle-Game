package fr.iutvalence.projet.battleArenaGame.shape;

import java.io.Serializable;
import fr.iutvalence.projet.battleArenaGame.move.*;

/**
 * Shape Enumeration that represents all available types of shapes
 * @author durantho
 *
 */
public enum Shape implements Serializable {
	
	Ball("Ball",100,2,5,3,new Coordinate[] {new Coordinate(0,0)}),
	Fist("Fist",100,1,1,2,new Coordinate[] {new Coordinate(0,0)}),
	Cross("Cross",100,3,5,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)}),
	Square("Square",100,3,4,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)}),
	Sword("Sword",100,2,1,3, new Coordinate[] {new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)}),
	Beam("Beam",100,3,1,4, new Coordinate[] {new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)});
	
	/**
	 * Represents the type of the shape
	 */
	private String type;
	/**
	 * Represents the number of health points that the shape will remove
	 */
	private int damage;
	
	/**
	 * Represents the cooldown of the shape in number of turns
	 */
	private int cooldown;
	
	/**
	 * Represents the range of the shape, to make it simple it's how far you can click from the pawn
	 */
	private int range;
	
	/**
	 * Represents the cost in action points,
	 * It will effect the cost of a spell
	 */
	private int spellCost;
	
	/**
	 * It represents the cells that the shape will effect
	 */
	private Coordinate[] effectedCoordinates;
	
	/**
	 * 
	 * @param type the type of the Shape
	 * @param damage the damage that the shape will inflict
	 * @param cooldown the cooldown of the shape
	 * @param range the range of the shape
	 * @param spellCost the cost of the shape in action points
	 * @param effectedCoords the coordinate that will be effect by the shape
	 */
	Shape(String type,int damage,int cooldown,int range,int spellCost, Coordinate[] effectedCoords){
		this.type=type;
		this.damage=damage;
		this.cooldown=cooldown;
		this.range=range;
		this.spellCost=spellCost;
		this.effectedCoordinates=effectedCoords;
	}

	/**
	 * Getter for the type of shape
	 * @return the type of the shape
	 */
	public String getType() {
		return type;
	}

	/**
	 * Getter for the damages of the shape
	 * @return the damage that the shape will inflict
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Getter for the cooldown of the shape
	 * @return the reloading time of the shape in turns
	 */
	public int getCooldown() {
		return cooldown;
	}

	/**
	 * Getter for the range of the shape
	 * @return the range in cells (how far you can cast the spell from your pawn
	 */
	public int getRange() {
		return range;
	}

	/**
	 * Getter for the spellcost of the shape
	 * @return the cost of the shape in action points
	 */
	public int getSpellCost() {
		return spellCost;
	}

	/**
	 * Getter for the effectedCoordinates of the shape
	 * @return the array of coordinates that the shape will effect
	 */
	public Coordinate[] getEffectedCoordinates() {
		return effectedCoordinates;
	}
	
	/**
	 * Displays the coordinates clearly
	 * @return each coordinates that the shape will effect
	 */
	public String displayEffectedCoords() {
		int k;
		String effectedCoords = "[ ";
		for(k=0;k<this.effectedCoordinates.length;k++) {
			effectedCoords+= effectedCoordinates[k].toString()+" ";
		}
		effectedCoords+=" ]";
		return effectedCoords;
	}
	
	/**
	 * Displays the actual state of the shape
	 */
	public String toString() {
		return "[type="+this.type+",damage="+this.damage+",cooldown="+this.cooldown+",range="+this.range+",spellCost="+this.spellCost+"]";
	}

	
	
	
}
