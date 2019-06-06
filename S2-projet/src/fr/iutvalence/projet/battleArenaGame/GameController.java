package fr.iutvalence.projet.battleArenaGame;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
/**
 * Contains all methods that the player can call to affect the game
 */
public interface GameController
{
	/**
	 * Send a request for a move
	 * @param currentPlayerIndex is the index in the players list of the player which is currently playing
	 * @param destination is the destination that the player wants to reach
	 */
	public void moveRequest(int currentPlayerIndex,Coordinate destination);
	
	/**
	 * Send a request to cast a spell
	 * @param currentPlayerIndex is the index in the players list of the player which is currently playing
	 * @param spellIndex is the index of the spell in the spell page of the current pawn that the player wants to cast
	 * @param destination is the target of the spell that the player wants to hit
	 */
	public void spellRequest(int currentPlayerIndex,int spellIndex,Coordinate destination);
	
	/**
	 * Send a request to set a spell page to the current paw
	 * @param currentPlayerIndex is the index in the players list of the player which is currently playing
	 * @param pageToSet the spell page to set to the pawnDO
	 */
	//TODO replace (int)pageToSet by a SpellPage. The problem is that we need to access to the player's page in the GameView actually.
	//The solution may be to send spellPages with the configurable part of the application which will be worked later.
	public void setPageRequest(int currentPlayerIndex,int pageToSet);

}
