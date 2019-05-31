package fr.iutvalence.projet.battleArenaGame.spell;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.shape.Shape;

/**
 * 
 * @author Jules
 * Represent a Spell who have a shape, a cooldown and an Effect.
 * A spell can't be used if the currentCooldown is not 0.
 * A spell can't be used if it costs more than the actual amount of Action Points of the player
 * Spell is a component of SpellPage use to make damage to pawns.
 *
 */
public class Spell implements Serializable{
	
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -4985990245872091058L;

	/**
	 * The current cooldown for a spell
	 */
	private int currentCooldown;
	
	/**
	 * The shape of spell
	 */
	private Shape myShape;
	
	/**
	 * An effect of a spell
	 */
	private SpellEffect myEffect;
	
	/**
	 * Create a new empty Spell
	 */
	public Spell() 
	{
		this.currentCooldown = 0;
		this.myShape = null;
		this.myEffect = null;
	}
	
	public Spell(Spell pSpell)
	{
		this.currentCooldown = pSpell.currentCooldown;
		this.myShape = pSpell.myShape;
		this.myEffect = pSpell.myEffect;
	}
	
	/**
	 * Getter for the default cooldown of the spell
	 * @return  myShape.getCooldown The default cooldown of the spell
	 */
	public int getDefaultCooldown()
	{
		return this.myShape.getCooldown();
	}
		
	/**
	 * Set a new value to currentCooldown
	 * @param pcurrentCD new value of currentCooldown
	 */
	public void setCurrentCooldown(int pcurrentCD)
	{
		this.currentCooldown = pcurrentCD;
	}
	
	/**
	 * Getter for the currentCooldown of the Spell
	 * @return currentCooldown
	 */
	public int getCurrentCooldown()
	{
		return this.currentCooldown;
	}
	
	/**
	 * Set a new value to myEffect
	 * @param pEffectSpellEffect is the new value of myEffect
	 */
	public void setSpellEffect(SpellEffect pEffectSpellEffect)
	{
		this.myEffect = pEffectSpellEffect ;
	}
	
	/**
	 * Getter for myEffect
	 * @return myEffect the effect of the spell
	 */
	public SpellEffect getSpellEffect()
	{
		return this.myEffect;
	}
	
	/** 
	 * Set a new value to myShape
	 * @param pShape is the new value of myShape
	 */
	public void setShape(Shape pShape)
	{
		this.myShape = pShape;
	}
	
	/**
	 * Getter for myShape the spell shape
	 * @return myShape the shape of the spell
	 */
	public Shape getShape()
	{
		return this.myShape;
	}
	
	/**
	 * Set the current cooldown to the default cooldown defined by the shape
	 */
	public void resetCooldown()
	{
		this.currentCooldown=this.getDefaultCooldown();
	}
	
	public String toString()
	{
		return "[CurrentCooldown=" + currentCooldown + ", Shape :" + myShape + ", Effect:" + myEffect + "]";
	}
}
