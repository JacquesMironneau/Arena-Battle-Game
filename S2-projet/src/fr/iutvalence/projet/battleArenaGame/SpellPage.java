package fr.iutvalence.projet.battleArenaGame;

/**
 * This class represents a Spell page it is composed of 3 spells.
 * A spell page has a name, and a player.
 * each pawn have one spell page.
 * @author durantho
 */
public class SpellPage {
	private String pageName;
	
	private Spell spell1;
	private Spell spell2;
	private Spell spell3;
	private Player myPlayer;
	
	/**
	 * Constructor of a spell page
	 * @param pName The name you give to this SpellPage.
	 */
	public SpellPage(String pName)
	{
		this.pageName = pName;
		
		this.spell1 = new Spell();
		this.spell2 = new Spell();
		this.spell3 = new Spell();
	}
	
	/**
	 * @return the name off the page.
	 */
	public String getPageName()
	{
		return this.pageName;
	}
	/**
	 * return a spell
	 * @return spell1 
	 */
	public Spell getSpell1()
	{
		return this.spell1;
	}
	
	/**
	 * return a spell
	 * @return spell2
	 */
	public Spell getSpell2()
	{
		return this.spell2;
	}
	
	/**
	 * return a spell
	 * @return spell2
	 */
	public Spell getSpell3()
	{
		return this.spell3;
	}
	
	/**
	 * Enables the system to save the page 
	 * save the page in playerPage of player
	 */
	public void savePage()
	 {
		myPlayer.getplayerPages().add(this);
	 }

	/**
	 * Return the current state of the page,
	 * name
	 * spell1
	 * spell2
	 * spell3
	 */
	public String toString() {
		return "SpellPage [pageName=" + pageName + "]";
	}
	
}
