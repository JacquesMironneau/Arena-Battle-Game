package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Arrays;
import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.NotEnoughPointsException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellOnCooldownException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellOutOfRangeException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;

public class Board {
	
	/**
	 * These values are the default position of pawns at the start of the game
	 * The first number is the player's number and the second is the pawn's number
	 * TODO: move this in init() method
	 */
	public final static Coordinate BASE_POS_1PAWN1 = new Coordinate(2,0);
	public final static Coordinate BASE_POS_1PAWN2 = new Coordinate(7,1);
	public final static Coordinate BASE_POS_1PAWN3 = new Coordinate(12,0);
	public final static Coordinate BASE_POS_2PAWN1 = new Coordinate(2,14);
	public final static Coordinate BASE_POS_2PAWN2 = new Coordinate(7,13);
	public final static Coordinate BASE_POS_2PAWN3 = new Coordinate(12,14);

	
	/**
	 * This list represent Pawns currently living and define the turn order
	 */
	private ArrayList<Pawn> turnOrder;
	
	/**
	 * Represent the currentPawn of the game = the one that can be moved or can use spell.
	 */
	private Pawn currentPawn;

	/**
	 * Create the board including creation of pawns 
	 */
	public Board()
	{
		this.turnOrder = new ArrayList<Pawn>();
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, BASE_POS_1PAWN1 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, BASE_POS_2PAWN1 , null));
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, BASE_POS_1PAWN2 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, BASE_POS_2PAWN2 , null));
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, BASE_POS_1PAWN3 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, BASE_POS_2PAWN3 , null));
		
		this.currentPawn = this.turnOrder.get(0);
	}
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public void checkMove(Movement pMovement) throws InvalidMoveException
	{
		//If the pawn has enough move points to move
		if(this.currentPawn.getMovePoints() > pMovement.calculateDistance())
		{
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate() == this.turnOrder.get(indexArrayList).getPos())
					throw new InvalidMoveException("Selected position is occupated");
			}
			
			//Move the current pawn to coordinates
			this.currentPawn.setPos(pMovement.getDestCordinate());

			//Replace the pawn of the turnOrder (so the current one) by the currentPawn with moved coordinates)
			this.turnOrder.set(this.turnOrder.indexOf(this.currentPawn), this.currentPawn);
			
			
			//Send the turn order (need to create myServer and myClient (in Game consctructor and then in play method
			if(this.isServer)
				myServer.SendAll(this.turnOrder);
				
			else
				myClient.Send(this.turnOrder);
				
			//The movement is done
//	TODO remove =>	return true;
		}
		else {
			throw new InvalidMoveException("Not enough move points");
		}
		//The movement isn't correct
