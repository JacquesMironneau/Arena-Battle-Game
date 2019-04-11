package fr.iutvalence.projet.battleArenaGame;
/**
 * 
 * @author Jules
 * represent a Spell who have one shape, a cooldown and an Effect
 *
 */
public class Spell {
	
	/**
	 * current cooldown for a spell
	 */
	private int currentCooldown;
	
	/**
	 * shape of spell
	 */
	private Shape myShape;
	
	/**
	 * effect of a spell
	 */
	private SpellEffect myEffect;
	
	/**
	 * create a new empty Spell
	 * 
	 */
	public Spell() {	}
	
	
	/**	 
	 * recover defaultCooldown in his shape
	 * 
	 */
	public int getDefaultCooldown() {}
	
	/**
	 * set a new value to currentCooldown
	 * @param pcurrentCD new value of currentCooldown
	 */
	public void setCurrentCooldown(int pcurrentCD){}
	
	/**
	 * return currentCooldown of the Spell
	 * 
	 */
	public int getCurrentCooldown() {}
	
	/**
	 * set a new value to myEffect
	 * @param pEffectSpell_Effect is the new value of myEffect
	 */
	public void setSpellEffect(SpellEffect pEffectSpellEffect) {}
	
	/**
	 * set a new value to myShape
	 * @param pShape is the newvalue of myShape
	 */
	public void setShape(Shape pShape) {
		
	}
}
