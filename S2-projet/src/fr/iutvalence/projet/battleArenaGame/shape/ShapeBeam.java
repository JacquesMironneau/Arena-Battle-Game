package fr.iutvalence.projet.battleArenaGame.shape;

import java.io.Serializable;

/**
 * 
 * @author durantho
 * this class extends shape, this shape represents a beam like shape in the game.
 * 
 * 
 *           |0  ,0|  //represents the player 
 *           |n  ,0|
 *           |n+1,0|
 *           |n+i,0|
 */
public class ShapeBeam extends ShapeSpecial implements Serializable
{
	
	
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -8013775426779999474L;

	/**
	 * Constructor of a beam
	 * Add the coordinates of a beam shape in the effectedCells ArrayList.
	 * And set the correct values for the damage, the cooldown, the range and the spellCost.
	 * @param pType Type of the shape (in this case beam)
	 */
	public ShapeBeam(String pType) {
		super(pType);
		
		//found solutions to add the coordinates that the Beam will effect.
	}
	
	public void setShape()
	{
		this.damage = ShapeSpecial.SB_DAMAGE;
		this.cooldown = ShapeSpecial.SB_COOLDOWN;
		this.range = ShapeSpecial.SB_RANGE;
		this.spellCost = ShapeSpecial.SB_SPELLCOST;
	}
}
