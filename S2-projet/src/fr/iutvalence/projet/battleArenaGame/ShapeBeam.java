package fr.iutvalence.projet.battleArenaGame;

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
public class ShapeBeam extends ShapeSpecial{
	
	
	/**
	 * 
	 * @param pName Name of the shape
	 */
	public ShapeBeam(String pName) {
		super(pName);
		
		//found solutions to add the coordinates that the Beam will effect.
	}
	
	public void setShape()
	{
		this.damage = Shape.SB_DAMAGE;
		this.cooldown = Shape.SB_COOLDOWN;
		this.range = Shape.SB_RANGE;
		this.spellCost = Shape.SB_SPELLCOST;
	}
}
