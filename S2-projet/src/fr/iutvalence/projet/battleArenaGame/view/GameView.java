package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

/**
 * Handle the interaction with the user when the game is running
 */

public interface GameView {
	
	//Ask 
	
	/**
	 * Ask the player to pick between every possible actions choice
	 * @param currentPlayerIndex 
	 */
	public void askActionChoice(int currentPlayerIndex);
	
	/**
	 * ask the player to pick a spell
	 * @param currentPlayerIndex the index of the user chosen by the controller
	 */
	public void askSpell(int currentPlayerIndex);
	
	/**
	 * Ask the player to pick a page for his pawn
	 * @param currentPlayerIndex the index of the user chosen by the controller
	 */
	public void askPageSelection(int currentPlayerIndex);
	
	/**
	 * Displays the board 
	 * @param myBoard
	 */
	
	public void askMove(int currentPlayerIndex);
	
	
	//Display
	
	/**
	 * Display to the player what he have to do in order to select a spell
	 */
	public void displaySpellSelection(int currentPlayerIndex);
		
	/**
	 * Display the board to user
	 * @param myBoard
	 * @param nbPlayer
	 */
	public void displayBoard(int currentPlayerIndex,Board myBoard, int nbPlayer);

	/**
	 * Display a status messages based on StatusMessages enumeration 
	 */
	public void displayStatus(int currentPlayerIndex, StatusMessages msg);
	
	/**
	 * Display the owned spell pages
	 */
	public void displaySpellPage(int currentPlayerIndex);
	
	/**
	 * Display the actions that the player can move
	 */
	public void displayChoiceAction(int currentPlayerIndex);
	
	/**
	 * Display a ending message for the game
	 * @param Pstat status of the Game (victory,defeat,draw)
	 * @param teamId display the team linked to the status
	 */
	public void displayEnd(int currentPlayerIndex, String winTeam);
	
	/**
	 * Display that the turn has changed
	 */
	public void displayNextTurn(int currentPlayerIndex, int numPlayer);
	
	/**
	 * Ensure the player that his pawn has been moved
	 */
	public void displayMoveDone(int currentPlayerIndex);

	/**
	 * Display every attributes of every spell in a page
	 * @param pPage the described page
	 */
	public void displaySpellPageDetail(int currentPlayerIndex, SpellPage pPage);
	
	/**
	 * say Size is to small for the amount of pawns
	 */
	public void diplaySizeError(int currentPlayerIndex);
	
	/**
	 * Show for which pawn you have to select a page
	 * @param Pawn
	 */
	public void displaySelectForThisPawn(int currentPlayerIndex, String pawnName);
	
	/**
	 * Show to the player what he need to do to move
	 */
	public void displayMoveSelection(int currentPlayerIndex);
	
	
	//Setter
	public void setGameController(int currentPlayerIndex, GameController GC);
}
