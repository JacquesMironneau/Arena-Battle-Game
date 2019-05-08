package fr.iutvalence.projet.battleArenaGame.spell;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.shape.OldShape;

/**
 * 
 * @author Jules
 * Represent a Spell who have one shape, a cooldown and an Effect.
 * A spell can't be used if the currentCooldown is not 0.
 * Spell is a component of SpellPage use to make damage to pawns.
 *
 */
public class Spell implements Serializable{
	
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -4985990245872091058L;

	/**
	 * current cooldown for a spell
	 */
	private int currentCooldown;
	
	/**
	 * shape of spell
	 */
	private OldShape myShape;
	
	/**
	 * effect of a spell
	 */
	private SpellEffect myEffect;
	
	/**
	 * create a new empty Spell
	 */
	public Spell() 
	{
		this.currentCooldown = 0;
		this.myShape = null;
		this.myEffect = null;
	}
	
	
	/**	 
	 * recover defaultCooldown in his shape
	 * 
	 */
	public int getDefaultCooldown()
	{
		return this.myShape.getCooldown();
	}
		
	/**
	 * set a new value to currentCooldown
	 * @param pcurrentCD new value of currentCooldown
	 */
	public void setCurrentCooldown(int pcurrentCD)
	{
		this.currentCooldown = pcurrentCD;
	}
	
	/**
	 * return currentCooldown of the Spell
	 * 
	 */
	public int getCurrentCooldown()
	{
		return this.currentCooldown;
	}
	
	/**
	 * Set a new value to myEffect
	 * @param pEffectSpell_Effect is the new value of myEffect
	 */
	public void setSpellEffect(SpellEffect pEffectSpellEffect)
	{
		this.myEffect = pEffectSpellEffect ;
	}
	
	/**
	 * Getter for myEffect
	 * @return the effect of the spell
	 */
	public SpellEffect getSpellEffect()
	{
		return this.myEffect;
	}
	
	/** 
	 * Set a new value to myShape
	 * @param pShape is the newvalue of myShape
	 */
	public void setShape(OldShape pShape)
	{
		this.myShape = pShape;
	}
	
	/**
	 * Getter for myShape the spell shape
	 * @return the shape of the spell
	 */
	public OldShape getShape()
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
		return "[ currentCooldown=" + currentCooldown + ", myShape=" + myShape + ", myEffect=" + myEffect + "]";
	}
}
