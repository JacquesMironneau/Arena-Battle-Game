package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;

/**
 * Game class stands for the system of the BattleArena Game:
 * It handles the players IHM, the network, the board and manage turns
 */
public class Game implements GameController
{
	//Attributes 
   /**
    * Number of players in the game
    */
    private int maxPlayer;
    
	/**
	 * List of all player's IHM in the game
	 */
	private ArrayList<GameView> players;
	
	/**
	 * The board of the game
	 */
	private Board board;
	
	/**
	 * List of all spellPages in the game
	 */
	//TODO Will be changed to allow players to join a game with their own spell pages list and only use those
	private ArrayList<SpellPage> mySpellPages;
	
	/**
	 * Status of the Game
	 * Is Running if the game is not ended, Draw if there is no winner and Victory if a player win
	 */
	private EndStatus gameStatus;
	
	//Constructor
	/**
	 * Create a Game parameterized by the number of player, the number of pawn for each player
	 * and the board size wanted by the player who create the game.
	 * @param nbPlayer is the number of player wanted for this game
	 * @param nbPawn is the number of pawn for each player wanted for this game
	 * @param boardSize is the size of the board
	 */
	public Game(int nbPlayer,int nbPawn,int boardSize)
	{
		this.mySpellPages = new ArrayList<SpellPage>(); //TODO remove (do not create it every time we launch (will evolve to file read /DB) in V8
		this.maxPlayer = nbPlayer;
		this.players = new ArrayList<GameView>();
		for(int i=0;i<this.maxPlayer;i++)
			this.players.add(new PlayerConsole(this));
		this.board = new Board(nbPlayer,nbPawn,boardSize);
		try {
			this.createSpellPageForTest();
		} catch (SpellIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gameStatus = EndStatus.RUNNING;		
	}
	
	//Methods
	
	/**
	 * This is the algorithm of a game
	 * First it starts the selection of spell pages for pawns phase.
	 * Then it enters in the turn management loop which ask to the player to choose between 3 actions :
	 * -Move : Ask a move to the player for his pawn
	 * -Launch : Ask to the player the spell he wants to cast and where
	 * -EndTurn : End his turn
	 * The player can do an unlimited number of action in his turn while he has enough points to perform them
	 * It exist the turn loop if the game if finished 
	 */
	
	public void play()
	{	
		//Selection of pages for all pawns
		int currentPlayerIndex = 0;
		while(true)
		{	
			for(GameView gv : players)
					if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()==players.indexOf(gv))
						currentPlayerIndex = players.indexOf(gv);
			this.players.get(currentPlayerIndex).displaySpellPage(this.mySpellPages);
			this.players.get(currentPlayerIndex).displaySelectForThisPawn(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()));
			this.players.get(currentPlayerIndex).askPageSelection(currentPlayerIndex);
			this.board.nextPawn();
			//If the last pawn of the list has a page, which means that all pawns have pages
			if(!(this.board.getTurnOrder().get(this.board.getTurnOrder().size()-1).getSpellPage()==null))
				break;
		}
		
