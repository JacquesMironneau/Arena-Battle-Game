package fr.iutvalence.projet.battleArenaGame.test;

import java.io.IOException;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.view.CheapScanner;

public class TestCheapScanner {
	public static void main(String[] args) {
//		CheapScanner cs = new CheapScanner();
//		System.out.println("Veuillez saisir un entier : \n");
//		System.out.println(cs.getInt());
//		
//		System.out.println("Veuillez saisir une chaine de caracteres : \n");
//		System.out.println(cs.getStr());
		TestCheapScanner t = new TestCheapScanner();
//		t.askMove();
		t.askSpell();
	}
	
	public void askMove()
	{
		CheapScanner scan = new CheapScanner();
		
		
		System.out.println("entrez la coordon� X de la destination");
		int xCoord = -1;
		try
		{
			xCoord = (Integer)scan.getInt();
		} catch (NumberFormatException | IOException e)
		{}
		
		if(xCoord == -1) askMove();
		System.out.println("entre la coordonn� Y de la destination");
		int yCoord = -1;
		try
		{
			yCoord = (Integer)scan.getInt();
		} catch (NumberFormatException | IOException e)
		{}
		
		if(yCoord == -1) askMove();
		
		System.out.println(new Coordinate(xCoord,yCoord));
	}	
	
	public int askSpell() 
	{
		CheapScanner scan = new CheapScanner();
		int index = -1;
		do {
			System.out.println("entrez l'index du sort a lancer (1 ou 2 ou 3");
			try
			{
				index = (Integer)scan.getInt();
			} catch (NumberFormatException | IOException e)
			{
				e.printStackTrace();
			}
		}while(index < 1 || index > 3);
		
		return index;
			
	}

}
