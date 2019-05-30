package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;

public interface Player {

	
	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * 
	 */
	public void askMove();
	
	/**
	 * Ask to the system to cast a spell to a wanted position.
	 * @throws SpellNotFoundException in case of the pawn doesn't own the asked spell
	 */
	public void askSpell();

	/**
	 * ask to the system make an action
	 */
	
	public Choices askActionChoice();
	
	public Choices askChoiceMenu();
	
	/**
	 * display the board
	 */
	public void display();
	
	/**
	 * display the menu 
	 */
	public void displayMenu();
	
	public void displayError();
	
	public void displaySpellPage();
	
	public void displayChoiceMenu();
	
	public void displayChoiceAction();
	
	public void selectPageForPawns();
	
	public void displayEnd();
	
	/**
	 * ask to the system to make an new spell page
	 */
	public void askSpellPageCreation();
}
