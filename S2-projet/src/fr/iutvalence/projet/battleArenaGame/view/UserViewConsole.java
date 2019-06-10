package fr.iutvalence.projet.battleArenaGame.view;

import java.util.Arrays;
import java.util.HashSet;

import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public class UserViewConsole implements UserView{
	
	private UserController control; 
	private HashSet<Shape> gameShapes;
	private HashSet<SpellPage> mySpellPages;
	
	public UserViewConsole(UserController pUC)
	{
		this.control = pUC;
		createDefaultShapes();
	}
	
	/**
	 * Display the main menu
	 */
		@Override
		public void display(DisplayMessage msg) 
		{
			System.out.println(msg.getMsg());
		}
		
		@Override
		public void displayElementChoice()
		{
			for(Effect eff : Effect.values())
				System.out.println(eff.getElementName());
		}
		
		public void displatShapeChoice()
		{
			for(Shape shp : this.gameShapes)
				System.out.println(shp.getName());
		}

		public void askLocalConfig()
		{
			int nbPlayer,nbPlayerCons,nbPawn,size;
			CheapScanner scan = new CheapScanner();
			nbPlayer = scan.getInt();
			nbPawn = scan.getInt();
			size = scan.getInt();
			nbPlayerCons = scan.getInt();
			this.control.localConfigRequest(nbPlayer,nbPawn,size,nbPlayerCons);
		}
		
	@Override
	public void askChoiceMenu() 
	{
		CheapScanner scan = new CheapScanner();
		int index = scan.getInt();
		MenuChoices myChoice = MenuChoices.INVALIDE_CHOICE;
		switch(index)
		{
		case 1:
			myChoice = MenuChoices.CREATE_SPELL_PAGE;
			break;
		case 2:
			myChoice = MenuChoices.HOST_GAME;
			break;
		case 3:
			myChoice = MenuChoices.JOIN_GAME;
			break;
		case 4:
			myChoice = MenuChoices.LOCAL_GAME;
			break;
		}
		this.control.choiceMenuRequest(myChoice);
	}

	//TODO move this to User class
	@Override
	public void createDefaultShapes()
	{
		//Ball
		HashSet<Coordinate> ballShape = new HashSet<Coordinate>();
		ballShape.addAll(Arrays.asList(new Coordinate(0,0)));
		this.gameShapes.add(new Shape("Ball",10,2,5,3,ballShape));
		//Fist
		HashSet<Coordinate> fistShape = new HashSet<Coordinate>();
		fistShape.addAll(Arrays.asList(new Coordinate(0,0)));
		this.gameShapes.add(new Shape("Fist",15,1,1,2,fistShape));
		//Cross
		HashSet<Coordinate> crossShape = new HashSet<Coordinate>();
		crossShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)));
		this.gameShapes.add(new Shape("Cross",10,3,5,4,crossShape));
		//Square
		HashSet<Coordinate> squareShape = new HashSet<Coordinate>();
		squareShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)));
		this.gameShapes.add(new Shape("Square",10,3,4,4,squareShape));
		//Sword
		HashSet<Coordinate> swordShape = new HashSet<Coordinate>();
		swordShape.addAll(Arrays.asList(new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)));
		this.gameShapes.add(new Shape("Sword",8,2,1,3,swordShape));
		//Beam
		HashSet<Coordinate> beamShape = new HashSet<Coordinate>();
		beamShape.addAll(Arrays.asList(new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)));
		this.gameShapes.add(new Shape("Beam",10,3,1,4,beamShape));
	
	}
}

