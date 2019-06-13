package fr.iutvalence.projet.battleArenaGame.view;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.spell.SpellPage;

/**
 * PlayerWindow class
 * 
 * This class represents a player with a gui (window with buttons and listeners)
 * @author durantho
 *
 */
public class PlayerWindow extends JFrame implements GameView{
	

	
	/**
	 * This pane contains all the container and the visible informations on the screen.
	 */
	private JFrame mainContainer;
	
	private GameController gameController;
	
	private UserController myUser;
	private JGameCanvas gameBoard;

	private StatusMessages actionChoice;
	private int spellChoice;
	private int pageChoice;

	private JPanel boardPane;
	private JPanel selectionPane;
	private JPanel actionButtons;
	private JPanel spellButtons;
	
	

	private JPanel champSelect;
	
	
	/**
	 * Constructor of a new PlayerWindow
	 * It initializes the PlayerWindow with a default title,
	 * a default size,
	 * the default operation when closing the window,
	 * 
	 */
	public PlayerWindow(UserController uc) {
		        /*
		         * Super Constructor
		         */
		        this.mainContainer = new JFrame();
		        this.myUser = uc;
		        
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
		        this.mainContainer.setSize(screenSize);
		        this.mainContainer.setResizable(false);
		        
		        createActionPanel();
		        createSpellPanel();
		        createBoardPanel();
		        createPageSelectionPanel();
		        this.mainContainer.setVisible(true);
	}
	
	private void createBoardPanel()
	{
		this.boardPane = new JPanel();
		this.boardPane.setLayout(null);
		
		this.boardPane.add(actionButtons);
		
		JTextArea infos = new JTextArea();
		infos.setBounds(this.mainContainer.getWidth()*2/3,0,this.mainContainer.getWidth()*1/3,this.mainContainer.getHeight()*2/3);
		infos.setEditable(false);
		this.boardPane.add(infos);
		
		
		JTextArea spellDetail = new JTextArea();
		spellDetail.setBounds(this.mainContainer.getWidth()*2/3,this.mainContainer.getHeight()*2/3,this.mainContainer.getWidth()*1/3,this.mainContainer.getHeight()*1/3);
		spellDetail.setEditable(false);
		this.boardPane.add(spellDetail);
		
	}

