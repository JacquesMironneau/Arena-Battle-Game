package fr.iutvalence.projet.battleArenaGame.view;
//TODO
/*
 * Enlever tous les attrributs qui ne sont pas directement liés à la création de fenetre en JFrame
 * -> spellIndex + tous les strings
 * 
 * Déplacer les méthodes lié au Menu + la création de sort dans userViewWindow
 * + ne pas appeler des méthodes ask/display de puis d'autres méthodes ask ou display
 */
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.test.JGameCanvas;

/**
 * PlayerWindow class
 * 
 * This class represents a player with a gui (window with buttons and listeners)
 * @author durantho
 *
 */
public class PlayerWindow extends JFrame implements GameView{
	
	
	private int spellIndex;
	
	
	/**
	 * This pane contains all the container and the visible informations on the screen.
	 */
	private JLayeredPane mainContainer;
	
	/**
	 * This text field is used to get the pageName
	 */
	private JTextField tfPageName;
	
	/**
	 * This text field will contain the IP address of the server
	 */
	private JTextField tfServerIP;
	
	
	/**
	 * This is a Choice that contains all the elements that are available in the game
	 */
	private Choice chElement;
	
	/**
	 * This is a choice that contains all the shapes that are available in the game
	 */
	private Choice chShape;
	
	/**
	 * This represents all the spellPages that is possible to set to a pawn
	 */
	private Choice chSpellPages;
	
	/**
	 * This String is used to store the Element of the first spell of a spell page
	 */
	private String sp1ElCh = null;
	
	/**
	 * This String is used to store the Shape of the first spell of a spell page
	 */
	private String sp1ShCh = null;
	
	/**
	 * This String is used to store the Element of the second spell of a spell page
	 */
	private String sp2ElCh = null;
	
	/**
	 * This is used to store the Shape of the second spell of a spell page
	 */
	private String sp2ShCh = null;
	
	/**
	 * This is used to store the Element of the third spell of a spell page
	 */
	private String sp3ElCh = null;
	
	/**
	 * This is used to store the Shape of the third spell of a spell page
	 */
	private String sp3ShCh = null;
	
	private GameController gameController;
	
	private JGameCanvas gameBoard;
	
	
	/**
	 * Constructor of a new PlayerWindow
	 * It initializes the PlayerWindow with a default title,
	 * a default size,
	 * the default operation when closing the window,
	 * 
	 */
	public PlayerWindow() {
		/*
		 * Super Constructor
		 */
		super();
		
		/*
		 * Window properties
		 * no relative location
		 * Set default close operation
		 * Size by default
		 */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("Projet S2");
		this.setLocationRelativeTo(null);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
		this.setResizable(false);
		
		this.chElement = new Choice();
		this.chShape = new Choice();
		
		this.mainContainer = new JLayeredPane();
		this.setContentPane(this.mainContainer);
		
	}

	public void setGameController(GameController controller) {
		this.gameController = controller;
	}
	public String askPageName() {
		return this.tfPageName.getText();
	}

	public Effect askSpellElement() {
		Effect spellEffect = null;
		for (Effect effect : Effect.values()) {
			if (this.chElement.getSelectedItem() == effect.getEffectName())
				spellEffect = effect;
		}
		return spellEffect;
	}

