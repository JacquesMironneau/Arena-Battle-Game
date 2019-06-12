package fr.iutvalence.projet.battleArenaGame.spell;

import java.util.HashSet;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

public class Shape {

	public final static int DEFAULT_BALL_DAMAGE = 10;
	public final static int DEFAULT_BALL_COOLDOWN = 2;
	public final static int DEFAULT_BALL_RANGE = 5;
	public final static int DEFAULT_BALL_COST = 3;
	
	public final static int DEFAULT_FIST_DAMAGE = 15;
	public final static int DEFAULT_FIST_COOLDOWN = 1;
	public final static int DEFAULT_FIST_RANGE = 1;
	public final static int DEFAULT_FIST_COST = 2;
	
	public final static int DEFAULT_CROSS_DAMAGE = 10;
	public final static int DEFAULT_CROSS_COOLDOWN = 3;
	public final static int DEFAULT_CROSS_RANGE = 5;
	public final static int DEFAULT_CROSS_COST = 4;
	
	public final static int DEFAULT_SQUARE_DAMAGE = 10;
	public final static int DEFAULT_SQUARE_COOLDOWN = 3;
	public final static int DEFAULT_SQUARE_RANGE = 4;
	public final static int DEFAULT_SQUARE_COST = 4;
	
	public final static int DEFAULT_SWORD_DAMAGE = 8;
	public final static int DEFAULT_SWORD_COOLDOWN = 2;
	public final static int DEFAULT_SWORD_RANGE = 1;
	public final static int DEFAULT_SWORD_COST = 3;
		
	public final static int DEFAULT_BEAM_DAMAGE = 10;
	public final static int DEFAULT_BEAM_COOLDOWN = 3;
	public final static int DEFAULT_BEAM_RANGE = 1;
	public final static int DEFAULT_BEAM_COST = 4;
	/**
	 * Name of the Shape
	 */
	private String name;
	
	/**
	 * Damage of the Shape
	 */
	private int damage;
	
	
	/**
	 * Number of turn between two uses
	 */
	private int cooldown;
	
	
	/**
	 * Max number of case where you can launch the spell
	 */
	private int range;
	
	
	/**
	 * Number of action points used to cast the spell
	 */
	private int cost;
	
	
	/**
	 * List of Coordinate affected by the spell
	 */
	private HashSet<Coordinate> effectedCoordinates;
	
	
	//TODO super mathematical formula to deal with every parameters
	/**
	 * initialize a new shape with a given type,damage,cooldown range spellCost and an array of coordinate
	 * @param theType the type of the Shape
	 * @param theDamage the damage that the shape will inflict
	 * @param theCooldown the cooldown of the shape
	 * @param theRange the range of the shape
	 * @param theSpellCost the cost of the shape in action points
	 * @param theEffectedCoords the coordinate that will be effect by the shape
	 */
	public Shape(String pName,int pDamage,int pCooldown,int pRange,int pSpellCost, HashSet<Coordinate> pEffectedCoords)
	{
		this.name = pName;
		this.damage = pDamage;
		this.cooldown = pCooldown;
		this.range = pRange;
		this.cost = pSpellCost;
		this.effectedCoordinates = pEffectedCoords;
	}
	
	
	
	/*
	 * Getters for the shape
	 */
	
	public String getName() 
	{
		return this.name;
	}

	public int getDamage()
	{
		return this.damage;
	}


	public int getCooldown() 
	{
		return this.cooldown;
	}


	public int getRange() 
	{
		return this.range;
	}


	public int getSpellCost()
	{
		return this.cost;
	}

	public HashSet<Coordinate> getEffectedCoordinates()
	{
		return this.effectedCoordinates;
	}
	
	
	/**
	 * Displays the actual state of the shape
	 */
	public String toString()
	{
		return "[type="+this.name+",damage="+this.damage+",cooldown="+this.cooldown+",range="+this.range+",spellCost="+this.cost+"]";
	}

}
