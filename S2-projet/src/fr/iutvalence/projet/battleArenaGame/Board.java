package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Random;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;

/**
 * The board contains all of the pawns and handle modifications on the pawns
 */
public class Board
{

	//Attributes
	
	/**
	 * The list of all pawns on the board, stored in the order which they will be played
	 */
	private ArrayList<Pawn> turnOrder;
	
	/**
	 * Index of the pawn which is currently played in the turnOrder
	 */
	private int currentPawnIndex;

	/**
	 * Number of pawns on the board
	 */
	private int nbPawn;
	
	/**
	 * Size of the board, which is a square
	 */
	public int boardSize;
	
	
	//Constructor
	
	/**
	 * Create a board and initialize the turn order
	 * @param pNbPlayer is the number of player in the game
	 * @param pNbPawn is the number of pawn for each player
	 * @param pBoardSize is the size of the board
	 */
	public Board(int pNbPlayer,int pNbPawn,int pBoardSize)
	{
		this.turnOrder = new ArrayList<Pawn>();
		this.currentPawnIndex = 0;
		this.nbPawn = pNbPawn;
		this.boardSize = pBoardSize;
		this.initTurnOrder(pNbPlayer);
	}
	
	
	//Methods
	
	/**
	 * Check if a movement is valid and if so move the current pawn to the destination
	 * A move is valid if the pawn have enough move points, the destination is not an occupied coordinate and is in the board limit
	 * @param pDest is the wanted destination
	 * @return a StatusMessage corresponding to what happens
	 */
	public StatusMessages checkMove(Coordinate pDest)
	{
		Movement pMovement = new Movement(this.turnOrder.get(currentPawnIndex).getPos(),pDest);
		//If the pawn don't have enough move points to move
		if(this.turnOrder.get(currentPawnIndex).getMovePoints() < pMovement.getDistance())
			return StatusMessages.MOVE_OUT_OF_RANGE;
		
		//if the destination isn't in board limits
		if(pMovement.getDestCordinate().getCoordX()<0 || pMovement.getDestCordinate().getCoordX()>=this.boardSize || pMovement.getDestCordinate().getCoordY()<0 || pMovement.getDestCordinate().getCoordY()>=this.boardSize)
			return StatusMessages.MOVE_OUT_OF_BOARD;

		//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
		for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			if(pMovement.getDestCordinate().equals(this.turnOrder.get(indexArrayList).getPos()))
				return StatusMessages.CASE_OCCUPATED;

		//If the move is good
		//Move the current pawn to coordinates
		this.turnOrder.get(currentPawnIndex).setPos(pMovement.getDestCordinate());
		this.turnOrder.get(currentPawnIndex).setMovePoints(this.turnOrder.get(currentPawnIndex).getMovePoints()-pMovement.getDistance());				
		return StatusMessages.MOVE_DONE;
	}
	
	
	/**
	 * Apply the effects on the currentPawn 
	 */
	public void applyEffect() 
	{
		if(this.turnOrder.get(currentPawnIndex).getEffect().isEmpty()) return;
		for(PawnEffect eff : this.turnOrder.get(currentPawnIndex).getEffect())
		{
			this.turnOrder.get(currentPawnIndex).setHealthPoints(this.turnOrder.get(currentPawnIndex).getHealthPoints()+eff.getMyEffect().getHealthModifier());
			this.turnOrder.get(currentPawnIndex).setActionPoints(this.turnOrder.get(currentPawnIndex).getActionPoints()+eff.getMyEffect().getActionPointModifier());
			this.turnOrder.get(currentPawnIndex).setMovePoints(this.turnOrder.get(currentPawnIndex).getMovePoints()+eff.getMyEffect().getMovePointModifier());
		}
		this.turnOrder.get(currentPawnIndex).updateEffect();
	}
	
