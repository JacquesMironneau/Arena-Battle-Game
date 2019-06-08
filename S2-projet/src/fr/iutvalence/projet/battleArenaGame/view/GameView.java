package fr.iutvalence.projet.battleArenaGame.view;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

/**
 * Class to ask for choice and display for the HMI
 * ask stands for everything we ask the User to do
 * and display is what he currently see on the HMI.
 */

public interface GameView {

	/*
	 * Ask part
	 */
	
	
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
	 * Ask the player to choose a option in the menu
	 */
	//TODO delete this method, because the choice in the main menu will be done before the creation of the game
	//we don't know yet how to do this
	public void askChoiceMenu(int currentPlayerIndex);

	
	/**
	 * Ask the user to pick a name for his page
s	 */
	public String askPageName();
	
	/**
	 * Ask the user to pick between every spell element for his spell
	 * @return
	 */
	public Effect askSpellElement();
	
	/**
	 * Displays the board 
	 * @param myBoard
	 */
	
	public void askMove(int currentPlayerIndex);
	
	
	//DISPLAY
	
	/**
	 * Display to the player what he have to do in order to select a spell
	 */
	public void displaySpellSelection();
	
	
	/**
	 * Display the board to user
	 * @param myBoard
	 * @param nbPlayer
	 */
	public void displayBoard(Board myBoard, int nbPlayer);

	/**
	 * Display the launch menu with the different choices(Create Spells/Host/Join/localGame)
	 */
	public void displayMenu();
	
	/**
	 * Display a status messages based on StatusMessages enumeration 
	 */
	public void displayStatus(StatusMessages msg);
	
	/**
	 * Display the owned spell pages
	 */
	public void displaySpellPage(ArrayList<SpellPage> listPages);
	
	/**
	 * Display the actions that the player can move
	 */
	public void displayChoiceAction();
	
	/**
	 * Display a ending message for the game
	 * @param Pstat status of the Game (victory,defeat,draw)
	 * @param teamId display the team linked to the status
	 */
	public void displayEnd(String winTeam);
	
	/**
	 * Displays every possible choices for element
	 */
	public void displayElementChoice();
	
	/**
	 * Display every possible choices for a spell shape
	 */
	public void displayShapeChoice();
	
	
	/**
	 * Display that the turn has changed
	 */
	public void displayNextTurn(int numPlayer);
	
	/**
	 * Ensure the player that his pawn has been moved
	 */
	public void displayMoveDone();

	/**
	 * Display every attributes of every spell in a page
	 * @param pPage the described page
	 */
	public void displaySpellPageDetail(SpellPage pPage);
	
	
	/**
	 * say Size is to small for the amount of pawns
	 */
	public void diplaySizeError();
	
	/**
	 * show for which pawn you have to select a page
	 * @param Pawn
	 */
	public void displaySelectForThisPawn(Pawn thePawn);
	
	public void displayMoveSelection();
}
