package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.network.Client;
import fr.iutvalence.projet.battleArenaGame.network.Communication;
import fr.iutvalence.projet.battleArenaGame.network.Local;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.network.Server;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
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
    
    public final static String HOST_ADDRESS = "192.168.0.16";
    
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
	

	private Communication communication;
	/**
	 * Represents if a user the local player is playing now.
	 * It is used to manage when a player can play.
	 * If it's true the local player can play, else it can't. 
	 */
	private static boolean localPlayerTurn;

	/**
	 * True if player decide to stop his turn, false either
	 */
	private boolean endTurn;
	
	/**
	 * Message send by the client, used to synchronize
	 */
	private static String clientMessage;
	
	/**
	 * Message send by the server, used to synchronize 
	 */
	private static String serverMessage;
	
	/**
	 * Board of the game
	 */
	private Board board;
	
	/**
	 * List of all spellPages of the player
	 */
	private static ArrayList<SpellPage> mySpellPages;
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game(Player p)
	{
		Game.mySpellPages = new ArrayList<SpellPage>();
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
		switch(localPlayer.askChoiceMenu())
		{		
		case CREATE_SPELL_PAGE:
				//createSpellPage();
			try {
				this.createSpellPageForTest();
			} catch (SpellIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case HOST_GAME: // server
			//TODO review network
			
			this.communication = new Server(Game.PORT, myNetwork);
			this.communication.init();
			Game.localPlayerTurn = true;
			this.endTurn = false;
			
			
			this.board = new Board(this.communication,this.localPlayer);
			//TODO insert champSelect
			this.play();
			break;
		
		case JOIN_GAME: // client TODO test send from client to server
			Game.localPlayerTurn = false;
			this.endTurn = false;

			this.communication = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
			//myClient = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
			//myClient.init(); // Connect the client to the server
			this.communication.init();
			this.board = new Board(this.communication,this.localPlayer);
			System.out.println("AIHJGHSDJIFGHSDFIL");
			this.play();
			break;
		case LOCAL_GAME:
			this.communication = new Local();
			Game.localPlayerTurn=true;
			this.board = new Board(this.communication,this.localPlayer);
			this.pawnSelection();
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
		
		this.pawnSelection();
		
		Board.setCurrentPawn(Board.getTurnOrder().get(0));

		while(!endGame()) // replace by boolean / or method to know if game is finished
		{
			this.localPlayer.displayBoard(board);
			System.out.println("Waiting for client-kun.." + Game.localPlayerTurn);
			try {
				Thread.sleep(1000);

			}catch(Exception e)
			{
				
			}
			if(Game.localPlayerTurn)
			{
				this.localPlayer.displayBoard(board);
				//Update effects and PA &PM of pawn
				Board.getCurrentPawn().setActionPoints(Pawn.DEFAULT_ACTION_POINTS);
				Board.getCurrentPawn().setMovePoints(Pawn.DEFAULT_MOVE_POINTS);
				this.board.applyEffect();
				if(Board.getCurrentPawn().getHealthPoints()<=0)
				{
					Board.removeDeads();
					endGame();
					endTurn();
				}
				while(!this.endTurn)
				{
					switch(this.localPlayer.askActionChoice())
					{
					case MOVE:
						this.board.checkMove(this.localPlayer.askMove());
						this.localPlayer.displayBoard(this.board);	
						break;
						
					case LAUNCH_SPELL:
						this.board.checkSpell(localPlayer.askSpell(),localPlayer.askMove());
						Board.removeDeads();
						this.localPlayer.displayBoard(board);
						if(Board.getCurrentPawn().getHealthPoints()<=0)
						{
							endTurn();
						}
						endGame();
						break;
					case END_TURN:

						endTurn();
						break;
					default:
						localPlayer.displayError();
					}
				}
			}
			this.endTurn = false; 
		}
	}
	
	/**
	 * Called in play() before the game loop
	 * It wait until both side have selected their pages for their pawn in order to start the game loop
	 * Make the page selection for the client
	 */
	private void pawnSelection()
	{
		System.out.println("Syncrhonize");

		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(Game.localPlayerTurn)
			{
				this.pageSelection();
				Game.localPlayerTurn = false;
				this.communication.sendToOther(Board.getTurnOrder());
				this.communication.sendToOther(Game.localPlayerTurn);
				break;
			}
		}
		
	}
	
	/**
	 * Getter for LocalPlayerTurn
	 * @return
	 */
	public static boolean getLocalPlayerTurn() {
		return localPlayerTurn;
	}

	/**
	 * Setter for localPlayerTurn
	 * @param localPlayerTurn
	 */
	public static void setLocalPlayerTurn(boolean pLocalPlayerTurn) {
		Game.localPlayerTurn = pLocalPlayerTurn;
	}

	/**
	 * TODO Add theses changes in the Network class
	 * Might Disconnect the user (myClient.disconnect() and the Server ( myServer.disconnectAll())e
	 */
	private boolean endGame()
	{
		if(Board.getWinTeam()==EndStatus.RUNNING)
			return false;
		else
		{	
			this.localPlayer.displayEnd(Board.getWinTeam(),Board.getTurnOrder().get(0).getTeamId());
			endTurn();
			return true;
		}
	}
	
	
	/**
	 * This method close the game
	 * Called when a the game is finished, after results
	 */
	private void closeGame()
	{
		System.exit(1); // This stop in a clean way the application
	}
	
	
	private void endTurn()
	{
		for(Pawn p :Board.getTurnOrder())
		{
			System.out.println(p.toString() + p.getTeam());
		}
		System.out.println("Le pion actuel est "+Board.getCurrentPawn().getTeam());
		Board.nextPawn();
		System.out.println("Le prochain pion est "+Board.getCurrentPawn().getTeam());
		if(Board.getCurrentPawn().getTeam()==PawnTeam.PAWN_LOCAL)
		{
			this.endTurn = true;
		}
		else if(Board.getCurrentPawn().getTeam()==PawnTeam.PAWN_REMOTE)
		{
			this.endTurn = true;
			Game.localPlayerTurn = false;
			this.communication.sendToOther(Board.getTurnOrder());
			this.communication.sendToOther(Board.getCurrentPawn());
			this.communication.sendToOther(Game.localPlayerTurn);
			
		}
		else
			System.out.println("Exception ni remote ni local");
	}
	
	/**
	 * Setter for ServerMessage
	 * @param pMessage : message received
	 */
	public static void setServerMessage(String pMessage)
	{
		serverMessage = pMessage;
	}
	
	/**
	 * Getter for serverMessage
	 * @return message corresponding to the server status
	 */
	public String getServerMessage()
	{
		return serverMessage;
	}
	
	/**
	 * Getter for clientMessage
	 * @param pMessage : message received
	 */
	public static void setClientMessage(String pMessage)
	{
		clientMessage = pMessage;
	}
	
	/**
	 * Getter for clientMessage
	 * @return message corresponding to client status
	 */
	public String getClientMessage()
	{
		return clientMessage;
	}
	
	public static ArrayList<SpellPage> getSpellPages()
	{
		return Game.mySpellPages;
	}
	
	/**
	 * Creation of a spellPage using player HMI selection
	 */
	public void createSpellPage()
	{
		SpellPage pageToAdd = new SpellPage(this.localPlayer.askPageName());
		try {
			pageToAdd.setSpell(0,null);
			pageToAdd.setSpell(1,null);
			pageToAdd.setSpell(2,null);
		} catch (SpellIndexException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean pageFinished = false;
		while(!pageFinished)
		{
		Spell spellToAdd = new Spell();
		this.localPlayer.displaySpellPageDetail(pageToAdd);
		int indexToAdd = this.localPlayer.askSpellIndex() -1;
		spellToAdd.setSpellEffect(this.localPlayer.askSpellElement());
		spellToAdd.setShape(this.localPlayer.askSpellShape(spellToAdd.getSpellEffect()));
		try {
			pageToAdd.setSpell(indexToAdd,spellToAdd);
		} catch (SpellIndexException e) {
			System.out.println("C'est mort");
			e.printStackTrace();
		}
		if(pageToAdd.getSpell(0)!= null && pageToAdd.getSpell(1)!= null && pageToAdd.getSpell(2)!= null)
			pageFinished = this.localPlayer.askValidation();
		
		}
		Game.mySpellPages.add(pageToAdd);
	}
	
	public void pageSelection()
	{
		
		for(Pawn p : Board.getTurnOrder())
		{
			if(p.getTeam() ==PawnTeam.PAWN_LOCAL)
				{	
				p.setSpellPage(new SpellPage(this.localPlayer.askSpellPageSelection()));
				}
			}
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
	s3.setShape(Shape.Square);
	s1.setSpellEffect(SpellEffect.Fire);
	s2.setSpellEffect(SpellEffect.Ice);
	s3.setSpellEffect(SpellEffect.Fire);
	
	p1.setSpell(0,s1);
	p1.setSpell(1,s2);
	p1.setSpell(2,s3);
	
	Game.mySpellPages.add(p1);
}

}
