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
import fr.iutvalence.projet.battleArenaGame.view.GameView;

/**
 * Game class stands for the system of the BattleArena Game:
 * It handles the players, the network, the spellpages of the player, the coordinate
 * and manage turns for every player. 
 * Here the game starts and end
 * 
 * @author mironnej
 *
 */

public class Game implements GameController
{
	
	
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
	 * Used in the network : Numbers of player (excluding the host)
	 */
    private int maxPlayer;
    
	
	/**
	 * First player of the game, the one who start in the first turn
	 */
	private ArrayList<GameView> players;
	
	
	/**
	 * Board of the game
	 */
	private Board board;
	
	/**
	 * List of all spellPages of the player
	 */
	private ArrayList<SpellPage> mySpellPages;
		
	
	/**
	 * Id of the player who win the game
	 */
	private int winnerID;
	

	
	
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game(ArrayList<GameView> gameViews,int nbPlayer,int nbPawn,int boardSize)
	{
		this.mySpellPages = new ArrayList<SpellPage>(); //TODO remove (do not create it every time we launch (will evolve to file read /DB) in V8
		this.maxPlayer = nbPlayer;
		this.players = gameViews;
		this.board = new Board(gameViews,nbPlayer,nbPawn,boardSize);
		try {
			this.createSpellPageForTest();
		} catch (SpellIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		int currentPlayerIndex = 0;
		while(true) 
		{
			for(GameView gv : players)
				{
					gv.displayBoard(board,this.maxPlayer);
					if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()==players.indexOf(gv))
						currentPlayerIndex = players.indexOf(gv);
				}
		
					this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).resetPoints();
					this.board.applyEffect();
					if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
					{
						this.board.removeDeads();
						this.board.nextPawn();
						break;
					}
					if(endGame())
						{
						break;
						}
					this.players.get(currentPlayerIndex).displayChoiceAction();
					switch(this.players.get(currentPlayerIndex).askActionChoice())
					{
					case MOVE:
						for(GameView gv : players)
						{
							gv.displayBoard(board,this.maxPlayer);
						}
						this.players.get(currentPlayerIndex).displayMoveSelection();
						this.board.checkMove(currentPlayerIndex,this.players.get(currentPlayerIndex).askMove());
						for(GameView gv : players)
						{
							gv.displayBoard(board,this.maxPlayer);
						}
						break;
					case LAUNCH_SPELL:
						this.players.get(currentPlayerIndex).displayBoard(board,this.maxPlayer);
						this.players.get(currentPlayerIndex).displaySpellPageDetail(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getSpellPage());
						int spellIndex = this.players.get(currentPlayerIndex).askIndexSelection()-1;
						this.players.get(currentPlayerIndex).displayMoveSelection();
						this.board.checkSpell(currentPlayerIndex,spellIndex, this.players.get(currentPlayerIndex).askMove());
						if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
						{
							this.board.nextPawn();
						}
						this.board.removeDeads();
						if(endGame())
							break;
						for(GameView gv : players)
						{
							gv.displayBoard(board,this.maxPlayer);
						}
						break;
					case END_TURN:
						this.board.nextPawn();
						break;
					default:
						for(GameView gv : players)
						{
							gv.displayError(ErrorMessages.SYSTEM_ERROR);
						}
					}
					if(endGame())
					{
						break;
					}
					for(GameView gv : players)
					{
						gv.displayNextTurn(currentPlayerIndex+1);
					}
				}
		if(this.winnerID==-1)
		{
			for(GameView gv : players)
			{
				gv.displayEnd("Tout le monde est mort ! Egalité !");
			}
		}
		else
			for(GameView gv : players)
			{
				gv.displayEnd("Victoire du joueur " + this.winnerID + "! GG !");
			}
		//closeGame();
	}
	
	/**
	 * Called in play() before the game loop
	 * It wait until both side have selected their pages for their pawn in order to start the game loop
	 * Make the page selection for the client
	 */
	public void pawnSelection()
	{
		System.out.println("Syncrhonize");

		while(!this.board.areAllPageSet())
		{	
			int currentPlayerIndex = 0;
			for(GameView gv : players)
			{
				if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()==players.indexOf(gv))
					currentPlayerIndex = players.indexOf(gv);
			}	
			this.players.get(currentPlayerIndex).displaySpellPage(this.mySpellPages);
			this.players.get(currentPlayerIndex).displaySelectForThisPawn(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()));
			this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).setSpellPage(new SpellPage(this.mySpellPages.get(this.players.get(currentPlayerIndex).askIndexSelection())));
			this.board.nextPawn();
		}
	}
	
	/**
	 * TODO Add theses changes in the Network class
	 * Might Disconnect the user (myClient.disconnect() and the Server ( myServer.disconnectAll())e
	 */
	public boolean endGame()
	{
		if(this.board.getTurnOrder().size()==0)
			{
			this.winnerID = -1;
			return true;
			}
		if(this.board.getTurnOrder().size()<= this.board.getNbPawn())
		{
			for(Pawn p : this.board.getTurnOrder())
				if(p.getTeamId()!=this.board.getTurnOrder().get(0).getTeamId())
					return false;			
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
	 * Getter for spell pages
	 * @return
	 */
	public ArrayList<SpellPage> getSpellPages()
	{
		return this.mySpellPages;
	}
	
	/**
	 * @return the board of the current Game
	 */
	public Board getBoard()
	{
		return this.board;
	}
	
	/**
	 * Creation of a spellPage using player HMI selection 
	 */
	//TODO fix that and think about how and when players can create their page, think how to keep pages saved
	//and how players access to their own pages only
/*	public void createSpellPage()
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
		this.mySpellPages.add(pageToAdd);
	}
	
	
	public synchronized Set<TeamId> getMyIds() 
	{
		return this.myIds;
	}*/
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
	
	this.mySpellPages.add(p1);
}

public void setBoard(Board b) {
	this.board=b;	

}



public int getMaxPlayer() {
	return maxPlayer;
}


public  void setNbPlayer(int nbPlayers)
{
	this.maxPlayer = nbPlayers;
}


@Override
public void editTurnOrder(ArrayList<Pawn> newTurnOrder)
{
	this.board.setTurnOrder(newTurnOrder);
}


@Override
public void editCurrentPawnIndex(int newCurrentPawnIndex)
{
	this.board.setCurrentPawnIndex(newCurrentPawnIndex);
}





}
