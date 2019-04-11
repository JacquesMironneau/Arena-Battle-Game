package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

/**
 * 
 * @author durantho
 * This class represents a player.
 * A player can ask to the system to move one of his pawn, or cast a spell, or end his turn.
 */
public class Player {
	private boolean isReady;
	private Pawn currentPawn;
	private ArrayList<SpellPage> playerPages;
	
	/**
	 * Constructor of Player Class it set isReady to false by default,
	 * the current pawn to null,
	 * and create a new ArrayList of SpellPage.
	 */
	public Player()
	{
		this.isReady = false;
		this.currentPawn = null;
		this.playerPages = new ArrayList<SpellPage>();
	}
	
	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * @param pDest The destination where the player want to move his current pawn.
	 */
	public void askMove(Movement pDest)
	{
		
	}
	
	/**
	 * Ask to the system to cast a spell to a wanted position.
	 * @param pDest Destination where the player like to cast a spell.
	 * @param pSpell The spell that the player want to cast.
	 */
	public void askSpell(Coordinate pDest, Spell pSpell)
	{
		
	}
	
	
	/**
	 * Set the current pawn to a specific pawn.
	 * @param pPawn The pawn that will be the current pawn of the player.
	 */
	public void setPawn(Pawn pPawn)
	{
		
	}
	
	/**
	 * Say to the system that a player finish his turn.
	 */
	public void endTurn()
	{
		
	}
	
	/**
	 * Ask to the system to set an effect to a spell.
	 * @param pSpellEffect The effect the player want to set on a spell.
	 */
	public void askSpellEffect(SpellEffect pSpellEffect)
	{
		
	}
	
	/**
	 * Ask to the system to apply a shape to a spell.
	 * @param pShape The shape that the player want to apply to a spell.
	 */
	public void askShape(Shape pShape)
	{
		
	}
	
	/**
	 * Tell the system that the player have finished to create his spell page
	 * This method loops while conditions of a valid spell page is missing
	 */
	public void validateSpellPage()
	{
		
	}
	
	/**
	 * Say to the system that this player is ready to play
	 * @return true the player is ready to play
	 */
	public boolean playerReady()
	{
		this.isReady = true;
	}
	
	/**
	 * Ask to the system if its possible to create a lobby
	 */
	public void askLobbyCreation()
	{
		
	}
}
