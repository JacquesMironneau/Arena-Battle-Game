package fr.iutvalence.projet.battleArenaGame;
/**
 * 
 * @author Jules
 * represent a shape for a spell. defined damage, max cooldown, range and his price.
 *  
 */
public class Shape {

	/**
	 * name of the shape.
	 */
	private String nameShape;
	
	/**
	 * damage of the shape in health points.
	 */
	private int damage;
	
	/**
	 * shape cooldown in turns.
	 */
	private int cooldown;
	
	/**
	 * the range of the shape in cells .
	 */
	private int range;
	
	/**
	 * price in action points.
	 */
	private int spellCost;
	
	/**
	 * create a new shape with a name
	 * @param pName is the name of the shape
	 */
	public Shape(String pName)
	{

	}
	
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