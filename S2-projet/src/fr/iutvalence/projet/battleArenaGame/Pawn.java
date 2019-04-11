package fr.iutvalence.projet.battleArenaGame;

import java.util.HashSet;

/**
 * Represents a Pawn
 * @author charvevi
 *
 */
public class Pawn {
	//TODO Complete the default values for HP, PM and PA
	/**
	 * Default amount of health for a Pawn
	 */
	public final static int DEFAULT_HEALTH_POINTS = 0 ;
	
	/**
	 * Default amount of move points for a Pawn
	 */
	public final static int DEFAULT_MOVE_POINTS = 0 ;
	
	/**
	 * Default amount of action points for a Pawn
	 */
	public final static int DEFAULT_ACTION_POINTS = 0 ;
	
	/**
	 * Current amount of health of a Pawn
	 */
	private int healthPoints;
	
	/**
	 * Current amount of movePoints (PM)
	 */
	private int movePoints;
	
	/**
	 * Current amount of actionPoints (PA)
	 */
	private int actionPoints;
	
	/**
	 * Current coordinate of the Pawn
	 */
	private Coordinate currentCoordinate;
	
	/**
	 * The team which the pawn's belong to
	 */
	private PawnTeam team;
	
	/**
	 * The SpellPage of the Pawn
	 */
	private SpellPage mySpellPage;
	
	/**
	 * List of all the active effect on the Pawn
	 */
	private HashSet<PawnEffect> activeEffects;
	
	/**
	 * Constructor for pawn:
	 * Set a team for the pawn
	 * and set his attributes to their default value : a pawn is ready to be in the game
	 */
	public Pawn(PawnTeam pteam)
	{
		this.team = pteam;
		this.healthPoints = Pawn.DEFAULT_HEALTH_POINTS;
		this.actionPoints = Pawn.DEFAULT_ACTION_POINTS;
		this.movePoints = Pawn.DEFAULT_MOVE_POINTS;
		
	}
	/**
	 * Setter for currentCoordinate
	 * @param pDestination : the destination of the Pawn
	 */
	public void setPos(Coordinate pDestination)
	{
		this.currentCoordinate = pDestination;
	}
	
	/**
	 * Getter for currentCoordinate
	 * @return the currentCoordinate of the Pawn
	 */
	public Coordinate getPos()
	{
		return this.currentCoordinate;
	}
	
	/**
	 * Setter for actionPoints
	 * @param pAP : amount of actionPoints to set
	 */
	public void setActionPoints(int pAP)
	{
		this.actionPoints=pAP;
	}
	
	/**
	 * Getter for actionPoints
	 * @return the current amount of actionPoints
	 */
	public int getActionPoints()
	{
		return this.actionPoints;
	}
	
	/**
	 * Setter for movePoints
	 * @param pMP : amount of movePoints to set
	 */
	public void setMovePoints(int pMP)
	{
		this.movePoints=pMP;
	}
	
	
	/**
	 * Getter for movePoints
	 * @return the amount of movePoints
	 */
	public int getMovePoints()
	{
		return this.movePoints;
	}
	
	/**
	 * Setter for HealthPoints
	 * @param pHP : amount of healthPoints to set
	 */
	public void setHealthPoints(int pHP)
	{
		this.healthPoints= pHP;
	}
	
	/**
	 * Getter for healthPoints
	 * @return the amount of healthPoints
	 */
	public int getHealthPoints()
	{
		return this.healthPoints;
	}
	
	/**
	 * Add an effect to the current list of Effect of this pawn
	 * @param pEffect : the effect to add
	 */
	public void addEffect(PawnEffect pEffect)
	{
		this.activeEffects.add(pEffect);
	}
	
	/**
	 * Update the turn counter of all the effects of this pawn.
	 * Removes the effect if the counter reach 0 
	 */
	//TODO Complete this method
	public void updateEffect()
	{
		
	}
	
	/**
	 * Setter for mySpellPage
	 * @param pPage : page to set
	 */
	public void setSpellPage(SpellPage pPage)
	{
		this.mySpellPage=pPage;
	}
}