		//Turn algorithm
		currentPlayerIndex = 0;
		while(true) 
		{
			this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).resetPoints();
			this.board.applyEffect();
			if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
			{
				this.board.removeDeads();
				this.board.nextPawn();
				break;
			}
			for(GameView gv : players)
			{
				gv.displayBoard(board,this.maxPlayer);
				if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()==players.indexOf(gv))
					currentPlayerIndex = players.indexOf(gv);
			}
			//	if(endGame()!=EndStatus.RUNNING)
			//	break;
			while(true)
			{
			this.players.get(currentPlayerIndex).displayChoiceAction();
			this.players.get(currentPlayerIndex).askActionChoice(currentPlayerIndex);
			if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getTeamId()!=currentPlayerIndex)
				break;
			}
			if(endGame() != EndStatus.RUNNING)
				break;
			for(GameView gv : players)
				gv.displayNextTurn(currentPlayerIndex+1);
		}
		if(gameStatus == EndStatus.DRAW)
			for(GameView gv : players)
				gv.displayEnd("Tout le monde est mort ! Egalité !");
		else
			for(GameView gv : players)
				gv.displayEnd("Victoire du joueur " + this.board.getTurnOrder().get(0).getTeamId() + "! GG !");
	}
	
	
	
	/**
	 * Check if the game is ended
	 * @return an EndStatus corresponding to the state of the game
	 */
	public EndStatus endGame()
	{
		if(this.board.getTurnOrder().size()==0)
			return EndStatus.DRAW;
		
		if(this.board.getTurnOrder().size()<= this.board.getNbPawn())
		{
			for(Pawn p : this.board.getTurnOrder())
				if(p.getTeamId()!=this.board.getTurnOrder().get(0).getTeamId())
					return EndStatus.RUNNING;			
			return EndStatus.VICTORY;
		}
		return EndStatus.RUNNING;
	}
	
	public  void setNbPlayer(int nbPlayers)
	{
		this.maxPlayer = nbPlayers;
	}

	@Override
	public void moveRequest(int currentPlayerIndex,Coordinate destination)
	{
		switch(this.board.checkMove(destination))
		{
		case MOVE_OUT_OF_RANGE:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.MOVE_OUT_OF_RANGE);
			break;
			
		case MOVE_OUT_OF_BOARD:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.MOVE_OUT_OF_BOARD);
			break;
			
		case CASE_OCCUPATED:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.CASE_OCCUPATED);
			break;
			
		case MOVE_DONE:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.MOVE_DONE);
			break;
		}
	}

	@Override
	public void spellRequest(int currentPlayerIndex,int spellIndex,Coordinate destination)
	{
		switch(this.board.checkSpell(spellIndex, destination))
		{
		case WRONG_INDEX:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.WRONG_INDEX);
			break;
			
		case SPELL_IN_COOLDOWN:  
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.SPELL_IN_COOLDOWN);
			break;
			
		case NOT_ENOUGH_ACTION_POINT:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.NOT_ENOUGH_ACTION_POINT);
			break;
			
		case SPELL_TARGET_OUT_OF_RANGE:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.SPELL_TARGET_OUT_OF_RANGE);
			break;
			
		case SPELL_SENT:
			this.players.get(currentPlayerIndex).displayStatus(StatusMessages.SPELL_SENT);
			break;
		}
	}
	
	@Override
	//TODO add answer messages
	public void setPageRequest(int currentPlayerIndex,int pageToSet)
	{
		for(Pawn p : this.board.getTurnOrder())
			if(p.getTeamId()==currentPlayerIndex && p.getSpellPage() == null)
				{
				p.setSpellPage(new SpellPage(this.getSpellPages().get(pageToSet)));
				this.players.get(currentPlayerIndex).displayStatus(StatusMessages.PAGE_SET);
				break;
				}
	}
	
	public void actionRequest(int currentPlayerIndex,StatusMessages choice)
	{
		switch(choice)
		{
		case MOVE:
			this.players.get(currentPlayerIndex).displayBoard(board,this.maxPlayer);
			this.players.get(currentPlayerIndex).displayMoveSelection();
			this.players.get(currentPlayerIndex).askMove(currentPlayerIndex);
			for(GameView gv : players)
				gv.displayBoard(board,this.maxPlayer);
			break;
		case LAUNCH_SPELL:
			this.players.get(currentPlayerIndex).displayBoard(board,this.maxPlayer);
			this.players.get(currentPlayerIndex).displaySpellPageDetail(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getSpellPage());
			this.players.get(currentPlayerIndex).displaySpellSelection();
			this.players.get(currentPlayerIndex).askSpell(currentPlayerIndex);
			if(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getHealthPoints()<=0)
				this.board.nextPawn();
			this.board.removeDeads();
			for(GameView gv : players)
				gv.displayBoard(board,this.maxPlayer);
			break;
		case END_TURN:
			this.board.nextPawn();
			break;
			
		}
		
	}
	
	/**
	 * Create a filled SpellPage
	 * @throws SpellIndexException
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

	//Getters
	public ArrayList<SpellPage> getSpellPages()
	{
		return this.mySpellPages;
	}
	
	public Board getBoard()
	{
		return this.board;
	}
	
	public int getMaxPlayer() {
		return maxPlayer;
	}
}