	private void createPageSelectionPanel()
	{
		this.selectionPane = new JPanel();
		this.selectionPane.setLayout(null);
		
		JComboBox<String> listPage = new JComboBox<String>();
		listPage.setBounds(this.mainContainer.getWidth()/4, this.mainContainer.getHeight()/8, this.mainContainer.getWidth()/4*2, this.mainContainer.getHeight()/8);
		for(SpellPage page :this.myUser.getSpellPages())
			listPage.addItem(page.getPageName());
		this.selectionPane.add(listPage);
	
		JButton validationButton = new JButton("Valider");
		validationButton.setBounds(this.mainContainer.getWidth()/4, this.mainContainer.getHeight()/4, this.mainContainer.getWidth()/4*2, this.mainContainer.getHeight()/8);
		validationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pageChoice = listPage.getSelectedIndex();
			}
		});
		
		this.selectionPane.add(validationButton);
	
	}

	private void createActionPanel()
	{
		this.actionButtons = new JPanel();
		GridLayout buttonsLayout = new GridLayout(0,3);
		this.actionButtons.setLayout(buttonsLayout);
		this.actionButtons.setBounds(0,this.mainContainer.getHeight()*2/3,this.mainContainer.getWidth()*2/3,this.mainContainer.getHeight()*1/3);
		
		JButton moveButton = new JButton("Move");
		moveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionChoice = StatusMessages.MOVE;
			}
		});
		this.actionButtons.add(moveButton);
		
		
		JButton castButton = new JButton("Cast");
		castButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionChoice = StatusMessages.MOVE;
			}
		});
		this.actionButtons.add(castButton);
		
		
		JButton endButton = new JButton("End Turn");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Component comp : mainContainer.getComponents()) {
					comp.setEnabled(false);
				}
				actionChoice = StatusMessages.END_TURN;
			}
		});
		this.actionButtons.add(endButton);
	}
	
	
	private void createSpellPanel()
	{
		this.spellButtons = new JPanel();
		GridLayout buttonsLayout = new GridLayout(0,3);
		this.spellButtons.setLayout(buttonsLayout);
		this.spellButtons.setBounds(0,this.mainContainer.getHeight()*2/3,this.mainContainer.getWidth()*2/3,this.mainContainer.getHeight()*1/3);
		
		JButton moveButton = new JButton("1");
		moveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spellChoice = 0;
			}
		});
		this.spellButtons.add(moveButton);
		
		
		JButton castButton = new JButton("2");
		castButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spellChoice = 1;
			}
		});
		this.spellButtons.add(castButton);
		
		
		JButton endButton = new JButton("3");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Component comp : mainContainer.getComponents()) {
					comp.setEnabled(false);
				}
				spellChoice = 2;
			}
		});
		this.spellButtons.add(endButton);
		
		
	}
	private void switchPanel(JPanel panel)
	{
		this.mainContainer.remove(this.mainContainer.getContentPane());
		this.mainContainer.setContentPane(panel);
		this.mainContainer.validate();
		this.mainContainer.repaint();
	}
	
	private void switchButtons(JPanel panel)
	{
		this.boardPane.getComponent(0);
		this.boardPane.add(panel,0);
		this.boardPane.validate();
		this.boardPane.repaint();
	}

	public void setGameController(GameController controller) {
		this.gameController = controller;
	}
	
	public boolean askValidation() {
		//create a new confirm dialog to be sure that the player enter the correct values
		if(JOptionPane.showConfirmDialog(mainContainer,"Are you sure ?","Confirmation",JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION){
//			this.displayMenu();
			return true;
		}
		return false;
	}

	@Override
	public void displayStatus(StatusMessages error) {
		JOptionPane.showMessageDialog(mainContainer, error.getStatusMessage());
		
	}


	@Override
	public void displayChoiceAction() {
		switchButtons(actionButtons);
	}

	@Override
	public void displayEnd(String winTeam) {

		JOptionPane.showMessageDialog(mainContainer, "Victoire de l'équipe "+winTeam.getBytes());
		//displayMenu
		
	}


	@Override
	public void displayMoveDone() {
		JTextArea moveDoneArea = new JTextArea(StatusMessages.MOVE_DONE.getStatusMessage());
		moveDoneArea.setBounds(this.getWidth()-this.getWidth()/5,this.getHeight()-this.getHeight()/20,this.getWidth()/5,this.getHeight()/20);
		
	}

	@Override
	public void displaySpellSelection(SpellPage pPage) {
		
		switchButtons(spellButtons);
		JTextArea spellDetail = (JTextArea) this.boardPane.getComponent(2);
		
		String str = "";
		for(int spellIndex =0;spellIndex<3;spellIndex++)
		{
			if(pPage.getSpell(spellIndex)!=null)
				str+= spellIndex+1 +" )  "+ pPage.getSpell(spellIndex).toString();
			else
				str+=spellIndex+1 +")\n";
		}
		spellDetail.setText(str);
		
	
	}

	@Override
	public void diplaySizeError() {
		JOptionPane.showMessageDialog(mainContainer, "the size you entered isn't valid", "Warning", JOptionPane.WARNING_MESSAGE);
		
	}

	@Override
	public void displayNextTurn(int numPlayer) {
		
		
		
	}

	@Override
	public void displayMoveSelection() {
		
	}

	@Override
	public void displayBoard(Board myBoard, int nbPlayer) {
	JGameCanvas JGC = new JGameCanvas(myBoard.getTurnOrder(),myBoard.getBoardSize(),this.mainContainer.getWidth(),this.mainContainer.getHeight());
	JGC.setLayout(null);
	JGC.setBounds(0, 0, this.mainContainer.getWidth()*2/3,this.mainContainer.getHeight()*2/3);
	String str = "";
			//Pawns detail
			str += myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getName() + " : HP:" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getHealthPoints() + "/100 AP:" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getActionPoints() + "/6 MP:"
					+ myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getMovePoints() + "/6\n" 
					+ "Spell 1 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(0).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(0).getDefaultCooldown()
					+ "\nSpell 2 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(1).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(1).getDefaultCooldown()
					+ "\nSpell 3 :" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(2).getCurrentCooldown() + "/" + myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getSpellPage().getSpell(2).getDefaultCooldown();
			str+= "\nCurrent effects :";
					for(PawnEffect effect :myBoard.getTurnOrder().get(myBoard.getCurrentPawnIndex()).getEffect())
						str+= effect.getEffectName();
					str+="\n";
			
			
				for(int teamIndex = 0; teamIndex < nbPlayer; teamIndex++)
				{
					str += "Team" + teamIndex +":\n";
					for(Pawn p1 : myBoard.getTurnOrder())
						if(p1.getTeamId()==teamIndex)
							{
								str += p1.getName()+": HP:"+p1.getHealthPoints()+"/"+Pawn.DEFAULT_HEALTH_POINTS+"   Effects : ";
								for(PawnEffect effect :p1.getEffect())
									str+= effect.getEffectName() +"  ";
								str+="\n";
							}
							
				}
	JTextArea txt = (JTextArea) this.boardPane.getComponent(1);
	txt.setText(str);
	//	label.setBounds(this.mainContainer.getWidth()/3,0,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
	JGC.setBounds(0,0,this.mainContainer.getWidth()*2/3,this.mainContainer.getHeight()*2/3);
	this.boardPane.add(JGC);
	this.gameBoard = JGC;
	switchPanel(this.boardPane);
	}

	@Override
	public void askActionChoice(int currentPlayerIndex) 
	{
		this.actionChoice = StatusMessages.WAIT;
		for(Component comp :this.actionButtons.getComponents())
			comp.setEnabled(true);
		while(actionChoice.equals(StatusMessages.WAIT))
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gameController.actionRequest(currentPlayerIndex, actionChoice);
	}
	
	@Override
	public void askMove(int currentPlayerIndex) {
		this.gameBoard.EnableListener();
		this.gameBoard.setClickedCoordinate(JGameCanvas.NULL_COORDINATE);
		while(this.gameBoard.getClickedCoordinate().equals(JGameCanvas.NULL_COORDINATE))
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.gameBoard.DisableListener();
		
		for(Component comp :this.actionButtons.getComponents())
			comp.setEnabled(false);
				
		this.gameController.moveRequest(currentPlayerIndex,gameBoard.getClickedCoordinate());
		
	}

	@Override
	public void askSpell(int currentPlayerIndex) {
		this.spellChoice = -1;
		for(Component comp :this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(true);
		while(this.spellChoice == -1)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Component comp :this.mainContainer.getContentPane().getComponents())
			if(comp instanceof JButton)
				comp.setEnabled(false);
		this.gameBoard.EnableListener();
		this.gameBoard.setClickedCoordinate(JGameCanvas.NULL_COORDINATE);
		while(this.gameBoard.getClickedCoordinate().equals(JGameCanvas.NULL_COORDINATE))
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.gameBoard.DisableListener();
				gameController.spellRequest(currentPlayerIndex, spellChoice, this.gameBoard.getClickedCoordinate());
			
		
			
	}
	
	@Override
    public void askPageSelection(int currentPlayerIndex) {
		this.pageChoice = -1;
		this.mainContainer.getContentPane().getComponent(1).setEnabled(true);
		while(this.pageChoice == -1)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.mainContainer.getContentPane().getComponent(1).setEnabled(false);
        this.gameController.setPageRequest(currentPlayerIndex,this.myUser.getSpellPages().get(pageChoice));
    }

    @Override
    public void displaySelectForThisPawn(String pawnName) {
        switchPanel(this.selectionPane);
    }
}
