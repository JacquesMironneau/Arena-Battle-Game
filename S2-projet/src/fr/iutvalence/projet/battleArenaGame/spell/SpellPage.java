package fr.iutvalence.projet.battleArenaGame.spell;

/**
 * This class represents a Spell page it is composed of 3 spells.
 * It's basically a container for some spells
 * A spell page has a name.
 * each pawn have one spell page.
 */

public class SpellPage{

	/**
	 * name of the page.
	 */
	private String pageName;
	
	/**
	 * Array of spells in this page
	 */
	private Spell[] spells;

	/**
	 * Constructor of a spell page
	 * create a SpellPage with 3 empty spell
	 * @param pName The name you give to this SpellPage.
	 */
	public SpellPage(String pName,Spell spell1,Spell spell2,Spell spell3)
	{
		this.spells = new Spell[3];
		this.pageName = pName;
		this.spells[0] = spell1;
		this.spells[1] = spell2;
		this.spells[2] = spell3;
	}

	/**
	 * Create a spellPage based on another one
	 * @param pCopiedPage the SpellPage Copied
	 */
	public SpellPage(SpellPage pCopiedPage)
	{
		this.spells = new Spell[3];
		this.pageName = pCopiedPage.pageName;
		this.spells[0] = new Spell(pCopiedPage.getSpell(0));
		this.spells[1] = new Spell(pCopiedPage.getSpell(1));
		this.spells[2] = new Spell(pCopiedPage.getSpell(2));

		
	}
	

	/*
	 *  Getters for the spellPage
	 */
	public String getPageName()
	{
		return this.pageName;
	}

	public Spell[] getSpell()
	{
		return this.spells;
	}
	
	public Spell getSpell(int spellIndex)
	{
		return this.spells[spellIndex];
	}
	
	/**
	 * setter for Spell
	 */
	
	public void setSpell(int pageIndex,Spell pSpell)
	{
		this.spells[pageIndex] = pSpell;
	}
	
	/**
	 * Return the current state of the page,
	 * name and spells
	 */
	public String toString() {
		return "SpellPage [pageName=" + pageName + ", spell1=" + this.spells[0] + ", spell2=" + this.spells[1] + ", spell3=" + this.spells[2] + "]";
	}
	
}
