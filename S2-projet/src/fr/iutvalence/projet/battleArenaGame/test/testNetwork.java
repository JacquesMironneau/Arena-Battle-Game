package fr.iutvalence.projet.battleArenaGame.test;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.NetworkUnknownTypeException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public class testNetwork
{
	public static void main(String[] args)
	{
		
		System.out.println("------------------test network #1----------");
		ArrayList<Pawn> testArrayListOfPawns = new ArrayList<Pawn>();
		testArrayListOfPawns.add(new Pawn(PawnTeam.PawnJ1, new Coordinate(0,0), new SpellPage("Page de feu")));
		testArrayListOfPawns.add(new Pawn(PawnTeam.PawnJ2, new Coordinate(1,1), new SpellPage("Page de glace")));
		testArrayListOfPawns.add(new Pawn(PawnTeam.PawnJ1, new Coordinate(1,0), new SpellPage("Page de tenèbres")));
		testArrayListOfPawns.add(new Pawn(PawnTeam.PawnJ2, new Coordinate(2,1), new SpellPage("Page de magie noire")));
		testArrayListOfPawns.add(new Pawn(PawnTeam.PawnJ1, new Coordinate(2,0), new SpellPage("Page de mathématiques (c'est comme la page d'au dessus)")));
		testArrayListOfPawns.add(new Pawn(PawnTeam.PawnJ2, new Coordinate(3,1), new SpellPage("Page de pierre(le type hein)")));

		Game elle = new Game();
		//on affiche le turn order de la game qu'on manipule
		//Puis on le modifie
		System.out.println("La liste est " + elle.getTurnOrder());
		
		try
		{
			new Network(elle).Transform(testArrayListOfPawns);
		} catch (NetworkUnknownTypeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elle.setTurnOrder(testArrayListOfPawns);
		System.out.println();
		System.out.println(elle.getTurnOrder());
		

	}
}
