package fr.iutvalence.projet.battleArenaGame;

import java.net.Socket;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.MenuChoices;

/**
 * Handle the application algorithm before a game starts
 */
public interface UserController {

	/**
	 * Request to launch a local game with a configuration
	 * @param nbPlayer the number of player in the game
	 * @param nbPawn the number of pawns for each player
	 * @param BoardSize the size of the board
	 * @param nbPlayerCons the number of players who plays on a console
	 */
	public void localConfigRequest(int nbPlayer,int nbPawn,int BoardSize,int nbPlayerCons);
	
	/**
	 * Request to launch a game on a server with a configuration
	 * @param nbPlayer the number of player in the game
	 * @param nbPawn the number of pawns for each player
	 * @param BoardSize the size of the board
	 */
	public void serverConfigRequest(int nbPlayer,int nbPawn, int BoardSize);

	/**
	 * Request for an action among available menu choices
	 * @param choice the action to do
	 */
	public void choiceMenuRequest(MenuChoices choice);
	
	/**
	 * Request to create a spell page
	 * @param name the name for the page
	 * @param spell1 the first spell of the page
	 * @param spell2 the second spell of the page
	 * @param spell3 the third spell of the page
	 */
	public void createSpellPageRequest(String name,Spell spell1,Spell spell2, Spell spell3);
	
	/**
	 * Create all default shapes and add them to the list of available shapes
	 */
	public void createDefaultShapes();
	
	/**
	 * Get the list of available shapes
	 * @return the list of available shapes
	 */
	public ArrayList<Shape> getGameShapes();
	
	/**
	 * Get the list of user's spellPages
	 * @return the list of available spellPages
	 */
	public ArrayList<SpellPage> getSpellPages();
	
	
	public void clientConfigConnection(String ip);
	
	public void clientReceiveConfigInformation(String msg);
	
	
}

