package fr.iutvalence.projet.battleArenaGame.shape;

import java.io.Serializable;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * 
 * @author durantho
 * This class represents the shape that is linked with an effect there are not available for all the effect,
 * for example in a spell that is made of fire it special shape necessarily be a Cross.
 * Those Shapes affect more cells on the board.
 */
public class ShapeSpecial extends Shape implements Serializable{
	
//TODO assign correct damages values for each shape.
	
	/**
	 *serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object 
	 */
	private static final long serialVersionUID = -24959536532205165L;
//beam constants
	/**
	 * Represents the damage of a beam shape
	 */
	public final static int SB_DAMAGE = 0;
	/**
	 *  Represents the cooldown of a beam shape
	 */
	public final static int SB_COOLDOWN= 3;
		
	/**
	 * Represents the range of a beam shape
	 */
	public final static int SB_RANGE = 1;	
	/**
	 * Represents the cost of a beam shape
	 */
	public final static int SB_SPELLCOST = 4;
		
//Cross constants
	/**
	 * Represents the damage of a cross shape
	 */
	public final static int SC_DAMAGE = 0;
	/**
	 * Represents the cooldown of a cross shape
	 */
	public final static int SC_COOLDOWN= 3;
	/**
	 * Represents the range of a cross shape
     */
	public final static int SC_RANGE = 5;
	/**
	 * Represents the cost of a cross shape
	 */
	public final static int SC_SPELLCOST = 4;
		
//Square constants
	/**
	 * Represents the damage of a square shape.
	 */
	public final static int SSQ_DAMAGE = 0;
	/**
	 * Represents the cooldown of a square shape.
	 */
	public final static int SSQ_COOLDOWN = 3;
	/**
	 * Represents the range of a square shape.
	 */
	public final static int SSQ_RANGE = 4;
	/**
	 * Represents the cost of a square shape.
	 */
	public final static int SSQ_SPELLCOST = 4;
	
//Attributes	
	/**
	 * Represents the cells that the spell will effect, it contains all the coordinates that this shape will effect
	 */
	protected ArrayList<Coordinate> effectedCoordinates;

//Constructor	
	/**
	 * Constructor of a shape special,
	 * it initialize the arrayList that contains all the coordinates that the shape will effect.
	 * @param pType the type of the shape.
	 */
	public ShapeSpecial(String pType) 
	{
		super(pType);
		this.effectedCoordinates = new ArrayList<Coordinate>();	
	}

//Getters
	/**
	 * 
	 * @return the coordinates that the shape will effect.
	 */
	public ArrayList<Coordinate> getAffectedCoordinates()
	{
		return this.effectedCoordinates;
	}

//toString	
	@Override
	public String toString() {
		return "ShapeSpecial [effectedCoordinates=" + effectedCoordinates + ", name=" + type + ", damage=" + damage
				+ ", cooldown=" + cooldown + ", range=" + range + ", spellCost=" + spellCost + "]";
	}
	
	

}
