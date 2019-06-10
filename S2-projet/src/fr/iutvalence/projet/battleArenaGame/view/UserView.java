package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.spell.Effect;

public interface UserView {

	/**
	 * Display the launch menu with the different choices(Create Spells/Host/Join/localGame)
	 */
	public void display(DisplayMessage msg);

	/**
	 * Ask the player to choose a option in the menu
	 */
	public void askChoiceMenu();

	/**
	 * Ask the user to pick between every spell element for his spell
	 */
	public void askSpellElement();

	/**
	 * Displays every possible choices for element
	 */
	public void displayElementChoice();

	/**
	 * Display every possible choices for a spell shape
	 */
	public void displayShapeChoice();
	
	
	
	
}
