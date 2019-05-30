package fr.iutvalence.projet.battleArenaGame.view;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public interface Player {

	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * @param pDest The destination where the player want to move his current pawn.
	 */
	public void askMove(Coordinate pDest);
	
	/**
	 * Ask to the system to cast a spell to a wanted position.
	 * @param pDest Destination where the player like to cast a spell.
	 * @param pSpell The spell that the player want to cast.
	 * @throws SpellNotFoundException in case of the pawn doesn't own the asked spell
	 */
	public void askSpell(Coordinate pDest, Spell pSpell);
	
	
	/**
	 * Set the current pawn to a specific pawn.
	 * @param pPawn The pawn that will be the current pawn of the player.
	 */
	public void setPawn(Pawn pPawn);

	/**
	 * Say to the system that a player finish his turn.
	 */
	public void endTurn();
	
	/**
	 * 
	 * @return if the player has decided to finish his turn or not
	 */
	public boolean getTurn();

	/**
	 * Work in progress
	 * Tell the system that the player have finished to create his spell page
	 * This method loops while conditions of a valid spell page is missing
	 * Might be useless, depending on how the loop works
	 */

	 
	//TODO
	public void validateSpellPage();
	
	
	/**
	 * Say to the system that this player is ready to play
	 * @return true the player is ready to play
	 */
	public void playerReady();
	
	public Pawn getPlayerCurrentPawn();
	/**
	 * save a page in playerPages
	 * @param page : the page to save.
	 */
	public void addSpellPage(SpellPage page);

	/**
	 * Send the arrayList to edit it during the save
	 */
	public ArrayList<SpellPage> getPlayerPage();

	
}
