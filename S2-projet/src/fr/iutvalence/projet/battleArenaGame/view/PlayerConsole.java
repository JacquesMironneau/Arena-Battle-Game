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
	@Override
	public void askSpellPageCreation() throws SpellIndexException
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
					this.displaySpellPage();
					System.out.println("Choisisez l'index de la page que vous voulez donner au pion numÃ©ro" + pawnNumber );
					selectedPageIndex = scan.nextInt();
					Board.getTurnOrder().get(Board.getTurnOrder().indexOf(temporaryPawns[pawnNumber])).setSpellPage(Game.getLocalPlayer().getPlayerPage().get(selectedPageIndex));
				}
				
				
				}
			if(isServer)
				myServer.SendAll(this.turnOrder);
			else
				myClient.Send(this.turnOrder);
	}


	@Override
	public void displaySpellPage() {
		for(SpellPage myPage: Game.getSpellPage())
		{
			System.out.println(myPage.getPageName());
		}
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
		System.out.println("4) créé partie local");
		
	}


	@Override
	public void displayEnd(EndStatus pStat) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Choices askChoiceMenu() 
	{	
		CheapScanner scan = new CheapScanner();
		int mychoice = (Integer)scan.getInt();
		System.out.println("faite votre choix");
		switch (mychoice) {
		
		case 1:
			return Choices.CREATE_SPELL_PAGE;
		case 2:
			return Choices.HOST_GAME;
		case 3:
			return Choices.JOIN_GAME;
		case 4:
			return Choices.LOCAL_GAME;
		
		}
	}


	@Override
	public void displayError() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public Choices askActionChoice() 
	{
		CheapScanner scan = new CheapScanner();
		int mychoice= (Integer)scan.getInt();
		System.out.println("faite votre choix");
		
		switch(mychoice)
		{
		case 1:
			return Choices.MOVE;
		case 2:
			return Choices.LAUNCH_SPELL;
		}
		
	}


	@Override
	public void displayChoiceAction() {
		System.out.println("1) bouger mon pion");
		System.out.println("2) lancer un sort");
		
	}


	@Override
	public String askPageName() {
		CheapScanner scan = new CheapScanner();
		String myName = scan.getStr();
		System.out.println("entrez le nom de votre page");
		return myName;
	}


	@Override
	public int askSpellIndex() {
		CheapScanner scan = new CheapScanner();
		System.out.println("entrez à quel index de votre sort dans la page de sort entre 1 et 3");
		int index = (Integer)scan.getInt();
		while (3<index || index<0)
		{
			index = (Integer)scan.getInt();
			System.out.println("j'ai dis entre 1 et 3");
		}
		return index;
	}


	@Override
	public String askSpellElement() 
	{	
		Spell createdSpell = new Spell();
		CheapScanner scan = new CheapScanner();
		
		String elementName;

		do {		
		System.out.println("Choisiser l'element du sort a creer");
		elementName = scan.getStr();
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
	}while(elementName ==null);
	return elementName;
	}


	@Override
	public Shape askSpellShape() {
		CheapScanner scan = new CheapScanner();
		String myShape = scan.getStr();
		
	}


	@Override
	public boolean askValidation() {
		CheapScanner scan = new CheapScanner();
		System.out.println("validez votre saisie?");
		System.out.println("1) oui       2)non");
		int myChoice;
		do
		{
			myChoice = (Integer)scan.getInt();
			switch (myChoice)
			{
			case 1:return true;
			case 2: return false;
			default: myChoice = 0;
			}
		}while(myChoice==0);
	return false;
	}


	@Override
	public void displayElementChoice() {
		for (SpellEffect i : SpellEffect.values())
		{
			System.out.println(i.getElementName());
		}
		
	}


	@Override
	public void displayShapeChoice() {
		for(Shape i :Shape.values())
		{
			System.out.println(i.getType());
		}
		
	}


	@Override
	public void displaySpellInCooldown(Spell pSpell) {
		System.out.println("il reste "+ pSpell.getCurrentCooldown() +"à attendre");
	}


	@Override
	public void displaySpellOutOfRange(Spell pSpell) {
		System.out.println("la portée max est "+ pSpell.getShape().getRange());
		
	}


	@Override
	public void displayNotEnoughActionPoints() {
		
		
	}


	@Override
	public void displaySpellLaunched() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void displayNextTurn() {
		// TODO Auto-generated method stub
		
	}

	
}
