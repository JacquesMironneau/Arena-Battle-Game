package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.exceptions.SpellIndexException;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.shape.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
import fr.iutvalence.projet.battleArenaGame.spell.SpellEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

/**
 * PlayerWindow class
 * 
 * This class represents a player with a gui (window with buttons and listeners)
 * @author durantho
 *
 */
public class PlayerWindow extends JFrame implements Player, Runnable{
	
	/**
	 * This represents the default title of a PlayerWindow
	 */
	public final static String TITLE = "Battle Arena Game";

	/**
	 * This represents the lenght of the main content pane grid layout
	 * It is used to set the panel that contains the buttons to the middle
	 */
	public final static int GRID_SIZE = 9;
	
	/**
	 * This List will contains all the buttons that will be displayed on the main menu
	 */
	private List<JButton> buttons;
	
	/**
	 * initButtons method
	 * 
	 * This method will place the buttons correctly on the ButtonPane
	 * 
	 */
	private void mainMenuButtons() {
		
		//construction of a new ArrayList that will contain our buttons 
		this.buttons = new ArrayList<JButton>();
		
		
		this.buttons.add(new JButton("Créer une partie"));
		this.buttons.add(new JButton("Rejoindre une partie"));
		this.buttons.add(new JButton("Partie locale"));
		this.buttons.add(new JButton("Créer une page de sorts"));
		
		//set the background color to white for each button in the ArrayList
		for (JButton button : this.buttons) {
			button.setBackground(Color.white);
		}
		
		buttons.get(0).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Partie créée");	
			}	
		});
		
		buttons.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Partie rejointe");
			}
		});
		
		buttons.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Partie locale créée");
			}
		});
		
		buttons.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Menu de création d'une page de sorts");
			}
		});
		
	}
	
	/**
	 * Constructor of a new PlayerWindow
	 * It initializes the PlayerWindow with a default title,
	 * a default size,
	 * the default operation when closing the window,
	 * 
	 * This will also set a new layout for the window
	 */
	public PlayerWindow() {
		/*
		 * Super Constructor
		 */
		super();
		
		/*
		 * Window properties
		 * TITLE
		 * no relative location
		 * Set default close operation
		 * Size by default
		 */
		this.setTitle(PlayerWindow.TITLE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000,800);
		
		this.displayMenu();
		
		/*
		 * Enable window visibility
		 */
		this.setVisible(true);
	}
	
	/**
	 * Create a new window with a custom size
	 * @param ww the window's width
	 * @param wh the window's height
	 */
	public PlayerWindow(int ww, int wh) {
		
		/*
		 * Super constructor
		 */
		super();
		
		/*
		 * WINDOW PROPERTIES
		 * TITLE
		 * no relative location
		 * default close operation
		 * custom size
		 */
		this.setTitle(PlayerWindow.TITLE);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(ww,wh);
		
		this.displayMenu();
		
		/*
		 * Enable window visibility
		 */
		this.setVisible(true);

}
	@Override
	public void displayMenu() {
		
		/*
		 * Add a new JPanel
		 */
		JPanel ButtonPane = new JPanel();
		
		/*
		 * Call the initButtons method
		 */
		this.mainMenuButtons();
		
		/*
		 * Change the panel to arrange the items within it according to a 4rows by 1 column template
		 */
		ButtonPane.setLayout(new GridLayout(4,1));
		
		/*
		 * Change the default layout to display a 9x9 grid and place the panel that contains the buttons to the middle of the window
		 */
		this.setLayout(new GridLayout(3,3));
		
		/*
		 * Add each button from the List<JButton> to the created panel
		 */
		for (JButton button : this.buttons) {
			ButtonPane.add(button);
		}
		
		/*
		 * Add different panels in the contentPane and place the one with the buttons in the middle
		 */
		for (int index = 1; index < PlayerWindow.GRID_SIZE; index++) {
			if (index != 5)
				this.getContentPane().add(new JPanel());
			else
				this.getContentPane().add(ButtonPane);		
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askSpell() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Choices askActionChoice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Choices askChoiceMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void askSpellPageCreation() throws SpellIndexException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayChoiceAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectPageForPawns() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayEnd(EndStatus Pstat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String askPageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int askSpellIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String askSpellElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape askSpellShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean askValidation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void displayElementChoice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayShapeChoice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellInCooldown(Spell pSpell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellOutOfRange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayNotEnoughActionPoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellLaunched() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayNextTurn() {
		// TODO Auto-generated method stub
		
	}
}
