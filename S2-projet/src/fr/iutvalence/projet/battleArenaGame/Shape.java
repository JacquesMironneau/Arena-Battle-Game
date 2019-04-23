package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Jules
 * represent a shape for a spell. defined damage, max cooldown, range and his price.
 *  
 */
public class Shape {

//Constants
	
	//TODO assign correct values to each of these constants.
	
//beam constants
	/**
	 * Represents the damage of a beam shape
	 */
	public final static int SB_DAMAGE = 0;
	/**
	 * Represents the cooldown of a beam shape
	 */
	public final static int SB_COOLDOWN= 0;
	
	/**
	 * Represents the range of a beam shape
	 */
	public final static int SB_RANGE = 0;
	
	/**
	 * Represents the cost of a beam shape
	 */
	public final static int SB_SPELLCOST = 0;

//cross constants
	/**
	 * Represents the damage of a cross shape
	 */
	public final static int SC_DAMAGE = 0;
	/**
	 * Represents the cooldown of a cross shape
	 */
	public final static int SC_COOLDOWN= 0;
	/**
	 * Represents the range of a cross shape
	 */
	public final static int SC_RANGE = 0;
	
	/**
	 * Represents the cost of a cross shape
	 */
	public final static int SC_SPELLCOST = 0;
	
//square constants
	/**
	 * Represents the damage of a square shape.
	 */
	public final static int SSQ_DAMAGE = 0;
	/**
	 * Represents the cooldown of a square shape.
	 */
	public final static int SSQ_COOLDOWN = 0;
	/**
	 * Represents the range of a square shape.
	 */
	public final static int SSQ_RANGE = 0;
	/**
	 * Represents the cost of a square shape.
	 */
	public final static int SSQ_SPELLCOST = 0;
	
//standard constants
	/**
	 * Represents the damage of a standard shape.
	 */
	public final static int STD_DAMAGE = 0;
	/**
	 * Represents the cooldown of a standard shape.
	 */
	public final static int STD_COOLDOWN = 0;
	/**
	 * Represents the range of a standard shape.
	 */
	public final static int STD_RANGE = 0;
	/**
	 * Represents the cost of a standard shape.
	 */
	public final static int STD_SPELLCOST = 0;
	
//Attributes
	
	/**
	 * the name of the shape.
	 */
	protected String name;
	
	/**
	 * Represents the damage of the shape in health points.
	 */
	protected int damage;
	
	/**
	 * Represents the cooldown of the shape in turns
	 */
	protected int cooldown;
	
	/**
	 * Represents the range of the shape in number of cells
	 */
	protected int range;
	
	/**
	 * Represents the price of the spell in action points.
	 */
	protected int spellCost;
	
	/**
	 * Represents the coordinate that the shape will affect.
	 */
	private Coordinate effectedCoordinate;
	
	
	
//Constructor	
	
	/**
	 * create a new shape with a name and the coordinate that this shape will effect.
	 * it also set the values for the different types of shapes. 
	 * @param pName is the name of the shape
	 */
	public Shape(String pName)
	{
		this.name = pName;
		this.effectedCoordinate = new Coordinate(0,0);
		this.setShape();
	}
	
	
//getters
	/**
	 * @return damage of the shape in health points
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * @return the cooldown of the shape in number of turns
	 */
	public int getCooldown(){
		return this.cooldown;
	}
	
	/**
	 * @return the price of the shape (spellcost)
	 */
	public int getSpellCost(){
		return this.spellCost;
	}
	
	/**
	 * 
	 * @return the coordinate that the shape will effect;
	 */
	public Coordinate getEffectedCoordinate()
	{
		return this.effectedCoordinate;
	}
	
	/**
	 * 
	 * @return the name of the shape.
	 */
	public String getName()
	{
		return this.name;
	}
	
//setters	
	/**
	 * this method enables to set the values that match with different types of shapes
	 */
	public void setShape()
	{
		this.damage = STD_DAMAGE;
		this.cooldown = STD_COOLDOWN;
		this.range = STD_RANGE;
		this.spellCost = STD_SPELLCOST;
	}
	

	

	
}
