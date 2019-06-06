package fr.iutvalence.projet.battleArenaGame.pawn;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;

/**
 * Represent an effect that can affect a Pawn
 * He has an element Name (fire, ice...), an effect name (ignite, freeze,...), a default duration and a current duration
 *
 */
public class PawnEffect implements Effect {
	

	/**
	 * Current duration of the effect (number of Turn)
	 */
	private int currentDuration;
	
	/**
	 * Name of the element (Fire...)
	 */
	private String elementName;
	
	/**
	 * Name of the effect (Ignite...)
	 */
	private String effectName;
	
	/**
	 * Default duration of the effect (number of Turn)
	 */
	private int effectDuration;
	
	/**
	 * Constructor for PawnEffect from a SpellEffect
	 * Copy all attributes from the spellEffect and set the current duration to the default effect duration
	 * @param pSpellEffect the effect to copy
	 */
	public PawnEffect(SpellEffect pSpellEffect)
	{
		this.elementName = pSpellEffect.getElementName();
		this.effectName = pSpellEffect.getEffectName();
		this.effectDuration = pSpellEffect.getEffectDuration();
		this.currentDuration = this.effectDuration;
	}

		
	/*
	 * Getters
	 */
	public String getElementName()
	{
		return this.elementName;
	}
	

	public String getEffectName()
	{
		return this.effectName;
	}
	
	public int getEffectDuration()
	{
		return this.effectDuration;
	}
	
	public int getCurrentDuration()
	{
		return this.currentDuration;
	}
	/*
	 * Setter for PawnEffect
	 */
	
	public void setCurrentDuration(int pNewDuration)
	{
		this.currentDuration= pNewDuration;
	}

	
}
