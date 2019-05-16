package fr.iutvalence.projet.battleArenaGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import fr.iutvalence.projet.battleArenaGame.exceptions.InvalidMoveException;
import fr.iutvalence.projet.battleArenaGame.exceptions.NotEnoughPointsException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellNotFoundException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellOnCooldownException;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellOutOfRangeException;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.network.Client;
import fr.iutvalence.projet.battleArenaGame.network.Network;
import fr.iutvalence.projet.battleArenaGame.network.Server;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnTeam;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
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
    
    public final static String HOST_ADDRESS = "172.26.105.6";
    
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
     * Message saying that client is ready to start
     */
    public final static String CLIENT_READY = "ClientIsReady";
    
    /**
     * Message saying that server is ready to start
     */
    public final static String SERVER_READY = "ServerIsReady";
    
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
	 * Message send by the client, used to synchronize
	 */
	private String clientMessage;
	
	/**
	 * Message send by the server, used to synchronize 
	 */
	private String serverMessage;
	
	
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
	@SuppressWarnings("resource")
	public void launch()
	{	
		int result;
		Scanner sc;
		
		while(true) 
		{
		sc = new Scanner(System.in); //TODO replace by java.io stream
		
		System.out.println("----------------Menu-----------------");
		System.out.println("1) Créer une page de sort");
		System.out.println("2) Créer une partie");
		System.out.println("3) Rejoindre une partie");
	
		result = sc.nextInt();
		/*
		 * For now, this represent the menu of the game
		 */
		switch(result)
		{
		
		case 1:
			try {
				//createSpellPage();
				createSpellPageForTest(); //TODO Remove this, used for test and uncomment previous line
			} catch (SpellIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case 2: // server
			this.isServer = true;
			this.localPlayerTurn = true;
			this.endTurn = false;
			
			myServer = new Server(Game.PORT, myNetwork);
			myServer.init(); // Launch the server
			
			init(); // setup the game, TODO think about champselect, might move init there
			this.play();
			break;
		
		case 3: // client TODO test send from client to server
			this.isServer = false;
			this.localPlayerTurn = false;
			this.endTurn = false;

			
			myClient = new Client(Game.PORT,Game.HOST_ADDRESS, myNetwork);
			myClient.connect(); // Connect the client to the server
			
			// set up the game TODO: think about champselect, might move init there
			this.play();
			break;
			
		default:
			System.out.println("Please enter a valid choice...");
		}
		
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
		this.synchronizePlayers();
		while(!endGame()) // replace by boolean / or method to know if game is finished
		{
			if(this.localPlayerTurn)
			{
				this.localPlayer.setPawn(this.currentPawn);
				this.currentPawn.setActionPoints(6);
				this.currentPawn.setMovePoints(6);
				this.turnOrder.set(this.turnOrder.indexOf(this.currentPawn), this.currentPawn);
				this.turnOrder.set(this.turnOrder.indexOf(this.localPlayer.getPlayerCurrentPawn()), this.currentPawn);
				this.localPlayer.setPawn(this.currentPawn);
				applyEffect();
				
				//Update effects and PA &PM of pawn
				while(!this.endTurn)
				{
					Scanner sc = new Scanner(System.in); //TODO: java.io instead
					System.out.println("Choisissez une action :"); //edit that in a more precise way
					int choice = sc.nextInt();
	
					switch(choice)
					{
					case 1:
						int choose,X,Y;
						do 
						{
							System.out.println("choisissez un sort");
							choose = sc.nextInt();

						}while(choose >3 || choose<1 );
						
						do
						{
							System.out.println("choisissez un X");
							X = sc.nextInt();	
						}while(X<0 || X>Game.BOARD_SIZE);
						
						do
						{
							System.out.println("choisissez un Y");
							Y = sc.nextInt();	
						}while(Y<0 || Y>Game.BOARD_SIZE);
						
						
						this.localPlayer.askSpell(new Coordinate(X,Y),this.currentPawn.getSpellPage().getSpell(choose)); // bug here; a pawn need to be selected
						
						break;
					case 2:
						//TODO get arguments
						this.localPlayer.askMove(null);
	
						break;
					case 3:
						
						nextPawn();
						
						if(this.currentPawn.getTeam() == PawnTeam.PAWN_REMOTE)
						{
							this.endTurn = true; //  set it to true somewhere
							this.localPlayerTurn = false; // this one is set to true in the network class


							if(this.isServer)
								{
									myServer.SendAll(this.currentPawn);
									myServer.SendAll(this.turnOrder);
									myServer.SendAll(this.localPlayerTurn);
								}
							else
								{
									myClient.Send(this.currentPawn);
									myClient.Send(this.turnOrder);
									myClient.Send(this.localPlayerTurn);
								}
						}
						else
						{
							this.currentPawn.setActionPoints(6);
							this.currentPawn.setMovePoints(6);
							this.turnOrder.set(this.turnOrder.indexOf(currentPawn), currentPawn);
							applyEffect();


						}
						//Si le next pawn est remote
						
						
						//Sinon: appeler les méthodes de remise à max des PA/PM/ Applyeffect
						
						break;
					}
				}
				//nextPawn();
			}
			this.endTurn = false; //CARE TODO
		}
	}
	
	/**
	 * Called in play() before the game loop
	 * It wait until both side have selected their pages for their pawn in order to start the game loop
	 * Make the page selection for the client
	 */
	private void synchronizePlayers()
	{
		System.out.println("Syncrhonize");
		if(!this.isServer)
		{
			while(!SERVER_READY.equals(serverMessage))
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.selectPageForPawns();
			myClient.Send(CLIENT_READY);
		}
		else
		{
			while(!CLIENT_READY.equals(clientMessage))
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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
		
		
		
		
		this.selectPageForPawns();
		this.currentPawn = this.turnOrder.get(0);
		//is a server here
		
		myServer.SendAll(Game.SERVER_READY);
	
	}
	
	
	/**
	 * checkMove method: Check if the movement is valid
	 * A movement is valid if the player have enough movementPoint, and if the selected destination is not taken or outside the board
	 * @param mov 
	 * @param Move pMove: A movement chose by the player
	 * @return true if the chosen move by the player is valid and authorized
	 */
	public void checkMove(Movement pMovement) throws InvalidMoveException
	{
		//If the pawn has enough move points to move
		if(this.currentPawn.getMovePoints() > pMovement.calculateDistance())
		{
			//Check if the coordinates of the pawn are free (in order to move, the case must be free and not occupated by another pawn)
			for(int indexArrayList = 0; indexArrayList < this.turnOrder.size(); indexArrayList++)
			{
				if(pMovement.getDestCordinate() == this.turnOrder.get(indexArrayList).getPos())
					throw new InvalidMoveException("Selected position is occupated");
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
//	TODO remove =>	return true;
		}
		else {
			throw new InvalidMoveException("Not enough move points");
		}
		//The movement isn't correct
//	TODO remove =>	return false; 
	}
	
	/**
	 * apply effect to current pawn at the start of the turn
	 */
	private void applyEffect() 
	{
		int index = this.turnOrder.indexOf(currentPawn);
		
		for(PawnEffect eff : this.currentPawn.getEffect() )
		{
			
			switch(eff.getEffectName())
			{
			case "Ignite":
				this.currentPawn.setHealthPoints(this.currentPawn.getHealthPoints()-5);
				break;
			case "Slow":
				this.currentPawn.setMovePoints(this.currentPawn.getMovePoints()-2);
				break;
			case "Silence":
				this.currentPawn.setActionPoints(this.currentPawn.getActionPoints()-2);
				break;
			case "stun":
				this.currentPawn.setMovePoints(this.currentPawn.getMovePoints()-1);
				this.currentPawn.setActionPoints(this.currentPawn.getActionPoints()-1);
				break;
			case "Crit":
				//TODO set effect
				break;
			case "weakness":
				//TODO set effect
				break;
			
			}
		
		}
		
		this.currentPawn.updateEffect();
		this.turnOrder.set(index, currentPawn);
		//replace current pawn by the right one in turnorder
	}
	
	
	/**
	 *  checkSpell method: Check if the chosen spell is valid
	 * A spell is valid if the player have enough action Point, if his spell have a cooldown equals to 0 and if the selected destination outside the board
	
	 * @return  true if the chosen spell by the player is valid and authorized
	 */
	public void checkSpell(Spell pSpell, Movement pMovement) throws SpellNotFoundException, SpellIndexException, NotEnoughPointsException, SpellOutOfRangeException, SpellOnCooldownException
	{
		
		
		if(pSpell.getCurrentCooldown() == 0)
		{	
			//The spell is on cooldown
			throw new SpellOnCooldownException();
		}
		else if(pSpell.getShape().getSpellCost() > this.currentPawn.getActionPoints())
		{
			//The spell cost is bigger than the pawn's action points
			throw new NotEnoughPointsException();
		}
		else if(pMovement.getDistance() > pSpell.getShape().getRange())
		{
			//The target is too far away
			throw new SpellOutOfRangeException();
		}
		else
		{
			//The spell is sent
			//Remove action points used
			this.currentPawn.setActionPoints(this.currentPawn.getActionPoints() - pSpell.getShape().getSpellCost());
			//Set the cooldown on the spell used
			//TODO Exception if spell not found next TODO (review or check)
			pSpell.resetCooldown();
			int spellIndexInPage = -1;
			for(int index=0;index < 3;index++)
			{
				if(pSpell.equals(this.currentPawn.getSpellPage().getSpell(index)))
					spellIndexInPage = index;	
				//TODO review or check
				else
					throw new SpellNotFoundException(pSpell);
			}
			
			this.turnOrder.get(this.turnOrder.indexOf(currentPawn)).getSpellPage().getSpell(spellIndexInPage).resetCooldown();
			//Check on all case affected by the spell shape
			ArrayList<Coordinate> effectedCoordinateList = new ArrayList<Coordinate>(Arrays.asList(pSpell.getShape().getEffectedCoordinates()));
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
		
		if(nextPawnIndex==turnOrder.size())
			{
				this.localPlayer.setPawn(this.turnOrder.get(0));
				this.currentPawn = this.turnOrder.get(0);

			}
		else
			{
				this.localPlayer.setPawn(this.turnOrder.get(nextPawnIndex));
				this.currentPawn = this.turnOrder.get(nextPawnIndex);
			}
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
	 * @throws SpellIndexException 
	 */
	public void createSpellPage() throws SpellIndexException
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
		localPlayer.addSpellPage(pageToAdd);
	}
	
	
	/**
	 * Start the selection of the spell pages for the pawns of local player
	 */
	private void selectPageForPawns()
	{
		System.out.println("Select page for pawns");
		Scanner scan = new Scanner(System.in);
		int selectedPageIndex;
		Pawn[] temporaryPawns = new Pawn[3];
		boolean selectionFinished = false;
		//add every local pawns 
		int loopCount = 0;
		for(Pawn p: turnOrder)
			if(p.getTeam()==PawnTeam.PAWN_LOCAL)
				{
					temporaryPawns[loopCount]=p;
					loopCount++;
				}
		
		int pawnNumber = 1;
			for(Pawn pPawn: temporaryPawns)
			{
				displaySpellPages();
				System.out.println("Choisisez l'index de la page que vous voulez donner au pion numéro" + pawnNumber );
				selectedPageIndex = scan.nextInt();
				this.turnOrder.get(this.turnOrder.indexOf(pPawn)).setSpellPage(this.localPlayer.getPlayerPage().get(selectedPageIndex));		
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
					displaySpellPages();
					System.out.println("Choisisez l'index de la page que vous voulez donner au pion numéro" + pawnNumber );
					selectedPageIndex = scan.nextInt();
					this.turnOrder.get(this.turnOrder.indexOf(temporaryPawns[pawnNumber])).setSpellPage(this.localPlayer.getPlayerPage().get(selectedPageIndex));
				}
				
				
				}
			if(isServer)
				myServer.SendAll(this.turnOrder);
			else
				myClient.Send(this.turnOrder);
	}
	
	/**
	 * Display all pages of the player with an index
	 */
	private void displaySpellPages()
	{
		for(int pageIndex=0;pageIndex < this.localPlayer.getPlayerPage().size();pageIndex++)
		{
			System.out.println(pageIndex + ":" + this.localPlayer.getPlayerPage().get(pageIndex));
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
	
	public void setCurrentPawn(Pawn thePawn)
	{
		this.currentPawn = thePawn;
	}
	
	/**
	 * Setter for ServerMessage
	 * @param pMessage : message received
	 */
	public void setServerMessage(String pMessage)
	{
		this.serverMessage = pMessage;
	}
	
	/**
	 * Getter for serverMessage
	 * @return message corresponding to the server status
	 */
	public String getServerMessage()
	{
		return this.serverMessage;
	}
	
	/**
	 * Getter for clientMessage
	 * @param pMessage : message received
	 */
	public void setClientMessage(String pMessage)
	{
		this.clientMessage = pMessage;
	}
	
	/**
	 * Getter for clientMessage
	 * @return message corresponding to client status
	 */
	public String getClientMessage()
	{
		return this.clientMessage;
	}

/**
 * Used for test
 * Create a spell page with 3 spells
 * @throws SpellIndexException 
 *
 */
	//TODO Remove this method, used for test
public void createSpellPageForTest() throws SpellIndexException
{
	SpellPage p1 = new SpellPage("page1");
	Spell s1 = new Spell();
	Spell s2 = new Spell();
	Spell s3 = new Spell();
	s1.setShape(Shape.Ball);
	s2.setShape(Shape.Fist);
	s3.setShape(Shape.Sword);
	s1.setSpellEffect(SpellEffect.Fire);
	s2.setSpellEffect(SpellEffect.Ice);
	s3.setSpellEffect(SpellEffect.Electricity);
	
	p1.setSpell(0,s1);
	p1.setSpell(1,s2);
	p1.setSpell(2,s3);
	
	
	this.localPlayer.addSpellPage(p1);
}

}