	public boolean askValidation() {
		//create a new confirm dialog to be sure that the player enter the correct values
		if(JOptionPane.showConfirmDialog(mainContainer,"Are you sure ?","Confirmation",JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION){
			this.displayMenu();
			return true;
		}
		return false;

	}

	public void displayMenu() {
		//disable the visibility of the window to avoid graphical bugs
		this.setVisible(false);
		this.setLocation(0, 0);
		//remove all the content from the content pane
		this.getContentPane().removeAll();
		
		//create a new button that tells to the system to initialize a new game and add it to the content pane
		JButton btnCreateGame = new JButton("CrÃ©er une partie");
		btnCreateGame.setBounds(this.getWidth()/4, this.getHeight()/8, this.getWidth()/4*2, this.getHeight()/8);
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gc that calls displayGameConfig()
				displayGameConfig();
			}
		});
		this.getContentPane().add(btnCreateGame);
		
		//add a new button that tells to the system to join a game and add it the content pane
		JButton btnJoinGame = new JButton("Rejoindre une partie");
		btnJoinGame.setBounds(this.getWidth()/4, this.getHeight()/8*2, this.getWidth()/4*2, this.getHeight()/8);
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gc that calls displayJoinGame();
			}
		});
		this.getContentPane().add(btnJoinGame);
		
		//creates a new button that tells to the system to create a new local game and add it to the content pane
		JButton btnCreateLocalGame = new JButton("CrÃ©er une partie locale");
		btnCreateLocalGame.setBounds(this.getWidth()/4, this.getHeight()/8*3, this.getWidth()/4*2, this.getHeight()/8);
		btnCreateLocalGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//createlocalgame.method
			}
		});
		this.getContentPane().add(btnCreateLocalGame);
		
		//creates a new buttons that will display the spell page creation menu
		JButton btnCreateSpellPage = new JButton("CrÃ©er une page de sorts");
		btnCreateSpellPage.setBounds(this.getWidth()/4,this.getHeight()/8*4, this.getWidth()/4*2, this.getHeight()/8);
		btnCreateSpellPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// initialize the strings that contains the spell details to null to avoid mismatch and problems within the spell creation
				sp1ElCh = null;
				sp1ShCh = null;
				sp2ElCh = null;
				sp2ShCh = null;
				sp3ElCh = null;
				sp3ShCh = null;
				//gc.displayMain();
			}
		});
		this.getContentPane().add(btnCreateSpellPage);
		
		//enable the visibility of the window
		this.setVisible(true);
	}
	
	public void displayGameConfig() {
		//size of the board
		//number of players
		//number of pawns
		this.setVisible(false);
		
		JLabel size = new JLabel("Entrez la taille du plateau de jeu");
		size.setBounds(0,0,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(size);
		
		JTextField sizeField = new JTextField();
		sizeField.setBounds(0,this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(sizeField);
		
		JLabel numberOfPlayer = new JLabel("Entrez le nombre de joueurs");
		numberOfPlayer.setBounds(0,this.getHeight()/20*2,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(numberOfPlayer);
		
		JTextField numberOfPlayerField = new JTextField();
		numberOfPlayerField.setBounds(0,this.getHeight()/20*3,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(numberOfPlayerField);
		
		JLabel numberOfPawns = new JLabel("Veuillez saisir le nombre de pions");
		numberOfPawns.setBounds(0,this.getHeight()/20*4,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(numberOfPawns);
		
		JTextField numberOfPawnsField = new JTextField();
		numberOfPawnsField.setBounds(0, this.getHeight()/20*5, this.getWidth()/3, this.getHeight()/20);
		this.getContentPane().add(numberOfPawnsField);
		
		JButton btnValider = new JButton("valider");
		btnValider.setBounds(this.getWidth()/5*2,this.getHeight()/20*7,this.getWidth()/5,this.getHeight()/20);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbPlayer = Integer.parseInt(numberOfPlayerField.getText());
				int nbPawns = Integer.parseInt(numberOfPawnsField.getText());
				int boardSize = Integer.parseInt(sizeField.getText());
				
				//TODO instantiate a new GameController (game) with those options.
			}
		});
		this.getContentPane().add(btnValider);
		
		this.setVisible(true);
	}


	protected void displayJoinGame() {
		this.setVisible(false);
		this.getContentPane().removeAll();
		
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(this.getWidth()/5*2,this.getHeight()/20*3+20,this.getWidth()/5,this.getHeight()/20);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//displayBoard(new Board(0, 0, 0), 0); //change this
				//ip of the server
			}
		});
		this.getContentPane().add(btnValider);
		btnValider.setEnabled(false);
		
		JLabel lblTitle = new JLabel("Rejoindre une partie");
		lblTitle.setBounds(this.getWidth()/3,0,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(lblTitle);
		
		JLabel lblIndications = new JLabel("Veuillez saisir l'adresse IP du serveur :");
		lblIndications.setBounds(this.getWidth()/3,this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		this.getContentPane().add(lblIndications);
		
		this.tfServerIP = new JTextField();
		this.tfServerIP.setBounds(this.getWidth()/3,this.getHeight()/20*2+10,this.getWidth()/3,this.getHeight()/20);
		this.tfServerIP.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				if(tfServerIP.getCaretPosition() == 0)
					btnValider.setEnabled(false);
				else
					btnValider.setEnabled(true);
			}
		});
		this.getContentPane().add(this.tfServerIP);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(this.getWidth()/5*2,this.getHeight()/20*4+20,this.getWidth()/5,this.getHeight()/20);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gameController that calls displayMenu();
			}
		});
		this.getContentPane().add(btnRetour);
		
		this.setVisible(true);
	}

	/**
	 * protected method that displays the spellPage creation menu
	 */
	public void displaySpellPageCreation() {
		//disable the visibility of the window to avoid visual bugs (ex: no content)
		this.setVisible(false);
		this.setLocation(0, 0);
		this.getContentPane().removeAll();
		
		// creates a new button for the creation of the first spell
		JButton btnSpell1 = new JButton("Spell1");
		btnSpell1.setBounds(0,10,(this.getWidth()/3),(this.getHeight()/3)-10);
		btnSpell1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gameController that calls displayCreateSpell(1);
			}
		});
		this.getContentPane().add(btnSpell1);
		
		// creates a new button for the creation of the second spell
		JButton btnSpell2 = new JButton("Spell2");
		btnSpell2.setBounds((this.getWidth()/3),10,(this.getWidth()/3),(this.getHeight()/3)-10);
		btnSpell2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gameController hat calls displayCreateSpell(2);
			}
		});
		this.getContentPane().add(btnSpell2);
		
		// creates a new button for the creation of the third spell
		JButton btnSpell3 = new JButton("Spell3");
		btnSpell3.setBounds((this.getWidth()/3*2),10,(this.getWidth()/3),(this.getHeight()/3)-10);
		btnSpell3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gameController that calls displayCreateSpell(3);
			}
		});
		this.getContentPane().add(btnSpell3);
		
		//creates a new JLabel to indicate what the following JTextField is use for
		JLabel lblPageName = new JLabel("Enter the name of the page");
		lblPageName.setBounds(0,(this.getHeight()/2)-this.getHeight()/20,this.getWidth()/5,this.getHeight()/20);
		this.getContentPane().add(lblPageName);
		
		//add a new button that will send all the information of the spell page and redirect the player to the main menu
		JButton btnValiderPageCreation = new JButton("Valider");
		btnValiderPageCreation.setBounds(this.getWidth()/3, this.getHeight()/3*2, this.getWidth()/3, this.getHeight()/20);
		//disable the button by default because the player cannot validate the creation without the three spells created, and the page name
		btnValiderPageCreation.setEnabled(false);
		btnValiderPageCreation.addActionListener(new ActionListener() {
			
			/*
			 * if the strings that contains spell details are empty then
			 * 		show a warning pop-up that says to the player he has not created all the spells
			 * else
			 * 		the validation is complete
			 * 		send the name of the page 
			 */
			public void actionPerformed(ActionEvent arg0) {
				if (sp1ElCh == null || sp1ShCh == null || sp2ElCh == null || sp2ShCh == null || sp3ElCh == null || sp3ShCh == null)
					JOptionPane.showMessageDialog(mainContainer, "You have not set all the spells", "Warning", JOptionPane.WARNING_MESSAGE);
				else {
					
					//TODO gerer tout ca pour que ca fasse  des pages qpouihefviofcfjÃ°Ã‚Ã¸ÃŽÃ¸Ã´uw neibzq (
					Spell s1 = new Spell();
					Spell s2 = new Spell();
					Spell s3 = new Spell();
					
//					SpellPage sp = new SpellPage();
					//TODO method from gc that calls askValidation();
					//TODO method from gc that calls askPageName();
				}
			}
		});
		this.getContentPane().add(btnValiderPageCreation);
		
		/* textField with a caretListener that disable the validation button if the carret is placed at index 0 in the text field
		 * it contains the name of the page
		 */
		this.tfPageName = new JTextField();
		this.tfPageName.setBounds(0,this.getHeight()/2,this.getWidth()/2,this.getHeight()/20);
		this.tfPageName.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent arg0) {
				if(tfPageName.getCaretPosition() == 0) 
					btnValiderPageCreation.setEnabled(false);
				else
					btnValiderPageCreation.setEnabled(true);
			}
		});
		this.getContentPane().add(this.tfPageName);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBounds(this.getWidth()/3,this.getHeight()/3*2+this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gc that calls displayMenu();
			}
		});
		this.getContentPane().add(btnRetour);
		
		this.setVisible(true);
	}

	/**
	 * This method displays a menu that allows the player to create a spell
	 * @param spellIndex the spell you want to create/modify
	 */
	public void displayCreateSpell(int spellIndex) {
		//disable the visibility to avoid bugs
		this.setVisible(false);
		this.setLocation(0, 0);
		this.getContentPane().removeAll();
		//create a new button to allow the player to validate the creation of his spell and refresh the string that contains the spell informations
		JButton btnValiderSpellCreation = new JButton("Valider");
		btnValiderSpellCreation.setBounds(this.getWidth()/3, this.getHeight()/3*2, this.getWidth()/3, this.getHeight()/20);
		btnValiderSpellCreation.setEnabled(false);
		btnValiderSpellCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch(spellIndex) {
				case 1:
					sp1ElCh = chElement.getSelectedItem();
					sp1ShCh = chShape.getSelectedItem();
					break;
				case 2:
					sp2ElCh = chElement.getSelectedItem();
					sp2ShCh = chShape.getSelectedItem();
					break;
				case 3:
					sp3ElCh = chElement.getSelectedItem();
					sp3ShCh = chShape.getSelectedItem();
				}
				//askSpellElement();
				//askSpellShape();
				//method from gc that calls displaySpellPageCreation();
			}
		});
		this.getContentPane().add(btnValiderSpellCreation);
		
		this.displayElementChoice();
		
		//set the values that we have stored before to the choice when we want to edit them either the values within the choices are reinitialized
		if(this.sp1ElCh != null && spellIndex == 1)
			this.chElement.select(this.sp1ElCh);
		
		if(this.sp2ElCh != null && spellIndex == 2)
			this.chElement.select(this.sp2ElCh);
		
		if(this.sp3ElCh != null && spellIndex == 3)
			this.chElement.select(this.sp3ElCh);
		
		this.chElement.setBounds(this.getWidth()/3, 10, this.getWidth()/3, this.getHeight()/10);
		
		this.chElement.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chElement.getSelectedItem() == "Choose an element") {
					chShape.setEnabled(false);
				}
				else {
					chShape.setEnabled(true);
				}
					
			}
		});
		
		this.getContentPane().add(this.chElement);
	
		this.chShape.setEnabled(false);
		this.displayShapeChoice();
		
		if(this.sp1ShCh != null && spellIndex == 1)
			this.chShape.select(sp1ShCh);
		if(this.sp2ShCh != null && spellIndex == 2)
			this.chShape.select(sp2ShCh);
		if(this.sp3ShCh != null && spellIndex == 3)
			this.chShape.select(sp3ShCh);
		
		this.chShape.setBounds(this.getWidth()/3, this.getHeight()/10+10,this.getWidth()/3,this.getHeight()/10);
		
		this.chShape.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chShape.getSelectedItem()=="Choose a shape")
					btnValiderSpellCreation.setEnabled(false);
				else if(chShape.getSelectedItem()=="Custom") {
					JOptionPane.showMessageDialog(mainContainer, "This functionnality is under devel");	
				}
				else {
					btnValiderSpellCreation.setEnabled(true);
				}
			}
		});
		this.getContentPane().add(this.chShape);
		
		if (this.chShape.getSelectedItem() != "Choose a shape")
			btnValiderSpellCreation.setEnabled(true);
		else
			btnValiderSpellCreation.setEnabled(false);
		
		this.setVisible(true);
	}

	@Override
	public void displayStatus(StatusMessages error) {
		JOptionPane.showMessageDialog(mainContainer, error.getStatusMessage());
		
	}

	@Override
	public void displaySpellPage(ArrayList<SpellPage> listPages) {
		//version 1
		this.setVisible(false);
		
		/*
		 * TODO need turn order in order to get the correct amount of iterations
		 * Number of iterations / number of pawns need turn order
		 */
		this.displaySelectForThisPawn(null);
		this.chSpellPages.removeAll();
		this.chSpellPages.add("Choose a spell page");
		for(SpellPage sp : listPages) {
			this.chSpellPages.add(sp.getPageName());
		}
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(this.getWidth()/5*2,this.getHeight()-this.getHeight()/20,this.getWidth()/5,this.getHeight()/20);
		btnValider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO method from gameController that calls askPageSelection
			}
		});
		
		
		
		
		
		
		
	}

	@Override
	public void displayChoiceAction() {
		// TODO Auto-generated method stub
		//unused
		
	}

	@Override
	public void displayEnd(String winTeam) {
		JOptionPane.showMessageDialog(mainContainer, "Victoire de l'Ã©quipe "+winTeam.getBytes());
		//displayMenu
		
	}




	@Override
	public void displayMoveDone() {
		JTextArea moveDoneArea = new JTextArea(StatusMessages.MOVE_DONE.getStatusMessage());
		moveDoneArea.setBounds(this.getWidth()-this.getWidth()/5,this.getHeight()-this.getHeight()/20,this.getWidth()/5,this.getHeight()/20);
		
	}

	@Override
	public void displaySpellPageDetail(SpellPage pPage) {
		this.setVisible(false);
		this.setLocation(0, 0);
		this.getContentPane().removeAll();
		
		JLabel lblTitle = new JLabel("Spell page details");
		lblTitle.setBounds(0,0,this.getWidth(),this.getHeight()/20);
		this.getContentPane().add(lblTitle);
		
		JLabel lblName = new JLabel(pPage.getPageName());
		lblName.setBounds(0, this.getHeight()/20, this.getWidth(), this.getHeight()/20);
		this.getContentPane().add(lblName);
		
		JTextArea spell1Details = new JTextArea(pPage.getSpell(0).toString());
		spell1Details.setEditable(false);
		spell1Details.setBounds(0,this.getHeight()/2,this.getWidth()/3,this.getHeight()/2);
		this.getContentPane().add(spell1Details);
		
		JTextArea spell2Details = new JTextArea(pPage.getSpell(1).toString());
		spell2Details.setEditable(false);
		spell2Details.setBounds(this.getWidth()/3, this.getHeight()/2, this.getWidth()/3, this.getWidth()/3);
		this.getContentPane().add(spell2Details);
		
		JTextArea spell3Details = new JTextArea(pPage.getSpell(2).toString());
		spell3Details.setEditable(false);
		spell3Details.setBounds(this.getWidth()/3*2, this.getHeight()/2, this.getWidth()/3, this.getHeight()/2);
		this.getContentPane().add(spell3Details);
		
		this.setVisible(true);
	}

	@Override
	public void diplaySizeError() {
		JOptionPane.showMessageDialog(mainContainer, "the size you entered isn't valid", "Warning", JOptionPane.WARNING_MESSAGE);
		
	}

	@Override
	public void displayNextTurn(int numPlayer) {
		JOptionPane.showMessageDialog(mainContainer, "It's now the turn of the player "+numPlayer, "Tour suivant", JOptionPane.INFORMATION_MESSAGE);
		
	}

	@Override
	public void displaySelectForThisPawn(Pawn thePawn) {
		new JLabel("Veuillez selectionner une page pour le pion nÂ° "+thePawn.getName());
		
	}

	@Override
	public void displayMoveSelection() {
		this.gameBoard.highlight(this.gameBoard.getYIndex(), this.gameBoard.getXIndex());
		
	}




	@Override
	public void displaySpellSelection() {
		this.setVisible(false);
		JButton spell1 = new JButton("Spell 1");
		spell1.setBounds(0, this.getHeight()/3*2, this.getWidth()/6, this.getHeight()/3);
		spell1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//TODO				gameController.actionRequest(currentPlayerIndex, StatusMessages.LAUNCH_SPELL);
				spellIndex = 0;
			}
		});
		this.getContentPane().add(spell1);
		
		JButton spell2 = new JButton("Spell 2");
		spell2.setBounds(this.getWidth()/6,this.getHeight()/3*2,this.getWidth()/6,this.getHeight()/3);
		spell2.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
