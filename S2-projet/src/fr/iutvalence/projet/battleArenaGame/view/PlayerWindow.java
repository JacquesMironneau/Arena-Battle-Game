package fr.iutvalence.projet.battleArenaGame.view;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public class PlayerWindow implements Player extends JFrame{

	@Override
	public void askMove(Coordinate pDest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askSpell(Coordinate pDest, Spell pSpell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPawn(Pawn pPawn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTurn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validateSpellPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerReady() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pawn getPlayerCurrentPawn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSpellPage(SpellPage page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<SpellPage> getPlayerPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
