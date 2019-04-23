package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;

/**
 * 
 * @author durantho
 * This class represents the shape that is linked with an effect.
 */
public class ShapeSpecial extends Shape{
	
	/**
	 * Represents the cells that the spell will effect.
	 */
	protected ArrayList<Coordinate> effectedCoordinates;

	public ShapeSpecial(String pName) {
		super(pName);
		this.effectedCoordinates = new ArrayList<Coordinate>();
		
	}
	
	public ArrayList<Coordinate> getAffectedCoordinates()
	{	
		return this.effectedCoordinates;
	}
	
	//@override
	public void setShape()
	{
		this.damage = 0;
		this.cooldown = 0;
		this.range = 0;
		this.spellCost = 0;
	}

}
