package fr.iutvalence.projet.battleArenaGame.view;


import java.awt.Choice;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

//TODO remove all prints and checks (move it in game algorithm)
public class PlayerConsole implements GameView{

	
	private GameController myController;
	
	public PlayerConsole(GameController controller)
	{
		this.myController = controller;
	}
	
	
	//ASK
	
	
	public void askSpell(int currentPlayerIndex)
	{
		CheapScanner scan = new CheapScanner();
		int spellIndex = scan.getInt();
		int xCoord = -1;
		int yCoord = -1;
		xCoord = (Integer)scan.getInt();
		yCoord = (Integer)scan.getInt();
		this.myController.spellRequest(currentPlayerIndex, spellIndex,new Coordinate(xCoord,yCoord));
		
	}
	
	/**
	 * Create a new Coordinate with 2 int given by the player
	 * Does not check is the position is valid
	 * @return Coordinate
	 */
	public void askMove(int currentPlayerIndex) {
		CheapScanner scan = new CheapScanner();
		
		int xCoord = -1;
		int yCoord = -1;
		xCoord = (Integer)scan.getInt();
		yCoord = (Integer)scan.getInt();
		this.myController.moveRequest(currentPlayerIndex,new Coordinate(xCoord,yCoord));
		
	}
	//TODO see GameView
	@Override
	public void askChoiceMenu() 
	{	
		
	}

	public void askActionChoice(int currentPlayerIndex) 
	{
		CheapScanner scan = new CheapScanner();
		int numChoice = -1;
		numChoice= (Integer)scan.getInt();	 
		StatusMessages myChoice = StatusMessages.SYSTEM_ERROR;
		switch(numChoice)
		{
		case 1:
			myChoice = StatusMessages.MOVE;
			break;
		case 2:
			myChoice = StatusMessages.LAUNCH_SPELL;
			break;
		case 3:
			myChoice = StatusMessages.END_TURN;
			break;
		default:
			myChoice=StatusMessages.WRONG_INDEX;
		}
	this.myController.actionRequest(currentPlayerIndex,myChoice);
	}
	
	public void askPageSelection(int currentPlayerIndex)
	{
		CheapScanner scan = new CheapScanner();
		int pageIndex = scan.getInt();
		this.myController.setPageRequest(currentPlayerIndex,pageIndex);
	}
	
	
	@Override
	public String askPageName() {
		CheapScanner scan = new CheapScanner();
		String myName = scan.getStr();
		return myName;
	}
	
	@Override
	public Effect askSpellElement() 
	{	
		CheapScanner scan = new CheapScanner();
		String elementName;
		elementName = scan.getStr();
		switch(elementName)
		{
		case "Fire":
			return Effect.Fire;
		case "Ice":
			return Effect.Ice;
		case "Stone":
			return Effect.Stone;
		case "Electricity" :
			return Effect.Electricity;
		case "Wind":
			return Effect.Wind;
		case "Darkness":
			return Effect.Darkness;
		default:
			elementName = null;
		} 				
	return null;
	}


	//DISPLAY
	
	public void displaySpellSelection()
	{
		System.out.println("Entrer l'index du sort à lancer,\n puis la ligne de la cible, puis la colonne de la cible");
		
	}
	
	
	/**
	 * Display a list of spell pages with their name and an index
	 */
	@Override
	public void displaySpellPage(ArrayList<SpellPage> listPages) {
		int count=0;
		for(SpellPage myPage: listPages)
		{  
			System.out.println(count+")"+myPage.getPageName());
			count++;
		}
	}


/**
 * Display the main menu
 */
	@Override
	public void displayMenu() {
		System.out.println("----------------Menu-----------------");
		System.out.println("1) Créer une partie");
		System.out.println("2) Rejoindre une partie");
		System.out.println("3) cr�� partie local");
		
	}


	@Override
	public void displayEnd(String winTeam) 
	{
		System.out.println(winTeam);
	}


	

	@Override
	public void displayChoiceAction() {
		System.out.println("1) bouger mon pion\n2) lancer un sort\n3)Fin du tour");
		
	}



	@Override
	public void displayElementChoice() {
		for (Effect i : Effect.values())
		{
			System.out.println(i.getElementName());
		}
		
	}

	@Override
	public void displayShapeChoice() {
		System.out.println("Ball - Fist - Sword - Square - Cross - Beam");
	}



