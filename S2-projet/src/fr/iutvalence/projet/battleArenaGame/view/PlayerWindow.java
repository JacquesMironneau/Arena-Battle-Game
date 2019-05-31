package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.move.Movement;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
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
public class PlayerWindow extends JFrame implements Player{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8221795073024999L;
	
	/**
	 * This represents the default title of a PlayerWindow
	 */

	/**
	 * This represents the length of the main content pane grid layout
	 * It is used to set the panel that contains the buttons to the middle
	 */
	public final static int MAIN_MENU_GRID_LENGHT = 9;
	public final static int SPELL_PAGE_CREATION_MENU_GRID_LENGHT = 9;
	
	/**
	 * This List will contains all the buttons that will be displayed on the main menu
	 */
	private List<JButton> buttons;
	
	/**
	 * initButtons method
	 * This method will place the buttons correctly on the ButtonPane
	 */
	private void mainMenuButtons() {
		
		//construction of a new ArrayList that will contain our buttons 
		this.buttons = new ArrayList<JButton>();
		
		//Creation of the buttons that we will add
		this.buttons.add(new JButton("Créer une partie"));
		this.buttons.add(new JButton("Rejoindre une partie"));
		this.buttons.add(new JButton("Partie locale"));
		this.buttons.add(new JButton("Créer une page de sorts"));
		
		//set the background color to white for each button in the ArrayList
		for (JButton button : this.buttons) {
			button.setBackground(Color.white);
		}
		
		//ActionListener for the first button 
		buttons.get(0).addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Partie créée");	
				//TODO display createLobby
			}	
		});
		
		//ActionListener for the second button
		buttons.get(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Partie rejointe");
				//TODO display joinLobby
			}
		});
		
		//Action Listener for the third button
		buttons.get(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Partie locale créée");
				//TODO display create local game
			}
		});
		
		//Action Listener for the fourth button
		buttons.get(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				displaySpellPageCreationMenu();
				
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
		 * no relative location
		 * Set default close operation
		 * Size by default
		 */
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1920,1080);
		
		this.displayMenu();
		
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
		this.setVisible(false);
		
		this.setTitle("Battle Arena Game");
		this.getContentPane().removeAll();
		this.getContentPane().setLayout(new GridLayout(3, 3, 0, 0));
		
		JPanel panel = new JPanel();
		this.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		this.getContentPane().add(panel_1);
		
		JLabel lblMainMenu = new JLabel("Main Menu");
		panel_1.add(lblMainMenu);
		
		JPanel panel_2 = new JPanel();
		this.getContentPane().add(panel_2);
		
		JPanel panel_3 = new JPanel();
		this.getContentPane().add(panel_3);
		
		JPanel panel_4 = new JPanel();
		this.getContentPane().add(panel_4);
		panel_4.setLayout(new GridLayout(4, 1, 0, 0));
		
		JButton btnNewButton = new JButton("Host a Game");
		panel_4.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Join a Game");
		panel_4.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Local Game");
		panel_4.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Create a new SP");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displaySpellPageCreationMenu();
			}
		});
		panel_4.add(btnNewButton_3);
		
		JPanel panel_5 = new JPanel();
		this.getContentPane().add(panel_5);
		
		JPanel panel_6 = new JPanel();
		this.getContentPane().add(panel_6);
		
		JPanel panel_7 = new JPanel();
		this.getContentPane().add(panel_7);
		
		JPanel panel_8 = new JPanel();
		this.getContentPane().add(panel_8);
		this.setVisible(true);
		
	}
	
	/**
	 * Displays the menu for the SpellPageCreation
	 */
	public void displaySpellPageCreationMenu() {
		
		
		this.setVisible(false);
		
		this.getContentPane().removeAll();
		
		this.getContentPane().setLayout(new GridLayout(2, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Spell 1", null, panel_1, null);
		panel_1.setLayout(new GridLayout(1, 3, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Select an element");
		panel_4.add(lblNewLabel);
		
		Choice choice = new Choice();
		choice.add("Choose an element");
		choice.add("Fire");
		choice.add("Ice");
		choice.add("Stone");
		choice.add("Wind");
		choice.add("Electricity");
		choice.add("Darkness");
		panel_4.add(choice);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		panel_5.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel lblSelectAShape = new JLabel("Select a shape");
		panel_5.add(lblSelectAShape);
		
		Choice choice_1 = new Choice();
		choice_1.add("Choose an Shape");
		choice_1.add("Fist");
		choice_1.add("Sword");
		choice_1.add("Square");
		choice_1.add("Ball");
		choice_1.add("Cross");
		choice_1.add("Beam");
		choice_1.add("Special");
		panel_5.add(choice_1);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Spell2", null, panel, null);
		panel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		panel_7.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel label = new JLabel("Select an element");
		panel_7.add(label);
		
		Choice choice_2 = new Choice();
		choice_2.add("Choose an element");
		choice_2.add("Fire");
		choice_2.add("Ice");
		choice_2.add("Stone");
		choice_2.add("Wind");
		choice_2.add("Electricity");
		choice_2.add("Darkness");
		panel_7.add(choice_2);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		panel_8.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel label_1 = new JLabel("Select a shape");
		panel_8.add(label_1);
		
		Choice choice_3 = new Choice();
		choice_3.add("Choose a shape");
		choice_3.add("Fist");
		choice_3.add("Sword");
		choice_3.add("Square");
		choice_3.add("Ball");
		choice_3.add("Cross");
		choice_3.add("Beam");
		choice_3.add("Special");
		panel_8.add(choice_3);
		
		JPanel panel_9 = new JPanel();
		tabbedPane.addTab("Spell3", null, panel_9, null);
		panel_9.setLayout(new GridLayout(1, 3, 0, 0));
		
		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10);
		panel_10.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel label_2 = new JLabel("Select an element");
		panel_10.add(label_2);
		
		Choice choice_4 = new Choice();
		choice_4.add("Choose an element");
		choice_4.add("Fire");
		choice_4.add("Ice");
		choice_4.add("Stone");
		choice_4.add("Wind");
		choice_4.add("Electricity");
		choice_4.add("Darkness");
		panel_10.add(choice_4);
		
		JPanel panel_11 = new JPanel();
		panel_9.add(panel_11);
		panel_11.setLayout(new GridLayout(7, 1, 0, 0));
		
		JLabel label_3 = new JLabel("Select a shape");
		panel_11.add(label_3);
		
		Choice choice_5 = new Choice();
		choice_5.add("Choose a shape");
		choice_5.add("Fist");
		choice_5.add("Sword");
		choice_5.add("Square");
		choice_5.add("Ball");
		choice_5.add("Cross");
		choice_5.add("Beam");
		choice_5.add("Special");
		panel_11.add(choice_5);
		
		JPanel panel_3 = new JPanel();
		this.getContentPane().add(panel_3);
		panel_3.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_3.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblSpellPageName = new JLabel("Spell Page Name");
		panel_2.add(lblSpellPageName);
		
		JTextField textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		
		JButton btnValider = new JButton("Valider");
		btnValider.setEnabled(false);
		
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				btnValider.setEnabled(true);
				
			}
			
		});

		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayMenu();
			}
		});
		panel_6.add(btnValider);
		
		System.out.println(choice.getSelectedItem());
		
		//reAllow the visibility of the window
		this.setVisible(true);
	}

	@Override
	public Movement askMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spell askSpell() {
		// TODO Auto-generated method stub
		return null;
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
	public void displayBoard(Board myBoard) {
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
	public SpellPage askSpellPageSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayEnd(EndStatus Pstat, TeamId teamId) {
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
	public SpellEffect askSpellElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shape askSpellShape(SpellEffect eff) {
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
	public void displaySpellOutOfRange(Spell pSpell) {
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

	@Override
	public void displayNotEnoughMovePoints() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMoveDone() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellPageDetail(SpellPage pPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMoveOutOfRange() {
		// TODO Auto-generated method stub
		
	}
	

}