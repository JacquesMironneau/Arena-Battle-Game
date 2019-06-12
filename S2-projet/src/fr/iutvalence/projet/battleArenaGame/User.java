package fr.iutvalence.projet.battleArenaGame;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.network.ClientTCP;
import fr.iutvalence.projet.battleArenaGame.network.GameClient;
import fr.iutvalence.projet.battleArenaGame.network.GameClientHandler;
import fr.iutvalence.projet.battleArenaGame.network.ServerTCP;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.view.DisplayMessage;
import fr.iutvalence.projet.battleArenaGame.view.GameView;
import fr.iutvalence.projet.battleArenaGame.view.MenuChoices;
import fr.iutvalence.projet.battleArenaGame.view.PlayerConsole;
import fr.iutvalence.projet.battleArenaGame.view.PlayerWindow;
import fr.iutvalence.projet.battleArenaGame.view.UserView;
/**
 *Handle users before they start a game 
 */
public class User implements UserController
{
	
	public final static int PORT = 19999;
	/**
	 * IHM used to interact with the user
	 */
	private UserView userView;
	private ArrayList<Shape> gameShapes;
	private ArrayList<SpellPage> myPages;
	
	
	
	public User(UserView userV)
	{
		this.userView = userV;
		this.userView.setController(this);
		createDefaultShapes();
		this.myPages = new ArrayList<SpellPage>();
		this.myPages.add(new SpellPage("DefaultPage",new Spell(Effect.Fire,this.gameShapes.get(0)),new Spell(Effect.Ice,this.gameShapes.get(1)),new Spell(Effect.Electricity,this.gameShapes.get(2))));
	}
	
	public void launch()
	{
		while(true)
		{
			userView.display(DisplayMessage.MENU);
			userView.askChoiceMenu();	
		}
	}

	@Override
	public void choiceMenuRequest(MenuChoices choice)
	{
		switch(choice)
		{
		case CREATE_SPELL_PAGE:
			this.userView.display(DisplayMessage.PAGE_CREATION);
			this.userView.displaySpellCreation();
			this.userView.askPageCreation();	
			break;
		case JOIN_GAME:
			userView.displayListServer();
			userView.askServerConnection(); // TODO: will call a method from UserController that creates a Socket with the IP + the port 
			
			break;
		case HOST_GAME:
			userView.display(DisplayMessage.SERVER_CONFIG);
			userView.askServerConfig();
			break;
		case LOCAL_GAME:
			userView.display(DisplayMessage.LOCAL_CONFIG);
			userView.askLocalConfig();
			break;
		}
	}
	
