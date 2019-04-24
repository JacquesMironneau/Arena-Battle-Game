package fr.iutvalence.projet.battleArenaGame.shape;

public class Test {
	
	public static void main(String[] args) {
		
		Shape shape1 = new Shape("default");
		ShapeSquare sqr = new ShapeSquare("square");
		ShapeCross cross = new ShapeCross("cross");
		ShapeBeam beam = new ShapeBeam("beam");
		
		System.out.println(shape1.toString());
		System.out.println(sqr.toString());
		System.out.println(cross.toString());
		System.out.println(beam.toString());
		
	}
}
