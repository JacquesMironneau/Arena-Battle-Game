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

}
