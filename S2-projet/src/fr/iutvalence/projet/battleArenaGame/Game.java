package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.network.Client;
import fr.iutvalence.projet.battleArenaGame.network.Communication;
import fr.iutvalence.projet.battleArenaGame.network.Local;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.network.Server;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
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
	 * Used in the network : Numbers of player (excluding the host)
	 */
    public int maxPlayer;
    
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

	
	/**
	 * Board of the game
	 */
	private Board board;
	
	/**
	 * List of all spellPages of the player
	 */
	private static ArrayList<SpellPage> mySpellPages;
		
	
	/**
	 * Id of the player who win the game
	 */
	private TeamId winnerID;
	
	/**
	 * 
	 */
	private Set<TeamId> myIds;
	
	
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game(Player p)
	{
		Game.mySpellPages = new ArrayList<SpellPage>(); //TODO remove (do not create it every time we launch (will evolve to file read /DB) in V8
		this.localPlayer = p;
		this.myNetwork = new Network(this);
		this.winnerID = null;
		this.myIds = new HashSet<TeamId>();
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
				
				this.communication = new Server(Game.PORT, myNetwork, this.maxPlayer-1);
				this.communication.init();
				this.maxPlayer = this.localPlayer.askHowManyPlayers();
				this.myIds.add(new TeamId(1));
				this.board = new Board(this.communication,this.localPlayer,maxPlayer);
				this.communication.sendToOther(new GameConfig(maxPlayer,this.board.getNbPawn(),this.board.getBoardSize()));
				pawnSelection();
				this.play();
				break;
			
			case JOIN_GAME: //Client
				this.maxPlayer=-1;

				this.communication = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
				this.communication.init();
				//Receive board from host
				while(this.maxPlayer==-1) {}
				
				this.board = new Board(this.communication,this.localPlayer, this.maxPlayer);
				
				
				pawnSelection();
				this.play();
				break;
				
			case LOCAL_GAME:
				
				this.communication = new Local(this);
				this.maxPlayer = this.localPlayer.askHowManyPlayers();
				for(int index = 1; index<=this.maxPlayer;index++)
					this.myIds.add(new TeamId(index));				
				this.board = new Board(this.communication,this.localPlayer,maxPlayer);
				pawnSelection();
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
	
	private void play()
	{	

		while(this.winnerID == null) 
		{
			this.localPlayer.displayBoard(board,this.maxPlayer);
			System.out.println("Waiting for others...");
			try {
				Thread.sleep(1000);

			}catch(Exception e)
			{
				
			}
			this.communication.sendToOther(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId());
			this.communication.broadcast(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId().getId(),this.board.getTurnOrder());
			if(this.myIds.contains(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()));
			{
				boolean myTurn = true;
				while(myTurn)
				{
					this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).resetPoints();
					this.board.applyEffect();
					if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
					{
						this.board.removeDeads();
						myTurn = false;
						break;
					}
					if(endGame())
						{
						myTurn = false;
						break;
						}
					switch(this.localPlayer.askActionChoice())
					{
					case MOVE:
						this.localPlayer.displayBoard(board,this.maxPlayer);
						this.board.checkMove(this.localPlayer.askMove());
						this.localPlayer.displayBoard(board,this.maxPlayer);
						this.communication.sendToOther(this.board.getTurnOrder());
						break;
					case LAUNCH_SPELL:
						this.localPlayer.displayBoard(board,this.maxPlayer);
						this.localPlayer.displaySpellPageDetail(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getSpellPage());
						this.board.checkSpell(this.localPlayer.askSpell(), this.localPlayer.askMove());
						if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
						{
							myTurn =false;
						}
						this.board.removeDeads();
						if(this.endGame())
							{
								myTurn = false;
							}
						this.localPlayer.displayBoard(board,this.maxPlayer);
						break;
					case END_TURN:
						myTurn = false;
						break;
					default:
						localPlayer.displayError(ErrorMessages.SYSTEM_ERROR);
					}
				}
				this.communication.sendToOther(this.board.getTurnOrder());
				this.board.nextPawn();
				this.communication.sendToOther(this.board.getCurrentPawnIndex());
			}
		}
		this.localPlayer.displayEnd(this.winnerID);
		//closeGame();
	}
	
	/**
	 * Called in play() before the game loop
	 * It wait until both side have selected their pages for their pawn in order to start the game loop
	 * Make the page selection for the client
	 */
	private void pawnSelection()
	{
		System.out.println("Syncrhonize");

		while(!this.board.areAllPageSet())
		{
			
				if(this.myIds.contains(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()))
				{
					this.localPlayer.displaySpellPage();
					this.localPlayer.displaySelectForThisPawn(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getName());
					this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).setSpellPage(Game.mySpellPages.get(this.localPlayer.askSpellPageSelection()));
					this.board.nextPawn();
					this.communication.sendToOther(this.board.getTurnOrder());
					this.communication.sendToOther(this.board.getCurrentPawnIndex());
				}
			
		}
		
	}
	
	/**
	 * TODO Add theses changes in the Network class
	 * Might Disconnect the user (myClient.disconnect() and the Server ( myServer.disconnectAll())e
	 */
	public boolean endGame()
	{
		if(this.board.getTurnOrder().size()<= this.board.getNbPawn())
		{
			for(Pawn p : this.board.getTurnOrder())
				if(p.getTeamId().getId()!=this.board.getTurnOrder().get(0).getTeamId().getId())
					return false;		
			this.winnerID = this.board.getTurnOrder().get(0).getTeamId();
			return true;
			
		}
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
		int indexToAdd = this.localPlayer.askSpell();
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
	
	
	public Set<TeamId> getMyIds() 
	{
		return this.myIds;
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

public void setBoard(Board b) {
	this.board=b;	

}


public TeamId getWinnerID() {
	return winnerID;
}


public void setWinnerID(TeamId winnerID) 
{
	this.winnerID = winnerID;
}


public void setNbPlayer(int nbPlayers)
{
	this.maxPlayer = nbPlayers;
}



}
