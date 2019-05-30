package fr.iutvalence.projet.battleArenaGame.spell;

import java.io.Serializable;

import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;

/**
 * This class represents a Spell page it is composed of 3 spells.
 * A spell page has a name.
 * each pawn have one spell page.
 * @author durantho
 */

public class SpellPage implements Serializable{
	
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = 474283235313170067L;

	/**
	 * name of the page.
	 */
	private String pageName;
	
	/**
	 * Table of spells in this page
	 */
	private Spell[] spells;

	/**
	 * Constructor of a spell page
	 * @param pName The name you give to this SpellPage.
	 */
	public SpellPage(String pName)
	{
		this.pageName = pName;
		this.spells = new Spell[3];
		this.spells[0] = new Spell();
		this.spells[1] = new Spell();
		this.spells[2] = new Spell();
	}
	
	/**
	 * Getter for the page name
	 * @return pageName the name off the page.
	 */
	public String getPageName()
	{
		return this.pageName;
	}
	/**
	 * return the table of spells.
	 * @return spells the array of spells
	 */
	public Spell[] getSpell()
	{
		return this.spells;
	}
	
	/**
	 * return the spell in the table of all spells
	 * @param spellIndex index of the spell to return
	 * @return spells[spellIndex] the spell at the specified index
	 */
	public Spell getSpell(int spellIndex)
	{
		return this.spells[spellIndex];
	}
	
	public void setSpell(int pageIndex,Spell pSpell) throws SpellIndexException
	{
		if(pageIndex>2 || pageIndex<0) throw new SpellIndexException(pageIndex);
		this.spells[pageIndex] = pSpell;
	}
	
	/**
	 * Return the current state of the page,
	 * name
	 * spells
	 */
	public String toString() {
		return "SpellPage [pageName=" + pageName + ", spell1=" + this.spells[0] + ", spell2=" + this.spells[1] + ", spell3=" + this.spells[2] + "]";
	}
	
}
