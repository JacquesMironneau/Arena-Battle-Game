package fr.iutvalence.projet.battleArenaGame.deprecated;

import java.io.Serializable;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;


/**
 * TODO PUT IN THE SHAPE ENUM
 */
/**
 * @author durantho
 * This class extends shape, this shape represents a beam like shape in the game.
 * The coordinate (0,0) represents where the player has clicked to cast his spell.
 * Example of a beam :
 * 
 *           |0  ,0|
 *           |0  ,0|
 *           |0,n+1|
 *           |0,n+i|
 */
public class ShapeBeam extends ShapeSpecial implements Serializable
{
	public final static int UP_DISTANCE = -5;
	public final static int LEFT_DISTANCE = -5;
	public final static int DOWN_DISTANCE = 5;
	public final static int RIGHT_DISTANCE = 5;
	
	private ArrayList<Coordinate> UpBeam;
	private ArrayList<Coordinate> RightBeam;
	private ArrayList<Coordinate> DownBeam;
	private ArrayList<Coordinate> LeftBeam;
	
	/**
	 * serialVersionUID is an hash code, which allow the JVM to check if attributes, names and type are the same for the object
	 */
	private static final long serialVersionUID = -8013775426779999474L;

	/**
	 * Constructor of a beam
	 * Add the coordinates of a beam shape in the effectedCells ArrayList.
	 * And set the correct values for the damage, the cooldown, the range and the spellCost.
	 * @param pType Type of the shape (in this case beam)
	 */
	public ShapeBeam(String pType) {
		super(pType);
		UpBeam = new ArrayList<Coordinate>();
		for (int yValue = 1; yValue != UP_DISTANCE ;yValue --)
			UpBeam.add(new Coordinate(0,yValue));
		
		RightBeam = new ArrayList<Coordinate>();
		for (int xValue = 1; xValue != RIGHT_DISTANCE; xValue ++)
			RightBeam.add(new Coordinate(xValue,0));
		
		DownBeam = new ArrayList<Coordinate>();
		for (int yValue = 1; yValue != DOWN_DISTANCE; yValue ++)
			DownBeam.add(new Coordinate(0,yValue));
		
		LeftBeam = new ArrayList<Coordinate>();
		for (int xValue = 1; xValue != LEFT_DISTANCE; xValue --)
			LeftBeam.add(new Coordinate(xValue,0));	
	
	}
	
	public void setShape()
	{
		this.damage = ShapeSpecial.SB_DAMAGE;
		this.cooldown = ShapeSpecial.SB_COOLDOWN;
		this.range = ShapeSpecial.SB_RANGE;
		this.spellCost = ShapeSpecial.SB_SPELLCOST;
	}
	
	/**
	 * Setter for EffectedCoordinates
	 * @param effectedCoords an arrayList that contains the coordinates that the Beam Shape will effect
	 */
	public void setEffectedCoordinates(ArrayList<Coordinate> effectedCoords)
	{
		this.effectedCoordinates = effectedCoords;
	}
}
