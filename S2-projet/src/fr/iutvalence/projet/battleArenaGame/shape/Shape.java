package fr.iutvalence.projet.battleArenaGame.shape;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * 
 * Shape Enumeration that represents all available types of shapes
 * A shape is the current shape of the spell, it defines how much damage the spell deals, how many cooldown turns he has, the range of the spell and its cost.
 * It also define every coordinates affected by the cast of the spell.
 *
 */
public enum Shape {
	
	Ball("Ball",100,2,5,3,new Coordinate[] {new Coordinate(0,0)}),
	Fist("Fist",100,1,1,2,new Coordinate[] {new Coordinate(0,0)}),
	Cross("Cross",100,3,5,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)}),
	Square("Square",100,3,4,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)}),
	Sword("Sword",100,2,1,3, new Coordinate[] {new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)}),
	Beam("Beam",100,3,1,4, new Coordinate[] {new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)});
	
	/**
	 * Represents the type (Ball,Sword...) of the shape
	 */
	private String type;
	/**
	 * Represents the number of health points that the spell will remove
	 */
	private int damage;
	
	/**
	 * Represents the reloading time of the spell in number of turns
	 */
	private int cooldown;
	
	/**
	 * Represents the range of the shape,the number of cells 
	 */
	private int range;
	
	/**
	 * Represents the needed action point  in order to cast a spell,
	 */
	private int spellCost;
	
	/**
	 * It represents the cells that the shape will affect
	 */
	private Coordinate[] effectedCoordinates;
	
	/**
	 * initialize a new shape with a given type,damage,cooldown range spellCost and an array of coordinate
	 * @param theType the type of the Shape
	 * @param theDamage the damage that the shape will inflict
	 * @param theCooldown the cooldown of the shape
	 * @param theRange the range of the shape
	 * @param theSpellCost the cost of the shape in action points
	 * @param theEffectedCoords the coordinate that will be effect by the shape
	 */
	Shape(String theType,int theDamage,int theCooldown,int theRange,int theSpellCost, Coordinate[] theEffectedCoords)
	{
		this.type = theType;
		this.damage = theDamage;
		this.cooldown = theCooldown;
		this.range = theRange;
		this.spellCost = theSpellCost;
		this.effectedCoordinates = theEffectedCoords;
	}

	/*
	 * Getters for the shape
	 */
	public String getType() 
	{
		return type;
	}

	public int getDamage()
	{
		return damage;
	}


	public int getCooldown() 
	{
		return cooldown;
	}


	public int getRange() 
	{
		return range;
	}


	public int getSpellCost()
	{
		return spellCost;
	}

	public Coordinate[] getEffectedCoordinates()
	{
		return effectedCoordinates;
	}
	
	
	/**
	 * Displays the actual state of the shape
	 */
	public String toString()
	{
		return "[type="+this.type+",damage="+this.damage+",cooldown="+this.cooldown+",range="+this.range+",spellCost="+this.spellCost+"]";
	}

	
	
	
}
