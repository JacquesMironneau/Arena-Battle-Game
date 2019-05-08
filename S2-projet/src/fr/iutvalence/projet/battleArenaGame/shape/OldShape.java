package fr.iutvalence.projet.battleArenaGame.shape;
import java.io.Serializable;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * 
 * @author Jules
 * represent a shape for a spell. defined damage, max cooldown, range and his price.
 * A shape represents how a spell is physically represented, it can be a ball, a fist, a sword
 */


/**
 * TODO ENUM OF SHAPES
 *
 */
public class OldShape implements Serializable{

	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -9146357777294575373L;
//Constants

//ball shape constants
	/**
	 * Represents the damage of a ball shape.
	 */
	public final static int BALL_DAMAGE = 0;
	/**
	 * Represents the cooldown of a ball shape.
	 */
	public final static int BALL_COOLDOWN = 2;
	/**
	 * Represents the range of a ball shape.
	 */
	public final static int BALL_RANGE = 5;
	/**
	 * Represents the cost of a ball shape.
	 */
	public final static int BALL_SPELLCOST = 3;


//fist shape constants
	/**
	 * Represents the damage of a fist shape.
	 */
	public final static int FIST_DAMAGE = 0;
	/**
	 * Represents the cooldown of a fist shape.
	 */
	public final static int FIST_COOLDOWN = 1;
	/**
	 * Represents the range of a fist shape.
	 */
	public final static int FIST_RANGE = 1;
	/**
	 * Represents the cost of a fist shape.
	 */
	public final static int FIST_SPELLCOST = 2;
	
	
//Attributes
	/**
	 * the name of the shape.
	 */
	protected String type;
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
	protected ArrayList<Coordinate> effectedCoordinates;
	
	
//Constructor	
	
	/**
	 * create a new shape with a name and the coordinate that this shape will effect.
	 * it also set the values for the different types of shapes. 
	 * @param pName is the name of the shape
	 */
	public OldShape(String pType)
	{
		this.type = pType;
		this.effectedCoordinates = new ArrayList<Coordinate>();
		this.setShape(this.type);
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
	public ArrayList<Coordinate> getEffectedCoordinates()
	{
		return this.effectedCoordinates;
	}
	
	/**
	 * 
	 * @return the name of the shape.
	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * 
	 * @return the range of the shape 
	 */
	public int getRange()
	{
		return this.range;
	}
	
//setters	
	/**
	 * this method enables to set the values that match with different types of shapes
	 */
	public void setShape()
	{
		this.damage = 0;
		this.cooldown = 0;
		this.range = 0;
		this.spellCost = 0;
	}
	
	public void setShape(String type)
	{
		switch(type)
		{
			case "ball":
				this.damage = BALL_DAMAGE;
				this.cooldown = BALL_COOLDOWN;
				this.range = BALL_RANGE;
				this.spellCost = BALL_SPELLCOST;
				this.effectedCoordinates.add(new Coordinate(0,0));
				break;
				
			case "fist":
				this.damage = FIST_DAMAGE;
				this.cooldown = FIST_COOLDOWN;
				this.range = FIST_RANGE;
				this.spellCost = FIST_SPELLCOST;
				this.effectedCoordinates.add(new Coordinate(0,0));
				break;
				
		}
	}

//toString
	@Override
	public String toString() {
		return "Shape [name=" + type + ", damage=" + damage + ", cooldown=" + cooldown + ", range=" + range
				+ ", spellCost=" + spellCost + ", effectedCoordinates=" + effectedCoordinates + "]";
	}
	
	
	
	
}
