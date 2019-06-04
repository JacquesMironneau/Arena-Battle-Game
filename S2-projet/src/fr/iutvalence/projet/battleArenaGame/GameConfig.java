package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;

/**
 * Contains all needs to configure a game (to send config to clients)
 * @author charvevi
 *
 */
public class GameConfig {
	
	private int nbPlayers;
	private int boardSize;
	private int nbPawns;
	private ArrayList<Pawn> turnOrder;
	private int currentPawnIndex;
	
	public int getNbPlayers()
	{
		return nbPlayers;
	}
	public int getBoardSize()
	{
		return boardSize;
	}
	public int getNbPawns()
	{
		return nbPawns;
	}
	public ArrayList<Pawn> getTurnOrder()
	{
		return turnOrder;
	}
	
	public int getCurrentPawnIndex()
	{
		return this.currentPawnIndex;
	}
	
	public GameConfig(int pNbPlayer,int pNbPawns,int pBoardSize,ArrayList<Pawn> pTurnOrder,int pCurrentPawnIndex)
	{
		this.nbPlayers = pNbPlayer;
		this.nbPawns = pNbPawns;
		this.boardSize = pBoardSize;
		this.turnOrder = pTurnOrder;
		this.currentPawnIndex = pCurrentPawnIndex;
	}

}