	@Override
	public void localConfigRequest(int nbPlayers, int nbPawns, int boardSize, int nbConsoleView)
	{
		if(nbPlayers<1)
			{
				this.userView.display(DisplayMessage.WRONG_PLAYER_NUMBER);
				return;
			}
		if(nbPawns<1)
		{
			this.userView.display(DisplayMessage.WRONG_PAWN_NUMBER);
			return;
		}
		if(boardSize*boardSize<nbPlayers*nbPawns)
		{
			this.userView.display(DisplayMessage.WRONG_BOARD_SIZE);
			boardSize = (int)Math.sqrt(nbPlayers*nbPawns)+1;
		}
		ArrayList<GameView> listPlayer = new ArrayList<GameView>();
		for(int consoleCount = 0; consoleCount < nbConsoleView; consoleCount ++)
			listPlayer.add(new PlayerConsole(this));
		for(int windowCount = 0;windowCount < nbPlayers-nbConsoleView;windowCount++)
			listPlayer.add(new PlayerWindow(this));
		
		new Game(listPlayer,nbPlayers,nbPawns,boardSize).play();		
	}
	
	
	@Override
	public void createDefaultShapes()
	{
		this.gameShapes = new ArrayList<Shape>();
		//Ball
		HashSet<Coordinate> ballShape = new HashSet<Coordinate>();
		ballShape.addAll(Arrays.asList(new Coordinate(0,0)));
		this.gameShapes.add(new Shape("Ball",Shape.DEFAULT_BALL_DAMAGE,Shape.DEFAULT_BALL_COOLDOWN,Shape.DEFAULT_BALL_RANGE,Shape.DEFAULT_BALL_COST,ballShape));
		
		//Fist
		HashSet<Coordinate> fistShape = new HashSet<Coordinate>();
		fistShape.addAll(Arrays.asList(new Coordinate(0,0)));
		this.gameShapes.add(new Shape("Fist",Shape.DEFAULT_FIST_DAMAGE, Shape.DEFAULT_FIST_COOLDOWN,Shape.DEFAULT_FIST_RANGE,Shape.DEFAULT_FIST_COST,fistShape));
		
		//Cross
		HashSet<Coordinate> crossShape = new HashSet<Coordinate>();
		crossShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)));
		this.gameShapes.add(new Shape("Cross",Shape.DEFAULT_CROSS_DAMAGE,Shape.DEFAULT_CROSS_COOLDOWN,Shape.DEFAULT_CROSS_RANGE,Shape.DEFAULT_CROSS_COST,crossShape));
		
		//Square
		HashSet<Coordinate> squareShape = new HashSet<Coordinate>();
		squareShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)));
		this.gameShapes.add(new Shape("Square",Shape.DEFAULT_SQUARE_DAMAGE,Shape.DEFAULT_SQUARE_COOLDOWN,Shape.DEFAULT_SQUARE_RANGE,Shape.DEFAULT_SQUARE_COST,squareShape));
		
		//Sword
		HashSet<Coordinate> swordShape = new HashSet<Coordinate>();
		swordShape.addAll(Arrays.asList(new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)));
		this.gameShapes.add(new Shape("Sword",Shape.DEFAULT_SWORD_DAMAGE,Shape.DEFAULT_SWORD_COOLDOWN,Shape.DEFAULT_SWORD_RANGE,Shape.DEFAULT_SWORD_COST,swordShape));
		
		//Beam
		HashSet<Coordinate> beamShape = new HashSet<Coordinate>();
		beamShape.addAll(Arrays.asList(new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)));
		this.gameShapes.add(new Shape("Beam",Shape.DEFAULT_BEAM_DAMAGE,Shape.DEFAULT_BEAM_COOLDOWN,Shape.DEFAULT_BEAM_RANGE,Shape.DEFAULT_BEAM_COST,beamShape));
	}
	@Override
	public ArrayList<Shape> getGameShapes()
	{
		return this.gameShapes;
	}
	@Override
	public ArrayList<SpellPage> getSpellPages()
	{
		return this.myPages;
	}
	
	@Override
	public void createSpellPageRequest(String name, Spell spell1, Spell spell2, Spell spell3)
	{
		this.myPages.add(new SpellPage(name,spell1,spell2,spell3));
		this.userView.display(DisplayMessage.PAGE_CREATED);
	}
	
	
	@Override
	public void serverConfigRequest(int nbPlayer, int nbPawn, int BoardSize)
	{
		ArrayList<GameView> listPlayer = new ArrayList<GameView>();
		listPlayer.add(new PlayerConsole(this));

		if(nbPlayer<1)
		{
			this.userView.display(DisplayMessage.WRONG_PLAYER_NUMBER);
			return;
		}
		if(nbPawn<1)
		{
			this.userView.display(DisplayMessage.WRONG_PAWN_NUMBER);
			return;
		}
		if(BoardSize*BoardSize<nbPlayer*nbPawn)
		{
			this.userView.display(DisplayMessage.WRONG_BOARD_SIZE);
			BoardSize = (int)Math.sqrt(nbPlayer*nbPawn)+1;
		}
		
		for(Socket s : new ServerTCP(nbPlayer).getSockets())
			listPlayer.add(new GameClientHandler(s));
	
		new Game(listPlayer,nbPlayer,nbPawn,BoardSize).play();
	}
	@Override
	public void clientConfigConnection(String ip)
	{
		//launch the view
		new GameClient(new ClientTCP(ip).getSocket(), new PlayerConsole(this));
		
	}
	@Override
	public void clientReceiveConfigInformation(String msg)
	{
		this.userView.displayHowManyConnectedPeople(msg);
	}
	
}
