package fr.iutvalence.projet.battleArenaGame.pawn;

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
 */

public class Pawn
{

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
	private int teamId;
	
	/**
	 * The SpellPage of the Pawn
	 */
	private SpellPage mySpellPage;
	
	/**
	 * Id of the pawn (his name)
	 */
	private String name;

	/**
	 * List of all the active effect on the Pawn
	 */
	private ArrayList<PawnEffect> activeEffects;
	
	/**
	 * Constructor of Pawn, set his points to defaults values
	 * 
	 * @param pteam : The Pawn's Team id
	 * @param pBaseCoordinate : The Pawn's coordinate when he is created
	 * @param pSpellPage : The Pawn's spell page, selected by the player
	 */
	public Pawn(int pTeamId,Coordinate pBaseCoordinate,String pName)
	{
		this.healthPoints = Pawn.DEFAULT_HEALTH_POINTS;
		this.actionPoints = Pawn.DEFAULT_ACTION_POINTS;
		this.movePoints = Pawn.DEFAULT_MOVE_POINTS;
		
		this.currentCoordinate = pBaseCoordinate;
		this.activeEffects= new ArrayList<PawnEffect>();
		this.teamId = pTeamId;
		this.name = pName;
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
	
	 /**
	 *  Add an effect to the current list of Effect of this pawn
	 * @param pEffect the effect to add
	 */
	public void addEffect(PawnEffect pEffect)
	{
		this.activeEffects.add(pEffect);
	}
	
	/**
	 * @return if the pawn own a page Spell
	 */
	public boolean haveSpellPage()
	{
		return this.mySpellPage != null;
	}
	
	
	
	/*
	 * Getters
	 */

	public Coordinate getPos()
	{
		return this.currentCoordinate;
	}

	public int getHealthPoints()
	{
		return this.healthPoints;
	}
	
	public int getActionPoints()
	{
		return this.actionPoints;
	}
	
	public int getMovePoints()
	{
		return this.movePoints;
	}
	
	public ArrayList<PawnEffect> getEffect()
	{
		return this.activeEffects;
	}

	public SpellPage getSpellPage()
	{
		return this.mySpellPage;
	}

	public String getName()
	{
		return this.name;
	}

	public int getTeamId()
	{
		return this.teamId;
	}
	
	
	
	/*
	 * Setters
	 */

	public void setPos(Coordinate pDestination)
	{
		this.currentCoordinate = pDestination;
	}
	
	public void setActionPoints(int pAP)
	{
		this.actionPoints=pAP;
	}

	public void setMovePoints(int pMP)
	{
		this.movePoints=pMP;
	}
	
	public void setHealthPoints(int pHP)
	{	
		this.healthPoints = pHP;
		if(this.healthPoints < 0) 
			this.healthPoints = 0;
		
	}
	
	public void setSpellPage(SpellPage pPage)
	{
		this.mySpellPage=pPage;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actionPoints;
		result = prime * result + ((activeEffects == null) ? 0 : activeEffects.hashCode());
		result = prime * result + ((currentCoordinate == null) ? 0 : currentCoordinate.hashCode());
		result = prime * result + healthPoints;
		result = prime * result + movePoints;
		result = prime * result + ((mySpellPage == null) ? 0 : mySpellPage.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + teamId;
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
		if (movePoints != other.movePoints)
			return false;
		if (mySpellPage == null) {
			if (other.mySpellPage != null)
				return false;
		} else if (!mySpellPage.equals(other.mySpellPage))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (teamId != other.teamId)
			return false;
		return true;
	}

}

