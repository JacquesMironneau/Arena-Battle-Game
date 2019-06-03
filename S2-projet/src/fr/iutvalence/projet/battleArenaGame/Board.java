package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Random;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Communication;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
import fr.iutvalence.projet.battleArenaGame.view.ErrorMessages;
import fr.iutvalence.projet.battleArenaGame.view.Player;


public class Board{
	

	/**
	 * These values are the default position of pawns at the start of the game
	 * The first number is the player's number and the second is the pawn's number
	 */
/**	public final static Coordinate BASE_POS_1PAWN1 = new Coordinate(2,0);
	public final static Coordinate BASE_POS_1PAWN2 = new Coordinate(7,1);
	public final static Coordinate BASE_POS_1PAWN3 = new Coordinate(12,0);
	public final static Coordinate BASE_POS_2PAWN1 = new Coordinate(2,14);
	public final static Coordinate BASE_POS_2PAWN2 = new Coordinate(7,13);
	public final static Coordinate BASE_POS_2PAWN3 = new Coordinate(12,14);
*/
	public final static Coordinate BASE_POS_1PAWN1 = new Coordinate(2,0);
	public final static Coordinate BASE_POS_1PAWN2 = new Coordinate(2,1);
	public final static Coordinate BASE_POS_1PAWN3 = new Coordinate(2,2);
	public final static Coordinate BASE_POS_2PAWN1 = new Coordinate(3,1);
	public final static Coordinate BASE_POS_2PAWN2 = new Coordinate(3,3);
	public final static Coordinate BASE_POS_2PAWN3 = new Coordinate(3,2);
	
	private Player player;
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
	 * Create the board including creation of pawns 
	 */
	public Board(Communication pCommunication, Player pPlayer)
	{
		this.turnOrder = new ArrayList<Pawn>();
		this.initTurnOrder();
		
		this.player = pPlayer;
		this.currentPawnIndex = 0;
	}
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public void checkMove(Coordinate pDest)
	{
		
		Movement pMovement = new Movement(this.turnOrder.get(currentPawnIndex).getPos(),pDest);
		//If the pawn don't have enough move points to move
		if(this.turnOrder.get(currentPawnIndex).getMovePoints() < pMovement.calculateDistance())
		{
			this.player.displayError(ErrorMessages.NOT_ENOUGH_MOVE_POINT); 
			return;
		}
		
		//if the destination isn't in board limits
		if(pMovement.getDestCordinate().getCoordX()<0 || pMovement.getDestCordinate().getCoordX()>=Game.BOARD_SIZE || pMovement.getDestCordinate().getCoordY()<0 || pMovement.getDestCordinate().getCoordY()>=Game.BOARD_SIZE)
			{
				this.player.displayError(ErrorMessages.MOVE_OUT_OF_BOARD); 
				return;
			}
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate().equals(this.turnOrder.get(indexArrayList).getPos()))
				{
					this.player.displayError(ErrorMessages.CASE_OCCUPATED);
					return;
				}
			}
			//If the move is good
			//Move the current pawn to coordinates
			this.turnOrder.get(currentPawnIndex).setPos(pMovement.getDestCordinate());
			this.turnOrder.get(currentPawnIndex).setMovePoints(this.turnOrder.get(currentPawnIndex).getMovePoints()-pMovement.getDistance());				
			this.player.displayMoveDone();
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
	public void checkSpell(int pSpellIndex, Coordinate pDest)
	{
		
		Movement pMovement = new Movement(this.turnOrder.get(currentPawnIndex).getPos(),pDest);
		//If the spell is on cooldown
		if(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getCurrentCooldown() > 0)
		{	
			this.player.displayError(ErrorMessages.SPELL_IN_COOLDOWN);
			return;
		}
		//If the pawn has not enought action points (cost too much)
		if(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getSpellCost() > this.turnOrder.get(currentPawnIndex).getActionPoints())
		{
			this.player.displayError(ErrorMessages.NOT_ENOUGH_ACTION_POINT);
			return;
		}
		//If the spell target is too far (range too short)
		if(pMovement.getDistance() > this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getRange())
		{
			this.player.displayError(ErrorMessages.SPELL_TARGET_OUT_OF_RANGE);
			return;
		}
		
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
		this.player.displaySpellLaunched();
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
	public void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		this.turnOrder = pTurnOrder;
	}
	
	public void setCurrentPawnIndex(int pPawnIndex)
	{
		this.currentPawnIndex = pPawnIndex;
	}
	
	public int getCurrentPawnIndex()
	{
		return this.currentPawnIndex;
	}
	
	public int getNbPawn() {
		return this.nbPawn;
	}
	
	
	private Boolean exist(int X,int Y)
	{
		if (this.getTurnOrder().isEmpty())
			return false;
		else for(Pawn p : this.getTurnOrder())
		{if (p.getPos().getCoordX()==X && p.getPos().getCoordY()==Y)
		return true;
		}return false;
	}
	
	private void initTurnOrder()
	{
		int X;
		int Y;
		Random rand = new Random();
		this.nbPawn = this.player.askNbPawn();
		do {
		Game.boardSize = this.player.askBoardSize();
		}while(Game.boardSize<this.nbPawn*Game.maxPlayer);
		for (int i=0;i<= this.nbPawn;i++)
			{for(int k=0;k<=Game.maxPlayer;k++)
			{
				do {
				X = rand.nextInt(Game.BOARD_SIZE);
				Y = rand.nextInt(Game.BOARD_SIZE);
				}while(this.exist(X,Y));
			this.getTurnOrder().add(new Pawn(new TeamId(k),new Coordinate(X,Y),null));
			}
		}
	}
	
	
	public boolean areAllPageSet()
	{
		for(Pawn p : this.getTurnOrder())
			if(p.getSpellPage()==null)
				return false;
		return true;
	}
}
