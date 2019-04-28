package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Client;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.network.Server;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

/**
 * Game class stands for the system of the BattleArena Game:
 * It handles the players, the network, the spellpages of the player, the coordinate
 * and manage turns for every player. 
 * Here the game starts and end
 * 
 * @author mironnej
 *
 */

public class Game
{
	/**
	 * Size of the board (length of the square)
	 */
	public final static int BOARD_SIZE = 15;
	
	/**
	 * Used in the network : Numbers of player (excluding the host)
	 */
    public final static int MAXPLAYERS = 1;
    
    /**
     * Used in the network : Message send by a Client to left the application
     */
    public final static String QUIT = "quit";
	
    
    /**
     * Used in the network: Port 
     */
    
    public final static int PORT = 12899;
    
    /**
     * Used in the network: IP address of the server (might be deleted when UDP auto IP will be implemented
     */
    
    public final static String HOST_ADDRESS = "192.168.1.49";
    
    /** 
     * Winning message that will be send to the winner
     */
    public final static String VICTORY = "Victory";
    
    /**
     * Defeat message that will be send to the looser
     */
    
    public final static String DEFEAT = "Defeat";
    
    /**
     * Draw message that will be send to the other player
     */
    public final static String DRAW = "Draw";
    
	/**
	 * These values are the default position of pawns at the start of the game
	 * The first number is the player's number and the second is the pawn's number
	 * TODO: move this in init() method
	 */
	public final static Coordinate BASE_POS_1PAWN1 = new Coordinate(2,0);
	public final static Coordinate BASE_POS_1PAWN2 = new Coordinate(7,1);
	public final static Coordinate BASE_POS_1PAWN3 = new Coordinate(12,0);
	public final static Coordinate BASE_POS_2PAWN1 = new Coordinate(2,14);
	public final static Coordinate BASE_POS_2PAWN2 = new Coordinate(7,13);
	public final static Coordinate BASE_POS_2PAWN3 = new Coordinate(12,14);
	/**
	 * Number of all Pawn in the game
	 */
	public final static int PAWN_NUMBER = 6;
	/**
	 * First player of the game, the one who start in the first turn
	 */
	private Player localPlayer;

	/**
	 * This list represent Pawns currently living and define the turn order
	 */
	private ArrayList<Pawn> turnOrder;
	
	/**
	 * Represent the GUI of the game
	 *TODO might be removed
	 */
	private GraphicUserInterface myGui;

	/**
	 * Represents the network of the game, it enables the players's computers to communicates game Data.
	 */
	private Network myNetwork;
	
	/*
	 * Represent the currentPawn of the game = the one that can be moved or can use spell.
	 */
	private Pawn currentPawn;
	
	/**
	 * If the user chose to join a game, he will be a Client in the network system.
	 */
	private Client myClient;
	
	/**
	 * If the user chose to join a game, he will be represented as the Server in the network system.
	 */
	private Server myServer;
	
	/*
	 * Represents if the user of the system embodies the server or is just a client
	 * Used 
	 */
	private boolean isServer;

	/**
	 * Represents if a user the local player is playing now.
	 * It is used to manage when a player can play.
	 * If it's true the local player can play, else it can't. 
	 */
	private boolean localPlayerTurn;

	/**
	 * True if player decide to stop his turn, false either
	 */
	private boolean endTurn;
	
	
	/**
	 * Constructor for Game
	 * create the game, and call init to initialize the board.
	 */
	public Game()
	{
		this.localPlayer = new Player(this);
		this.turnOrder = new ArrayList<Pawn>();
		this.myNetwork = new Network(this);
		//Server and client are created in play method if the player chose to create a game (create Server) or to join (create Client)
	}
	
	
	/**
	 * The first method call, set up the network
	 * and allow the player to navigate in the menu
	 */
	public void launch()
	{	
		
		Scanner sc = new Scanner(System.in); //TODO replace by java.io stream
		
		System.out.println("----------------Menu-----------------");
		System.out.println("1) Créer une page de sort");
		System.out.println("2) Créer une partie");
		System.out.println("3) Rejoindre une partie");
	
		int result = sc.nextInt();
		/*
		 * For now, this represent the menu of the game
		 */
		switch(result)
		{
		
		case 1:
			this.localPlayerTurn = true;
			init();
			play();
			createSpellPage();
			break;
			
		case 2: // server
			this.isServer = true;
			this.localPlayerTurn = true;
			this.endTurn = false;
			
			myServer = new Server(Game.PORT, myNetwork);
			myServer.init(); // Launch the server
			
			init(); // setup the game, TODO hink about champselect, might move init there
			break;
		
		case 3: // client TODO test send from client to server
			this.isServer = false;
			this.localPlayerTurn = false;
			this.endTurn = false;

			
			myClient = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
			myClient.connect(); // Connect the client to the server
			
			init(); // set up the game TODO: think about champselect, might move init there
			break;
			
		default:
			System.out.println("Please enter a valid choice...");
		}
		
	}
	
	

