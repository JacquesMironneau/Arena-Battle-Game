package fr.iutvalence.projet.battleArenaGame.spell;


/**
 * Interface that represents available effects
 * Has an element name, an effect name and a default duration
 * Used to define PawnEffect and SpellEffect
 */
public interface Effect {
	/**
	 * Getter for the element name
	 * @return the element name
	 */
	public String getElementName();
	
	/**
	 * Getter for the effect name
	 * @return the effect name
	 */
	public String getEffectName();
	
	/**
	 * Getter for the effect duration
	 * @return the effect duration
	 */
	public int getEffectDuration();

}
