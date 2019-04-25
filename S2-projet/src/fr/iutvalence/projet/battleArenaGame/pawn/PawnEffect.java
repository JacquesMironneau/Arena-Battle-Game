package fr.iutvalence.projet.battleArenaGame.pawn;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;

/**
 * Represent an effect that can affect a Pawn
 * He has an element Name (fire, ice...), an effect name (ignite, freeze,...), a default duration and a current duration
 * @author charvevi
 *
 */
public class PawnEffect implements Effect, Serializable {
	
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
	 * @param pNewDuration : new Duration for the effect
	 */
	public void setCurrentDuration(int pNewDuration)
	{
		this.currentDuration= pNewDuration;
	}
	
	/**
	 * Getter for the current duration
	 * @return : the current duration (number of Turn)
	 */
	public int getCurrentDuration()
	{
		return this.currentDuration;
	}

}