	/**
	 * Check if the cast of a spell is valid and if so cast it with the current pawn 
	 * @param pSpellIndex is the index in the spell page of the spell to cast
	 * @param pDest the destination of the spell
	 * @return a status message corresponding to what happens
	 */
	public StatusMessages checkSpell(int pSpellIndex, Coordinate pDest)
	{
		pSpellIndex-=1;
		//If spellIndex is wrong
		if(pSpellIndex<0 || pSpellIndex>2)
			return StatusMessages.WRONG_INDEX;
		
		//If the spell is on cooldown
		if(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getCurrentCooldown() > 0)	
			return StatusMessages.SPELL_IN_COOLDOWN;
		
		//If the pawn has not enought action points (cost too much)
		if(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getSpellCost() > this.turnOrder.get(currentPawnIndex).getActionPoints())
			return StatusMessages.NOT_ENOUGH_ACTION_POINT;
		
		//If the spell target is too far (range too short)
		Movement pMovement = new Movement(this.turnOrder.get(currentPawnIndex).getPos(),pDest);
		if(pMovement.getDistance() > this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getRange())
			return StatusMessages.SPELL_TARGET_OUT_OF_RANGE;
		
		//The spell is send
		//Remove cost in action points
		this.turnOrder.get(currentPawnIndex).setActionPoints(this.turnOrder.get(currentPawnIndex).getActionPoints() - this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getSpellCost());
		//Set the reload delay on the spell used
		this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).resetCooldown(); 
		//Check all case affected by the shape
			for(Coordinate addedCoordinate : this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getEffectedCoordinates())
			{
				Coordinate coordinateToAffect = Coordinate.addCoordinate(pMovement.getDestCordinate(),addedCoordinate);
				//If there is a pawn on affected case
				for(Pawn p : this.turnOrder)
				{
					if(p.getPos().equals(coordinateToAffect))
					{
						//Update HP
						p.setHealthPoints(p.getHealthPoints()-this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getDamage());
						//Add effect
						p.addEffect(new PawnEffect(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getSpellEffect()));
					}
				}
			}
			return StatusMessages.SPELL_SENT;
	}
	
		
	/**
	 * Set the current pawn index to the index of the next pawn in the turn order
	 */
	public void nextPawn() 
	{
		this.currentPawnIndex++;
		if(this.currentPawnIndex==this.turnOrder.size())
			this.currentPawnIndex=0;
	}
	
	
	/**
	 * Remove all dead pawns (hp <= 0) from the turn order 
	 */
	public void removeDeads()
	{
		boolean find = false;
		Pawn tempPawn = this.turnOrder.get(currentPawnIndex);
		ArrayList<Pawn> temp = new ArrayList<Pawn>();
		for(Pawn p: this.turnOrder)
			if(p.getHealthPoints() <= 0)
				temp.add(p);
		this.turnOrder.removeAll(temp);
		for(Pawn p: this.turnOrder)
			if(p.equals(tempPawn))
			{
				this.currentPawnIndex = this.turnOrder.indexOf(tempPawn);
				find = true;
			}
		if(!find)
			this.nextPawn();
	}
	
	/**
	 * Check if the position created with the 2 parameters is occupied
	 * @param X is the row of the coordinate
	 * @param Y is the column of the coordinate
	 * @return true if the position is occupied
	 */
	private boolean exist(int X,int Y)
	{
		if (this.getTurnOrder().isEmpty())
			return false;
		else for(Pawn p : this.getTurnOrder())
		{if (p.getPos().getCoordX()==X && p.getPos().getCoordY()==Y)
		return true;
		}return false;
	}
	
	/**
	 * Fill the turn order by creating pawns to add
	 * @param maxPlayer is the number in the game
	 */
	private void initTurnOrder(int maxPlayer)
	{
		int X;
		int Y;
		Random rand = new Random();
		for (int i=1;i<= this.nbPawn;i++)
			{
			for(int k=0;k<maxPlayer;k++)
			{
				do
				{
				X = rand.nextInt(this.boardSize);
				Y = rand.nextInt(this.boardSize);
				}while(this.exist(X,Y));
				this.getTurnOrder().add(new Pawn(k,new Coordinate(X,Y),"J"+k +"."+i));
			}
		}
	}
	
	//Getters
	
	public ArrayList<Pawn> getTurnOrder()
	{
		return this.turnOrder;
	}
	
	public int getCurrentPawnIndex()
	{
		return this.currentPawnIndex;
	}
	
	public synchronized int getNbPawn() 
	{
		return this.nbPawn;
	}
	
	public synchronized int getBoardSize()
	{
		return boardSize;
	}
	
	//Setters
	
	public synchronized void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		this.turnOrder = pTurnOrder;
	}
	
	public synchronized void setCurrentPawnIndex(int pPawnIndex)
	{
		this.currentPawnIndex = pPawnIndex;
	}
	
	public void setBoardSize(int boardSize) 
	{
		this.boardSize = boardSize;
	}

	public void setNbPawns(int nbPawns)
	{
		this.nbPawn = nbPawns;
	}
}