	@Override
	public void displayNextTurn(int numPlayer)
	{
		System.out.println("Tour du joueur" + numPlayer);	
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
		System.out.println("Choissisez l'index du sort a lancer");
	}


	@Override
	public void displayBoard(Board myBoard,int nbPlayer)
	{
		Boolean noPawn = true;
		int maxCharLength = (int)Math.log10(myBoard.getBoardSize())+1;
		int pawnCharLength = (int)Math.log10(myBoard.getNbPawn())+1+(int)Math.log10(nbPlayer)+1+2;
		if(maxCharLength < pawnCharLength)
			maxCharLength = pawnCharLength;
		String str = "";
		//Display the grid
		for(int iShift=0;iShift<(int)Math.log10(myBoard.boardSize)+1;iShift++)
		{
			str += " ";
		}		
			str+= "|0";
			for(int rShift=0;rShift<maxCharLength-1;rShift++)
				str+=" ";
			for(int i=1;i<myBoard.boardSize;i++)
			{
					str+= "|";
					str+= i;
					for(int j=0;j<maxCharLength-((int)Math.log10(i)+1);j++)
					{
						str+=" ";
					}			
			}			
			str+="|";
			str+="\n";
			for(int iShift=0;iShift<(int)Math.log10(myBoard.boardSize)+1;iShift++)
			{
				str += " ";
			}	
			str+=" ";
			for(int lineCount=0;lineCount<myBoard.getBoardSize()*(maxCharLength+1);lineCount++)
				str+="-";
			str+="\n";
				for(int i=0;i<myBoard.boardSize;i++)
				{
					str+= i;
					if(i==0)
					{
						for(int iShift=0;iShift<(int)Math.log10(myBoard.boardSize);iShift++)
						{
							str += " ";
						}
					}
					else
						for(int iShift=0;iShift<(int)Math.log10(myBoard.boardSize)+1-((int)Math.log10(i)+1);iShift++)
						{
							str += " ";
						}
					for(int j=0;j<myBoard.boardSize;j++)
					{
						str += "|";
						noPawn = true;
						for(Pawn p: myBoard.getTurnOrder())
						{
							if(p.getPos().equals(new Coordinate(i,j)))
								{
									str+= p.getName();
									for(int missName=0;missName<maxCharLength-p.getName().length();missName++)
										str+=" ";
									noPawn = false;
								}
						}	
						if(noPawn)
							for(int k=0;k<maxCharLength;k++)
							{
								str+="_";
							}
					}
					str += "|" +"\n";
				}
				//Pawns detail
				str += myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getName() + " : HP:" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getHealthPoints() + "/100 AP:" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getActionPoints() + "/6 MP:"
						+ myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getMovePoints() + "/6\n" 
						+ "Spell 1 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown()
						+ "\nSpell 2 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(1).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(1).getDefaultCooldown()
						+ "\nSpell 3 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(2).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(2).getDefaultCooldown();
				str+= "\nCurrent effects :";
						for(PawnEffect effect :myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getEffect())
							str+= effect.getEffectName();
						str+="\n";
				
				
					for(int teamIndex = 0; teamIndex < nbPlayer; teamIndex++)
					{
						str += "Team" + teamIndex +":\n";
						for(Pawn p1 : myBoard.getTurnOrder())
							if(p1.getTeamId()==teamIndex)
								{
									str += p1.getName()+": HP:"+p1.getHealthPoints()+"/"+Pawn.DEFAULT_HEALTH_POINTS+"   Effects : ";
									for(PawnEffect effect :p1.getEffect())
										str+= effect.getEffectName() +"  ";
									str+="\n";
								}
								
					}
				
				
				System.out.println(str);
	}


		@Override
		public void displayStatus(StatusMessages msg)
		{
			System.out.println(msg.getStatusMessage());
		}


	
	public void diplaySizeError(){
		System.out.println("entrez une taille plus grande");
	}
	

	@Override
	public void displaySelectForThisPawn(Pawn thePawn) {
		System.out.println("choisissez une page pour le pion "+ thePawn.getName());
		
	}
	
	public void displayMoveSelection()
	{
		System.out.println("Entrer la coordonée X puis la coordonée Y (ligne puis colonne)");
	}


	@Override
	public void askChoiceMenu(int currentPlayerIndex) {
		// TODO Auto-generated method stub
		
	}


	
}

	

