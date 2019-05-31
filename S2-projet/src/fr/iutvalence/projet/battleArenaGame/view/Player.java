package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public interface Player {

	
	/**
	 * Ask to the system to move a pawn to a wanted destination.
	 * @return 
	 * 
	 */
	public Movement askMove();
	
	/**
	 * Ask to the system to cast a spell to a wanted position.
	 * @return 
	 * @throws SpellNotFoundException in case of the pawn doesn't own the asked spell
	 */
	public int askSpell();

	/**
	 * ask to the system make an action
	 */
	
	public Choices askActionChoice();
	
	public Choices askChoiceMenu();

	/**
	 * display the board
	 */
	public void displayBoard(Board myBoard);
	
	/**
	 * display the menu 
	 */
	public void displayMenu();
	
	public void displayError(ErrorMessages error);
	
	public void displaySpellPage();
	
	public void displayChoiceAction();
	
	public SpellPage askSpellPageSelection();
	
	public void displayEnd(EndStatus Pstat, TeamId teamId);
	
	public String askPageName();
	
	public int askSpellIndex();
	
	public SpellEffect askSpellElement();
	
	public Shape askSpellShape(SpellEffect eff);
	
	public boolean askValidation();
	
	public void displayElementChoice();
	
	public void displayShapeChoice();
	
	public void displaySpellLaunched();
	
	public void displayNextTurn();
	
	public void displayMoveDone();

	public void displaySpellPageDetail(SpellPage pPage); //affiche le detail d'une page (index : element, forme ...)
	
}
