package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
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
	 * ask to the system to make a new spell page
	 * @throws SpellIndexException 
	 */
	public void askSpellPageCreation() throws SpellIndexException;
	
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
	
	public void displayChoiceAction();
	
	public void selectPageForPawns();
	
	public void displayEnd(EndStatus Pstat);
	
	public String askPageName();
	
	public int askSpellIndex();
	
	public String askSpellElement();
	
	public Shape askSpellShape();
	
	public boolean askValidation();
	
	public void displayElementChoice();
	
	public void displayShapeChoice();
	
	public void displaySpellInCooldown(Spell pSpell);
	
	public void displaySpellOutOfRange();
	
	public void displayNotEnoughActionPoints();
	
	public void displaySpellLaunched();
	
	public void displayNextTurn();
	

}
