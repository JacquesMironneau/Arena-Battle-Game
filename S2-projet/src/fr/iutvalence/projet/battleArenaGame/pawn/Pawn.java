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
	public final static int DEFAULT_HEALTH_POINTS = 100 ;
	
	/**
	 * Default amount of move points for a Pawn
	 */
	public final static int DEFAULT_MOVE_POINTS = 6 ;
	
	/**
	 * Default amount of action points for a Pawn
	 */
	public final static int DEFAULT_ACTION_POINTS = 6 ;
	
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
	 * Id of the team which the paw's belong to 
	 */
	private TeamId teamId;
	
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
	public Pawn(TeamId pTeamId,Coordinate pBaseCoordinate,SpellPage pSpellPage)
	{
		this.healthPoints = Pawn.DEFAULT_HEALTH_POINTS;
		this.actionPoints = Pawn.DEFAULT_ACTION_POINTS;
		this.movePoints = Pawn.DEFAULT_MOVE_POINTS;
		this.currentCoordinate = pBaseCoordinate;
		this.mySpellPage = pSpellPage;
		this.activeEffects= new ArrayList<PawnEffect>();
		this.teamId = pTeamId;
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
	 * Set move and action points of the pawn to his default values (values at the start of the turn)
	 */
	public void resetPoints()
	{
		this.movePoints = DEFAULT_MOVE_POINTS;
		this.actionPoints = DEFAULT_ACTION_POINTS;
	}
	
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actionPoints;
		result = prime * result + ((activeEffects == null) ? 0 : activeEffects.hashCode());
		result = prime * result + ((currentCoordinate == null) ? 0 : currentCoordinate.hashCode());
		result = prime * result + healthPoints;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + movePoints;
		result = prime * result + ((mySpellPage == null) ? 0 : mySpellPage.hashCode());
		result = prime * result + ((teamId == null) ? 0 : teamId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pawn other = (Pawn) obj;
		if (actionPoints != other.actionPoints)
			return false;
		if (activeEffects == null) {
			if (other.activeEffects != null)
				return false;
		} else if (!activeEffects.equals(other.activeEffects))
			return false;
		if (currentCoordinate == null) {
			if (other.currentCoordinate != null)
				return false;
		} else if (!currentCoordinate.equals(other.currentCoordinate))
			return false;
		if (healthPoints != other.healthPoints)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (movePoints != other.movePoints)
			return false;
		if (mySpellPage == null) {
			if (other.mySpellPage != null)
				return false;
		} else if (!mySpellPage.equals(other.mySpellPage))
			return false;
		if (teamId == null) {
			if (other.teamId != null)
				return false;
		} else if (!teamId.equals(other.teamId))
			return false;
		return true;
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
	
	
	public String getId()
	{
		return this.id;
	}
	
	public TeamId getTeamId()
	{
		return this.teamId;
	}
	
	/**
	 * Return true if the pawn have a spellPage
	 * @return
	 */
	public boolean haveSpellPage()
	{
		return this.mySpellPage != null;
	}
}

