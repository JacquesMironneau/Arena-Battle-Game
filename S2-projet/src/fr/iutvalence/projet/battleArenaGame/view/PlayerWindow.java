package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.EndStatus;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
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
public class PlayerWindow extends JFrame implements GameView{
	
	private JLayeredPane mainContainer;
	private JTextField tfPageName;
	
	private Choice chElement;
	private Choice chShape;
	
	private String sp1ElCh = null;
	private String sp1ShCh = null;
	
	private String sp2ElCh = null;
	private String sp2ShCh = null;
	
	private String sp3ElCh = null;
	private String sp3ShCh = null;
	
	
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
		this.setTitle("Projet S2");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1920,1080);
		
		this.chElement = new Choice();
		this.chShape = new Choice();
		
		this.mainContainer = new JLayeredPane();
		this.setContentPane(this.mainContainer);
				
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
		
		JLayeredPane mainContainer = new JLayeredPane();
		
		/*
		 * WINDOW PROPERTIES
		 * TITLE
		 * no relative location
		 * default close operation
		 * custom size
		 */
		this.setTitle("Projet S2");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(ww,wh);
		
		this.chElement = new Choice();
		this.chShape = new Choice();
		
		this.mainContainer = new JLayeredPane();
		this.setContentPane(this.mainContainer);
		
		this.displayMenu();
		
		/*
		 * Enable window visibility
		 */
		this.setVisible(true);
}

	@Override
	public Coordinate askMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int askSpell() {
		// TODO Auto-generated method stub
		return 0;
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
	public int askSpellPageSelection(ArrayList<SpellPage> listPages) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String askPageName() {
		return this.tfPageName.getText();
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
	public void displayBoard(Board myBoard, int nbPlayer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMenu() {
		this.setVisible(false);
		this.getContentPane().removeAll();
		
		JButton btnCreateGame = new JButton("Créer une partie");
		btnCreateGame.setBounds(this.getWidth()/4, this.getHeight()/8, this.getWidth()/4*2, this.getHeight()/8);
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//startgame.method
			}
		});
		this.getContentPane().add(btnCreateGame);
		
		JButton btnJoinGame = new JButton("Rejoindre une partie");
		btnJoinGame.setBounds(this.getWidth()/4, this.getHeight()/8*2, this.getWidth()/4*2, this.getHeight()/8);
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//joingame.method
			}
		});
		this.getContentPane().add(btnJoinGame);
		
		JButton btnCreateLocalGame = new JButton("Créer une partie locale");
		btnCreateLocalGame.setBounds(this.getWidth()/4, this.getHeight()/8*3, this.getWidth()/4*2, this.getHeight()/8);
		btnCreateLocalGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//createlocalgame.method
			}
		});
		this.getContentPane().add(btnCreateLocalGame);
		
		JButton btnCreateSpellPage = new JButton("Créer une page de sorts");
		btnCreateSpellPage.setBounds(this.getWidth()/4,this.getHeight()/8*4, this.getWidth()/4*2, this.getHeight()/8);
		btnCreateSpellPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displaySpellPageCreation();
			}
		});
		this.getContentPane().add(btnCreateSpellPage);
		
		this.setVisible(true);
	}
	
	protected void displaySpellPageCreation() {
		this.setVisible(false);
		this.getContentPane().removeAll();
		
		JButton btnSpell1 = new JButton("Spell1");
		btnSpell1.setBounds(0,10,(this.getWidth()/3),(this.getHeight()/3)-10);
		btnSpell1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(1);
			}
		});
		this.getContentPane().add(btnSpell1);
		
		JButton btnSpell2 = new JButton("Spell2");
		btnSpell2.setBounds((this.getWidth()/3),10,(this.getWidth()/3),(this.getHeight()/3)-10);
		btnSpell2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(2);
			}
		});
		this.getContentPane().add(btnSpell2);
		
		JButton btnSpell3 = new JButton("Spell3");
		btnSpell3.setBounds((this.getWidth()/3*2),10,(this.getWidth()/3),(this.getHeight()/3)-10);
		btnSpell3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayCreateSpell(3);
			}
		});
		this.getContentPane().add(btnSpell3);
		
		JLabel lblPageName = new JLabel("Enter the name of the page");
		lblPageName.setBounds(0,(this.getHeight()/2)-this.getHeight()/20,this.getWidth()/5,this.getHeight()/20);
		this.getContentPane().add(lblPageName);
		
		JButton btnValiderPageCreation = new JButton("Valider");
		btnValiderPageCreation.setBounds(this.getWidth()/3, this.getHeight()/3*2, this.getWidth()/3, this.getHeight()/20);
		btnValiderPageCreation.setEnabled(false);
		btnValiderPageCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				askPageName();
				displayMenu();
			}
		});
		this.getContentPane().add(btnValiderPageCreation);
		
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
		
		this.setVisible(true);
	}

	protected void displayCreateSpell(int spellIndex) {
		
		this.setVisible(false);
		this.getContentPane().removeAll();
		
		JButton btnValiderSpellCreation = new JButton("Valider");
		btnValiderSpellCreation.setBounds(this.getWidth()/3, this.getHeight()/3*2, this.getWidth()/3, this.getHeight()/20);
		btnValiderSpellCreation.setEnabled(false);
		btnValiderSpellCreation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				askSpellElement();
				//askSpellShape();
				displaySpellPageCreation();
			}
		});
		this.getContentPane().add(btnValiderSpellCreation);
		
		this.chElement.removeAll();
		this.chElement.add("Choose an element");
		this.chElement.add("Fire");
		this.chElement.add("Ice");
		this.chElement.add("Wind");
		this.chElement.add("Stone");
		this.chElement.add("Electricity");
		this.chElement.add("Darkness");
		
		if(this.sp1ElCh != null && spellIndex == 1)
			this.chElement.select(this.sp1ElCh);
		
		if(this.sp2ElCh != null && spellIndex == 2)
			this.chElement.select(this.sp2ElCh);
		
		if(this.sp3ElCh != null && spellIndex == 3)
			this.chElement.select(this.sp3ElCh);
		
		
		this.chElement.setBounds(this.getWidth()/3, 10, this.getWidth()/3, this.getHeight()/10);
		
		this.chElement.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(chElement.getSelectedItem() == "Choose an element")
					chShape.setEnabled(false);
				else {
					chShape.setEnabled(true);
					
					switch(spellIndex) {
					case 1:
						sp1ElCh = chElement.getSelectedItem();
						break;
					case 2:
						sp2ElCh = chElement.getSelectedItem();
						break;
					case 3:
						sp3ElCh = chElement.getSelectedItem();
						break;
					}
				}
					
			}
		});
		
		this.getContentPane().add(this.chElement);
		
		if(this.chElement.getSelectedItem() != "Choose en element")
			this.chShape.setEnabled(true);
		else
			this.chShape.setEnabled(false);
		
		this.chShape.removeAll();
		this.chShape.add("Choose a shape");
		this.chShape.add("Fist");
		this.chShape.add("Ball");
		this.chShape.add("Sword");
		this.chShape.add("Square");
		this.chShape.add("Cross");
		this.chShape.add("Beam");
		this.chShape.add("Custom");
		
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
					new JOptionPane();
					JOptionPane.showMessageDialog(mainContainer, "This functionnality is under devel");	
				}
				else
					btnValiderSpellCreation.setEnabled(true);
				
				switch(spellIndex) {
				case 1:
					sp1ShCh = chShape.getSelectedItem();
					break;
				case 2:
					sp2ShCh = chShape.getSelectedItem();
					break;
				case 3:
					sp3ShCh = chShape.getSelectedItem();
					break;
					
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
	public void displayError(ErrorMessages error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellPage(ArrayList<SpellPage> listPages) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayChoiceAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayEnd(TeamId winTeam) {
		// TODO Auto-generated method stub
		
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
	public void displaySpellLaunched() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayNextTurn() {
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
	public int askNbPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int askNbPawn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int askBoardSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void diplaySizeError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySelectForThisPawn(String Pawn) {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public void displayMenu() {
//		this.setVisible(false);
//		
//		this.setTitle("Battle Arena Game");
//		this.getContentPane().removeAll();
//		this.getContentPane().setLayout(new GridLayout(3, 3, 0, 0));
//		
//		JPanel panel = new JPanel();
//		this.getContentPane().add(panel);
//		
//		JPanel panel_1 = new JPanel();
//		this.getContentPane().add(panel_1);
//		
//		JLabel lblMainMenu = new JLabel("Main Menu");
//		panel_1.add(lblMainMenu);
//		
//		JPanel panel_2 = new JPanel();
//		this.getContentPane().add(panel_2);
//		
//		JPanel panel_3 = new JPanel();
//		this.getContentPane().add(panel_3);
//		
//		JPanel panel_4 = new JPanel();
//		this.getContentPane().add(panel_4);
//		panel_4.setLayout(new GridLayout(4, 1, 0, 0));
//		
//		JButton btnNewButton = new JButton("Host a Game");
//		panel_4.add(btnNewButton);
//		
//		JButton btnNewButton_1 = new JButton("Join a Game");
//		panel_4.add(btnNewButton_1);
//		
//		JButton btnNewButton_2 = new JButton("Local Game");
//		panel_4.add(btnNewButton_2);
//		
//		JButton btnNewButton_3 = new JButton("Create a new SP");
//		btnNewButton_3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				displaySpellPageCreationMenu();
//			}
//		});
//		panel_4.add(btnNewButton_3);
//		
//		JPanel panel_5 = new JPanel();
//		this.getContentPane().add(panel_5);
//		
//		JPanel panel_6 = new JPanel();
//		this.getContentPane().add(panel_6);
//		
//		JPanel panel_7 = new JPanel();
//		this.getContentPane().add(panel_7);
//		
//		JPanel panel_8 = new JPanel();
//		this.getContentPane().add(panel_8);
//		this.setVisible(true);
//		
//	}
//	
//	/**
//	 * Displays the menu for the SpellPageCreation
//	 */
//	public void displaySpellPageCreationMenu() {
//		
//		
//		this.setVisible(false);
//		
//		this.getContentPane().removeAll();
//		
//		this.getContentPane().setLayout(new GridLayout(2, 1, 0, 0));
//		
//		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		this.getContentPane().add(tabbedPane);
//		
//		JPanel panel_1 = new JPanel();
//		tabbedPane.addTab("Spell 1", null, panel_1, null);
//		panel_1.setLayout(new GridLayout(1, 3, 0, 0));
//		
//		JPanel panel_4 = new JPanel();
//		panel_1.add(panel_4);
//		panel_4.setLayout(new GridLayout(7, 1, 0, 0));
//		
//		JLabel lblNewLabel = new JLabel("Select an element");
//		panel_4.add(lblNewLabel);
//		
//		Choice choice = new Choice();
//		choice.add("Choose an element");
//		choice.add("Fire");
//		choice.add("Ice");
//		choice.add("Stone");
//		choice.add("Wind");
//		choice.add("Electricity");
//		choice.add("Darkness");
//		
//		panel_4.add(choice);
//		
//		JPanel panel_5 = new JPanel();
//		panel_1.add(panel_5);
//		panel_5.setLayout(new GridLayout(7, 1, 0, 0));
//		
//		JLabel lblSelectAShape = new JLabel("Select a shape");
//		panel_5.add(lblSelectAShape);
//		
//		Choice choice_1 = new Choice();
//		choice_1.add("Choose a shape");
//		choice_1.add("Fist");
//		choice_1.add("Sword");
//		choice_1.add("Ball");
//		choice_1.add("Special");
//		panel_5.add(choice_1);
//		
//		JPanel panel = new JPanel();
//		tabbedPane.addTab("Spell2", null, panel, null);
//		panel.setLayout(new GridLayout(1, 3, 0, 0));
//		
//		JPanel panel_7 = new JPanel();
//		panel.add(panel_7);
//		panel_7.setLayout(new GridLayout(7, 1, 0, 0));
//		
//		JLabel label = new JLabel("Select an element");
//		panel_7.add(label);
//		
//		Choice choice_2 = new Choice();
//		choice_2.add("Choose an element");
//		choice_2.add("Fire");
//		choice_2.add("Ice");
//		choice_2.add("Stone");
//		choice_2.add("Wind");
//		choice_2.add("Electricity");
//		choice_2.add("Darkness");
//		panel_7.add(choice_2);
//		
//		JPanel panel_8 = new JPanel();
//		panel.add(panel_8);
//		panel_8.setLayout(new GridLayout(7, 1, 0, 0));
//		
//		JLabel label_1 = new JLabel("Select a shape");
//		panel_8.add(label_1);
//		
//		Choice choice_3 = new Choice();
//		choice_3.add("Choose a shape");
//		choice_3.add("Fist");
//		choice_3.add("Sword");
//		choice_3.add("Ball");
//		choice_3.add("Special");
//		panel_8.add(choice_3);
//		
//		JPanel panel_9 = new JPanel();
//		tabbedPane.addTab("Spell3", null, panel_9, null);
//		panel_9.setLayout(new GridLayout(1, 3, 0, 0));
//		
//		JPanel panel_10 = new JPanel();
//		panel_9.add(panel_10);
//		panel_10.setLayout(new GridLayout(7, 1, 0, 0));
//		
//		JLabel label_2 = new JLabel("Select an element");
//		panel_10.add(label_2);
//		
//		Choice choice_4 = new Choice();
//		choice_4.add("Choose an element");
//		choice_4.add("Fire");
//		choice_4.add("Ice");
//		choice_4.add("Stone");
//		choice_4.add("Wind");
//		choice_4.add("Electricity");
//		choice_4.add("Darkness");
//		panel_10.add(choice_4);
//		
//		JPanel panel_11 = new JPanel();
//		panel_9.add(panel_11);
//		panel_11.setLayout(new GridLayout(7, 1, 0, 0));
//		
//		JLabel label_3 = new JLabel("Select a shape");
//		panel_11.add(label_3);
//		
//		Choice choice_5 = new Choice();
//		choice_5.add("Choose a shape");
//		choice_5.add("Fist");
//		choice_5.add("Sword");
//		choice_5.add("Ball");
//		choice_5.add("Special");
//		panel_11.add(choice_5);
//		
//		JPanel panel_3 = new JPanel();
//		this.getContentPane().add(panel_3);
//		panel_3.setLayout(new GridLayout(2, 1, 0, 0));
//		
//		JPanel panel_2 = new JPanel();
//		panel_3.add(panel_2);
//		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
//		
//		JLabel lblSpellPageName = new JLabel("Spell Page Name");
//		panel_2.add(lblSpellPageName);
//		
//		JTextField textField = new JTextField();
//		panel_2.add(textField);
//		textField.setColumns(10);
//		
//		JPanel panel_6 = new JPanel();
//		panel_3.add(panel_6);
//		
//		JButton btnValider = new JButton("Valider");
//		btnValider.setEnabled(false);
//		
//		if (choice.getSelectedItem()=="Choose an element")
//			btnValider.setEnabled(false);
//		
//		if(choice_1.getSelectedItem() == "Choose a shape")
//			btnValider.setEnabled(false);
//		
//		if(choice_2.getSelectedItem() == "Choose an element")
//			btnValider.setEnabled(false);
//		
//		if(choice_3.getSelectedItem() == "Choose a shape")
//			btnValider.setEnabled(false);
//		
//		if(choice_4.getSelectedItem() == "Choose an element")
//			btnValider.setEnabled(false);
//		
//		if(choice_5.getSelectedItem() == "Choose a shape")
//			btnValider.setEnabled(false);
//		
//		
//		
//		textField.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyPressed(KeyEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void keyReleased(KeyEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				btnValider.setEnabled(true);
//				
//			}
//			
//		});
//		
//		choice.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				if (choice.getSelectedItem() == "Choose an element")
//					btnValider.setEnabled(false);
//				else
//					btnValider.setEnabled(true);
//				
//			}
//			
//		});
//		
//		choice_1.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				if (choice_1.getSelectedItem() == "Choose a shape")
//					btnValider.setEnabled(false);
//				else
//					btnValider.setEnabled(true);
//				
//			}
//			
//		});
//		
//		choice_2.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				if (choice_2.getSelectedItem() == "Choose an element")
//					btnValider.setEnabled(false);
//				else
//					btnValider.setEnabled(true);
//				
//			}
//			
//		});
//		
//		choice_3.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				if (choice_3.getSelectedItem() == "Choose a shape")
//					btnValider.setEnabled(false);
//				else
//					btnValider.setEnabled(true);
//				
//			}
//			
//		});
//		
//		choice_4.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				if (choice_4.getSelectedItem() == "Choose an element")
//					btnValider.setEnabled(false);
//				else
//					btnValider.setEnabled(true);
//				
//			}
//			
//		});
//		
//		choice_5.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent arg0) {
//				if (choice_5.getSelectedItem() == "Choose a shape")
//					btnValider.setEnabled(false);
//				else
//					btnValider.setEnabled(true);
//				
//			}
//			
//		});
//
//		btnValider.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				askValidation();
//				displayMenu();
//			}
//		});
//		panel_6.add(btnValider);
//		
//		//reAllow the visibility of the window
//		this.setVisible(true);
//		
//		String res = null;
//		var components = this.getContentPane().getComponents();
//		for (Object comp: components) {
//			res+=comp+"\n";
//			if (comp.getClass()==JPanel)
//			
//		}
//		
//		System.out.println(res);
//	}





}