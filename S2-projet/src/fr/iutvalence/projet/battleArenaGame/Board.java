package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Communication;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
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

	/**
	 * Type of communication
	 */
	private  Communication communication;
	
	/**
	 * Create the board including creation of pawns 
	 */
	public Board(Communication pCommunication, Player pPlayer)
	{
		this.turnOrder = new ArrayList<Pawn>();
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL,TeamId.TEAM_1,BASE_POS_1PAWN1 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE,TeamId.TEAM_2, BASE_POS_2PAWN1 , null));
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL,TeamId.TEAM_1, BASE_POS_1PAWN2 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE,TeamId.TEAM_2, BASE_POS_2PAWN2 , null));
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL,TeamId.TEAM_1, BASE_POS_1PAWN3 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE,TeamId.TEAM_2, BASE_POS_2PAWN3 , null));
		
		this.player = pPlayer;
		this.currentPawnIndex = 0;
		
		this.communication = pCommunication;
	}
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public boolean checkMove(Coordinate pDest)
	{
		
		Movement pMovement = new Movement(this.turnOrder.get(currentPawnIndex).getPos(),pDest);
		//If the pawn don't have enough move points to move
		if(this.turnOrder.get(currentPawnIndex).getMovePoints() < pMovement.calculateDistance())
		{
			this.player.displayError(ErrorMessages.NOT_ENOUGH_MOVE_POINT); 
			return false;
		}
		
		//if the destination isn't in board limits
		if(pMovement.getDestCordinate().getCoordX()<0 || pMovement.getDestCordinate().getCoordX()>=Game.BOARD_SIZE || pMovement.getDestCordinate().getCoordY()<0 || pMovement.getDestCordinate().getCoordY()>=Game.BOARD_SIZE)
			{
				this.player.displayError(ErrorMessages.MOVE_OUT_OF_BOARD); 
				return false;
			}
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate() == this.turnOrder.get(indexArrayList).getPos())
				{
					this.player.displayError(ErrorMessages.CASE_OCCUPATED);
					return false;
				}
			}
			//If the move is good
			//Move the current pawn to coordinates
			this.turnOrder.get(currentPawnIndex).setPos(pMovement.getDestCordinate());
			this.turnOrder.get(currentPawnIndex).setMovePoints(this.turnOrder.get(currentPawnIndex).getMovePoints()-pMovement.getDistance());				
			this.player.displayMoveDone();
			return true;
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
	public boolean checkSpell(int pSpellIndex, Movement pMovement)
	{
		//If the spell is on cooldown
		if(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getCurrentCooldown() > 0)
		{	
			this.player.displayError(ErrorMessages.SPELL_IN_COOLDOWN);
			return false;
		}
		//If the pawn has not enought action points (cost too much)
		if(this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getSpellCost() > this.turnOrder.get(currentPawnIndex).getActionPoints())
		{
			this.player.displayError(ErrorMessages.NOT_ENOUGH_ACTION_POINT);
			return false;
		}
		//If the spell target is too far (range too short)
		if(pMovement.getDistance() > this.turnOrder.get(currentPawnIndex).getSpellPage().getSpell(pSpellIndex).getShape().getRange())
		{
			this.player.displayError(ErrorMessages.SPELL_TARGET_OUT_OF_RANGE);
			return false;
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
		return true;
	}
	
	
	
	/**
	 * Change the currentPawn of the player to the next one in the turnOrder array List
	 * If the currentPawn is the last one, change to the first one
	 */
	public void nextPawn() 
	{
		this.currentPawnIndex++;
		if(this.currentPawnIndex==this.turnOrder.size())
			this.currentPawnIndex=0;

	}
	
	
	
	/**
	 * Remove the dead pawns (hp <= 0) from the arrayList every time a new turn begin
	 */
	public void removeDeads()
	{
		ArrayList<Pawn> temp = new ArrayList<Pawn>();
		for(Pawn p: this.turnOrder)
			if(p.getHealthPoints() <= 0)
				temp.add(p);
		this.turnOrder.removeAll(temp);
	}
	
	/**
	 * Give the status of the game
	 * @return EndStatus enum (Draw, Victory, Defeat or Running)
	 */
	public EndStatus getWinTeam()
	{
		if(this.turnOrder.size()==0)
			return EndStatus.DRAW;
		int vLocal = 0;
		int vRemote = 0;
		for(Pawn p : this.turnOrder)
		{
			if(p.getTeam()==PawnTeam.PAWN_LOCAL)
				vLocal++;
			else
				vRemote++;
		}
		if(vLocal>0 && vRemote>0)
			return EndStatus.RUNNING;
		if(vLocal==0)
			return EndStatus.DEFEAT;
		if(vRemote==0)
			return EndStatus.VICTORY;		
		return null;
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
}