	/**
	 * Manage method for turns
	 * First the play method check if game is ended: No more pawns in one team player.
	 * It first select the currentPawn
	 * Here we allow player to choose actions for the currentPawn.
	 * There's a loop where the current player can ask for movement, spell casting.
	 * Then the system check if theses move or cast are authorized then it does the job.
	 * When the player ask to end his turn, the system switch to next player with a new currentPawn.
	 * 
	 */
	
	//TODO Manage the two players, the two different ArrayList (Does the pawn of the LocalPlayer are the odd ones ?
	// Manage turn, one player should not play while it's the other turn
	//Might need to send if the player can play or not
	
	public void play()
	{
		while(!endGame()) // replace by boolean / or method to know if game is finished
		{
			if(this.localPlayerTurn)
			{
				//Update effects and PA &PM of pawn
				while(!this.endTurn)
				{
					Scanner sc = new Scanner(System.in); //TODO: java.io instead
					System.out.println("Choississez une action :"); //edit that in a more precise way
					int choice = sc.nextInt();
	
					switch(choice)
					{
					case 1:
						//get distance and spell TODO
						this.localPlayer.askSpell(new Coordinate(3,3), new Spell()); // bug here; a pawn need to be selected
						
						break;
					case 2:
						//TODO get arguments
						this.localPlayer.askMove(null);
	
						break;
					case 3:
						this.localPlayerTurn = false; // this one is set to true in the network class
						this.endTurn = true; // TODO set it to true somewhere
						
						if(this.isServer)
							myServer.SendAll(this.localPlayerTurn);
						else
							myClient.Send(this.localPlayerTurn);
						
						break;
						
					}
				}
				nextPawn();
			}
			this.endTurn = false; //CARE TODO
		}
	}
	
