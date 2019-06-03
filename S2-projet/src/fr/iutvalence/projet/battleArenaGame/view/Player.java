package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
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
public interface Player {

	

	
	/**
	 * ASKS PARTS
	 */
	
	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * @return the movement chosen 
	 * 
	 */
	public Coordinate askMove();
	/**
	 * Ask to the player which spell he want's to cast
	 * @return the index of the spell chosen
	 */
	public int askSpell();
	
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
	 * Ask the player to choose between his pages
	 * @return the chosen page
	 */
	public SpellPage askSpellPageSelection();
	
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
	 * Ask the user to TODO
	 * @param eff
	 * @return
	 */
	public Shape askSpellShape(SpellEffect eff);
	
	/**
	 * @return if player validate his choices
	 */
	public boolean askValidation();

	
	/**
	 * Displays
	 */
	/**
	 * Displays the board 
	 * @param myBoard
	 */
	public void displayBoard(Board myBoard);

	/**
	 * Display the launch menu with the differents choices(Create Spells/Host/Join/localGame)
	 */
	public void displayMenu();
	
	/**
	 * Display an error message based on the ErrorMessages enumeration 
	 */
	public void displayError(ErrorMessages error);
	
	/**
	 * Display the owned spellpages
	 */
	public void displaySpellPage();
	
	/**
	 * Display the actions that the player can move
	 */
	public void displayChoiceAction();
	
	/**
	 * Display a ending message for the game
	 * @param Pstat status of the Game (victory,defeat,draw)
	 * @param teamId display the team linked to the status
	 */
	public void displayEnd(EndStatus Pstat, TeamId teamId);
	
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
	public void displayNextTurn();
	
	/**
	 * Ensure the player that his pawn has been moved
	 */
	public void displayMoveDone();

	/**
	 * Display every attributes of every spell in a page
	 * @param pPage the described page
	 */
	public void displaySpellPageDetail(SpellPage pPage);
	
	public int askNbPlayer();
	
	public int askNbPawn();
	
}
