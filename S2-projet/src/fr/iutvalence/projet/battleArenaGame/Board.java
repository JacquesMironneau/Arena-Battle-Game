package fr.iutvalence.projet.battleArenaGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.StatusMessages;


public class Board implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This list represent Pawns currently living and define the turn order
	 */
	private ArrayList<Pawn> turnOrder;
	
	/**
	 * Represent the currentPawn of the game = the one that can be moved or can use spell.
	 */
	private int currentPawnIndex;

	
	private int nbPawn;
	
	/**
	 * Size of the board (length of the square)
	 */
	public int boardSize;
	
	
	/**
	 * Create the board including creation of pawns 
	 */
	public Board(int pNbPlayer,int pNbPawn,int pBoardSize)
	{
		this.turnOrder = new ArrayList<Pawn>();
		this.currentPawnIndex = 0;
		this.nbPawn = pNbPawn;
		this.boardSize = pBoardSize;
		this.initTurnOrder(pNbPlayer);
	}
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public StatusMessages checkMove(Coordinate pDest)
	{
		
		Movement pMovement = new Movement(this.turnOrder.get(currentPawnIndex).getPos(),pDest);
		//If the pawn don't have enough move points to move
		if(this.turnOrder.get(currentPawnIndex).getMovePoints() < pMovement.calculateDistance())
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
	 * apply effect to current pawn at the start of the turn
	 */
	public void applyEffect() 
	{
		if(this.turnOrder.get(currentPawnIndex).getEffect().isEmpty()) return;
		for(PawnEffect eff : this.turnOrder.get(currentPawnIndex).getEffect() )
		{
			switch(eff.getEffectName())
			{
			case "Ignite":
				this.turnOrder.get(currentPawnIndex).setHealthPoints(this.turnOrder.get(currentPawnIndex).getHealthPoints()-5);
				break;
			case "Slow":
				this.turnOrder.get(currentPawnIndex).setMovePoints(this.turnOrder.get(currentPawnIndex).getMovePoints()-2);
				break;
			case "Silence":
				this.turnOrder.get(currentPawnIndex).setActionPoints(this.turnOrder.get(currentPawnIndex).getActionPoints()-2);
				break;
			case "Stun":
				this.turnOrder.get(currentPawnIndex).setMovePoints(this.turnOrder.get(currentPawnIndex).getMovePoints()-1);
				this.turnOrder.get(currentPawnIndex).setActionPoints(this.turnOrder.get(currentPawnIndex).getActionPoints()-1);
				break;
			case "Crit":
				//TODO set effect
				break;
			case "Weakness":
				//TODO set effect
				break;
			}
		}
		this.turnOrder.get(currentPawnIndex).updateEffect();
	}
	
	/**
	 *  checkSpell method: Check if the chosen spell is valid
	 * A spell is valid if the player have enough action Point, if his spell have a cooldown equals to 0 and if the selected destination outside the board
	
	 * @return  true if the chosen spell by the player is valid and authorized
	 */
	public StatusMessages checkSpell(int currentPlayerIndex,int pSpellIndex, Coordinate pDest)
	{
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
		//Set the cooldown on the spell used
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
	 * Change the currentPawn of the player to the next one in the turnOrder array List
	 * If the currentPawn is the last one, change to the first one
	 */
	public int nextPawn() 
	{
		this.currentPawnIndex++;
		if(this.currentPawnIndex==this.turnOrder.size())
			this.currentPawnIndex=0;
		return this.turnOrder.size()
;	}
	
	
	
	/**
	 * Remove the dead pawns (hp <= 0) from the arrayList every time a new turn begin
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
		/**
		 * 
		 */
		for(Pawn p: this.turnOrder)
		{
			if(p.equals(tempPawn))
			{
				this.currentPawnIndex = this.turnOrder.indexOf(tempPawn);
				find = true;
			}
		}
		if(!find)
			this.nextPawn();
		
	}
	
	
	/**
	 * Getter for turnOrder
	 * @return the turnOrder
	 */
	public ArrayList<Pawn> getTurnOrder()
	{
		return this.turnOrder;
	}
	
	/**
	 * Setter for turnOrder
	 * @param pTurnOrder : the new turnOrder to set
	 */
	public synchronized void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		this.turnOrder = pTurnOrder;
	}
	
	public synchronized void setCurrentPawnIndex(int pPawnIndex)
	{
		this.currentPawnIndex = pPawnIndex;
	}
	
	public int getCurrentPawnIndex()
	{
		return this.currentPawnIndex;
	}
	
	public synchronized int getNbPawn() {
		return this.nbPawn;
	}
	
	
	private boolean exist(int X,int Y)
	{
		if (this.getTurnOrder().isEmpty())
			return false;
		else for(Pawn p : this.getTurnOrder())
		{if (p.getPos().getCoordX()==X && p.getPos().getCoordY()==Y)
		return true;
		}return false;
	}
	
	private void initTurnOrder(int maxPlayer)
	{
		int X;
		int Y;
		Random rand = new Random();
		for (int i=1;i<= this.nbPawn;i++)
			{
			for(int k=1;k<=maxPlayer;k++)
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
	
	
	public synchronized int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	
	public void setNbPawns(int nbPawns)
	{
		this.nbPawn = nbPawns;
	}
}
