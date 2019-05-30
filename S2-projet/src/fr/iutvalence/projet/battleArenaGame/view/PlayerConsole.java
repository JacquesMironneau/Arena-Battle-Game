package fr.iutvalence.projet.battleArenaGame.view;


import java.util.ArrayList;
import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;


public class PlayerConsole implements Player{

	
	public PlayerConsole()
	{
	}
	
	
	@Override
	public Movement askMove() {
		this.displayBoard();
		CheapScanner scan = new CheapScanner();
		
		int xCoord = (Integer)scan.getInt();
		System.out.println("entrez la coordoné X de la destination");
		
		int yCoord = (Integer)scan.getInt();
		System.out.println("entre la coordonné Y de la destination");
		
		Coordinate vDest = new Coordinate(xCoord,yCoord);
		
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		return new Movement(Board.getCurrentPawn().getPos(), vDest);
		 
		
		
	}

	@Override
	public Spell askSpell() {
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		this.displayBoard();
		this.displaySpellPageDetail(Board.getCurrentPawn().getSpellPage());
		CheapScanner scan = new CheapScanner();
		int index = (Integer)scan.getInt();
		System.out.println("entrez l'index du sort a lancer (1 ou 2 ou 3");

		return Board.getCurrentPawn().getSpellPage().getSpell(index);
			
	}
	
	
	/**
	 * Start the selection of the spell pages for the pawns of local player
	 */
	@Override
	public SpellPage askSpellPageSelection()
	{
		//Afficher toutes les pages et demander d'en choisir une
		this.displaySpellPage();
		CheapScanner scan = new CheapScanner();
		int index;
		do {
			System.out.println("choisissez votre page");
			index=(Integer)scan.getInt();
		}while (index<0 || index>Game.getSpellPages().size());
	return Game.getSpellPages().get(index);
	}


	@Override
	public void displaySpellPage() {
		int count=0;
		for(SpellPage myPage: Game.getSpellPages())
		{  
			System.out.println(count+myPage.getPageName());
			count++;
		}
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
		switch(pStat)
		{
		case VICTORY:
			System.out.println("vous etes le boss");
		case DEFEAT:
			System.out.println("lol t'as loose");
		case DRAW:
			System.out.println("wow c'etait un match d'enfer, hyper close vous etes mort en même temps et ca c'est beau bordel");
		case RUNNING:
			break;
		}
		
	}


	@Override
	public Choices askChoiceMenu() 
	{	
		this.displayMenu();
		CheapScanner scan = new CheapScanner();
		int mychoice;
		
		do {
		
		System.out.println("faite votre choix");
		mychoice = (Integer)scan.getInt();
		switch (mychoice) {
		
		case 1:
			return Choices.CREATE_SPELL_PAGE;
		case 2:
			return Choices.HOST_GAME;
		case 3:
			return Choices.JOIN_GAME;
		case 4:
			return Choices.LOCAL_GAME;
		default:
			mychoice=0;
		}
		}while(mychoice==0);
	return null;
	}


	@Override
	public void displayError() {
		System.out.println("il y a une erreur");
		
	}



	@Override
	public Choices askActionChoice() 
	{
		this.displayChoiceAction();
		CheapScanner scan = new CheapScanner();
		int mychoice;
		do {
		System.out.println("faite votre choix");
		mychoice= (Integer)scan.getInt();
		
		switch(mychoice)
		{
		case 1:
			return Choices.MOVE;
		case 2:
			return Choices.LAUNCH_SPELL;
		default:
			mychoice=0;
		}
		}while(mychoice==0);
	return null;
	}


	@Override
	public void displayChoiceAction() {
		System.out.println("1) bouger mon pion");
		System.out.println("2) lancer un sort");
		
	}


	@Override
	public String askPageName() {
		CheapScanner scan = new CheapScanner();
		System.out.println("entrez le nom de votre page");
		String myName = scan.getStr();
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
	public SpellEffect askSpellElement() 
	{	
		this.displayElementChoice();
		CheapScanner scan = new CheapScanner();
		
		String elementName;

		do {		
		System.out.println("Choisiser l'element du sort a creer");
		elementName = scan.getStr();
		switch(elementName)
		{
		case "Fire":
			return SpellEffect.Fire;
		case "Ice":
			return SpellEffect.Ice;
		case "Stone":
			return SpellEffect.Stone;
		case "Electricity" :
			return SpellEffect.Electricity;
		case "Wind":
			return SpellEffect.Wind;
		case "Darkness":
			return SpellEffect.Darkness;
		default:
			elementName = null;
		} 				
	}while(elementName ==null);
	return null;
	}


	@Override
	public Shape askSpellShape(SpellEffect eff) {
		CheapScanner scan = new CheapScanner();
		this.displayShapeChoice();
		
		String choose;
		do
		{
		choose = scan.getStr();
		switch(choose)
		{
		case "fist":
			return Shape.Fist;
		case "ball":
			return Shape.Ball;
		case "sword":
			return Shape.Sword;
		case "special":
			switch(eff.getEffectName())
			{
			case "Fire": 
				return Shape.Cross;
			case "Wind":
				return Shape.Cross;
			case "Ice":
				return Shape.Beam;
			case "Electricity":
				return Shape.Beam;
			case "Stone":
				return Shape.Square;
			case "Darkness":
				return Shape.Square;
			}
			
		default:
			choose = null;
		}
	}while(choose==null);
	return null;}


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
		System.out.println("tu as seulement "+ Board.getCurrentPawn().getActionPoints());
		
	}


	@Override
	public void displaySpellLaunched() {
		System.out.println("le sort a été lancé");
		
	}


	@Override
	public void displayNextTurn() {
		System.out.println("on passe au tour suivant");
		
	}


	@Override
	public void displayNotEnoughMovePoints() {
		System.out.println("tu n'as pas suffisament de points de mouvement");
		
	}


	@Override
	public void displayMoveDone() {
		System.out.println("le mouvement a bien été effectué");
	}


	@Override
	public void displaySpellPageDetail(SpellPage pPage) {
		System.out.println("Cooldown max "+ pPage.getSpell(0).getDefaultCooldown()+" shape "+pPage.getSpell(0).getShape()+" element "+pPage.getSpell(0).getSpellEffect());
		System.out.println("Cooldown max "+ pPage.getSpell(1).getDefaultCooldown()+" shape "+pPage.getSpell(1).getShape()+" element "+pPage.getSpell(1).getSpellEffect());
		System.out.println("Cooldown max "+ pPage.getSpell(2).getDefaultCooldown()+" shape "+pPage.getSpell(2).getShape()+" element "+pPage.getSpell(2).getSpellEffect());
	}


	@Override
	public void displayBoard() {
		// TODO Auto-generated method stub
		
	}

	
}
