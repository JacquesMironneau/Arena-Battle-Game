package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

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
	public Game(ArrayList<GameView> listPlayers,int nbPlayer,int nbPawn,int boardSize)
	{
		this.maxPlayer = nbPlayer;
		this.players = new ArrayList<GameView>();
		this.players = listPlayers;
		for(GameView gv : this.players)
			gv.setGameController(this);
		this.board = new Board(nbPlayer,nbPawn,boardSize);
		//this.createSpellPageForTest();
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
			this.players.get(currentPlayerIndex).displaySelectForThisPawn(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getName());
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
		this.players.get(currentPlayerIndex).displayStatus(this.board.checkMove(destination));
	}

	@Override
	public void spellRequest(int currentPlayerIndex,int spellIndex,Coordinate destination)
	{
		this.players.get(currentPlayerIndex).displayStatus(this.board.checkSpell(spellIndex, destination));
	}
	
	@Override
	public void setPageRequest(int currentPlayerIndex,SpellPage pageToSet)
	{
		for(Pawn p : this.board.getTurnOrder())
			if(p.getTeamId()==currentPlayerIndex && p.getSpellPage() == null)
			{
				p.setSpellPage(new SpellPage(pageToSet));
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
			this.players.get(currentPlayerIndex).displaySpellSelection(this.board.getTurnOrder().get(this.board.getCurrentPawnIndex()).getSpellPage());
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
	
	//Getters
	public Board getBoard()
	{
		return this.board;
	}
	
	public int getMaxPlayer() {
		return maxPlayer;
	}
}