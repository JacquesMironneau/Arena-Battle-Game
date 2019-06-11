package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;
import fr.iutvalence.projet.battleArenaGame.test.JGameCanvas;

public class UserViewWindowGraphics extends JFrame implements UserView {
	//TODO removes attributes, complete auto implemented methods
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
	 * This String is used to store the Element Name of the first spell of a spell page
	 */
	private String sp1ElCh = null;
	
	/**
	 * This String is used to store the Shape Name of the first spell of a spell page
	 */
	private String sp1ShCh = null;
	
	/**
	 * This String is used to store the Element Name of the second spell of a spell page
	 */
	private String sp2ElCh = null;
	
	/**
	 * This is used to store the Shape Name of the second spell of a spell page
	 */
	private String sp2ShCh = null;
	
	/**
	 * This is used to store the Element Name of the third spell of a spell page
	 */
	private String sp3ElCh = null;
	
	/**
	 * This is used to store the Shape Name of the third spell of a spell page
	 */
	private String sp3ShCh = null;
	
	private boolean pause;
	
	private Spell sp1;
	private Spell sp2;
	private Spell sp3;
	
	private UserController controller;
	
	private JGameCanvas gameBoard;
	
	private HashSet<Shape> gameShapes;
	private HashSet<SpellPage> mySpellPages;
	private int spellIndex;
	
	public UserViewWindowGraphics() {
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
		this.mainContainer.setBackground(Color.white);
		this.setContentPane(this.mainContainer);
	}
	
	public void setGameController(UserController pController) {
		this.controller = pController;
	}
	
