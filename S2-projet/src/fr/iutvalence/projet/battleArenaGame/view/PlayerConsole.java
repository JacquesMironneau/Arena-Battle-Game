package fr.iutvalence.projet.battleArenaGame.view;


import java.util.ArrayList;
import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;


public class PlayerConsole implements Player{

	
	public PlayerConsole()
	{
	}
	
	
	@Override
	public void askMove() {
		
		CheapScanner scan = new CheapScanner();
		
		int xCoord = (Integer)scan.getInt();
		System.out.println("entrez la coordoné X de la destination de votre sort");
		
		int yCoord = (Integer)scan.getInt();
		System.out.println("entre la coordonné Y de la destination de votre sort");
		
		Coordinate vDest = new Coordinate(xCoord,yCoord);
		
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		Movement mov = new Movement(Board.getCurrentPawn().getPos(), vDest);
		 // Try the move chosen by the player
		try {
			Board.checkMove(mov);
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void askSpell() {
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		
		this.displaySpellPage();
		CheapScanner scan = new CheapScanner();
		int index = (Integer)scan.getInt();
		System.out.println("entrez l'index du sort a lancer (1 ou 2 ou 3");

		int xCoord = (Integer)scan.getInt();
		System.out.println("entrez la coordoné X de la destination de votre sort");
		
		int yCoord = (Integer)scan.getInt();
		System.out.println("entre la coordonné Y de la destination de votre sort");
		
		Coordinate vDest = new Coordinate(xCoord,yCoord);
		Spell vSpell = Board.getCurrentPawn().getSpellPage().getSpell(index);
		
		
		Movement mov = new Movement(Board.getCurrentPawn().getPos(), vDest);
		 // Try to launch the spell to 
		try {
			Board.checkSpell(vSpell, mov);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/** TODO
	 * Create a spell page, including the creation of his 3 spells and add
	 * it to the player spellPage list
	 * WORK IN PROGRESS
	 * @throws SpellIndexException 
	 */
	public void createSpellPage() throws SpellIndexException
	{
		//Creation of a spellPage
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Entrer le nom de la page de sort");
		String pageName = scan.nextLine();
		SpellPage pageToAdd = new SpellPage(pageName);
		for(int index=0;index<pageToAdd.getSpell().length;index++)
		{
			pageToAdd.setSpell(index, null);	
		}
		
		boolean pageFinished = false;
		
		while(!pageFinished)
		{
			Spell createdSpell = new Spell();
			String elementName;
			String shapeName;
			int spellIndexToCreate;
			do
			{
				System.out.println("Choisir l'index du sort a creer");
				spellIndexToCreate = scan.nextInt();
				if(spellIndexToCreate<1 || spellIndexToCreate > 3)
					spellIndexToCreate = 0;			
			}while(spellIndexToCreate == 0);
			
			do
			{
				System.out.println("Choisiser l'element du sort a creer");
				elementName = scan.next();
				
				switch(elementName)
				{
				case "Fire":
					createdSpell.setSpellEffect(SpellEffect.Fire);
					break;
				case "Ice":
					createdSpell.setSpellEffect(SpellEffect.Ice);
					break;
				case "Stone":
					createdSpell.setSpellEffect(SpellEffect.Stone);
					break;
				case "Electricity" :
					createdSpell.setSpellEffect(SpellEffect.Electricity);
					break;
				case "Wind":
					createdSpell.setSpellEffect(SpellEffect.Wind);
					break;
				case "Darkness":
					createdSpell.setSpellEffect(SpellEffect.Darkness);
					break;
				default:
					elementName = null;				
				}
			}while(elementName==null);
			
			do
			{
				System.out.println("Choisiser la forme du sort a creer");
				shapeName = scan.next();
				switch(shapeName)
				{
				case "fist":
					createdSpell.setShape(Shape.Fist);
					break;
				case "ball":
					createdSpell.setShape(Shape.Ball);
					break;
				case "sword":
					createdSpell.setShape(Shape.Sword);
					break;
				case "special":
					switch(elementName)
					{
					case "Fire": 
						createdSpell.setShape(Shape.Cross);
						break;
					case "Wind":
						createdSpell.setShape(Shape.Cross);
						break;
					case "Ice":
						createdSpell.setShape(Shape.Beam);
						break;
					case "Electricity":
						createdSpell.setShape(Shape.Beam);
						break;
					case "Stone":
						createdSpell.setShape(Shape.Square);
						break;
					case "Darkness":
						createdSpell.setShape(Shape.Square);
						break;
					}
					break;
				default:
					shapeName = null;
				}
			}while(shapeName==null);
			
		pageToAdd.setSpell(spellIndexToCreate-1,createdSpell);
		if(pageToAdd.getSpell(0)!= null && pageToAdd.getSpell(1)!= null && pageToAdd.getSpell(2)!= null )
		{
			System.out.println("Entrer 'oui' pour terminer la creation / Entrer 'non' pour recreer un sort");
			String isFinished = scan.next();
			if(isFinished.equals("oui"))
				pageFinished = true;		
		}

			pageToAdd.setSpell(spellIndexToCreate-1,createdSpell);
			if(pageToAdd.getSpell(0)!= null && pageToAdd.getSpell(1)!= null && pageToAdd.getSpell(2)!= null )
			{
				System.out.println("Entrer 'oui' pour terminer la creation / Entrer 'non' pour recreer un sort");
				String isFinished = scan.nextLine();
				if(isFinished.equals("oui"))
					pageFinished = true;		
			}

		}
		Game.addSpellPage(pageToAdd);
	}
	
	/**
	 * Start the selection of the spell pages for the pawns of local player
	 */
	@Override
	public void selectPageForPawns()
	{
		System.out.println("Select page for pawns");
		Scanner scan = new Scanner(System.in);
		int selectedPageIndex;
		Pawn[] temporaryPawns = new Pawn[3];
		boolean selectionFinished = false;
		//add every local pawns 
		int loopCount = 0;
		for(Pawn p: Board.getTurnOrder())
			if(p.getTeam()==PawnTeam.PAWN_LOCAL)
				{
					temporaryPawns[loopCount]=p;
					loopCount++;
				}
		
		int pawnNumber = 1;
			for(Pawn pPawn: temporaryPawns)
			{
				this.displaySpellPages();
				System.out.println("Choisisez l'index de la page que vous voulez donner au pion numÃ©ro" + pawnNumber );
				selectedPageIndex = scan.nextInt();
				Board.getTurnOrder.get(Board.getTurnOrder().indexOf(pPawn)).setSpellPage(this.localPlayer.getPlayerPage().get(selectedPageIndex));		
				pawnNumber++;
			}
			while(!selectionFinished)
			{
			System.out.println("Taper 'oui' pour terminer la selection / Taper 'non' pour modifier la page d'un pion");
				if(scan.next().equals("oui"))
					selectionFinished = true;
				else
				{
					System.out.println("Choisier le numero du pion a modifier");
					pawnNumber = scan.nextInt();
					this.displaySpellPages();
					System.out.println("Choisisez l'index de la page que vous voulez donner au pion numÃ©ro" + pawnNumber );
					selectedPageIndex = scan.nextInt();
					Board.getTurnOrder().get(Board.getTurnOrder.indexOf(temporaryPawns[pawnNumber])).setSpellPage(this.localPlayer.getPlayerPage().get(selectedPageIndex));
				}
				
				
				}
			if(isServer)
				myServer.SendAll(this.turnOrder);
			else
				myClient.Send(this.turnOrder);
	}


	@Override
	public void askSpell(Coordinate pDest, Spell pSpell) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void askAction() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayMenu() {
		System.out.println("----------------Menu-----------------");
		System.out.println("1) CrÃ©er une page de sort");
		System.out.println("2) CrÃ©er une partie");
		System.out.println("3) Rejoindre une partie");
		
	}


	@Override
	public void displayEnd() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Choices askActionChoice() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Choices askChoiceMenu() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void displayError() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displaySpellPage() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayChoiceMenu() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayChoiceAction() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void askSpellPageCreation() {
		// TODO Auto-generated method stub
		
	}
	
}
