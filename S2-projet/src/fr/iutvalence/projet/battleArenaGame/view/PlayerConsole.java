package fr.iutvalence.projet.battleArenaGame.view;


import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
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
		CheapScanner scan = new CheapScanner();
		
		
		System.out.println("entrez la coordon� X de la destination");
		int xCoord = (Integer)scan.getInt();
		
		System.out.println("entre la coordonn� Y de la destination");
		int yCoord = (Integer)scan.getInt();
		Coordinate vDest = new Coordinate(xCoord,yCoord);
		
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		return new Movement(Board.getCurrentPawn().getPos(), vDest);
		 
		
		
	}

	@Override
	public Spell askSpell() {
		//TODO Check if index entered is out of limit
		/*
		 * Create a movement with the coordinates of the currentPawn and the Destination (coordinate) chosen by the player
		 */
		this.displaySpellPageDetail(Board.getCurrentPawn().getSpellPage());
		CheapScanner scan = new CheapScanner();
		
		System.out.println("entrez l'index du sort a lancer (1 ou 2 ou 3");
		int index = (Integer)scan.getInt();
		return Board.getCurrentPawn().getSpellPage().getSpell(index-1);
			
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
			index=(Integer)scan.getInt()-1;
		}while (index<0 || index>Game.getSpellPages().size());
	return Game.getSpellPages().get(index);
	}


	@Override
	public void displaySpellPage() {
		int count=1;
		for(SpellPage myPage: Game.getSpellPages())
		{  
			System.out.println(count+")"+myPage.getPageName());
			count++;
		}
	}



	@Override
	public void displayMenu() {
		System.out.println("----------------Menu-----------------");
		System.out.println("1) Créer une page de sort");
		System.out.println("2) Créer une partie");
		System.out.println("3) Rejoindre une partie");
		System.out.println("4) cr�� partie local");
		
	}


	@Override
	public void displayEnd(EndStatus pStat, TeamId teamId) {
		switch(pStat)
		{
		case VICTORY:
			System.out.println("vous etes le boss " + teamId);
			break;
		case DEFEAT:
			System.out.println("lol t'as loose" + teamId);
			break;
		case DRAW:
			System.out.println("wow c'etait un match d'enfer, hyper close vous etes mort en m�me temps et ca c'est beau bordel");
			break;
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
		System.out.println("Une erreur s'est produite...");
		
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
		case 3:
			return Choices.END_TURN;
		default:
			mychoice=0;
		}
		}while(mychoice==0);
	return null;
	}


	@Override
	public void displayChoiceAction() {
		System.out.println("1) bouger mon pion\n2) lancer un sort\n3)Fin du tour");
		
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
		System.out.println("entrez � quel index de votre sort dans la page de sort entre 1 et 3");
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
		System.out.println("Choisiser la forme du sort a creer");
		choose = scan.getStr();
		switch(choose)
		{
		case "Fist":
			return Shape.Fist;
		case "Ball":
			return Shape.Ball;
		case "Sword":
			return Shape.Sword;
		case "Special":
			switch(eff.getEffectName())
			{
			case "Fire": 
			case "Wind":
				return Shape.Cross;
			case "Ice":
			case "Electricity":
				return Shape.Beam;
			case "Stone":
			case "Darkness":
				return Shape.Square;		
			default:
				
			}
			default:
				choose=null;
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
		System.out.println("il reste "+ pSpell.getCurrentCooldown() +" a attendre");
	}


	@Override
	public void displaySpellOutOfRange(Spell pSpell) {
		System.out.println("la port�e max est "+ pSpell.getShape().getRange());
		
	}


	@Override
	public void displayNotEnoughActionPoints() {
		System.out.println("tu as seulement "+ Board.getCurrentPawn().getActionPoints());
		
	}


	@Override
	public void displaySpellLaunched() {
		System.out.println("le sort a �t� lanc�");
		
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
		System.out.println("le mouvement a bien �t� effectu�");
	}


	@Override
	public void displaySpellPageDetail(SpellPage pPage) {
		for(int spellIndex =0;spellIndex<3;spellIndex++)
		{
			if(pPage.getSpell(spellIndex)!=null)
				System.out.println(spellIndex+1 +" )  "+ pPage.getSpell(spellIndex).toString());
			else
				System.out.println(spellIndex+1 +")\n");
		}
	}


	@Override
	public void displayBoard(Board myBoard)
	{
		String str = "  |  0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 | 13 | 14 | \n"
				+"  |__________________________________________________________________________|\n";
				for(int i=0;i<Game.BOARD_SIZE;i++)
				{
					if(i<10)
						str+= i + " ";
					else
						str+= i;
					for(int j=0;j<Game.BOARD_SIZE;j++)
					{
						str += "|";
						if(myBoard.getPawnOnCell(new Coordinate(i,j))!= null)
							str += myBoard.getPawnOnCell(new Coordinate(i,j)).getId();		
						else
							str+="____";
						
					}
					str += "|" +"\n";
				}
				
				str += Board.getCurrentPawn().getId() + " : HP:" + Board.getCurrentPawn().getHealthPoints() + "/100 AP:" + Board.getCurrentPawn().getActionPoints() + "/6 MP:"
						+ Board.getCurrentPawn().getMovePoints() + "/6\n" 
						+ "Spell 1 :" + Board.getCurrentPawn().getSpellPage().getSpell(0).getCurrentCooldown() + "/" + Board.getCurrentPawn().getSpellPage().getSpell(0).getDefaultCooldown()
						+ "\nSpell 2 :" + Board.getCurrentPawn().getSpellPage().getSpell(1).getCurrentCooldown() + "/" + Board.getCurrentPawn().getSpellPage().getSpell(1).getDefaultCooldown()
						+ "\nSpell 3 :" + Board.getCurrentPawn().getSpellPage().getSpell(2).getCurrentCooldown() + "/" + Board.getCurrentPawn().getSpellPage().getSpell(2).getDefaultCooldown();
				str+= "\nCurrent effects :" + Board.getCurrentPawn().getEffect().toString()+"\n";
				
				str+= "Allied Pawns :\n";
				for(Pawn p :Board.getTurnOrder())
				{
					if(p.getTeam()==Board.getCurrentPawn().getTeam())
						str += p.getId() + ": " + p.getHealthPoints() + "/100 \n";
			 	}
				str+= "Enemy Pawns :\n";
				for(Pawn p :Board.getTurnOrder())
				{
					if(p.getTeam()!=Board.getCurrentPawn().getTeam())
						str += p.getId() + ": " + p.getHealthPoints() + "/100 \n";
			 	}
				
				System.out.println(str);
			}
		public void displayOutOfRange()
		{
			System.out.println("Mouvement en dehors de limits du plateau");
		}
		
	}

	

