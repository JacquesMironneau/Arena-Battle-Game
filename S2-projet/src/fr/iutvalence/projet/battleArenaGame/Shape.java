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
	
	/**
	 * Those represents the damage, the cooldown and the range of a Beam.
	 */
	public final static int SB_DAMAGE = 0;
	public final static int SB_COOLDOWN= 0;
	public final static int SB_RANGE = 0;
	public final static int SB_SPELLCOST = 0;
	
	/**
	 * Those represents the damage, the cooldown and the range of a Cross.
	 */
	public final static int SC_DAMAGE = 0;
	public final static int SC_COOLDOWN= 0;
	public final static int SC_RANGE = 0;
	public final static int SC_SPELLCOST = 0;
	
	
	/**
	 * Those represents the damage, the cooldown and the range of a Special Shape.
	 */
	public final static int SSP_DAMAGE = 0;
	public final static int SSP_COOLDOWN = 0;
	public final static int SSP_RANGE = 0;
	public final static int SSP_SPELLCOST = 0;
	
	/**
	 * Those represents the damage, the cooldown and the range of a Square.
	 */
	public final static int SSQ_DAMAGE = 0;
	public final static int SSQ_COOLDOWN = 0;
	public final static int SSQ_RANGE = 0;
	public final static int SSQ_SPELLCOST = 0;
	
//Attributes
	
	/**
	 * name of the shape.
	 */
	protected String nameShape;
	
	/**
	 * damage of the shape in health points.
	 */
	protected int damage;
	
	/**
	 * shape cooldown in turns.
	 */
	protected int cooldown;
	
	/**
	 * the range of the shape in cells .
	 */
	protected int range;
	
	/**
	 * price in action points.
	 */
	protected int spellCost;
	
	/**
	 * Represents the cells that the spell will effect.
	 */
	protected ArrayList<Coordinate> effectedCells = new ArrayList<Coordinate>();
	
	
//Constructor	
	
	/**
	 * create a new shape with a name
	 * @param pName is the name of the shape
	 */
	public Shape(String pName)
	{
		this.nameShape = pName;
		this.effectedCells = new ArrayList<Coordinate>();
		
		switch(this.nameShape)
		{
			case "beam":
				break;
		
			case "cross":
				break;
		
			case "square":
				break;
				
			
			default:
				break;
		}
	}
	
	
//methods
	/**
	 * return damage attribute of shape 
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * return cooldown attribute of shape
	 */
	public int getCooldown(){
		return this.cooldown;
	}
	
	/**
	 * return spellCost attribute of shape
	 */
	public int getSpellCost(){
		return this.spellCost;
	}
	
}