//TODO				gameController.actionRequest(currentPlayerIndex, StatusMessages.LAUNCH_SPELL);
				spellIndex = 1;
			}
		});
		this.getContentPane().add(spell2);
		
		JButton spell3 = new JButton("Spell 3");
		spell3.setBounds(this.getWidth()/6*2,this.getHeight()/3*2,this.getWidth()/6,this.getHeight()/3);
		spell3.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
//TODO				gameController.actionRequest(currentPlayerIndex, StatusMessages.LAUNCH_SPELL);
				spellIndex = 2;
			}
		});
		this.getContentPane().add(spell3);
		
		this.setVisible(true);
	}

	@Override
	public void displayBoard(Board myBoard, int nbPlayer) {
		this.setVisible(false);
		this.getContentPane().removeAll();
		this.gameBoard = new JGameCanvas(myBoard.getTurnOrder(),myBoard.getBoardSize(),this.getWidth()/3,this.getHeight()/3);
		this.gameBoard.setBounds(0, 0, this.getWidth()/3*2, this.getHeight()/3*2);
		this.getContentPane().add(this.gameBoard);
		this.mainContainer.setLayer(this.gameBoard, this.mainContainer.lowestLayer());
		
		JTextArea infos = new JTextArea();
		infos.setBounds(this.getWidth()/3*2, this.getHeight()/6, this.getWidth()/3, this.getHeight());
		infos.setEditable(false);
		for (Pawn p : myBoard.getTurnOrder()) {
			infos.setText(p.getName()+"\nHP: "+p.getHealthPoints()+" / "+Pawn.DEFAULT_HEALTH_POINTS+"\nPA: "+p.getActionPoints()+" / "+Pawn.DEFAULT_ACTION_POINTS+"\nPM:"+p.getMovePoints()+" / "+Pawn.DEFAULT_MOVE_POINTS+"\n\n"/*+"\n\n"+paramString()+p.getSpellPage().toString()+"\n\n\n"*/);
		}
		
		
		
		this.getContentPane().add(infos);
		this.askActionChoice(0);
		this.setVisible(true);
		
	}


	@Override
	public void askActionChoice(int currentPlayerIndex) {
		this.setVisible(false);
		for(Component comp : this.getContentPane().getComponents()) {
			comp.setEnabled(true);
		}
		JButton endTurn = new JButton("Terminer le tour");
		endTurn.setBounds(this.getWidth()/6*3,this.getHeight()/3*2,this.getWidth()/6,this.getHeight()/3);
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Component comp : mainContainer.getComponents()) {
					comp.setEnabled(false);
				}
				gameController.actionRequest(currentPlayerIndex, StatusMessages.END_TURN);
			}
		});
		
		this.displaySpellSelection();
		this.getContentPane().add(endTurn);

		this.setVisible(true);
	}
	
	@Override
	public void askMove(int currentPlayerIndex) {
		this.gameController.moveRequest(currentPlayerIndex,new Coordinate(this.gameBoard.getXIndex(),this.gameBoard.getYIndex()));
		
	}

	@Override
	public void askSpell(int currentPlayerIndex) {
		this.gameController.spellRequest(currentPlayerIndex, this.spellIndex,new Coordinate(this.gameBoard.getXIndex(),this.gameBoard.getYIndex()));
		
	}

	@Override
	public void askPageSelection(int currentPlayerIndex) {
		if(!(chSpellPages.getSelectedIndex()==0))
			this.gameController.setPageRequest(currentPlayerIndex, chSpellPages.getSelectedIndex()-1);
		
	}

	
	public static void main(String[] args) {
//		Game game = new Game(2,3,9);
		PlayerWindow p = new PlayerWindow();
		p.displayMenu();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.displayMenu();

//		PlayerWindow pw = new PlayerWindow();
//		pw.setGameController(game);
//		pw.displayBoard(game.getBoard(), 2);
//		pw.askActionChoice(0);
//		pw.displayCreateSpell(1);
//		pw.displaySpellSelection();
//		pw.displaySpellPage(null);
		
	}

}