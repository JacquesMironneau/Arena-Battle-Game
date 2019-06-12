package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

public class UserViewWindow extends JFrame implements UserView {
	//TODO removes attributes, complete auto implemented methods
	/**
	 * This pane contains all the container and the visible informations on the screen.
	 */
	private JFrame mainContainer;
	
	private MenuChoices menuChoice;
	private boolean pause;
	
	private Spell[] pageToCreate;
	private String pageName;
	
	private int[] localConfig;
	private int[] serverConfig;
	private boolean pageOk;
	
	private UserController controller;
	
	private JGameCanvas gameBoard;
	
	private HashSet<Shape> gameShapes;
	private HashSet<SpellPage> mySpellPages;
	private int spellIndex;
	
	private JPanel mainMenu;
	private JPanel localConfigMenu;
	private JPanel serverConfigMenu;
	private JPanel spellPageCreationMenu;
	private JPanel spellCreationMenu;
	
	
	
	public UserViewWindow() {
		/*
		 * Super Constructor
		 */
		this.mainContainer = new JFrame();
		
		
		/*
		 * Window properties
		 * no relative location
		 * Set default close operation
		 * Size by default
		 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.mainContainer.setTitle("Projet S2");
		this.mainContainer.setLocationRelativeTo(null);
		this.mainContainer.setLocation(0, 0);
		this.mainContainer.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.mainContainer.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
		this.mainContainer.setResizable(false);
		
		
		/*
		 * Creation of mainMenu Panel
		 */
		createMainMenu();
		createLocalConfigMenu();
		createServerConfigMenu();
		createSpellPageCreationMenu();
       
        /*
         * 
         */
		this.mainContainer.setVisible(true);
	}
	
	
	private void createMainMenu()
	{
		this.mainMenu = new JPanel();
		this.mainMenu.setLayout(null);
        JButton btnCreateGame = new JButton("Créer une partie");
        btnCreateGame.setBackground(Color.white);
        btnCreateGame.setBounds(this.mainContainer.getWidth()/4, this.mainContainer.getHeight()/8, this.mainContainer.getWidth()/4*2, this.mainContainer.getHeight()/8);
        btnCreateGame.setEnabled(false);
        	//Add eventListener
        	btnCreateGame.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent arg0) {
        			menuChoice = MenuChoices.HOST_GAME;
        		}
        	});
        this.mainMenu.add(btnCreateGame);
        
        //add a new button that tells to the system to join a game and add it the content pane
        JButton btnJoinGame = new JButton("Rejoindre une partie");
        btnJoinGame.setBackground(Color.white);
        btnJoinGame.setBounds(this.mainContainer.getWidth()/4, this.mainContainer.getHeight()/8*2, this.mainContainer.getWidth()/4*2, this.mainContainer.getHeight()/8);
        btnJoinGame.setEnabled(false);
        
	      	//Add eventListener
	    	btnJoinGame.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent arg0) {
	    			menuChoice = MenuChoices.JOIN_GAME;
	    		}
	    	});
	    this.mainMenu.add(btnJoinGame);
	    
	    
        //creates a new button that tells to the system to create a new local game and add it to the content pane
        JButton btnCreateLocalGame = new JButton("Créer une partie locale");
        btnCreateLocalGame.setBackground(Color.white);
        btnCreateLocalGame.setBounds(this.mainContainer.getWidth()/4, this.mainContainer.getHeight()/8*3, this.mainContainer.getWidth()/4*2, this.mainContainer.getHeight()/8);
        btnCreateLocalGame.setEnabled(false);
	      	//Add eventListener
	    	btnCreateLocalGame.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent arg0) {
	    			menuChoice = MenuChoices.LOCAL_GAME;
	    		}
	    	});
        
	    this.mainMenu.add(btnCreateLocalGame);
	    
	    
        //creates a new buttons that will display the spell page creation menu
        JButton btnCreateSpellPage = new JButton("Créer une page de sorts");
        btnCreateSpellPage.setBackground(Color.white);
        btnCreateSpellPage.setBounds(this.mainContainer.getWidth()/4,this.mainContainer.getHeight()/8*4, this.mainContainer.getWidth()/4*2, this.mainContainer.getHeight()/8);
        btnCreateSpellPage.setEnabled(false);
        
	     	//Add eventListener
	    	btnCreateSpellPage.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent arg0) {
	    			menuChoice = MenuChoices.CREATE_SPELL_PAGE;
	    		}
	    	});
	    
	    this.mainMenu.add(btnCreateSpellPage);
	}
	
	private void createLocalConfigMenu()

	{
		this.localConfigMenu = new JPanel();
		this.localConfigMenu.setLayout(null);
		
		JLabel size = new JLabel("Entrez la taille du plateau de jeu");
		size.setBounds(this.mainContainer.getWidth()/3,0,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(size);
		
		JTextField sizeField = new JTextField("0");
		sizeField.setBounds(this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(sizeField);
		
		JLabel numberOfPlayer = new JLabel("Entrez le nombre de joueurs");
		numberOfPlayer.setBounds(this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20*2,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(numberOfPlayer);
		
		JTextField numberOfPlayerField = new JTextField("0");
		numberOfPlayerField.setBounds(this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20*3,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(numberOfPlayerField);
		
		JLabel numberOfPlayerCons = new JLabel("Entrez le nombre de joueurs console");
		numberOfPlayerCons.setBounds(this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20*4,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(numberOfPlayerCons);
		
		JTextField numberOfPlayerConsField = new JTextField("0");
		numberOfPlayerConsField.setBounds(this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20*5,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(numberOfPlayerConsField);
		
		JLabel numberOfPawns = new JLabel("Veuillez saisir le nombre de pions");
		numberOfPawns.setBounds(this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20*6,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(numberOfPawns);
		
		JTextField numberOfPawnsField = new JTextField("0");
		numberOfPawnsField.setBounds(this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/20*7, this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/20);
		this.localConfigMenu.add(numberOfPawnsField);
		
		localConfig = new int[] {0,0,0,0};
		JButton btnValider = new JButton("valider");
		btnValider.setBackground(Color.white);
		btnValider.setBounds(this.mainContainer.getWidth()/5*2,this.mainContainer.getHeight()/20*9,this.mainContainer.getWidth()/5,this.mainContainer.getHeight()/20);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbPlayer = Integer.parseInt(numberOfPlayerField.getText());
				int nbPawns = Integer.parseInt(numberOfPawnsField.getText());
				int boardSize = Integer.parseInt(sizeField.getText());
				int nbPlayerCons = Integer.parseInt(numberOfPlayerConsField.getText());
				localConfig = new int[]{nbPlayer,nbPawns,boardSize,nbPlayerCons};
			}
		});
		btnValider.setEnabled(false);
		this.localConfigMenu.add(btnValider);
		this.localConfigMenu.setVisible(true);
	}
	
	private void createServerConfigMenu()
	{
		this.serverConfigMenu = new JPanel();
		this.serverConfigMenu.setLayout(null);
		JLabel ssize = new JLabel("Entrez la taille du plateau de jeu");
		ssize.setBounds(0,0,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.serverConfigMenu.add(ssize);
		
		JTextField ssizeField = new JTextField("0");
		ssizeField.setBounds(0,this.mainContainer.getHeight()/20,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.serverConfigMenu.add(ssizeField);
		
		JLabel snumberOfPlayer = new JLabel("Entrez le nombre de joueurs");
		snumberOfPlayer.setBounds(0,this.mainContainer.getHeight()/20*2,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.serverConfigMenu.add(snumberOfPlayer);
		
		JTextField snumberOfPlayerField = new JTextField("0");
		snumberOfPlayerField.setBounds(0,this.mainContainer.getHeight()/20*3,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.serverConfigMenu.add(snumberOfPlayerField);
		
		JLabel snumberOfPawns = new JLabel("Veuillez saisir le nombre de pions");
		snumberOfPawns.setBounds(0,this.mainContainer.getHeight()/20*4,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
		this.serverConfigMenu.add(snumberOfPawns);
		
		JTextField snumberOfPawnsField = new JTextField("0");
		snumberOfPawnsField.setBounds(0, this.mainContainer.getHeight()/20*5, this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/20);
		this.serverConfigMenu.add(snumberOfPawnsField);
		
		serverConfig = new int[] {0,0,0};
		JButton sbtnValider = new JButton("valider");
		sbtnValider.setEnabled(false);
		sbtnValider.setBackground(Color.white);
		sbtnValider.setBounds(0,this.mainContainer.getHeight()/20*7,this.mainContainer.getWidth()/5,this.mainContainer.getHeight()/20);
		sbtnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbPlayer = Integer.parseInt(snumberOfPlayerField.getText());
				int nbPawns = Integer.parseInt(snumberOfPawnsField.getText());
				int boardSize = Integer.parseInt(ssizeField.getText());
				localConfig = new int[]{nbPlayer,nbPawns,boardSize};
			}
		});
		this.serverConfigMenu.add(sbtnValider);
	}
	
	private void createSpellPageCreationMenu()
	{
		this.spellPageCreationMenu = new JPanel();
		this.spellPageCreationMenu.setLayout(null);
		JButton btnSpell1 = new JButton("Spell1");
		btnSpell1.setBackground(Color.white);
		btnSpell1.setBounds(0,10,(this.mainContainer.getWidth()/3),(this.mainContainer.getHeight()/3)-10);
		btnSpell1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSpell(0);
				}
		});
		this.spellPageCreationMenu.add(btnSpell1);
		
		// creates a new button for the creation of the second spell
		JButton btnSpell2 = new JButton("Spell2");
		btnSpell2.setBackground(Color.white);
		btnSpell2.setBounds((this.mainContainer.getWidth()/3),10,(this.mainContainer.getWidth()/3),(this.mainContainer.getHeight()/3)-10);
		btnSpell2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSpell(1);
				}
		});
		this.spellPageCreationMenu.add(btnSpell2);
		
		// creates a new button for the creation of the third spell
		JButton btnSpell3 = new JButton("Spell3");
		btnSpell3.setBackground(Color.white);
		btnSpell3.setBounds((this.mainContainer.getWidth()/3*2),10,(this.mainContainer.getWidth()/3),(this.mainContainer.getHeight()/3)-10);
		btnSpell3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createSpell(2);
				}
		});
		this.spellPageCreationMenu.add(btnSpell3);
		
		//creates a new JLabel to indicate what the following JTextField is use for
		JLabel lblPageName = new JLabel("Enter the name of the page");
		lblPageName.setBounds(0,(this.mainContainer.getHeight()/2)-this.mainContainer.getHeight()/20,this.mainContainer.getWidth()/5,this.mainContainer.getHeight()/20);
		this.spellPageCreationMenu.add(lblPageName);
		
		JTextField tfPageName = new JTextField("P");
		tfPageName.setBounds(0,this.mainContainer.getHeight()/2,this.mainContainer.getWidth()/2,this.mainContainer.getHeight()/20);
		this.spellPageCreationMenu.add(tfPageName);
		
		//add a new button that will send all the information of the spell page and redirect the player to the main menu
		JButton btnValiderPageCreation = new JButton("Valider");
		btnValiderPageCreation.setBackground(Color.white);
		btnValiderPageCreation.setBounds(this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/3*2, this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/20);
		//disable the button by default because the player cannot validate the creation without the three spells created, and the page name
		btnValiderPageCreation.setEnabled(false);
		btnValiderPageCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pageName = tfPageName.getText();
				pageOk = true;
				}
		});
		this.spellPageCreationMenu.add(btnValiderPageCreation);
		this.spellPageCreationMenu.setVisible(true);
	}
	
	private void createSpellCreationMenu(int spellIndex)
	{
		this.spellCreationMenu = new JPanel();
		this.spellCreationMenu.setLayout(null);
		JComboBox<String> elementChoice = new JComboBox<String>();
		for (Effect element : Effect.values()) 
			elementChoice.addItem(element.getElementName());
		elementChoice.setBounds(this.mainContainer.getWidth()/6*2,this.mainContainer.getHeight()/20*4,this.mainContainer.getWidth()/6,this.mainContainer.getHeight()/20);
		this.spellCreationMenu.add(elementChoice);
		
		JComboBox<String> shapeChoice = new JComboBox<String>();
		for(Shape shp : this.controller.getGameShapes())
			shapeChoice.addItem(shp.getName());
		shapeChoice.setBounds(this.mainContainer.getWidth()/6*4,this.mainContainer.getHeight()/20*4,this.mainContainer.getWidth()/6,this.mainContainer.getHeight()/20);
		this.spellCreationMenu.add(shapeChoice);
		this.spellCreationMenu.setVisible(true);
		
		JButton btnValiderSpellCreation = new JButton("Valider");
		btnValiderSpellCreation.setBounds(this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/3*2, this.mainContainer.getWidth()/3, this.mainContainer.getHeight()/20);
		btnValiderSpellCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			Effect pEffect = Effect.valueOf((String)elementChoice.getSelectedItem());
			Shape pShape = null;
			for(Shape shp : controller.getGameShapes())
				{
					if(shp.getName().equals(((String)shapeChoice.getSelectedItem())))
						 pShape = shp;
				}
				pageToCreate[spellIndex] = new Spell(pEffect,pShape);
				switchPanel(spellPageCreationMenu);
			}
		});
		this.spellCreationMenu.add(btnValiderSpellCreation);
		
	}
	
	@Override
	public void setController(UserController pController) {
		this.controller = pController;
	}
	
	private void switchPanel(JPanel panel)
	{
		this.mainContainer.remove(this.mainContainer.getContentPane());
		this.mainContainer.setContentPane(panel);
		this.mainContainer.validate();
		this.mainContainer.repaint();
	}
	@Override
	public void display(DisplayMessage msg) {
		switch(msg)
		{
		case MENU:
			switchPanel(this.mainMenu);
			break;
		case LOCAL_CONFIG:
			switchPanel(this.localConfigMenu);
			break;
		case SERVER_CONFIG:
			switchPanel(this.serverConfigMenu);
			break;
		case PAGE_CREATION:
			switchPanel(this.spellPageCreationMenu);
			break;
		}
	}
	

	@Override
	public void askChoiceMenu()
	{
		this.menuChoice = MenuChoices.INVALID_CHOICE;
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(true);
				
		while(this.menuChoice.equals(MenuChoices.INVALID_CHOICE))
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(false);
		controller.choiceMenuRequest(this.menuChoice);
	}
	//TODO this method
	public void displayJoinGame() {
		this.setVisible(false);
		this.getContentPane().removeAll();
		
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(this.getWidth()/5*2,this.getHeight()/20*3+20,this.getWidth()/5,this.getHeight()/20);
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
//				display(DisplayMessage.MENU);
			}
		});
		this.getContentPane().add(btnRetour);
		
		this.setVisible(true);
	}
	
	@Override
	public void displaySpellCreation()
	{
		switchPanel(this.spellPageCreationMenu);
	}
		
	public void displaySelectForThisPawn(Pawn thePawn) {
		new JLabel("Veuillez selectionner une page pour le pion n° "+thePawn.getName());
		
	}
	
	//TODO this method
	@Override
	public void displayListServer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayServerLaunched(int ip, int port) {
		JOptionPane.showMessageDialog(this.getContentPane(),"Server @ip "+ip+" : "+port+" successfully launched","info", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void askPageCreation() 
	{
		this.pageToCreate = new Spell[3];
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(true);
		while(!pageOk)
		{	
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		this.controller.createSpellPageRequest(pageName, pageToCreate[0], pageToCreate[1], pageToCreate[2]);
	/*	JButton spell1 = (JButton) this.getContentPane().getComponent(0);
		spell1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(1);
				while (pause);
				sp1 = askSpellCreation();
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
			}
		});
		JButton spell2 = (JButton) this.getContentPane().getComponent(1);
		spell2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(2);
				while(pause);
				sp2 = askSpellCreation();
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
			}
		});
		JButton spell3 = (JButton) this.getContentPane().getComponent(2);
		spell3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(3);
				while(pause);
				sp3 = askSpellCreation();
				controller.choiceMenuRequest(MenuChoices.CREATE_SPELL_PAGE);
			}
		});
		JTextField pageNameField = (JTextField) this.getContentPane().getComponent(5);
		
		JButton btnValider = (JButton) this.getContentPane().getComponent(6);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.createSpellPageRequest(pageNameField.getText(),sp1,sp2,sp3);
			}
		});*/
	}

/*	@Override
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
	}*/

	@Override
	public void askServerConnection() {
		JTextField ipField = (JTextField) this.getContentPane().getComponent(3);
		JButton btnValider = (JButton)  this.getContentPane().getComponent(0);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField ipField = (JTextField) getContentPane().getComponent(3);
				String ip = ipField.getText();
			}
		});
		
	}

	@Override
	public void askServerConfig() {
		boolean ok = false;
		int nbPlayer = this.localConfig[0];
		int nbPawn = this.localConfig[1];
		int boardSize = this.localConfig[2];
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(true);
		while(!ok)
		{
			 nbPlayer = this.localConfig[0];
			 nbPawn = this.localConfig[1];
			 boardSize = this.localConfig[2];
			if(nbPlayer >1 && nbPawn >0 && boardSize*boardSize>nbPlayer*nbPawn)
			{
				ok = true;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(false);
		this.controller.serverConfigRequest(nbPlayer, nbPawn, boardSize);
	}

	@Override
	public void askLocalConfig()
	{
		boolean ok = false;
		int nbPlayer = this.localConfig[0];
		int nbPawn = this.localConfig[1];
		int boardSize = this.localConfig[2];
		int consoles = this.localConfig[3];
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(true);
		while(!ok)
		{
			 nbPlayer = this.localConfig[0];
			 nbPawn = this.localConfig[1];
			 boardSize = this.localConfig[2];
			 consoles = this.localConfig[3];
			if(nbPlayer >1 && nbPawn >0 && boardSize*boardSize>nbPlayer*nbPawn)
			{
				ok = true;
				
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Component comp : this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(false);
		this.controller.localConfigRequest(nbPlayer, nbPawn, boardSize, consoles);
	
		
	}

	private void createSpell(int spellIndex)
	{
		createSpellCreationMenu(spellIndex);
		switchPanel(this.spellCreationMenu);
	}


	
	
	@Override
	public Spell askSpellCreation() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void displayHowManyConnectedPeople(String msg) {
		// TODO Auto-generated method stub
		
	}
}
