package fr.iutvalence.projet.battleArenaGame.view;


import java.io.IOException;
import java.util.ArrayList;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;


public class PlayerConsole implements GameView{

	//TODO remove all prints and checks (move it in game algorithm)
	public PlayerConsole()
	{
	}
	
	
	//ASK
	
	/**
	 * Ask an int to the player
	 */
	@Override
	public int askIndexSelection()
	{
		int index = -1;
		CheapScanner scan = new CheapScanner();	 
		try {
			index = (Integer)scan.getInt();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index;
	}
	
	/**
	 * Create a new Coordinate with 2 int given by the player
	 * Does not check is the position is valid
	 * @return Coordinate
	 */
	public Coordinate askMove() {
		CheapScanner scan = new CheapScanner();
		
		int xCoord = -1;
		int yCoord = -1;
		try {
			xCoord = (Integer)scan.getInt();
			yCoord = (Integer)scan.getInt();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return new Coordinate(xCoord,yCoord);
	}
	
	@Override
	public Choices askChoiceMenu() 
	{	
		CheapScanner scan = new CheapScanner();
		int mychoice = -1;
		
		try {
			mychoice = (Integer)scan.getInt();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (mychoice) 
		{
		case 1:
			return Choices.HOST_GAME;
		case 2:
			return Choices.JOIN_GAME;
		case 3:
			return Choices.LOCAL_GAME;
		default:
			mychoice=0;
		}
	return null;
	}

	public Choices askActionChoice() 
	{
		CheapScanner scan = new CheapScanner();
		int mychoice = -1;
		try
		{
			mychoice= (Integer)scan.getInt();
		} catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
		
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
	return null;
	}
	
	
	@Override
	public String askPageName() {
		CheapScanner scan = new CheapScanner();
		String myName = scan.getStr();
		return myName;
	}
	
	@Override
	public SpellEffect askSpellElement() 
	{	
		CheapScanner scan = new CheapScanner();
		String elementName;
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
	return null;
	}


	//DISPLAY
	
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
		for (SpellEffect i : SpellEffect.values())
		{
			System.out.println(i.getElementName());
		}
		
	}

	@Override
	public void displayShapeChoice() {
		System.out.println("Ball - Fist - Sword - Square - Cross - Beam");
	}

		
	@Override
	public void displaySpellLaunched() {
		System.out.println("Spell cast !");
		
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
				str+= "\nCurrent effects :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getEffect().toString()+"\n";
				
				
					for(int teamIndex = 1; teamIndex <= nbPlayer; teamIndex++)
					{
						str += "Team" + teamIndex +":\n";
						for(Pawn p1 : myBoard.getTurnOrder())
							if(p1.getTeamId()==teamIndex)
								str += p1.getName()+": HP:"+p1.getHealthPoints()+"/"+Pawn.DEFAULT_HEALTH_POINTS+"   Effects :"+p1.getEffect().toString()+"\n";
					}
				
				
				System.out.println(str);
	}


		@Override
		public void displayError(ErrorMessages error)
		{
			System.out.println(error.getErrorMessage());
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


	
}

	

