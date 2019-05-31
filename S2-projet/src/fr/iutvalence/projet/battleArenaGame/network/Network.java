package fr.iutvalence.projet.battleArenaGame.network;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.NetworkUnknownTypeException;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;

/**
 * Represents the network of the game,
 * it enables the system to send and receive data to a distant computer.
 * @author mironnej,durantho
 *
 * This class will deal with every object received and call the rights methods in order to do the needed job.
 * For instance after a player do damages or effects, it send the affected pawn.
 */
public class Network {

	
	public Network()
	{
	}
	/**
	 * The Transform method, catch an arrayList sent by one of the emitter, caught in the receive method (server or client)
	 * and replace the current turnOrder array List of the Game class by the sent one
	 * 
	 * @param o: the received object 
	 */
	@SuppressWarnings("unchecked")
	public void Transform(Object transferedObject) throws NetworkUnknownTypeException
	{
		//TODO: remove that(debug only)
		System.out.println("[NETWORK]La classe de l'objet traité est " + transferedObject.getClass());
		
		if(transferedObject.getClass() == ArrayList.class)
		{
		
			
					
					
					//Unsafe but works actually
					@SuppressWarnings("unchecked")
					ArrayList<Pawn> ModifiedArrayListOfPawns = (ArrayList<Pawn>) transferedObject;
					System.out.println("[NETWORK]Modification de turnOrder en cours");
					for(Pawn p: ModifiedArrayListOfPawns)
					{
						if(p.getTeam() == PawnTeam.PAWN_LOCAL)
							{
								//System.out.println(p.getTeam());
								p.setTeam(PawnTeam.PAWN_REMOTE);
								//System.out.println("[NETWORK]Nouveau: " + p.getTeam());
							}
						else
							{	
								//System.out.println(p.getTeam());
								p.setTeam(PawnTeam.PAWN_LOCAL);
								//System.out.println("[NETWORK]Nouveau: " + p.getTeam());
							}
					}
					//For each pawn of the arrayList, it does print it (DEBUG ONLY: TODO: REMOVE THESES 3LINES)
					
				//	for(Pawn PawnIndexInTheArrayList : ModifiedArrayListOfPawns)
						//System.out.println(PawnIndexInTheArrayList);
					
					
					//Edit the array list of the current game
					Board.setTurnOrder(ModifiedArrayListOfPawns);
					
					/*System.out.println("After" + this.myGame.getTurnOrder().size());*/
				
			
		

		}
		else if(transferedObject.getClass() == Boolean.class) // This is used in order to manage turn in network
			Game.setLocalPlayerTurn(!(Boolean)transferedObject);
		
		else if(transferedObject.getClass() == String.class) 
			{
			/**
			 * If the client says he is ready, we tell the the server he is
			 */
				if(transferedObject.equals(Game.CLIENT_READY))
				{
					Game.setClientMessage(Game.CLIENT_READY);
					System.out.println("[NETWORK] Client is ready... ");
				}
			/**
			 * If the server says he is ready, we tell the client he is
			 */
				else if(transferedObject.equals(Game.SERVER_READY))
				{
					System.out.println("[NETWORK] Server is ready... ");
					Game.setServerMessage(Game.SERVER_READY);
				}
				/**
				 * Victory/deafeat/draw case
				 * For now we only display who win
				 */
				else {
					System.out.println("[NETWORK] end game message: " + transferedObject); //TODO REWORK THIS
				}
				
			}
		
		else if(transferedObject.getClass() == Pawn.class)
		{
			Board.setCurrentPawn((Pawn) transferedObject);
			System.out.println("[NETWORK] a pawn is sended");
		}
		
//		else if(transferedObject.getClass()==Board.class)
//		{
//			Board.setTurnOrder(Board.getTurnOrder());
//		}
		else throw new NetworkUnknownTypeException(transferedObject); //If the type of the sended object is not a boolean or an arrayList
	}
}
