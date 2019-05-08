package fr.iutvalence.projet.battleArenaGame.shape;

import java.io.Serializable;
import fr.iutvalence.projet.battleArenaGame.move.*;

public enum Shape implements Serializable {
	
	Ball("Ball",0,2,5,3,new Coordinate[] {new Coordinate(0,0)}),
	Fist("Fist",0,1,1,2,new Coordinate[] {new Coordinate(0,0)}),
	Cross("Cross",0,3,5,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)}),
	Square("Square",0,3,4,4,new Coordinate[] {new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)}),
	Sword("Sword",0,2,1,3, new Coordinate[] {new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)}),
	Beam("Beam",0,3,1,4, new Coordinate[] {new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)});
	
	private String type;
	private int damage;
	private int cooldown;
	private int range;
	private int spellCost;
	private Coordinate[] effectedCoordinates;
	
	Shape(String type,int damage,int cooldown,int range,int spellCost, Coordinate[] effectedCoords){
		this.type=type;
		this.damage=damage;
		this.cooldown=cooldown;
		this.range=range;
		this.spellCost=spellCost;
		this.effectedCoordinates=effectedCoords;
	}

	public String getType() {
		return type;
	}

	public int getDamage() {
		return damage;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getRange() {
		return range;
	}

	public int getSpellCost() {
		return spellCost;
	}

	public Coordinate[] getEffectedCoordinates() {
		return effectedCoordinates;
	}
	
	public String displayEffectedCoords() {
		int k;
		String effectedCoords = "[ ";
		for(k=0;k<this.effectedCoordinates.length;k++) {
			effectedCoords+= effectedCoordinates[k].toString()+" ";
		}
		effectedCoords+=" ]";
		return effectedCoords;
	}
	
	public String toString() {
		return "Shape [type="+this.type+",damage="+this.damage+",cooldown="+this.cooldown+",range="+this.range+",spellCost="+this.cooldown+"effectedCoordinates="+this.displayEffectedCoords()+"]";
	}

	
	
	
}