	public String askPageName() {
		return this.tfPageName.getText();
	}
	
	
	@Override
	public void display(DisplayMessage msg) {
		
		JLayeredPane MainMenu = new JLayeredPane();
		JButton btnCreateGame = new JButton("Créer une partie");
		btnCreateGame.setBackground(Color.white);
		btnCreateGame.setBounds(this.getWidth()/4, this.getHeight()/8, this.getWidth()/4*2, this.getHeight()/8);
		MainMenu.add(btnCreateGame);
		
		//add a new button that tells to the system to join a game and add it the content pane
		JButton btnJoinGame = new JButton("Rejoindre une partie");
		btnJoinGame.setBackground(Color.white);
		btnJoinGame.setBounds(this.getWidth()/4, this.getHeight()/8*2, this.getWidth()/4*2, this.getHeight()/8);
		MainMenu.add(btnJoinGame);
		
		//creates a new button that tells to the system to create a new local game and add it to the content pane
		JButton btnCreateLocalGame = new JButton("Créer une partie locale");
		btnCreateLocalGame.setBackground(Color.white);
		btnCreateLocalGame.setBounds(this.getWidth()/4, this.getHeight()/8*3, this.getWidth()/4*2, this.getHeight()/8);
		MainMenu.add(btnCreateLocalGame);
		
		//creates a new buttons that will display the spell page creation menu
		JButton btnCreateSpellPage = new JButton("Créer une page de sorts");
		btnCreateSpellPage.setBackground(Color.white);
		btnCreateSpellPage.setBounds(this.getWidth()/4,this.getHeight()/8*4, this.getWidth()/4*2, this.getHeight()/8);
		MainMenu.add(btnCreateSpellPage);
		
		
		
		JLayeredPane SpellPageCreationMenu = new JLayeredPane();
		JButton btnSpell1 = new JButton("Spell1");
		btnSpell1.setBackground(Color.white);
		btnSpell1.setBounds(0,10,(this.getWidth()/3),(this.getHeight()/3)-10);
		SpellPageCreationMenu.add(btnSpell1);
		
		// creates a new button for the creation of the second spell
		JButton btnSpell2 = new JButton("Spell2");
		btnSpell2.setBackground(Color.white);
		btnSpell2.setBounds((this.getWidth()/3),10,(this.getWidth()/3),(this.getHeight()/3)-10);
		SpellPageCreationMenu.add(btnSpell2);
		
		// creates a new button for the creation of the third spell
		JButton btnSpell3 = new JButton("Spell3");
		btnSpell3.setBackground(Color.white);
		btnSpell3.setBounds((this.getWidth()/3*2),10,(this.getWidth()/3),(this.getHeight()/3)-10);
		SpellPageCreationMenu.add(btnSpell3);
		
		//creates a new JLabel to indicate what the following JTextField is use for
		JLabel lblPageName = new JLabel("Enter the name of the page");
		lblPageName.setBounds(0,(this.getHeight()/2)-this.getHeight()/20,this.getWidth()/5,this.getHeight()/20);
		SpellPageCreationMenu.add(lblPageName);
		
		//add a new button that will send all the information of the spell page and redirect the player to the main menu
		JButton btnValiderPageCreation = new JButton("Valider");
		btnValiderPageCreation.setBackground(Color.white);
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
					askPageName();
				}
			}
		});
		SpellPageCreationMenu.add(btnValiderPageCreation);
		
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
		SpellPageCreationMenu.add(this.tfPageName);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.setBackground(Color.white);
		btnRetour.setBounds(this.getWidth()/3,this.getHeight()/3*2+this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				display(DisplayMessage.MENU);
			}
		});
		SpellPageCreationMenu.add(btnRetour);
		
		
		
		JLayeredPane LocalConfigMenu = new JLayeredPane();
		JLabel size = new JLabel("Entrez la taille du plateau de jeu");
		size.setBounds(this.getWidth()/3,0,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(size);
		
		JTextField sizeField = new JTextField();
		sizeField.setBounds(this.getWidth()/3,this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(sizeField);
		
		JLabel numberOfPlayer = new JLabel("Entrez le nombre de joueurs");
		numberOfPlayer.setBounds(this.getWidth()/3,this.getHeight()/20*2,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(numberOfPlayer);
		
		JTextField numberOfPlayerField = new JTextField();
		numberOfPlayerField.setBounds(this.getWidth()/3,this.getHeight()/20*3,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(numberOfPlayerField);
		
		JLabel numberOfPlayerCons = new JLabel("Entrez le nombre de joueurs console");
		numberOfPlayerCons.setBounds(this.getWidth()/3,this.getHeight()/20*4,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(numberOfPlayerCons);
		
		JTextField numberOfPlayerConsField = new JTextField();
		numberOfPlayerConsField.setBounds(this.getWidth()/3,this.getHeight()/20*5,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(numberOfPlayerConsField);
		
		JLabel numberOfPawns = new JLabel("Veuillez saisir le nombre de pions");
		numberOfPawns.setBounds(this.getWidth()/3,this.getHeight()/20*6,this.getWidth()/3,this.getHeight()/20);
		LocalConfigMenu.add(numberOfPawns);
		
		JTextField numberOfPawnsField = new JTextField();
		numberOfPawnsField.setBounds(this.getWidth()/3, this.getHeight()/20*7, this.getWidth()/3, this.getHeight()/20);
		LocalConfigMenu.add(numberOfPawnsField);
		
		JButton btnValider = new JButton("valider");
		btnValider.setBackground(Color.white);
		btnValider.setBounds(this.getWidth()/5*2,this.getHeight()/20*9,this.getWidth()/5,this.getHeight()/20);
		LocalConfigMenu.add(btnValider);
		
		
		
		JLayeredPane ServerConfigMenu = new JLayeredPane();
		JLabel ssize = new JLabel("Entrez la taille du plateau de jeu");
		ssize.setBounds(0,0,this.getWidth()/3,this.getHeight()/20);
		ServerConfigMenu.add(ssize);
		
		JTextField ssizeField = new JTextField();
		ssizeField.setBounds(0,this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		ServerConfigMenu.add(ssizeField);
		
		JLabel snumberOfPlayer = new JLabel("Entrez le nombre de joueurs");
		snumberOfPlayer.setBounds(0,this.getHeight()/20*2,this.getWidth()/3,this.getHeight()/20);
		ServerConfigMenu.add(snumberOfPlayer);
		
		JTextField snumberOfPlayerField = new JTextField();
		snumberOfPlayerField.setBounds(0,this.getHeight()/20*3,this.getWidth()/3,this.getHeight()/20);
		ServerConfigMenu.add(snumberOfPlayerField);
		
		JLabel snumberOfPawns = new JLabel("Veuillez saisir le nombre de pions");
		numberOfPawns.setBounds(0,this.getHeight()/20*4,this.getWidth()/3,this.getHeight()/20);
		ServerConfigMenu.add(numberOfPawns);
		
		JTextField snumberOfPawnsField = new JTextField();
		snumberOfPawnsField.setBounds(0, this.getHeight()/20*5, this.getWidth()/3, this.getHeight()/20);
		ServerConfigMenu.add(snumberOfPawnsField);
		
		JButton sbtnValider = new JButton("valider");
		sbtnValider.setBackground(Color.white);
		sbtnValider.setBounds(0,this.getHeight()/20*7,this.getWidth()/5,this.getHeight()/20);
		sbtnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbPlayer = Integer.parseInt(numberOfPlayerField.getText());
				int nbPawns = Integer.parseInt(numberOfPawnsField.getText());
				int boardSize = Integer.parseInt(sizeField.getText());
				
				controller.serverConfigRequest(nbPlayer, nbPawns, boardSize);
			}
		});
		ServerConfigMenu.add(sbtnValider);
		
		
		
		
		
		
		if (msg == DisplayMessage.MENU) {
			this.setVisible(false);
			this.mainContainer = MainMenu;
			this.setContentPane(this.mainContainer);
			this.setVisible(true);
		}
		if(msg == DisplayMessage.PAGE_CREATION) {
			this.setVisible(false);
			this.mainContainer=SpellPageCreationMenu;
			this.setContentPane(this.mainContainer);
			this.setVisible(true);
		}
		if(msg == DisplayMessage.LOCAL_CONFIG) {
			this.setVisible(false);
			this.mainContainer=LocalConfigMenu;
			this.setContentPane(this.mainContainer);
			this.setVisible(true);
		}
		
		if(msg==DisplayMessage.SERVER_CONFIG) {
			this.setVisible(false);
			this.mainContainer=ServerConfigMenu;
			this.setContentPane(this.mainContainer);
			this.setVisible(true);
		}
		
	}

	@Override
	public void askChoiceMenu() {
		
		this.pause = true;
		
		JButton btnCreateGame = (JButton) this.mainContainer.getComponent(0);
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.choiceMenuRequest(MenuChoices.HOST_GAME);
				System.out.println("create game");
				pause = false;
			}
		});
		JButton btnJoinGame = (JButton) this.mainContainer.getComponent(1);
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.choiceMenuRequest(MenuChoices.JOIN_GAME);
				System.out.println("join game");
				pause = false;
			}
		});
		JButton btnLocalGame = (JButton) this.mainContainer.getComponent(2);
		btnLocalGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.choiceMenuRequest(MenuChoices.LOCAL_GAME);
				System.out.println("localgame");
				pause = false;
			}
		});
		JButton btnCreateSpellPage = (JButton) this.mainContainer.getComponent(3);
		btnCreateSpellPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
				System.out.println("spell page");
				pause = false;
			}
		});
		
		while(pause);
	}
	
	public void displayJoinGame() {
		JLayeredPane JoinGameMenu = new JLayeredPane();
		JButton jbtnValider = new JButton("Valider");
		jbtnValider.setBackground(Color.white);
		jbtnValider.setBounds(this.getWidth()/5*2,this.getHeight()/20*3+20,this.getWidth()/5,this.getHeight()/20);
		JoinGameMenu.add(jbtnValider);
		jbtnValider.setEnabled(false);
		
		JLabel lblTitle = new JLabel("Rejoindre une partie");
		lblTitle.setBounds(this.getWidth()/3,0,this.getWidth()/3,this.getHeight()/20);
		JoinGameMenu.add(lblTitle);
		
		JLabel lblIndications = new JLabel("Veuillez saisir l'adresse IP du serveur :");
		lblIndications.setBounds(this.getWidth()/3,this.getHeight()/20,this.getWidth()/3,this.getHeight()/20);
		JoinGameMenu.add(lblIndications);
		
		this.tfServerIP = new JTextField();
		this.tfServerIP.setBounds(this.getWidth()/3,this.getHeight()/20*2+10,this.getWidth()/3,this.getHeight()/20);
		this.tfServerIP.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				if(tfServerIP.getCaretPosition() == 0)
					jbtnValider.setEnabled(false);
				else
					jbtnValider.setEnabled(true);
			}
		});
		JoinGameMenu.add(this.tfServerIP);
		
		JButton jbtnRetour = new JButton("Retour");
		jbtnRetour.setBackground(Color.white);
		jbtnRetour.setBounds(this.getWidth()/5*2,this.getHeight()/20*4+20,this.getWidth()/5,this.getHeight()/20);
		jbtnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				display(DisplayMessage.MENU);
			}
		});
		JoinGameMenu.add(jbtnRetour);
		
		this.setVisible(false);
		this.mainContainer=JoinGameMenu;
		this.setContentPane(this.mainContainer);
		this.setVisible(true);
	}
	
	/**
	 * This method displays a menu that allows the player to create a spell
	 * @param spellIndex the spell you want to create/modify
	 */
	public void displayCreateSpell(int spellIndex) {
		
		JLayeredPane spellCreationMenu = new JLayeredPane();
		//create a new button to allow the player to validate the creation of his spell and refresh the string that contains the spell informations
		JButton btnValiderSpellCreation = new JButton("Valider");
		btnValiderSpellCreation.setBackground(Color.white);
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
				
			}
		});
		spellCreationMenu.add(btnValiderSpellCreation);
		
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
					pause = true;
				}
				else {
					chShape.setEnabled(true);
					pause = false;
				}
					
			}
		});
		spellCreationMenu.add(this.chElement);
	
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
				if(chShape.getSelectedItem()=="Choose a shape") {
					btnValiderSpellCreation.setEnabled(false);
					pause = true;
				}
				else if(chShape.getSelectedItem()=="Custom") {
					JOptionPane.showMessageDialog(mainContainer, "This functionnality is under devel");	
					pause = true;
				}
				else {
					btnValiderSpellCreation.setEnabled(true);
					pause = false;
				}
			}
		});
		spellCreationMenu.add(this.chShape);
		
		if (this.chShape.getSelectedItem() != "Choose a shape")
			btnValiderSpellCreation.setEnabled(true);
		else
			btnValiderSpellCreation.setEnabled(false);
		
		this.setVisible(false);
		this.mainContainer = spellCreationMenu;
		this.setContentPane(this.mainContainer);
		this.setVisible(true);
		
	}

	public void displaySelectForThisPawn(Pawn thePawn) {
		new JLabel("Veuillez selectionner une page pour le pion n° "+thePawn.getName());
		
	}
	
	public void displaySpellPage(ArrayList<SpellPage> listPages) {
		this.setVisible(false);
		
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
	
	public void askPageSelection(int currentPlayerIndex) {
		if(!(chSpellPages.getSelectedIndex()==0)) {
			//TODO
		}
		
	}


	@Override
	public void displayElementChoice() {
		this.chElement.removeAll();
		this.chElement.add("Choose an element");
		for (Effect element : Effect.values()) {
			this.chElement.add(element.getElementName());
		}
		
	}

	@Override
	public void displayShapeChoice() {
		this.chShape.removeAll();
		this.chShape.add("Choose a shape");
		for (Shape shape : this.controller.getGameShapes()) {
			this.chShape.add(shape.getName());
		}
		this.chShape.add("Custom");
		
	}

	@Override
	public void displayListServer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayServerLaunched(int ip, int port) {
		JOptionPane.showMessageDialog(this.getContentPane(),"Server @ip "+ip+" : "+port+" successfully launched","info", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void askPageCreation() {
		JButton spell1 = (JButton) this.mainContainer.getComponent(0);
		spell1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(1);
				while (pause);
				sp1 = askSpellCreation();
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
			}
		});
		JButton spell2 = (JButton) this.mainContainer.getComponent(1);
		spell2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(2);
				while(pause);
				sp2 = askSpellCreation();
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
			}
		});
		JButton spell3 = (JButton) this.mainContainer.getComponent(2);
		spell3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(3);
				while(pause);
				sp3 = askSpellCreation();
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
			}
		});
		JTextField pageNameField = (JTextField) this.mainContainer.getComponent(5);
		
		JButton btnValider = (JButton) this.mainContainer.getComponent(6);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.createSpellPageRequest(pageNameField.getText(),sp1,sp2,sp3);
			}
		});
	}

	@Override
	public Spell askSpellCreation() {
		//TODO might review
		int effectIndex = -1;
		for (Effect effect : Effect.values()) {
			if (effect.ordinal() == this.chElement.getSelectedIndex()-1)
				effectIndex = effect.ordinal();
		}
		if(effectIndex<0 || effectIndex > Effect.values().length-1)
			effectIndex = 0;
	Effect pEffect = Effect.values()[effectIndex];
	
		int shapeIndex = -1;
		for (Shape shape : this.controller.getGameShapes()) {
			if(!((this.chShape.getSelectedIndex() == 0) ||(this.chShape.getSelectedIndex() == this.controller.getGameShapes().size()+1))) {
				if(shape.getName() == this.chShape.getSelectedItem())
					shapeIndex = this.chShape.getSelectedIndex()-1;
			}
		}
		if(shapeIndex<0 || shapeIndex > this.controller.getGameShapes().size()-1)
			shapeIndex = 0;
		Shape pShape = this.controller.getGameShapes().get(shapeIndex);
		return new Spell(pEffect,pShape);
	}

	@Override
	public void askServerConnection() {
		JTextField ipField = (JTextField) this.mainContainer.getComponent(3);
		JButton btnValider = (JButton)  this.mainContainer.getComponent(0);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField ipField = (JTextField) mainContainer.getComponent(3);
				String ip = ipField.getText();
			}
		});
		
	}

	@Override
	public void askServerConfig() {
		JButton btnValider = (JButton) this.mainContainer.getComponent(6);
		JTextField numberOfPlayerField = (JTextField) this.mainContainer.getComponent(3);
		JTextField sizeField = (JTextField) this.mainContainer.getComponent(1);
		JTextField numberOfPawnsField = (JTextField) this.mainContainer.getComponent(5);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbPlayer = Integer.parseInt(numberOfPlayerField.getText());
				int nbPawns = Integer.parseInt(numberOfPawnsField.getText());
				int boardSize = Integer.parseInt(sizeField.getText());
				
				controller.serverConfigRequest(nbPlayer, nbPawns, boardSize);
			}
		});
		
	}

	@Override
	public void askLocalConfig() {
		JButton btnValider = (JButton) this.mainContainer.getComponent(6);
		JTextField numberOfPlayerField = (JTextField) this.mainContainer.getComponent(3);
		JTextField sizeField = (JTextField) this.mainContainer.getComponent(1);
		JTextField numberOfPlayerConsField = (JTextField) this.mainContainer.getComponent(5);
		JTextField numberOfPawnsField = (JTextField) this.mainContainer.getComponent(7);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbPlayer = Integer.parseInt(numberOfPlayerField.getText());
				int nbPawns = Integer.parseInt(numberOfPawnsField.getText());
				int boardSize = Integer.parseInt(sizeField.getText());
				int nbPlayerCons = Integer.parseInt(numberOfPlayerConsField.getText());
				
				controller.localConfigRequest(nbPlayer, nbPawns, boardSize, nbPlayerCons);
			}
		});
		
	}

	@Override
	public void setController(UserController pController)
	{
		this.controller = pController;
	}
	
	
}



