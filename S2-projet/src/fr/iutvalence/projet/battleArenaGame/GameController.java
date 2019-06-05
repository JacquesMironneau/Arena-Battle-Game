package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;

public interface GameController
{
	
	public void editTurnOrder(ArrayList<Pawn> newTurnOrder);
	
	public void editCurrentPawnIndex(int newCurrentPawnIndex);
	
}
