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
import fr.iutvalence.projet.battleArenaGame.view.ErrorMessages;
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
    
    public final static String HOST_ADDRESS = "192.168.0.11";
    
	
	/**
	 * First player of the game, the one who start in the first turn
	 */
	private Player localPlayer;

	/**
	 * Represents the network of the game, it enables the players's computers to communicates game Data.
	 */
	private Network myNetwork;
	
	/**
	 * Represents how the Game is sending and receiving data (With others computers or with itself)
	 */
	private Communication communication;
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
	 * Board of the game
	 */
	private Board board;
	
	/**
	 * List of all spellPages of the player
	 */
	private static ArrayList<SpellPage> mySpellPages;
	
	private String gameLive;
	
	
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game(Player p)
	{
		Game.mySpellPages = new ArrayList<SpellPage>(); //TODO remove (do not create it every time we launch (will evolve to file read /DB)
		this.localPlayer = p;
		this.myNetwork = new Network(this);
		this.gameLive = "ALIVE";
	}
	
	
	/**
	 * The first method call, set up the network
	 * and allow the player to navigate in the menu
	 */
	public void launch()
	{			
		while(true) 
		{		
			switch(localPlayer.askChoiceMenu())
			{		
			case CREATE_SPELL_PAGE:
				try {
					this.createSpellPageForTest();
				} catch (SpellIndexException e) {
					e.printStackTrace();
				}
				break;
				
			case HOST_GAME: // server
				
				this.communication = new Server(Game.PORT, myNetwork);
				this.communication.init();
				this.localPlayerTurn = true;
				this.endTurn = false;
				
				
				this.board = new Board(this.communication,this.localPlayer);
				this.play();
				break;
			
			case JOIN_GAME: //Client
				
				this.localPlayerTurn = false;
				this.endTurn = false;
	
				this.communication = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
				this.communication.init();
				this.board = new Board(this.communication,this.localPlayer);
				this.play();
				break;
				
			case LOCAL_GAME:
				
				this.communication = new Local(this);
				this.localPlayerTurn=true;
				this.board = new Board(this.communication,this.localPlayer);
				this.pawnSelection();
				this.play();
				break;
				
			default:
				localPlayer.displayError(ErrorMessages.SYSTEM_ERROR);
			}
		
		}
	}
	

	/**TODO update this doc
	 * Manage method for turns
	 * First the play method check if game is ended: No more pawns in one team player.
	 * It first select the currentPawn
	 * Here we allow player to choose actions for the currentPawn.
	 * There's a loop where the current player can ask for movement, spell casting.
	 * Then the system check if theses move or cast are authorized then it does the job.
	 * When the player ask to end his turn, the system switch to next player with a new currentPawn.
	 * 
	 */
	
	public void play()
	{	
		
		this.pawnSelection();
		

		while(!endGame() && this.gameLive.equals("ALIVE")) // replace by boolean / or method to know if game is finished
		{
			this.localPlayer.displayBoard(this.board);
			System.out.println("Waiting for client-kun.." + this.localPlayerTurn);
			try {
				Thread.sleep(1000);

			}catch(Exception e)
			{
				
			}
			if(this.localPlayerTurn)
			{
				this.localPlayer.displayBoard(board);
				//Update effects and PA &PM of pawn
				this.board.getTurnOrder().get(board.getCurrentPawnIndex()).setActionPoints(Pawn.DEFAULT_ACTION_POINTS);
				this.board.getTurnOrder().get(board.getCurrentPawnIndex()).setMovePoints(Pawn.DEFAULT_MOVE_POINTS);
				
				this.board.applyEffect();
				
				if(board.getTurnOrder().get(board.getCurrentPawnIndex()).getHealthPoints()<=0)
				{
					board.removeDeads();
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
						this.localPlayer.displaySpellPageDetail(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getSpellPage());
						this.board.checkSpell(localPlayer.askSpell(),localPlayer.askMove());
						if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
							{
							this.board.removeDeads();
							endTurn();
							}
						this.board.removeDeads();
						this.localPlayer.displayBoard(this.board);
						endGame();
						break;
					case END_TURN:

						endTurn();
						break;
					default:
						localPlayer.displayError(ErrorMessages.SYSTEM_ERROR);
					}
				}
			}
			this.endTurn = false; 
		}
		this.closeGame();
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
			
			if(this.localPlayerTurn)
			{
				this.pageSelection();
				this.localPlayerTurn = false;
				this.communication.sendToOther(this.board.getTurnOrder());
				this.communication.sendToOther(this.localPlayerTurn);
				break;
			}
		}
		
	}
	
	/**
	 * Getter for LocalPlayerTurn
	 * @return
	 */
	public  boolean getLocalPlayerTurn() {
		return this.localPlayerTurn;
	}

	/**
	 * Setter for localPlayerTurn
	 * @param localPlayerTurn
	 */
	public void setLocalPlayerTurn(boolean pLocalPlayerTurn) {
		this.localPlayerTurn = pLocalPlayerTurn;
	}

	/**
	 * TODO Add theses changes in the Network class
	 * Might Disconnect the user (myClient.disconnect() and the Server ( myServer.disconnectAll())e
	 */
	private boolean endGame()
	{
		if(this.board.getWinTeam()==EndStatus.RUNNING)
			return false;
		else
		{	
			this.localPlayer.displayEnd(this.board.getWinTeam(),this.board.getTurnOrder().get(0).getTeamId());
			this.gameLive = "DEAD";
			this.communication.sendToOther("DEAD");
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
	
	/**
	 * End the turn of the player: if the next pawn is a remote one, send to other player the turnOrder + the autorisation to play
	 */
	private void endTurn()
	{
		//TODO  remove debug
		for(Pawn p :this.board.getTurnOrder())
		{
			System.out.println(p.toString() + p.getTeam());
		}
		this.board.nextPawn();
		//If there is only 1 pawn remaining
		if(this.board.getTurnOrder().size()==1 || this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeam()==PawnTeam.PAWN_REMOTE)
		{
			this.endTurn = true;
			this.localPlayerTurn = false;
			this.communication.sendToOther(this.board.getTurnOrder());
			this.communication.sendToOther(this.board.getCurrentPawnIndex());
			this.communication.sendToOther(this.localPlayerTurn);
		}
		else if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeam()==PawnTeam.PAWN_LOCAL)
				this.endTurn = true;		
		else
			this.localPlayer.displayError(ErrorMessages.PAWN_NO_TEAM);
	}
	
	/**
	 * Getter for spellspages
	 * @return
	 */
	public static ArrayList<SpellPage> getSpellPages()
	{
		return Game.mySpellPages;
	}
	
	/**
	 * @return the board of the current Game
	 */
	public Board getBoard()
	{
		return this.board;
	}
	
	/**
	 * Creation of a spellPage using player HMI selection //TODO remove this later
	 */
	public void createSpellPage()
	{
		SpellPage pageToAdd = new SpellPage(this.localPlayer.askPageName());
		try {
			pageToAdd.setSpell(0,null);
			pageToAdd.setSpell(1,null);
			pageToAdd.setSpell(2,null);
		} catch (SpellIndexException e1) {
			e1.printStackTrace();
		}
		boolean pageFinished = false;
		while(!pageFinished)
		{
		Spell spellToAdd = new Spell();
		this.localPlayer.displaySpellPageDetail(pageToAdd);
		int indexToAdd = this.localPlayer.askSpell() -1;
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
	
	/**
	 * Select page for every pawn of the player Team
	 */
	public void pageSelection()
	{
		for(Pawn p : this.board.getTurnOrder())
			if(p.getTeam() ==PawnTeam.PAWN_LOCAL)
				p.setSpellPage(new SpellPage(this.localPlayer.askSpellPageSelection()));
	}
	
	public void setGameLive(String pString)
	{
		this.gameLive = pString;
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
