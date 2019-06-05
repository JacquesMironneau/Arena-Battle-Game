package fr.iutvalence.projet.battleArenaGame.view;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
/**
 * Class for asks and display for the HMI
 * ask stands for everything we ask the User to do
 * and display is what he currently see on the HMI.
 * @author pashmi
 *
 */
public interface GameView {

	//ASK
	/**
	 * Ask the player to pick an index 
	 * @return
	 */
	public int askIndexSelection();
	
	
	/**
	 * Ask the player to pick between every possible actions choice
	 * @return
	 */
	public Choices askActionChoice();
	
	/**
	 * Ask the player to choose a option in the menu
	 * @return
	 */
	public Choices askChoiceMenu();

	
	/**
	 * Ask the user to pick a name for his page
	 * @return the picked name
	 */
	public String askPageName();
	
	/**
	 * Ask the user to pick between every spell element for his spell
	 * @return
	 */
	public SpellEffect askSpellElement();
	
	/**
	 * Displays the board 
	 * @param myBoard
	 */
	
	public Coordinate askMove();
	
	//DISPLAY
	public void displayBoard(Board myBoard, int nbPlayer);

	/**
	 * Display the launch menu with the different choices(Create Spells/Host/Join/localGame)
	 */
	public void displayMenu();
	
	/**
	 * Display an error message based on the ErrorMessages enumeration 
	 */
	public void displayError(ErrorMessages error);
	
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
	 * Ensure the player that his spell has been cast
	 */
	public void displaySpellLaunched();
	
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
