package fr.iutvalence.projet.battleArenaGame.spell;

import fr.iutvalence.projet.battleArenaGame.shape.Shape;

/**
 * 
 * Represent a Spell which have a shape, a cooldown(so a delay (in turns) between two uses) and an Effect.
 * A spell can't be used if the currentCooldown is not equals to 0.
 * A spell can't be used if it costs more than the actual amount of Action Points of the player
 * Spell is a component of SpellPage used to make damage to pawns.
 *
 */
public class Spell {
	

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
	private Effect myEffect;
	
	/**
	 * Create a new empty Spell with a cooldown ready
	 */
	public Spell() 
	{
		this.currentCooldown = 0;
		this.myShape = null;
		this.myEffect = null;
	}
	
	/**
	 * Create a Spell from an already existing one
	 * @param pSpell the paste Spell 
	 */
	public Spell(Spell pSpell)
	{
		this.currentCooldown = pSpell.currentCooldown;
		this.myShape = pSpell.myShape;
		this.myEffect = pSpell.myEffect;
	}
	
	
	/**
	 * This reset the cooldown of the spell to is default value.
	 */
	public void resetCooldown()
	{
		this.currentCooldown=this.getDefaultCooldown();
	}
	
	/*
	 * Getters for spell
	 */
	public int getDefaultCooldown()
	{
		return this.myShape.getCooldown();
	}
	
	public int getCurrentCooldown()
	{
		return this.currentCooldown;
	}
	
	public Effect getSpellEffect()
	{
		return this.myEffect;
	}
	
	public Shape getShape()
	{
		return this.myShape;
	}
	/*
	 * Setters go spell
	 */
	public void setCurrentCooldown(int pcurrentCD)
	{
		this.currentCooldown = pcurrentCD;
	}
	
	public void setSpellEffect(Effect pEffectSpellEffect)
	{
		this.myEffect = pEffectSpellEffect ;
	}
	

	public void setShape(Shape pShape)
	{
		this.myShape = pShape;
	}
	
	
	/*
	 * Display every attributes of the spell 	
	 */
	public String toString()
	{
		return "[CurrentCooldown=" + currentCooldown + ", Shape :" + myShape + ", Effect:" + myEffect + "]";
	}
}
