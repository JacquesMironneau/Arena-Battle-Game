package fr.iutvalence.projet.battleArenaGame.spell;


/**
 * 
 * Defines every possibles effects for a spell.
 * An effect change the statistics of a pawn at the start of the turn
 * An effect is initialized with :
 * 		Name("Element/Name","effect",duration [in turns], ModifiedHP, ModifiedAP, Modified MP);
 *	The modified attributes are the modifications done to the pawn which undergo the effect
 * (For instance the -5 in healthModifier, decrease Pawn's health points by 5.
 */
public enum Effect{
		
	/**
	 * first part is element 
	 * second is effect
	 * in third duration of the effect (in number of turn).
	 * 
	 */
	
	Fire("Fire","Ignite",3, -5, 0, 0),
	Ice("Ice","Slow",3, 0, 0, -2),
	Stone("Stone","Stun",3, 0, -1, -1),
	Electricity("Electricity","Silence",3, 0, -2, 0),
	Wind("Wind","Unknow",3, 0, 0, 0),
	Darkness("Darkness","Weakness",3, 0, 0 , 0);
	
	
	
	/**
	 * Name of the element (an element can be Fire, Ice ...)
	 */
	private String elementName;
	
	/**
	 * Name of the effect (might be Ignite ,Stun...)
	 */
	private String effectName;
	
	/**
	 * Default duration of the effect (number of Turn)
	 */
	private int effectDuration;

	/**
	 * Removed health by the effect
	 */
	private int healthModifier; 
	
	
	/**
	 * Removed action point by the effect
	 *
	 */
	private int actionPointModifier;
	
	/**
	 * Remove action point by the effect
	 */
	
	private int movePointModifier;
	
	
	/**
	 * Constructor of SpellEffect
	 * @param pElementName name of the element to set
	 * @param pEffectName name of the effect to set
	 * @param pEffectDuration default duration of the effect to set
	 * @param pHealthModifier: how many hp are removed
	 * @param pActionPointModifier: how many AP are removed
	 * @param pMovePointModifier: how many PM are removed
	 */
	Effect(String pElementName,String pEffectName, int pEffectDuration, int pHealthModifier, int pActionPointModifier, int pMovePointModifier)
	{
		this.elementName = pElementName;
		this.effectName = pEffectName;
		this.effectDuration = pEffectDuration;
		this.healthModifier = pHealthModifier;
		this.actionPointModifier = pActionPointModifier;
		this.movePointModifier = pMovePointModifier;
	}
	
	/*
	 * Getters for spellEffect
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

	public int getHealthModifier()
	{
		return this.healthModifier;
	}

	public int getActionPointModifier()
	{
		return this.actionPointModifier;
	}

	public int getMovePointModifier()
	{
		return this.movePointModifier;
	}

	
	
	
}
