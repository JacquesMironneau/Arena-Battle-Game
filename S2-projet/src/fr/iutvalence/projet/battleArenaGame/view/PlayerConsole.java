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
	public Coordinate askMove() {
		CheapScanner scan = new CheapScanner();
		
		System.out.println("entrez la coordon� X de la destination");
		int xCoord = (Integer)scan.getInt();
		
		System.out.println("entre la coordonn� Y de la destination");
		int yCoord = (Integer)scan.getInt();
		return new Coordinate(xCoord,yCoord);
	}

	@Override
	/**
	 * Ask to the player which spell he want's to cast
	 * @return the index of the spell chosen
	 */
	public int askSpell() 
	{
		CheapScanner scan = new CheapScanner();
		int index = -1;
		do {
			System.out.println("entrez l'index du sort a lancer (1 ou 2 ou 3");
			index = (Integer)scan.getInt();
		}while(index < 1 || index > 3);
		
		return index;
			
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
			switch(eff.getElementName())
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
		System.out.println("Ball - Fist - Sword - Special");
	}

		
	@Override
	public void displaySpellLaunched() {
		System.out.println("Spell cast !");
		
	}


	@Override
	public void displayNextTurn() {
		System.out.println("on passe au tour suivant");
		
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
		Boolean noPawn = true;
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
						noPawn = true;
						for(Pawn p: myBoard.getTurnOrder())
						{
							if(p.getPos().equals(new Coordinate(i,j)))
								{
									str+= p.getId();
									noPawn = false;
								}
						}	
						if(noPawn)
							str+="____";
						
					}
					str += "|" +"\n";
				}
				
				str += myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getId() + " : HP:" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getHealthPoints() + "/100 AP:" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getActionPoints() + "/6 MP:"
						+ myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getMovePoints() + "/6\n" 
						+ "Spell 1 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown()
						+ "\nSpell 2 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(1).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(1).getDefaultCooldown()
						+ "\nSpell 3 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(2).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(2).getDefaultCooldown();
				str+= "\nCurrent effects :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getEffect().toString()+"\n";
				
				str+= "Allied Pawns :\n";
				for(Pawn p :myBoard.getTurnOrder())
				{
					if(p.getTeam()==myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getTeam())
						str += p.getId() + ": " + p.getHealthPoints() + "/100 \n";
			 	}
				str+= "Enemy Pawns :\n";
				for(Pawn p :myBoard.getTurnOrder())
				{
					if(p.getTeam()!=myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getTeam())
						str += p.getId() + ": " + p.getHealthPoints() + "/100 \n";
			 	}
				
				System.out.println(str);
			}
		public void displayMoveOutOfRange()
		{
			System.out.println("Mouvement en dehors de limits du plateau");
		}


		@Override
		public void displayError(ErrorMessages error)
		{
			System.out.println(error.getErrorMessage());
		}


		@Override
		public int askNbPlayer() {
			int res;
			CheapScanner scan = new CheapScanner();
			do {
				System.out.println("entrez le nombre de joueur");
				res = scan.getInt();
			}while (res<1);
			return res;
		}
		
		public int askNbPawn() {
			int res;
			CheapScanner scan = new CheapScanner();
			do {
				System.out.println("entrez le nombre de pion par joueur");
				res = scan.getInt();
			}while (res < 0);
		return res;
		}
	}

	

