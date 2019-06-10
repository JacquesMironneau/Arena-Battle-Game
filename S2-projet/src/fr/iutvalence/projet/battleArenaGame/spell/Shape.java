package fr.iutvalence.projet.battleArenaGame.spell;

import java.util.HashSet;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

public class Shape {

	/*
	 * Default shapes :
	 * Ball :10,2,5,3,new Coordinate[] {new Coordinate(0,0)}),
	 * Fist:15,1,1,2,new Coordinate[] {new Coordinate(0,0)}),
	 *Cross:10,3,5,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)}),
	 *Square:10,3,4,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)}),
	 *Sword:8,2,1,3, new Coordinate[] {new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)}),
	 *Beam:10,3,1,4, new Coordinate[] {new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)});
	 */
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
