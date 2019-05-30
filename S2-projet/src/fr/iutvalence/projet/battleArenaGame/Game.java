package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.NotEnoughPointsException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellOnCooldownException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellOutOfRangeException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Client;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.network.Server;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.Choices;
import fr.iutvalence.projet.battleArenaGame.view.Player;

/**
 * Game class stands for the system of the BattleArena Game:
 * It handles the players, the network, the spellpages of the player, the coordinate
 * and manage turns for every player. 
 * Here the game starts and end
 * 
 * @author mironnej
 *
 */

public class Game
{
	/**
	 * Size of the board (length of the square)
	 */
	public final static int BOARD_SIZE = 15;
	
	/**
	 * Used in the network : Numbers of player (excluding the host)
	 */
    public final static int MAXPLAYERS = 1;
    
    /**
     * Used in the network : Message send by a Client to left the application
     */
    public final static String QUIT = "quit";
	
    
    /**
     * Used in the network: Port 
     */
    
    public final static int PORT = 12899;
    
    /**
     * Used in the network: IP address of the server (might be deleted when UDP auto IP will be implemented
     */
    
    public final static String HOST_ADDRESS = "172.26.105.6";
    
    /** 
     * Winning message that will be send to the winner
     */
    public final static String VICTORY = "Victory";
    
    /**
     * Defeat message that will be send to the looser
     */
    
    public final static String DEFEAT = "Defeat";
    
    /**
     * Draw message that will be send to the other player
     */
    public final static String DRAW = "Draw";
    
    /**
     * Message saying that client is ready to start
     */
    public final static String CLIENT_READY = "ClientIsReady";
    
    /**
     * Message saying that server is ready to start
     */
    public final static String SERVER_READY = "ServerIsReady";
    
	
	/**
	 * First player of the game, the one who start in the first turn
	 */
	private Player localPlayer;

	
	/**
	 * Represent the GUI of the game
	 *TODO might be removed
	 */
	private GraphicUserInterface myGui;

	/**
	 * Represents the network of the game, it enables the players's computers to communicates game Data.
	 */
	private Network myNetwork;
	
	
	/**
	 * If the user chose to join a game, he will be a Client in the network system.
	 */
	private Client myClient;
	
	/**
	 * If the user chose to join a game, he will be represented as the Server in the network system.
	 */
	private Server myServer;
	
	/*
	 * Represents if the user of the system embodies the server or is just a client
	 * Used 
	 */
	private boolean isServer;

	/**
	 * Represents if a user the local player is playing now.
	 * It is used to manage when a player can play.
	 * If it's true the local player can play, else it can't. 
	 */
	private boolean localPlayerTurn;

	/**
	 * True if player decide to stop his turn, false either
	 */
	private boolean endTurn;
	
	/**
	 * Message send by the client, used to synchronize
	 */
	private String clientMessage;
	
	/**
	 * Message send by the server, used to synchronize 
	 */
	private String serverMessage;
	
