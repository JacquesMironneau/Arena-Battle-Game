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
import fr.iutvalence.projet.battleArenaGame.network.Communication;
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
	private static ArrayList<Pawn> turnOrder;
	
	/**
	 * Represent the currentPawn of the game = the one that can be moved or can use spell.
	 */
	private static Pawn currentPawn;

	/**
	 * Type of communication
	 */
	private Communication communication;
	
	/**
	 * Create the board including creation of pawns 
	 */
	public Board(Communication pCommunication)
	{
		Board.turnOrder = new ArrayList<Pawn>();
		Board.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, BASE_POS_1PAWN1 , null));
		Board.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, BASE_POS_2PAWN1 , null));
		
		Board.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, BASE_POS_1PAWN2 , null));
		Board.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, BASE_POS_2PAWN2 , null));
		
		Board.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, BASE_POS_1PAWN3 , null));
		Board.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, BASE_POS_2PAWN3 , null));
		
		Board.currentPawn = Board.turnOrder.get(0);
		
		this.communication = pCommunication;
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
		if(Board.currentPawn.getMovePoints() > pMovement.calculateDistance())
		{
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < Board.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate() == Board.turnOrder.get(indexArrayList).getPos())
					throw new InvalidMoveException("Selected position is occupated");
			}
			
			//Move the current pawn to coordinates
			Board.currentPawn.setPos(pMovement.getDestCordinate());

			//TODO may be useless
			//Replace the pawn of the turnOrder (so the current one) by the currentPawn with moved coordinates)
			Board.turnOrder.set(Board.turnOrder.indexOf(Board.currentPawn), Board.currentPawn);
			
			
			//Send the turn order (need to create myServer and myClient (in Game consctructor and then in play method
				this.communication.sendToOther(Board.turnOrder);
				
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
	public void applyEffect() 
	{
		int index = Board.turnOrder.indexOf(currentPawn);
		
		if(Board.currentPawn.getEffect().isEmpty())
		{
			System.out.println("Effet vide");
			return;
		}
		for(PawnEffect eff : Board.currentPawn.getEffect() )
		{
			
			
			switch(eff.getEffectName())
			{
			case "Ignite":
				Board.currentPawn.setHealthPoints(Board.currentPawn.getHealthPoints()-5);
				break;
			case "Slow":
				Board.currentPawn.setMovePoints(Board.currentPawn.getMovePoints()-2);
				break;
			case "Silence":
				Board.currentPawn.setActionPoints(Board.currentPawn.getActionPoints()-2);
				break;
			case "stun":
				Board.currentPawn.setMovePoints(Board.currentPawn.getMovePoints()-1);
				Board.currentPawn.setActionPoints(Board.currentPawn.getActionPoints()-1);
				break;
			case "Crit":
				//TODO set effect
				break;
			case "weakness":
				//TODO set effect
				break;
			
			}
		
		}
		
		Board.currentPawn.updateEffect();
		Board.turnOrder.set(index, currentPawn);
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
		else if(pSpell.getShape().getSpellCost() > Board.currentPawn.getActionPoints())
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
			Board.currentPawn.setActionPoints(Board.currentPawn.getActionPoints() - pSpell.getShape().getSpellCost());
			//Set the cooldown on the spell used
			//TODO Exception if spell not found next TODO (review or check)
			pSpell.resetCooldown();
			int spellIndexInPage = -1;
			for(int index=0;index < 3;index++)
			{
				if(pSpell.equals(Board.currentPawn.getSpellPage().getSpell(index)))
					spellIndexInPage = index;	
				//TODO review or check
				else
					throw new SpellNotFoundException(pSpell);
			}
			
			Board.turnOrder.get(Board.turnOrder.indexOf(currentPawn)).getSpellPage().getSpell(spellIndexInPage).resetCooldown();
			//Check on all case affected by the spell shape
			ArrayList<Coordinate> effectedCoordinateList = new ArrayList<Coordinate>(Arrays.asList(pSpell.getShape().getEffectedCoordinates()));
			for(int effectedIndex=0;effectedIndex <effectedCoordinateList.size();effectedIndex++)
			{
				//If there is a pawn on the affected case
				Coordinate effectedCase = pMovement.getDestCordinate().addCoordinate(effectedCoordinateList.get(effectedIndex));
				if(this.getPawnOnCell(effectedCase)!= null)
				{
					Pawn pawnToAffect = this.getPawnOnCell(effectedCase);
					int indexOfPawnToAffect = Board.turnOrder.indexOf(pawnToAffect);
					//Set the new HP on the affected Pawn
					pawnToAffect.setHealthPoints(pawnToAffect.getHealthPoints()-pSpell.getShape().getDamage());
					//Add the effect on the affectPawn
					pawnToAffect.addEffect(new PawnEffect(pSpell.getSpellEffect()));
					Board.turnOrder.set(indexOfPawnToAffect, pawnToAffect);
				}
				
			}
			//And send data to the other player, use:  Send(this.turnOrder); (might need a try catch statement)
			
		}
		
		this.communication.sendToOther(Board.turnOrder);
	}
	
	
	
	/**
	 * Change the currentPawn of the player to the next one in the turnOrder array List
	 * If the currentPawn is the last one, change to the first one
	 */
	public void nextPawn() 
	{
		int nextPawnIndex = Board.turnOrder.indexOf(currentPawn)+1;
		
		if(nextPawnIndex==turnOrder.size())
			{
				Board.currentPawn = Board.turnOrder.get(0);

			}
		else
			{
				Board.currentPawn = Board.turnOrder.get(nextPawnIndex);
			}
	}
	
	
	/**
	 *  Check if a pawn is on coordinates passed in parameters
	 *  If it exists return the pawn
	 *  else returns null
	 */
	private Pawn getPawnOnCell(Coordinate pCoordinate)
	{
		for(int pawnIndex = 0; pawnIndex < Board.turnOrder.size();pawnIndex++)
		{
			if(Board.turnOrder.get(pawnIndex).getPos()==pCoordinate)
				return Board.turnOrder.get(pawnIndex);
		}
		return null;
	}
	
	
	/**
	 * Remove the dead pawns (hp <= 0) from the arrayList every time a new turn begin
	 */
	private void removeDeads()
	{
		for(int pawnIndex = 0; pawnIndex >Board.turnOrder.size();pawnIndex++)
		{
			if(Board.turnOrder.get(pawnIndex).getHealthPoints()<=0)
			{
				Board.turnOrder.remove(pawnIndex);
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
	public static ArrayList<Pawn> getTurnOrder()
	{
		return turnOrder;
	}
	
	/**
	 * Setter for turnOrder
	 * @param pTurnOrder : the new turnOrder to set
	 * TODO: might need to be synchronized
	 */
	public static void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		turnOrder = pTurnOrder;
	}
	
	public static void setCurrentPawn(Pawn thePawn)
	{
		currentPawn = thePawn;
	}
	
	public static Pawn getCurrentPawn()
	{
		return currentPawn;
	}
}
