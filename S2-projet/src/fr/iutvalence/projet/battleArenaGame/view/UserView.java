package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;

/**
 * Handle the interaction with the user before the game start
*/
public interface UserView {

	//Display
	
	/**
	 * Display the launch menu with the different choices(Create Spells/Host/Join/localGame)
	 */
	public void display(DisplayMessage msg);
	
	/**
	 * Displays every possible choices for element
	 */
	public void displayElementChoice();

	/**
	 * Display every possible choices for a spell shape
	 */
	public void displayShapeChoice();
	
	/**
	 * Display the list of available servers
	 */
	public void displayListServer();
	
	/**
	 * Say to the user that the server has been successfully start and give information about ip and port
	 * @param ip the ip of the server
	 * @param port the port of the game
	 */
	public void displayServerLaunched(int ip, int port);

	//Ask
	
	/**
	 * Ask to the user to choose an option in the menu
	 */
	public void askChoiceMenu();

	/**
	 * Ask to the user to enter information to create a spell page
	 */
	public void askPageCreation();
	
	/**
	 * Ask to the user to enter information to create a spell
	 * @return
	 */
	public Spell askSpellCreation();

	/**
	 * Ask to the user to select a server to join
	 */
	public void askServerConnection();
	
	/**
	 * Ask to the user to enter configuration to start a game on a server
	 */
	public void askServerConfig();
	
	/**
	 * Ask to the user to enter configuration to start a local game
	 */
	public void askLocalConfig();
	
	
	/**
	 * Set a controller to the view to interact with the userController
	 * @param pController
	 */
	public void setController(UserController pController);
	
	
}
