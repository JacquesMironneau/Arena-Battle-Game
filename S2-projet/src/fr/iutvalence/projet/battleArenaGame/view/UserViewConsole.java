package fr.iutvalence.projet.battleArenaGame.view;

import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;

public class UserViewConsole implements UserView{
	
	private UserController controller; 
	
	public UserViewConsole()
	{
	}
	
	public void setController(UserController pController)
	{
		this.controller = pController;
	}
	
	//Display
	
	@Override
	public void display(DisplayMessage msg) 
	{
		System.out.println(msg.getMsg());
	}
	
	
	@Override
	public void displayElementChoice()
	{
		int index = 0;
		System.out.println("Elements :");
		for(Effect eff : Effect.values())
		{
			System.out.println(index + ")" + eff.getElementName());
			index++;
		}
		System.out.println('\n');
	}
		
	@Override
	public void displayShapeChoice()
	{
		int index = 0;
		System.out.println("Shapes :");
		for(Shape shp : this.controller.getGameShapes())
			{
				System.out.println(index+")"+shp.getName());
				index++;
			}
		System.out.println('\n');
	}

	@Override
	public void displayListServer()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayServerLaunched(int ip, int port)
	{
		System.out.println("Le server a bien été lancé.\n Server info :\n ip : "+ ip +"\n Port : "+port);
	}
	
	//Ask
	
	@Override
	public void askLocalConfig()
	{
		int nbPlayer,nbPlayerCons,nbPawn,size;
		CheapScanner scan = new CheapScanner();
		nbPlayer = scan.getInt();
		nbPawn = scan.getInt();
		size = scan.getInt();
		nbPlayerCons = scan.getInt();
		this.controller.localConfigRequest(nbPlayer,nbPawn,size,nbPlayerCons);
	}
		
	@Override
	public void askChoiceMenu() 
	{
		CheapScanner scan = new CheapScanner();
		int index = scan.getInt();
		MenuChoices myChoice = MenuChoices.INVALID_CHOICE;
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
		this.controller.choiceMenuRequest(myChoice);
	}

	@Override
	public void askPageCreation()
	{
		Spell spell1 = askSpellCreation();
		Spell spell2 = askSpellCreation();
		Spell spell3 = askSpellCreation();
		CheapScanner scan = new CheapScanner();
		String name = scan.getStr();
		if(name.equals(""))
			name = "DefaultName";
		this.controller.createSpellPageRequest(name,spell1,spell2,spell3);
		
	}
	
	public Spell askSpellCreation()
	{
		CheapScanner scan = new CheapScanner();
		int effectIndex = scan.getInt();
		if(effectIndex<0 || effectIndex > Effect.values().length-1)
				effectIndex = 0;
		Effect pEffect = Effect.values()[effectIndex];
		int shapeIndex = scan.getInt();
		if(shapeIndex<0 || shapeIndex > this.controller.getGameShapes().size()-1)
			shapeIndex = 0;
		Shape pShape = this.controller.getGameShapes().get(shapeIndex);
		return new Spell(pEffect,pShape);
	}

	@Override
	public void askServerConnection()
	{
		CheapScanner scan = new CheapScanner();
		String ip = scan.getStr();
		this.controller.clientConfigConnection(ip);
	}

	@Override
	public void askServerConfig()
	{
		int nbPlayer,nbPawn,size;
		CheapScanner scan = new CheapScanner();
		nbPlayer = scan.getInt();
		nbPawn = scan.getInt();
		size = scan.getInt();
		this.controller.serverConfigRequest(nbPlayer,nbPawn,size);		
	}

	
	
}

