package fr.iutvalence.projet.battleArenaGame.pawn;

import java.io.Serializable;

/**
 * 
 * @author pashmi
 * Represents the different team for a pawn
 * He can be a pawn of the first player team or of the second one.
 * It is used to see if every pawns of a same player are dead or not
 * 
 * The PAWN_LOCAL represents the team of the player who plays on the server (P1)
 * The PAWN_REMOTE represents the team of the player who plays on the client (P2)
 */
public enum PawnTeam implements Serializable
{
	PAWN_LOCAL,
	PAWN_REMOTE
}
