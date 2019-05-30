package fr.iutvalence.projet.battleArenaGame.spell;

import java.io.Serializable;

/**
 * 
 * @author jules chapelle
 * Defines an effect for a spell.
 * An effect change the stats of a pawn at the start of the turn
 * An effect is initalized with :
 * 		Name("Element/Name","effect",duration [in turns]);
 */
public enum SpellEffect implements Effect, Serializable{
		
	/**
	 * first part is element 
	 * second is effect
	 * in third duration of the effect (in number of turn).
	 */
	Fire("Fire","Ignite",3),
	Ice("Ice","Slow",3),
	Stone("Stone","Stun",3),
	Electricity("Electricity","Silence",3),
	Wind("Wind","Unknow",3),
	Darkness("Darkness","Weakness",3);
	
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
	 * Constructor of SpellEffect
	 * @param pElementName name of the element to set
	 * @param pEffectName name of the effect to set
	 * @param pEffectDuration default duration of the effect to set
	 */
	SpellEffect(String pElementName,String pEffectName, int pEffectDuration)
	{
		this.elementName=pElementName;
		this.effectName=pEffectName;
		this.effectDuration=pEffectDuration;
	}
	
	/**
	 * Getter for the element name
	 * @return elementName the element name
	 */
	public String getElementName()
	{
		return this.elementName;
	}
	
	/**
	 * Getter for the effect name
	 * @return effectName the effect name
	 */
	public String getEffectName()
	{
		return this.effectName;
	}
	
	/**
	 * Getter for the effect duration
	 * @return effectDuration the effect duration
	 */
	public int getEffectDuration()
	{
		return this.effectDuration;
	}
}
