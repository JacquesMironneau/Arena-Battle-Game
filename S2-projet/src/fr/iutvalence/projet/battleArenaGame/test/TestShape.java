package fr.iutvalence.projet.battleArenaGame.test;

import fr.iutvalence.projet.battleArenaGame.shape.*;

/**
 * Test class for shapes
 * @author durantho
 *
 */
public class TestShape {
	
	public static void main(String[] args) {
		
		Shape myFist = Shape.Fist;
		Shape myBall = Shape.Ball;
		Shape mySword = Shape.Sword;
		Shape mySquare = Shape.Square;
		Shape myCross = Shape.Cross;
		Shape myBeam = Shape.Beam;
		
		
		System.out.println(myFist.toString());
		System.out.println(myBall.toString());
		System.out.println(mySword.toString());
		
		System.out.println(mySquare.toString());
		System.out.println(myCross.toString());
		System.out.println(myBeam.toString());
	}
}