	/**
	 * Board of the game
	 */
	private Board board;
	
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game(Player p)
	{
		this.localPlayer = p;
		//this.turnOrder = new ArrayList<Pawn>();
		this.myNetwork = new Network();
		//Server and client are created in play method if the player chose to create a game (create Server) or to join (create Client)
	}
	
	
	/**
	 * The first method call, set up the network
	 * and allow the player to navigate in the menu
	 */
	@SuppressWarnings("resource")
	public void launch()
	{			
		while(true) 
		{		
		/*
		 * For now, this represent the menu of the game
		 */
			localPlayer.displayMenu();
		switch(localPlayer.askChoiceMenu())
		{
		
		case Choices.CREATE_SPELL_PAGE:
			try {
				//createSpellPage();
				localPlayer.askSpellPageCreation();
			} catch (SpellIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case Choices.HOST_GAME: // server
			//TODO review network
			this.isServer = true;
			this.localPlayerTurn = true;
			this.endTurn = false;
			
			myServer = new Server(Game.PORT, myNetwork);
			myServer.init(); // Launch the server
			this.board = new Board();
			//TODO insert champSelect
			this.play();
			break;
		
		case Choices.JOIN_GAME: // client TODO test send from client to server
			this.isServer = false;
			this.localPlayerTurn = false;
			this.endTurn = false;

			
			myClient = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
			myClient.init(); // Connect the client to the server
			
			//TODO get board from server
			//TODO Insert champ select
			this.play();
			break;
			
		default:
			localPlayer.displayError();
		}
		
	}
	}
	

	/**
	 * Manage method for turns
	 * First the play method check if game is ended: No more pawns in one team player.
	 * It first select the currentPawn
	 * Here we allow player to choose actions for the currentPawn.
	 * There's a loop where the current player can ask for movement, spell casting.
	 * Then the system check if theses move or cast are authorized then it does the job.
	 * When the player ask to end his turn, the system switch to next player with a new currentPawn.
	 * 
	 */
	
	//TODO Manage the two players, the two different ArrayList (Does the pawn of the LocalPlayer are the odd ones ?
	// Manage turn, one player should not play while it's the other turn
	//Might need to send if the player can play or not
	
	public void play()
	{	
		//TODO ??
		this.synchronizePlayers();
		
		Board.setCurrentPawn(Board.getTurnOrder().get(0));

		while(!endGame()) // replace by boolean / or method to know if game is finished
		{
			System.out.println("Waiting for client-kun.." + this.localPlayerTurn);
			try {
				Thread.sleep(1000);

			}catch(Exception e)
			{
				
			}
			if(this.localPlayerTurn)
			{
				//Update effects and PA &PM of pawn
				//this.localPlayer.setPawn(Board.getCurrentPawn()); TODO FIX
				Board.getCurrentPawn().setActionPoints(6);
				Board.getCurrentPawn().setMovePoints(6);
				//this.board.getTurnOrder().set(this.board.getTurnOrder().indexOf(this.board.getCurrentPawn()), this.board.getCurrentPawn());
				this.board.applyEffect();
				
				
				while(!this.endTurn)
				{
					this.localPlayer.displayChoiceAction();
					
					switch(this.localPlayer.askActionChoice())
					{
					case Choices.LAUNCH_SPELL:
						localPlayer.askSpell();
						break;
						
					case Choices.MOVE:
						this.localPlayer.askMove();
						break;
						
					case Choices.END_TURN:
						this.board.nextPawn();			
						if(Board.getCurrentPawn().getTeam() == PawnTeam.PAWN_REMOTE)
						{
							this.endTurn = true; //  set it to true somewhere
							this.localPlayerTurn = false; // this one is set to true in the network class


							if(this.isServer)
								{
									myServer.sendToOther(Board.getCurrentPawn());
									myServer.sendToOther(Board.getTurnOrder());
									myServer.sendToOther(this.localPlayerTurn);
								}
							else
								{
									myClient.sendToOther(Board.getCurrentPawn());
									myClient.sendToOther(Board.getTurnOrder());
									myClient.sendToOther(this.localPlayerTurn);
								}
						}
						/**else
						{
							Board.getCurrentPawn().setActionPoints(6);
							this.currentPawn.setMovePoints(6);
							this.turnOrder.set(this.turnOrder.indexOf(currentPawn), currentPawn);
							applyEffect();


						}*/
						break;
					default:
						localPlayer.displayError();
					}
				}
			}
			this.endTurn = false; //CARE TODO
		}
	}
	
	/**
	 * Called in play() before the game loop
	 * It wait until both side have selected their pages for their pawn in order to start the game loop
	 * Make the page selection for the client
	 */
	private void synchronizePlayers()
	{
		System.out.println("Syncrhonize");
		if(!this.isServer)
		{
			while(!SERVER_READY.equals(serverMessage))
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.selectPageForPawns(); //TODO Do this method in game (call method of player to display or ask)
			myClient.sendToOther(CLIENT_READY);
		}
		else
		{
			while(!CLIENT_READY.equals(clientMessage))
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * Getter for LocalPlayerTurn
	 * @return
	 */
	public boolean getLocalPlayerTurn() {
		return localPlayerTurn;
	}

	/**
	 * Setter for localPlayerTurn
	 * @param localPlayerTurn
	 */
	public void setLocalPlayerTurn(boolean localPlayerTurn) {
		this.localPlayerTurn = localPlayerTurn;
	}

	/**
	 * TODO Add theses changes in the Network class
	 * Might Disconnect the user (myClient.disconnect() and the Server ( myServer.disconnectAll())
	 */
	private boolean endGame()
	{
		int alivePawnsPlayerServer = 0, alivePawnsPlayerClient = 0;
		
		if(Board.getTurnOrder().size() <= 3)
		{
			for(Pawn pawnIndex : Board.getTurnOrder())
			{
				if(pawnIndex.getTeam() == PawnTeam.PAWN_LOCAL)
					alivePawnsPlayerServer++;
				
				if(pawnIndex.getTeam() == PawnTeam.PAWN_REMOTE)
					alivePawnsPlayerClient++;
			}
			
			if(alivePawnsPlayerServer == 0 && alivePawnsPlayerClient == 0)
			{
				System.out.println(Game.DRAW);
				
				if(this.isServer)
					myServer.sendToOther(Game.DRAW);
				else
					myClient.sendToOther(Game.DRAW);
			}

			else if(alivePawnsPlayerServer == 0)
			{
				
				/*
				 * Here the server loose
				 */
				
				//If this Game is the one of the server, it send Victory to the client, and display a loosing screen
				//to the current user
				if(this.isServer)
				{
					myServer.sendToOther(Game.VICTORY);
					System.out.println("You loose :c ");
				}
				
				//If this Game is the one of the client, it send Defeat to the client, and display a winning screen
				//to the current user
				else
				{
						myClient.sendToOther(Game.DEFEAT);
						System.out.println("You win !");
				}
			}
			
			/*
			 * Here the client loose
			 */
			else if(alivePawnsPlayerClient == 0)
			{
				/*
				 * If this Game is the one of the server, it sends defeat to the client
				 * and display win to the server
				 */
				if(this.isServer)
				{
					myServer.sendToOther(Game.DEFEAT);
					System.out.println("You win ");
				}
				/*
				 * If this Game is the one of the client, it sends win to the client
				 * and display loose to the server
				*/
				else
				{
					myClient.sendToOther(Game.VICTORY);
					System.out.println("You loose :c");
				}
			}
			return true;
		}
		//DEBUG TODO remove
		else
			return false;
	}
	
	
	/**
	 * This method close the game
	 * Called when a the game is finished, after results
	 */
	private void closeGame()
	{
		System.exit(1); // This stop in a clean way the application
	}
	
	
//	/**TODO REMOVE 
//	 * Display all pages of the player with an index
//	 */
//	private void displaySpellPages()
//	{
//		for(int pageIndex=0;pageIndex < this.localPlayer.getPlayerPage().size();pageIndex++)
//		{
//			System.out.println(pageIndex + ":" + this.localPlayer.getPlayerPage().get(pageIndex));
//		}
//	}

	
	/**
	 * Setter for ServerMessage
	 * @param pMessage : message received
	 */
	public void setServerMessage(String pMessage)
	{
		this.serverMessage = pMessage;
	}
	
	/**
	 * Getter for serverMessage
	 * @return message corresponding to the server status
	 */
	public String getServerMessage()
	{
		return this.serverMessage;
	}
	
	/**
	 * Getter for clientMessage
	 * @param pMessage : message received
	 */
	public void setClientMessage(String pMessage)
	{
		this.clientMessage = pMessage;
	}
	
	/**
	 * Getter for clientMessage
	 * @return message corresponding to client status
	 */
	public String getClientMessage()
	{
		return this.clientMessage;
	}

/**
 * Used for test
 * Create a spell page with 3 spells
 * @throws SpellIndexException 
 *
 */
	//TODO Remove this method, used for test
public void createSpellPageForTest() throws SpellIndexException
{
	SpellPage p1 = new SpellPage("page1");
	Spell s1 = new Spell();
	Spell s2 = new Spell();
	Spell s3 = new Spell();
	s1.setShape(Shape.Ball);
	s2.setShape(Shape.Fist);
	s3.setShape(Shape.Sword);
	s1.setSpellEffect(SpellEffect.Fire);
	s2.setSpellEffect(SpellEffect.Ice);
	s3.setSpellEffect(SpellEffect.Electricity);
	
	p1.setSpell(0,s1);
	p1.setSpell(1,s2);
	p1.setSpell(2,s3);
	
	//TODO DO A ADD SPELL PAGE IN GAME CLASS
	//this.localPlayer.addSpellPage(p1);
}

}
