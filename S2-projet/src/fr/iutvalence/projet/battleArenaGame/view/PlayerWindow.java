package fr.iutvalence.projet.battleArenaGame.view;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
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
	 * 
	 */
	private static final long serialVersionUID = -8426609022478204799L;

	
	/**
	 * This pane contains all the container and the visible informations on the screen.
	 */
	private JLayeredPane mainContainer;
	
	private GameController gameController;
	
	private UserController myUser;
	private JGameCanvas gameBoard;
	
	
	/**
	 * Constructor of a new PlayerWindow
	 * It initializes the PlayerWindow with a default title,
	 * a default size,
	 * the default operation when closing the window,
	 * 
	 */
	public PlayerWindow(UserController pUserController) {
		/*
		 * Super Constructor
		 */
		super();
		this.myUser = pUserController;		
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
		
		
		this.mainContainer = new JLayeredPane();
		this.setContentPane(this.mainContainer);
		
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
		this.setVisible(false);
		for(Component comp : this.getContentPane().getComponents()) {
			comp.setEnabled(true);
		}
		JButton move = new JButton("Se déplacer");
		move.setBounds(this.getWidth()/6*2, this.getHeight()/3*2, this.getWidth()/6, this.getHeight()/3);
		this.getContentPane().add(move);
		
		JButton spell = new JButton("Lancer un sort");
		spell.setBounds(this.getWidth()/6,this.getHeight()/3*2,this.getWidth()/6,this.getHeight()/3);
		this.getContentPane().add(spell);
		
		JButton endTurn = new JButton("Terminer le tour");
		endTurn.setBounds(this.getWidth()/6*3,this.getHeight()/3*2,this.getWidth()/6,this.getHeight()/3);
		this.getContentPane().add(endTurn);

		this.setVisible(true);
		
		JButton s1 = new JButton("Sort 1");
		s1.setBounds(0, this.getHeight()/3*2, this.getWidth()/6, this.getHeight()/3/3);
		this.getContentPane().add(s1);
		s1.setEnabled(false);
		JButton s2 = new JButton("Sort 2");
		s2.setBounds(0, this.getHeight()/3*2+this.getHeight()/3/3, this.getWidth()/6, this.getHeight()/3/3);
		this.getContentPane().add(s2);
		s2.setEnabled(false);
		JButton s3 = new JButton("Sort 3");
		s3.setBounds(0, this.getHeight()/3*2+(this.getHeight()/3/3)*2, this.getWidth()/6, this.getHeight()/3/3);
		this.getContentPane().add(s3);
		s3.setEnabled(false);
		
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
		JTextArea messages = (JTextArea) this.getContentPane().getComponentAt(this.getWidth()/3*2+1,1);
		messages.setText("Au tour du joueur n° "+numPlayer);
		
		
	}

	@Override
	public void displayMoveSelection() {
		this.gameBoard.highlight(this.gameBoard.getYIndex(), this.gameBoard.getXIndex());
		JTextArea messages = (JTextArea) this.getContentPane().getComponentAt(this.getWidth()/3*2+1, 1);
		messages.setText("CASE SELECT : (" + this.gameBoard.getXIndex() + "," + this.gameBoard.getYIndex() + ")");
		
	}

	@Override
	public void displaySpellSelection() {
		this.setVisible(false);
		
		JButton s1 = (JButton) this.getContentPane().getComponentAt(1, this.getHeight()/3*2+1);
		s1.setEnabled(true);
		
		JButton s2 = (JButton) this.getContentPane().getComponentAt(1,this.getHeight()/3*2+this.getHeight()/3/3+1);
		s2.setEnabled(true);
		
		JButton s3 = (JButton) this.getContentPane().getComponentAt(1,this.getHeight()/3*2+(this.getHeight()/3/3)*2+1);
		s3.setEnabled(true);
		
		
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
		
		
		JTextArea messages = new JTextArea();
		messages.setBounds(this.getWidth()/3*2, 0, this.getWidth()/3, this.getHeight()/6);
		this.getContentPane().add(messages);
		
		JTextArea infos = new JTextArea();
		infos.setBounds(this.getWidth()/3*2, this.getHeight()/6, this.getWidth()/3, this.getHeight()-this.getHeight()/6);
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
		JButton endTurn = (JButton) this.getContentPane().getComponentAt(this.getWidth()/6*3+1,this.getHeight()/3*2+1);
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Component comp : mainContainer.getComponents()) {
					comp.setEnabled(false);
				}
				gameController.actionRequest(currentPlayerIndex, StatusMessages.END_TURN);
			}
		});
		
		JButton move = (JButton) this.getContentPane().getComponentAt(this.getWidth()/6*2+1,this.getHeight()/3*2+1);
		move.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameController.actionRequest(currentPlayerIndex, StatusMessages.MOVE);
			}
		});
		
		JButton spell = (JButton) this.getContentPane().getComponentAt(this.getWidth()/6+1, this.getHeight()/3*2+1);
		spell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameController.actionRequest(currentPlayerIndex, StatusMessages.LAUNCH_SPELL);
			}
		});
		
	}
	
	@Override
	public void askMove(int currentPlayerIndex) {
		this.gameController.moveRequest(currentPlayerIndex,new Coordinate(this.gameBoard.getXIndex(),this.gameBoard.getYIndex()));
		
	}

	@Override
	public void askSpell(int currentPlayerIndex) {
		JButton s1 = (JButton) this.getContentPane().getComponentAt(1, this.getHeight()/3*2+1);
		s1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameController.spellRequest(currentPlayerIndex, 0, new Coordinate(gameBoard.getXIndex(),gameBoard.getYIndex()));
			}
		});
		
		JButton s2 = (JButton) this.getContentPane().getComponentAt(1,this.getHeight()/3*2+this.getHeight()/3/3+1);
		s2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameController.spellRequest(currentPlayerIndex, 1, new Coordinate(gameBoard.getXIndex(),gameBoard.getYIndex()));
			}
		});
		
		JButton s3 = (JButton) this.getContentPane().getComponentAt(1,this.getHeight()/3*2+(this.getHeight()/3/3)*2+1);
		s3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameController.spellRequest(currentPlayerIndex, 2, new Coordinate(gameBoard.getXIndex(),gameBoard.getYIndex()));
			}
		});
		
		
	}



	
	public static void main(String[] args) {
//		Game game = new Game(2,3,9);
		PlayerWindow p = new PlayerWindow();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		PlayerWindow pw = new PlayerWindow();
//		pw.setGameController(game);
//		pw.displayBoard(game.getBoard(), 2);
//		pw.askActionChoice(0);
//		pw.displayCreateSpell(1);
//		pw.displaySpellSelection();
//		pw.displaySpellPage(null);
		
	}

	@Override
	public void askPageSelection(int currentPlayerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySpellPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySelectForThisPawn(String pawnName) {
		// TODO Auto-generated method stub
		
	}


}