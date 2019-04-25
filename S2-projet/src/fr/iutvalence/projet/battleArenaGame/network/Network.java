package fr.iutvalence.projet.battleArenaGame.network;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

/**
 * Represents the network of the game,
 * it enables the system to send and receive data to a distant computer.
 * @author mironnej,durantho
 *
 * This class will deal with every object received and call the rights methods in order to do the needed job.
 * For instance after a player do damages or effects, it send the affected pawn.
 */
public class Network {

	//myGame represent the Game of the network
	private Game myGame;
	
	public Network(Game pGame)
	{
		this.myGame = pGame;
	}
	/**
	 * The Transform method, catch an arrayList sent by one of the emitter, caught in the receive method (server or client)
	 * and replace the current turnOrder array List of the Game class by the sent one
	 * 
	 * @param o: the received object 
	 */
	public void Transform(Object transferedObject)
	{
		//TODO: remove that(debug only)
		System.out.println("La classe de l'objet traité est " + transferedObject.getClass());
		
		if(transferedObject.getClass() == ArrayList.class)
		{
			System.out.println(this.myGame.getTurnOrder().size());
			//Unsafe but works actually
			@SuppressWarnings("unchecked")
			ArrayList<Pawn> ModifiedArrayListOfPawns = (ArrayList<Pawn>) transferedObject;

			//For each pawn of the arrayList, it does print it (DEBUG ONLY: TODO: REMOVE THESES 3LINES)
			for(Pawn PawnIndexInTheArrayList : ModifiedArrayListOfPawns)
				System.out.println(PawnIndexInTheArrayList);
			
			
			//Edit the array list of the current game
			this.myGame.setTurnOrder(ModifiedArrayListOfPawns);
			System.out.println("After" + this.myGame.getTurnOrder().size());

		}
	}
	
	/*
	 * Test main 
	 * TODO: remove
	 */
	/*public static void main(String[] args)
	{
		
		System.out.println("hllo");
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
		
		new Network(elle).Transform(testArrayListOfPawns);
		elle.setTurnOrder(testArrayListOfPawns);
		System.out.println();
		System.out.println(elle.getTurnOrder());
		

	}*/
}
