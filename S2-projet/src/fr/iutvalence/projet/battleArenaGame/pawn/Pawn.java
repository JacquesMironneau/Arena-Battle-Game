package fr.iutvalence.projet.battleArenaGame.pawn;

import java.io.Serializable;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;


/**
 * Represents a Pawn
 * It has HealthPoints,
 * MovePoints,
 * ActionPoints,
 * A spellPage,
 * Coordinates,
 * Active effects 
 * A pawn and belong to a team (each player).
 * each player have 3 pawns.
 *
 * @author charvevi
 */
public class Pawn implements Serializable
{
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -3994360615192173337L;

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
	 * Number of local Pawn 
	 */
	private static int localPawnCount = 1;
	/**
	 * Number of remote Pawn
	 */
	private static int remotePawnCount = 1;
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
	 * Id of the pawn (his name)
	 */
	private String id;

	/**
	 * List of all the active effect on the Pawn
	 */
	private ArrayList<PawnEffect> activeEffects;
	
	/**
	 * Constructor of Pawn
	 * @param pteam : The Pawn's Team
	 * @param pBaseCoordinate : The Pawn's coordinate when he is created
	 * @param pSpellPage : The Pawn's spell page, selected by the player
	 */
	public Pawn(PawnTeam pteam,Coordinate pBaseCoordinate,SpellPage pSpellPage)
	{
		this.team = pteam;
		this.healthPoints = Pawn.DEFAULT_HEALTH_POINTS;
		this.actionPoints = Pawn.DEFAULT_ACTION_POINTS;
		this.movePoints = Pawn.DEFAULT_MOVE_POINTS;
		this.currentCoordinate = pBaseCoordinate;
		this.mySpellPage = pSpellPage;
		this.activeEffects= new ArrayList<PawnEffect>();
		
		if(this.team==PawnTeam.PAWN_LOCAL)
		{
			this.id = "J1." + localPawnCount;
			localPawnCount++;
		}
		if(this.team==PawnTeam.PAWN_REMOTE)
			{
				this.id = "J2." + remotePawnCount;
				remotePawnCount++;
			}
		

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
	 * @param pMP the amount of movePoints to set
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
	 * @param pHP the amount of healthPoints to set
	 */
	public void setHealthPoints(int pHP)
	{	
		this.healthPoints = pHP;
		if(this.healthPoints < 0) 
			this.healthPoints = 0;
		
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
	 * Getter for the team of the pawn
	 * @return in which team the pawn is
	 */
	public PawnTeam getTeam()
	{
		return this.team;
	}
	
	/**
	 * Add an effect to the current list of Effect of this pawn
	 * @param pEffect the effect to add
	 */
	public void addEffect(PawnEffect pEffect)
	{
		this.activeEffects.add(pEffect);
	}
	
	/**
	 * Getter for the list of the pawn's effect
	 * @return an HashSet of PawnEffect
	 */
	public ArrayList<PawnEffect> getEffect()
	{
		return this.activeEffects;
	}
	
	/**
	 * Reduce the counter of all effect on this pawn by 1
	 * Removes the effect if the counter reach 0 
	 */
	public void updateEffect()
	{
		
		for(int arrayIndex=0;arrayIndex<this.getEffect().size();arrayIndex++)
		{
		  PawnEffect effectToUpdate = this.getEffect().get(arrayIndex);
		  effectToUpdate.setCurrentDuration(effectToUpdate.getCurrentDuration() -1);
		  PawnEffect updatedEffect = effectToUpdate;
		  
		  if(updatedEffect.getCurrentDuration() == 0)
			  this.getEffect().remove(arrayIndex);
		  
		  else
			  this.getEffect().set(arrayIndex, updatedEffect);
		}
	}
	
	/**
	 * Setter for mySpellPage
	 * @param pPage the page to set
	 */
	public void setSpellPage(SpellPage pPage)
	{
		this.mySpellPage=pPage;
	}
	
	/**
	 * Getter for the pawn spellPage
	 * @return the pawn SpellPage
	 */
	public SpellPage getSpellPage()
	{
		return this.mySpellPage;
	}
	
	public void setTeam(PawnTeam theTeam)
	{
		this.team = theTeam;
	}
	
	public String getId()
	{
		return this.id;
	}
}

