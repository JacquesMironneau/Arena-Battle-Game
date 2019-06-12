package fr.iutvalence.projet.battleArenaGame.view;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import fr.iutvalence.projet.battleArenaGame.Board;
import fr.iutvalence.projet.battleArenaGame.Game;
import fr.iutvalence.projet.battleArenaGame.GameController;
import fr.iutvalence.projet.battleArenaGame.UserController;
import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.PawnEffect;
import fr.iutvalence.projet.battleArenaGame.spell.Effect;
import fr.iutvalence.projet.battleArenaGame.spell.Shape;
import fr.iutvalence.projet.battleArenaGame.spell.Spell;
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
	private JFrame mainContainer;
	
	private GameController gameController;
	
	private UserController myUser;
	private JGameCanvas gameBoard;


	private JPanel boardPane;


	private JPanel champSelect;
	
	
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
		        this.mainContainer.setSize(screenSize);
		        this.mainContainer.setResizable(false);
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
	JGameCanvas JGC = new JGameCanvas(myBoard.getTurnOrder(),myBoard.getBoardSize(),this.mainContainer.getWidth(),this.mainContainer.getHeight());
	JGC.setLayout(null);
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
	System.out.println(str);
	JLabel label = new JLabel(str);
	label.setBounds(this.mainContainer.getWidth()/3,0,this.mainContainer.getWidth()/3,this.mainContainer.getHeight()/20);
	JGC.add(label);
	this.mainContainer.setContentPane(JGC);
	}


	@Override
	public void askActionChoice(int currentPlayerIndex) {
		JButton endTurn = (JButton) this.getContentPane().getComponentAt(this.getWidth()/6*4,this.getHeight()/204);
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
		ArrayList<GameView> gv = new ArrayList<GameView>();
		gv.add(new PlayerConsole(null));
		PlayerConsole P1 = new PlayerConsole(null);
		Game G = new Game(gv,2,3,15);
		P1.setGameController(G);
		gv.get(0).setGameController(G);


		PlayerWindow pw = new PlayerWindow();
		ArrayList<Shape> gameShapes = new ArrayList<Shape>();
		//Ball
		HashSet<Coordinate> ballShape = new HashSet<Coordinate>();
		ballShape.addAll(Arrays.asList(new Coordinate(0,0)));
		gameShapes.add(new Shape("Ball",10,2,5,3,ballShape));
		//Fist
		HashSet<Coordinate> fistShape = new HashSet<Coordinate>();
		fistShape.addAll(Arrays.asList(new Coordinate(0,0)));
		gameShapes.add(new Shape("Fist",15,1,1,2,fistShape));
		//Cross
		HashSet<Coordinate> crossShape = new HashSet<Coordinate>();
		crossShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(-2,0),new Coordinate(-1,0),new Coordinate(1,0),new Coordinate(2,0),new Coordinate(0,-2),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(0,2)));
		gameShapes.add(new Shape("Cross",10,3,5,4,crossShape));
		//Square
		HashSet<Coordinate> squareShape = new HashSet<Coordinate>();
		squareShape.addAll(Arrays.asList(new Coordinate(0,0),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(-1,0),new Coordinate(-1,-1),new Coordinate(-1,1),new Coordinate(1,0),new Coordinate(1,-1),new Coordinate(1,1)));
		gameShapes.add(new Shape("Square",10,3,4,4,squareShape));
		//Sword
		HashSet<Coordinate> swordShape = new HashSet<Coordinate>();
		swordShape.addAll(Arrays.asList(new Coordinate(-1,-1),new Coordinate(-1,0),new Coordinate(-1,1),new Coordinate(0,-1),new Coordinate(0,1),new Coordinate(1,-1),new Coordinate(1,0),new Coordinate(1,1)));
		gameShapes.add(new Shape("Sword",8,2,1,3,swordShape));
		//Beam
		HashSet<Coordinate> beamShape = new HashSet<Coordinate>();
		beamShape.addAll(Arrays.asList(new Coordinate(0,1),new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5)));
		gameShapes.add(new Shape("Beam",10,3,1,4,beamShape));
		
		Spell s1 =new Spell();
		s1.setShape(gameShapes.get(0));
		Spell s2 =new Spell();
		Spell s3 =new Spell();
		s2.setShape(gameShapes.get(1));
		s3.setShape(gameShapes.get(2));
		
		SpellPage page1 = new SpellPage("Namepage1",s1,s2,s3);
		Effect anEffect = Effect.Fire;
		page1.getSpell(0).setSpellEffect(anEffect);
		page1.getSpell(1).setSpellEffect(anEffect);
		page1.getSpell(2).setSpellEffect(anEffect);
		G.getBoard().getTurnOrder().get(0).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(1).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(2).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(3).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(4).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(5).setSpellPage(page1);
		G.getBoard().getTurnOrder().get(0).addEffect(new PawnEffect(anEffect));
		G.getBoard().getTurnOrder().get(1).addEffect(new PawnEffect(anEffect));
		G.getBoard().getTurnOrder().get(4).addEffect(new PawnEffect(anEffect));
		System.out.println(pw.mainContainer.getHeight());
		pw.displayBoard(G.getBoard(),2);
		pw.mainContainer.repaint();
		pw.mainContainer.setVisible(true);
	}
	
	@Override
    public void askPageSelection(int currentPlayerIndex) {
        Choice spellPages = (Choice) this.mainContainer.getComponent(1);
        int pageIndex = spellPages.getSelectedIndex();
        this.gameController.setPageRequest(currentPlayerIndex,this.myUser.getSpellPages().get(pageIndex));
    }

    @Override
    public void displaySpellPage() {
        Choice spellPages = (Choice) this.mainContainer.getComponent(1);
        for (SpellPage page : this.myUser.getSpellPages()) {
            spellPages.add(page.getPageName());
        }
        
    }

    @Override
    public void displaySelectForThisPawn(String pawnName) {
        this.mainContainer.add(new JLabel("Selectionnez une page pour le pion "+pawnName));
        this.mainContainer.add(new Choice());
        
    }
}