//	TODO remove =>	return false; 
	}
	
	
	/**
	 * apply effect to current pawn at the start of the turn
	 */
	private void applyEffect() 
	{
		int index = this.turnOrder.indexOf(currentPawn);
		
		if(this.currentPawn.getEffect().isEmpty())
		{
			System.out.println("Effet vide");
			return;
		}
		for(PawnEffect eff : this.currentPawn.getEffect() )
		{
			
			
			switch(eff.getEffectName())
			{
			case "Ignite":
				this.currentPawn.setHealthPoints(this.currentPawn.getHealthPoints()-5);
				break;
			case "Slow":
				this.currentPawn.setMovePoints(this.currentPawn.getMovePoints()-2);
				break;
			case "Silence":
				this.currentPawn.setActionPoints(this.currentPawn.getActionPoints()-2);
				break;
			case "stun":
				this.currentPawn.setMovePoints(this.currentPawn.getMovePoints()-1);
				this.currentPawn.setActionPoints(this.currentPawn.getActionPoints()-1);
				break;
			case "Crit":
				//TODO set effect
				break;
			case "weakness":
				//TODO set effect
				break;
			
			}
		
		}
		
		this.currentPawn.updateEffect();
		this.turnOrder.set(index, currentPawn);
		//replace current pawn by the right one in turnorder
	}
	
	/**
	 *  checkSpell method: Check if the chosen spell is valid
	 * A spell is valid if the player have enough action Point, if his spell have a cooldown equals to 0 and if the selected destination outside the board
	
	 * @return  true if the chosen spell by the player is valid and authorized
	 */
	public void checkSpell(Spell pSpell, Movement pMovement) throws SpellNotFoundException, SpellIndexException, NotEnoughPointsException, SpellOutOfRangeException, SpellOnCooldownException
	{
		
		
		if(pSpell.getCurrentCooldown() > 0)
		{	
			//The spell is on cooldown
			//throw new SpellOnCooldownException();
			System.out.println("Sort en CD");
		}
		else if(pSpell.getShape().getSpellCost() > this.currentPawn.getActionPoints())
		{
			//The spell cost is bigger than the pawn's action points
			throw new NotEnoughPointsException();
		}
		else if(pMovement.getDistance() > pSpell.getShape().getRange())
		{
			//The target is too far away
			System.out.println("Out of range ");
			//throw new SpellOutOfRangeException();
		}
		else
		{
			//The spell is sent
			//Remove action points used
			this.currentPawn.setActionPoints(this.currentPawn.getActionPoints() - pSpell.getShape().getSpellCost());
			//Set the cooldown on the spell used
			//TODO Exception if spell not found next TODO (review or check)
			pSpell.resetCooldown();
			int spellIndexInPage = -1;
			for(int index=0;index < 3;index++)
			{
				if(pSpell.equals(this.currentPawn.getSpellPage().getSpell(index)))
					spellIndexInPage = index;	
				//TODO review or check
				else
					throw new SpellNotFoundException(pSpell);
			}
			
			this.turnOrder.get(this.turnOrder.indexOf(currentPawn)).getSpellPage().getSpell(spellIndexInPage).resetCooldown();
			//Check on all case affected by the spell shape
			ArrayList<Coordinate> effectedCoordinateList = new ArrayList<Coordinate>(Arrays.asList(pSpell.getShape().getEffectedCoordinates()));
			for(int effectedIndex=0;effectedIndex <effectedCoordinateList.size();effectedIndex++)
			{
				//If there is a pawn on the affected case
				Coordinate effectedCase = pMovement.getDestCordinate().addCoordinate(effectedCoordinateList.get(effectedIndex));
				if(this.getPawnOnCell(effectedCase)!= null)
				{
					Pawn pawnToAffect = this.getPawnOnCell(effectedCase);
					int indexOfPawnToAffect = this.turnOrder.indexOf(pawnToAffect);
					//Set the new HP on the affected Pawn
					pawnToAffect.setHealthPoints(pawnToAffect.getHealthPoints()-pSpell.getShape().getDamage());
					//Add the effect on the affectPawn
					pawnToAffect.addEffect(new PawnEffect(pSpell.getSpellEffect()));
					this.turnOrder.set(indexOfPawnToAffect, pawnToAffect);
				}
				
			}
			//And send data to the other player, use:  Send(this.turnOrder); (might need a try catch statement)
			
		}
		
		if(this.isServer)
			myServer.SendAll(this.turnOrder);
			
		else
			myClient.Send(this.turnOrder);
	}
	
	
	
	/**
	 * Change the currentPawn of the player to the next one in the turnOrder array List
	 * If the currentPawn is the last one, change to the first one
	 */
	private void nextPawn() 
	{
		int nextPawnIndex = this.turnOrder.indexOf(currentPawn)+1;
		
		if(nextPawnIndex==turnOrder.size())
			{
				this.currentPawn = this.turnOrder.get(0);

			}
		else
			{
				this.currentPawn = this.turnOrder.get(nextPawnIndex);
			}
	}
	
	
	
	
	
	/**
	 * Remove the dead pawns (hp <= 0) from the arrayList every time a new turn begin
	 */
	private void removeDeads()
	{
		for(int pawnIndex = 0; pawnIndex >this.turnOrder.size();pawnIndex++)
		{
			if(this.turnOrder.get(pawnIndex).getHealthPoints()<=0)
			{
				this.turnOrder.remove(pawnIndex);
			}
		}
	}
	
	
	/*
	 * Edit in turnOrder is mainly done in the network package
	 * (To apply the modifications done by a player to the other
	 */
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
	 * @param pTurnOrder : the new turnOrder to set
	 * TODO: might need to be synchronized
	 */
	public void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		this.turnOrder = pTurnOrder;
	}
	
	public void setCurrentPawn(Pawn thePawn)
	{
		this.currentPawn = thePawn;
	}
	
}
