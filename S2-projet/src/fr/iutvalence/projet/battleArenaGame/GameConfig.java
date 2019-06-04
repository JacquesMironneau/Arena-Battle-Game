package fr.iutvalence.projet.battleArenaGame;

import java.io.Serializable;

/**
 * Contains all needs to configure a game (to send config to clients)
 * @author charvevi
 *
 */
public class GameConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int nbPlayers;
	private int boardSize;
	private int nbPawns;

	
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

	
	public GameConfig(int pNbPlayer,int pNbPawns,int pBoardSize)
	{
		this.nbPlayers = pNbPlayer;
		this.nbPawns = pNbPawns;
		this.boardSize = pBoardSize;

	}

}
