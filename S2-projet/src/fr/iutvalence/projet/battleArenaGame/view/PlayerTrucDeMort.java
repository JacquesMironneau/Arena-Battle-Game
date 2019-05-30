package fr.iutvalence.projet.battleArenaGame.view;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
// THIS IS IHM
/*REMINDER:
 * 
 * Player : -> enlever les attributs (currentPawn est useless)
 * les pages vont dans un tableau de joueur dans game
 * La game dégage (y penser)
 * TurnInProgress(comprendre)
 * 
 * Pour game:
 * couper en 2 la game et faire un board qui gère tout
 */
/**
 * 
 * @author durantho
 * This class represents a player.
 * A player can ask to the system to move one of his pawn, or cast a spell, or end his turn.
 * each player interact with the application on different computer.
 */

public class PlayerTrucDeMort{
	
	/**
	 * represents if a player is ready or not
	 * the true state stands for ready.
	 */
	//private boolean isReady;
	
	/**
	 * pawn currently moving or casting spell
	 */	
	private Pawn currentPawn;
	
	/**
	 * arrayList of SpellPages that the player will have.
	 * embodies every pages made by the player
	 */
	private ArrayList<SpellPage> playerPages;
	
	/**
	 * The game in which the player is
	 */
	
	private Game game;
	

	
	/**
	 * Describe the turn of the player, if the turn is in progress (true) the player can ask for moves and spells
	 */
	private boolean turnInProgress;
	
	
	/**
	 * Constructor of Player Class it set isReady to false by default,
	 * the current pawn to null,
	 * and create a new ArrayList of SpellPage.
	 */
	public Player(Game pGame)
	{
		/**
		 * By default, a player is linked to his Game object, and is not ready, has no current pawn and his list of pages is empty.
		 */
		this.game = pGame;
		//this.isReady = false;
		this.currentPawn = null;
		this.playerPages = new ArrayList<SpellPage>();
	}
	
	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * @param pDest The destination where the player want to move his current pawn.
	 */
	public void askMove(Coordinate pDest)
	{
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		Movement mov = new Movement(this.currentPawn.getPos(), pDest);
		 // Try the move chosen by the player
		try {
			this.game.checkMove(mov);
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Ask to the system to cast a spell to a wanted position.
	 * @param pDest Destination where the player like to cast a spell.
	 * @param pSpell The spell that the player want to cast.
	 * @throws SpellNotFoundException in case of the pawn doesn't own the asked spell
	 */
	public void askSpell(Coordinate pDest, Spell pSpell)
	{
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		Movement mov = new Movement(this.currentPawn.getPos(), pDest);
		 // Try to launch the spell to 
		try {
			this.game.checkSpell(pSpell, mov);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Set the current pawn to a specific pawn.
	 * @param pPawn The pawn that will be the current pawn of the player.
	 */
	public void setPawn(Pawn pPawn)
	{
		this.currentPawn = pPawn;
	}
	
	/**
	 * Say to the system that a player finish his turn.
	 */
	public void endTurn()
	{
		this.turnInProgress = false;
	}
	
	/**
	 * 
	 * @return if the player has decided to finish his turn or not
	 */
	public boolean getTurn()
	{
		return this.turnInProgress;
	}
	
/*	
	Work in progress
	 * Tell the system that the player have finished to create his spell page
	 * This method loops while conditions of a valid spell page is missing
	 * Might be useless, depending on how the loop works
	 
	//TODO
	public void validateSpellPage()
	{
		
	}
*/
	
	/**
	 * Say to the system that this player is ready to play
	 * @return true the player is ready to play
	 */
//	public void playerReady()
//	{
//		this.isReady = true;
//	}
	
	public Pawn getPlayerCurrentPawn()
	{
		return this.currentPawn;
	}
	/**
	 * save a page in playerPages
	 * @param page : the page to save.
	 */
	public void addSpellPage(SpellPage page)
	{
		this.playerPages.add(page);
	}

	/**
	 * Send the arrayList to edit it during the save
	 */
	public ArrayList<SpellPage> getPlayerPage()
	{
		return this.playerPages;
	}
	
}
