package fr.iutvalence.projet.battleArenaGame.pawn;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;

/**
 * Represent an effect that can affect a Pawn
 * He has an element Name (fire, ice...), an effect name (ignite, freeze,...), a default duration and a current duration
 * @author charvevi
 *
 */
public class PawnEffect implements Effect, Serializable {
	
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = 8320330691124777890L;

	/**
	 * Current duration of the effect (number of Turn)
	 */
	private int currentDuration;
	
	/**
	 * Name of the element
	 */
	private String elementName;
	
	/**
	 * Name of the effect
	 */
	private String effectName;
	
	/**
	 * Default duration of the effect (number of Turn)
	 */
	private int effectDuration;
	
	/**
	 * Constructor for PawnEffect from a SpellEffect
	 * Copy all atributes from the spellEffect and set the current duration to the default effect duration
	 * @param pSpellEffect the effect to copy
	 */
	public PawnEffect(SpellEffect pSpellEffect)
	{
		this.elementName = pSpellEffect.getElementName();
		this.effectName = pSpellEffect.getEffectName();
		this.effectDuration = pSpellEffect.getEffectDuration();
		this.currentDuration = this.effectDuration;
	}

	
	/**
	 * Getter for the element name
	 * @return the element name
	 */
	public String getElementName()
	{
		return this.elementName;
	}
	
	/**
	 * Getter for the effect name
	 * @return the effect name
	 */
	public String getEffectName()
	{
		return this.effectName;
	}
	
	/**
	 * Getter for the effect duration
	 * @return the effect duration
	 */
	public int getEffectDuration()
	{
		return this.effectDuration;
	}
	
	/**
	 * Setter for the currentDuration
	 * @param pNewDuration the new Duration for the effect
	 */
	public void setCurrentDuration(int pNewDuration)
	{
		this.currentDuration= pNewDuration;
	}
	
	/**
	 * Getter for the current duration
	 * @return the current duration (number of Turn)
	 */
	public int getCurrentDuration()
	{
		return this.currentDuration;
	}
	
	
}
