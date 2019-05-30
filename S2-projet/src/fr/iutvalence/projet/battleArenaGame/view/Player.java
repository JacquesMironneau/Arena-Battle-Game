package fr.iutvalence.projet.battleArenaGame.view;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public interface Player {

	
	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * @param pDest The destination where the player want to move his current pawn.
	 */
	public void askMove(Coordinate pDest);
	
	/**
	 * Ask to the system to cast a spell to a wanted position.
	 * @param pDest Destination where the player like to cast a spell.
	 * @param pSpell The spell that the player want to cast.
	 * @throws SpellNotFoundException in case of the pawn doesn't own the asked spell
	 */
	public void askSpell(Coordinate pDest, Spell pSpell);

	/**
	 * ask to the system make an action
	 */
	public void askAction();
	
	/**
	 * display the board
	 */
	public void display();
	
	/**
	 * display the menu 
	 */
	public void displayMenu();
	
	/**
	 * ask to the system to make an new spell page
	 */
	public void createSpellPage();
	
	public void displayError();
	
	public void displaySpellPage();
	
	public Choices askChoicesMenu();
	
	public void dispalyChoiceMenu();
	
	public void selectPageForPawns();

	public Choices askActionChoices();
	
	public void displayEnd();
}
