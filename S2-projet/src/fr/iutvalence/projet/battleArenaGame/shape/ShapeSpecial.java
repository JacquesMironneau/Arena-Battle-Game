package fr.iutvalence.projet.battleArenaGame.shape;

import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;

/**
 * 
 * @author durantho
 * This class represents the shape that is linked with an effect.
 */
public class ShapeSpecial extends Shape{
	
	/**
	 * Represents the cells that the spell will effect, it contains all the coordinates that this shape will effect
	 */
	protected ArrayList<Coordinate> effectedCoordinates;

	
	/**
	 * Constructor of a shape special,
	 * it initialize the arrayList that contains all the coordinates that the shape will effect.
	 * @param pName the name of the shape.
	 */
	public ShapeSpecial(String pName) 
	{
		super(pName);
		this.effectedCoordinates = new ArrayList<Coordinate>();	
	}
	
	/**
	 * 
	 * @return the coordinates that the shape will effect.
	 */
	public ArrayList<Coordinate> getAffectedCoordinates()
	{
		return this.effectedCoordinates;
	}

//toString	
	@Override
	public String toString() {
		return "ShapeSpecial [effectedCoordinates=" + effectedCoordinates + ", name=" + name + ", damage=" + damage
				+ ", cooldown=" + cooldown + ", range=" + range + ", spellCost=" + spellCost + "]";
	}
	
	

}
