package fr.iutvalence.projet.battleArenaGame.network;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.GameConfig;
import fr.iutvalence.projet.battleArenaGame.exceptions.NetworkUnknownTypeException;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;

/**
 * Represents the network of the game,
 * it enables the system to send and receive data to a distant computer.
 * @author mironnej,durantho
 *
 * This class will deal with every object received and call the rights methods in order to do the needed job.
 * For instance after a player do damages or effects, it send the affected pawn.
 */
public class Network {

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
	@SuppressWarnings("unchecked")
	public void Transform(Object transferedObject) throws NetworkUnknownTypeException
	{
		//TODO: remove that(debug only)
		System.out.println("[NETWORK]La classe de l'objet traité est " + transferedObject.getClass());
		
		if(transferedObject.getClass() == ArrayList.class)
		{
			ArrayList<Pawn> ModifiedList = (ArrayList<Pawn>) transferedObject;
			myGame.getBoard().setTurnOrder(ModifiedList);
		}
//		else if(transferedObject.getClass() == Boolean.class) // This is used in order to manage turn in network
//			myGame.setLocalPlayerTurn(!(Boolean)transferedObject);

		else if(transferedObject.getClass() == Integer.class)
		{
			myGame.getBoard().setCurrentPawnIndex((Integer)transferedObject);
		}
		
		else if(transferedObject.getClass() == TeamId.class)
		{
			if(this.myGame.getMyIds().size()==0)
				this.myGame.getMyIds().add((TeamId)transferedObject);
			else
				this.myGame.setWinnerID((TeamId)transferedObject);
		}
		else if(transferedObject.getClass()==Board.class)
		{
			Board b = (Board)transferedObject;
			this.myGame.setBoard(new Board(this.myGame.getLocalPlayer(),this.myGame.getMaxPlayer(),b.getNbPawn(),b.getBoardSize()));
		}
		else if(transferedObject.getClass() == GameConfig.class)
		{
			GameConfig conf = (GameConfig) transferedObject;
			this.myGame.setNbPlayer(conf.getNbPlayers());
//			this.myGame.getBoard().setBoardSize(conf.getBoardSize());
//			this.myGame.getBoard().setNbPawns(conf.getNbPawns());
			
		}
//		else if(transferedObject.getClass() == String.class)
//		{
//			myGame.setGameLive((String)transferedObject);
//		}
		else throw new NetworkUnknownTypeException(transferedObject); //If the type of the sended object is not a boolean or an arrayList
	}
}