	/**
	 * Init method initialize the board witch the beginning places for every pawn,
	 * Set up the board with pawn initals coordinates (and life and so on)
	 * 
	 */
	private void init()
	{
		// Create the turn order
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, Game.BASE_POS_1PAWN1 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, Game.BASE_POS_2PAWN1 , null));
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, Game.BASE_POS_1PAWN2 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, Game.BASE_POS_2PAWN2 , null));
		
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_LOCAL, Game.BASE_POS_1PAWN3 , null));
		this.turnOrder.add(new Pawn(PawnTeam.PAWN_REMOTE, Game.BASE_POS_2PAWN3 , null));
		
		this.localPlayer.setPawn(this.turnOrder.get(0));
		
	
	}
	
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public boolean checkMove(Movement pMovement)
	{
		//If the pawn has enough move points to move
		if(this.currentPawn.getMovePoints() > pMovement.calculateDistance())
		{
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate() == this.turnOrder.get(indexArrayList).getPos())
					return false;
			}
			
			//Move the current pawn to coordinates
			this.currentPawn.setPos(pMovement.getDestCordinate());

			//Replace the pawn of the turnOrder (so the current one) by the currentPawn with moved coordinates)
			this.turnOrder.set(this.turnOrder.indexOf(this.currentPawn), this.currentPawn);
			
			
			//Send the turn order (need to create myServer and myClient (in Game consctructor and then in play method
			if(this.isServer)
				myServer.SendAll(this.turnOrder);
				
			else
				myClient.Send(this.turnOrder);
				
			//The movement is done
			return true;
		}
		//The movement isn't correct
		return false; 
	}
	
	
	
	/**
	 *  checkSpell method: Check if the chosen spell is valid
	 * A spell is valid if the player have enough action Point, if his spell have a cooldown equals to 0 and if the selected destination outside the board
	
	 * @return  true if the chosen spell by the player is valid and authorized
	 */
	public boolean checkSpell(Spell pSpell, Movement pMovement) throws SpellNotFoundException, SpellIndexException
	{
		//Find which spell is used on the pawn spellPage
		int spellNumber;
		
		if(pSpell.equals(this.currentPawn.getSpellPage().getSpell1()))
			spellNumber = 1;
		
		if(pSpell.equals(this.currentPawn.getSpellPage().getSpell2()))
			spellNumber = 2;
		
		if(pSpell.equals(this.currentPawn.getSpellPage().getSpell3()))
			spellNumber = 3;
		
		else 
		{
			//System.out.println("Error in checkSpell : the spell used is not found in the pawn's spellPage");
			throw new SpellNotFoundException(pSpell);
			/*return false;
			 * Test if this exception does really work
			 */
		}
		
		if(pSpell.getCurrentCooldown() == 0)
		{	
			//The spell is on cooldown
			return false;
		}
		else if(pSpell.getShape().getSpellCost() > this.currentPawn.getActionPoints())
		{
			//The spell cost is bigger than the pawn's action points
			return false;
		}
		else if(pMovement.getDistance() > pSpell.getShape().getRange())
		{
			//The target is too far away
			return false;
		}
		else
		{
			//The spell is sent
			//Remove action points used
			this.currentPawn.setActionPoints(this.currentPawn.getActionPoints() - pSpell.getShape().getSpellCost());
			//Set the cooldown on the spell used
			switch(spellNumber)
			{
			case 1:
				this.currentPawn.getSpellPage().getSpell1().resetCooldown();
				break;
			case 2:
				this.currentPawn.getSpellPage().getSpell2().resetCooldown();
				break;
			case 3:
				this.currentPawn.getSpellPage().getSpell3().resetCooldown();
				break;
			default:
				throw new SpellIndexException(spellNumber);
			}
			//Check on all case affected by the spell shape
			ArrayList<Coordinate> effectedCoordinateList = pSpell.getShape().getEffectedCoordinates();
			for(int effectedIndex=0;effectedIndex <effectedCoordinateList.size();effectedIndex++)
			{
				//If there is a pawn on the affected case
				Coordinate effectedCase = pMovement.getDestCordinate().addCoordinate(effectedCoordinateList.get(effectedIndex));
				if(this.getPawnOnCell(effectedCase)!= null)
				{
					Pawn pawnToAffect = this.getPawnOnCell(effectedCase);
					int indexOfPawnToAffect = this.turnOrder.indexOf(pawnToAffect);
					//Set the new HP on the affected Pawn
					pawnToAffect.setHealthPoints(pawnToAffect.getHealthPoints()-pSpell.getShape().getDamage());
					//Add the effect on the affectPawn
					pawnToAffect.addEffect(new PawnEffect(pSpell.getSpellEffect()));
					this.turnOrder.set(indexOfPawnToAffect, pawnToAffect);
				}
				
			}
			//And send data to the other player, use:  Send(this.turnOrder); (might need a try catch statement)
			
		}
		
		if(this.isServer)
			myServer.SendAll(this.turnOrder);
			
		else
			myClient.Send(this.turnOrder);
		
		return true; // To remove errors due to type returned
	}
	
	
	/**
	 * Getter for LocalPlayerTurn
	 * @return
	 */
	public boolean getLocalPlayerTurn() {
		return localPlayerTurn;
	}

	/**
	 * Setter for localPlayerTurn
	 * @param localPlayerTurn
	 */
	public void setLocalPlayerTurn(boolean localPlayerTurn) {
		this.localPlayerTurn = localPlayerTurn;
	}

	/**
	 * TODO Add theses changes in the Network class
	 * Might Disconnect the user (myClient.disconnect() and the Server ( myServer.disconnectAll())
	 */
	private boolean endGame()
	{
		int alivePawnsPlayerServer = 0, alivePawnsPlayerClient = 0;
		
		if(this.turnOrder.size() <= 3)
		{
			for(Pawn pawnIndex : turnOrder)
			{
				if(pawnIndex.getTeam() == PawnTeam.PAWN_LOCAL)
					alivePawnsPlayerServer++;
				
				if(pawnIndex.getTeam() == PawnTeam.PAWN_REMOTE)
					alivePawnsPlayerClient++;
			}
			
			if(alivePawnsPlayerServer == 0 && alivePawnsPlayerClient == 0)
			{
				System.out.println(Game.DRAW);
				
				if(this.isServer)
					myServer.SendAll(Game.DRAW);
				else
					myClient.Send(Game.DRAW);
			}

			else if(alivePawnsPlayerServer == 0)
			{
				
				/*
				 * Here the server loose
				 */
				
				//If this Game is the one of the server, it send Victory to the client, and display a loosing screen
				//to the current user
				if(this.isServer)
				{
					myServer.SendAll(Game.VICTORY);
					System.out.println("You loose :c ");
				}
				
				//If this Game is the one of the client, it send Defeat to the client, and display a winning screen
				//to the current user
				else
				{
						myClient.Send(Game.DEFEAT);
						System.out.println("You win !");
				}
			}
			
			/*
			 * Here the client loose
			 */
			else if(alivePawnsPlayerClient == 0)
			{
				/*
				 * If this Game is the one of the server, it sends defeat to the client
				 * and display win to the server
				 */
				if(this.isServer)
				{
					myServer.SendAll(Game.DEFEAT);
					System.out.println("You win ");
				}
				/*
				 * If this Game is the one of the client, it sends win to the client
				 * and display loose to the server
				*/
				else
				{
					myClient.Send(Game.VICTORY);
					System.out.println("You loose :c");
				}
			}
			return true;
		}
		//DEBUG TODO remove
		else
			return false;
	}
	
	
	
	/**
	 * This method close the game
	 * Called when a the game is finished, after results
	 */
	private void closeGame()
	{
		System.exit(1); // This stop in a clean way the application
	}
	
	/**
	 * Remove the dead pawns (hp <= 0) from the arrayList every time a new turn begin
	 */
	private void removeDeads()
	{
		for(int pawnIndex = 0; pawnIndex >this.turnOrder.size();pawnIndex++)
		{
			if(this.turnOrder.get(pawnIndex).getHealthPoints()<=0)
			{
				this.turnOrder.remove(pawnIndex);
			}
		}
	}
	

	/**
	 * Change the currentPawn of the player to the next one in the turnOrder array List
	 * If the currentPawn is the last one, change to the first one
	 */
	private void nextPawn() 
	{
		int nextPawnIndex = this.turnOrder.indexOf(currentPawn)+1;
		
		if(nextPawnIndex>PAWN_NUMBER-1)
			this.localPlayer.setPawn(this.turnOrder.get(0));
		else
			this.localPlayer.setPawn(this.turnOrder.get(nextPawnIndex));
	}
	
	
	/**
	 *  Check if a pawn is on coordinates passed in parameters
	 *  If it exists return the pawn
	 *  else returns null
	 */
	private Pawn getPawnOnCell(Coordinate pCoordinate)
	{
		for(int pawnIndex = 0; pawnIndex < this.turnOrder.size();pawnIndex++)
		{
			if(this.turnOrder.get(pawnIndex).getPos()==pCoordinate)
				return this.turnOrder.get(pawnIndex);
		}
		return null;
	}
	
	
	/** TODO
	 * Create a spell page, including the creation of his 3 spells and add
	 * it to the player spellPage list
	 * WORK IN PROGRESS
	 */
	public void createSpellPage()
	{
		//Creation of a spellPage
		//TODO Display spell creation menu
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Entrer le nom de la page de sort");
		String pageName = scan.nextLine();
		
		localPlayer.addSpellPage(new SpellPage(pageName));
		
		boolean pageFinished = false;
		
		//TODO Set true maybe XD
		while(pageFinished == false)
		{
			Spell createdSpell = new Spell();
		}
		
	}

	/*
	 * Edit in turnOrder is mainly done in the network package
	 * (To apply the modifications done by a player to the other
	 */
	/**
	 * Getter for turnOrder
	 * @return the turnOrder
	 */
	public ArrayList<Pawn> getTurnOrder()
	{
		return turnOrder;
	}
	
	/**
	 * Setter for turnOrder
	 * @param pTurnOrder : the new turnOrder to set
	 * TODO: might need to be synchronized
	 */
	public void setTurnOrder(ArrayList<Pawn> pTurnOrder)
	{
		this.turnOrder = pTurnOrder;
	}
	
	
	
}
