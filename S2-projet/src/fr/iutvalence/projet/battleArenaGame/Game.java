package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

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
	 * These values are the default position of pawns at the start of the game
	 * The first number is the player's number and the second is the pawn's number
	 */
	public final static Coordinate BASE_POS_1PAWN1 = new Coordinate(2,0);
	public final static Coordinate BASE_POS_1PAWN2 = new Coordinate(7,1);
	public final static Coordinate BASE_POS_1PAWN3 = new Coordinate(12,0);
	public final static Coordinate BASE_POS_2PAWN1 = new Coordinate(2,14);
	public final static Coordinate BASE_POS_2PAWN2 = new Coordinate(7,13);
	public final static Coordinate BASE_POS_2PAWN3 = new Coordinate(12,14);
	/**
	 * Number of all Pawn in the game
	 */
	public final static int PAWN_NUMBER = 6;
	/**
	 * First player of the game, the one who start in the first turn
	 */
	private Player player1;
	
	/**
	 * Second player of the game
	 */
	private Player player2;
	
	/**
	 * This list represent Pawns currently living and define the turn order
	 */
	private ArrayList<Pawn> turnOrder;
	
	/**
	 * Represent the GUI of the game
	 */
	private GraphicUserInterface myGui;

	/**
	 * Represents the network of the game, it enables the players's computers to communicates game Data.
	 */
	private Network myNetwork;
	
	/*
	 * Represent the currentPawn of the game = the one that can be moved or can use spell.
	 */
	private Pawn currentPawn;
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game()
	{
		this.turnOrder = new ArrayList<Pawn>();
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
	public void play()
	{	
	
		//Select an action in the menu
		createSpellPage();
		
		
	}
	
	/**
	 * Init method initialize the board witch the beginning places for every pawn,
	 * 
	 */
	private void init()
	{

	}
	
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public boolean checkMove(Movement pMovement)
	{
		//If the pawn has enough move points to move
		if(this.currentPawn.getMovePoints() > pMovement.calculateDistance())
		{
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate() == this.turnOrder.get(indexArrayList).getPos())
					return false;
			}
			
			//Move the current pawn to coordinates
			this.currentPawn.setPos(pMovement.getDestCordinate());

			//Replace the pawn of the turnOrder (so the current one) by the currentPawn with moved coordinates)
			this.turnOrder.set(this.turnOrder.indexOf(this.currentPawn), this.currentPawn);
			
			//The movement is done
			return true;
		}
		//The movement isn't correct
		return false; 
	}
	
	/**
	 *  checkSpell method: Check if the chosen spell is valid
	 * A spell is valid if the player have enough action Point, if his spell have a cooldown equals to 0 and if the selected destination outside the board
	
	 * @return  true if the chosen spell by the player is valid and authorized
	 */
	public boolean checkSpell(Spell pSpell, Movement pMovement)
	{
		//TODO: Get the origin and destination coordinate of the pMovement
		
		return true; // To remove errors due to type returned
	}
	
	
	/**
	 * TODO: check sequences diagram
	 */
	private void endGame()
	{
		
	}
	
	/**
	 * This method close the game when everything is finished
	 */
	private void closeGame()
	{
		
	}
	
	/**
	 * Remove the deads pawn(hp <= 0) from the arrayList every time a new turn begin
	 */
	private void removeDeads()
	{
		for(int pawnIndex = 0; pawnIndex >this.turnOrder.size();pawnIndex++)
		{
			if(this.turnOrder.get(pawnIndex).getHealthPoints()==0)
			{
				this.turnOrder.remove(pawnIndex);
			}
		}
	}
	
	
	

	/**
	 * Change the currentPawn to the next one in the turnOrder array List
	 * If the currentPawn is the last one, change to the first one
	 * 
	 */
	private void nextPawn()
	{
		int nextPawnIndex = this.turnOrder.indexOf(currentPawn)+1;
		if(nextPawnIndex>PAWN_NUMBER-1)
		{
			this.currentPawn = this.turnOrder.get(0);
		}
		else
		{
			this.currentPawn = this.turnOrder.get(nextPawnIndex);
		}
	}
	
	
	/** MIGHT BE DELETED OR REPLACED BY THE DISTANCE IN MOVEMENT
	 * The "calcul savant"
	 * Check if the chosen destination (for spell or movement) is in range
	 * 
	 
	private  boolean cellInRange(Coordinate pCoordinate, Coordinate dCoordinate)
	{
		
	}
	*/
	
	
	/**
	 *  Check if a pawn exist in the list on the coordinates passed in parameters
	 *  If it exists return the pawn
	 *  else returns null
	 */
	private Pawn getPawnOnCell(Coordinate pCoordinate)
	{
		return null;
	}
	
	
	/**
	 * Set new coordinates for a pawn with a selected movement
	 * TODO
	 */
	public void updateBoard(Movement pMove)
	{
		
	}
	/**
	 * Create a spell page, including the creation of his 3 spells and add
	 * it to the player spellPage list
	 * WORK IN PROGRESS
	 */
	public void createSpellPage()
	{
		//Creation of a spellPage
		//TODO Display spell creation menu
		Scanner scan = new Scanner(System.in);
		System.out.println("Entrer le nom de la page de sort");
		String pageName = scan.nextLine();
		player1.addSpellPage(new SpellPage(pageName));
		boolean pageFinished = false;
		while(pageFinished == false)
			{
				
				Spell createdSpell = new Spell();
				
			}
		
	}

	/**
	 * Getter for turnOrder
	 * @return the turnOrder
	 */
	public ArrayList<Pawn> getTurnOrder()
	{
		return turnOrder;
	}
	
	/**
	 * Setter for turnOrder
	 * @param pTurnOrder : the turnOrder to set
	 */
	public void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		this.turnOrder = pTurnOrder;
	}
	
	
	
}
