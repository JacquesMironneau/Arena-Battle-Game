package fr.iutvalence.projet.battleArenaGame.spell;


/**
 * 
 * Defines every possibles effects for a spell.
 * An effect change the statistics of a pawn at the start of the turn
 * An effect is initialized with :
 * 		Name("Element/Name","effect",duration [in turns]);
 */
public enum SpellEffect implements Effect {
		
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
	 * Constructor of SpellEffect
	 * @param pElementName name of the element to set
	 * @param pEffectName name of the effect to set
	 * @param pEffectDuration default duration of the effect to set
	 */
	SpellEffect(String pElementName,String pEffectName, int pEffectDuration)
	{
		this.elementName = pElementName;
		this.effectName = pEffectName;
		this.effectDuration = pEffectDuration;
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
}
