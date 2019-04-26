package fr.iutvalence.projet.battleArenaGame.test;

import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.shape.ShapeBeam;
import fr.iutvalence.projet.battleArenaGame.shape.ShapeCross;
import fr.iutvalence.projet.battleArenaGame.shape.ShapeSquare;

/**
 * Test class for shapes
 * @author durantho
 *
 */
public class TestShape {
	
	public static void main(String[] args) {
		
		Shape fist = new Shape("fist");
		Shape ball = new Shape("ball");
		Shape sword = new Shape("sword");
		
		ShapeSquare sqr = new ShapeSquare("square");
		ShapeCross cross = new ShapeCross("cross");
		ShapeBeam beam = new ShapeBeam("beam");
		
		System.out.println(fist.toString());
		System.out.println(ball.toString());
		System.out.println(sword.toString());
		
		System.out.println(sqr.toString());
		System.out.println(cross.toString());
		System.out.println(beam.toString());
	}
}
