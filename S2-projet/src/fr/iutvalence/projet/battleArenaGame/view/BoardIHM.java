package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;

enum Tile{
	FREE,PAWN
}


public class BoardIHM extends JPanel{
	
	//TODO init this
	private int boardSize=15;
	private int ts = 25;
	private Image tileset;
	private Tile board[][] = new Tile[boardSize][boardSize];
	
	public BoardIHM() {
		tileset = Toolkit.getDefaultToolkit().getImage("resources/tileset.png");
	}

	protected void createBoard(){
	ArrayList<Pawn> listPawn = new ArrayList<Pawn>();
	listPawn.add(new Pawn(1,new Coordinate(1,5),"fgrhzfs"));
	listPawn.add(new Pawn(1,new Coordinate(4,1),"aahahahah"));
	for(int i=0;i<boardSize;i++)
		for(int k=0;k<boardSize;k++) {
			board[i][k] = Tile.FREE;
			//TODO init listPawn
			for(Pawn p : listPawn) {
				if (p.getPos().getCoordX()==i && p.getPos().getCoordY()==k)
					board[i][k] = Tile.PAWN;							
			}
		}
	}		

	public void drawTile(Graphics g,Tile t, int x,int y) {
		int mx = t.ordinal()%10;
        int my = t.ordinal()/10;
		g.drawImage(tileset, x, y, x+ts, y+ts,mx*ts, my*ts,  mx*ts+ts, my*ts+ts, this);
//		g.drawImage(tileset, x, y, this);
	}
	
	 protected void paintComponent(Graphics g) {
	        g.setColor(Color.BLUE);
	        g.fillRect(0, 0, getWidth(), getHeight());
	        for(int i=0;i<boardSize;i++)
	    		for(int k=0;k<boardSize;k++)
	                drawTile(g, board[k][i],i*ts ,k*ts);
	    }

				
	 
	 public static void main(String[] args) {
		JFrame jp1 = new JFrame();
		BoardIHM b = new BoardIHM();
		jp1.getContentPane().add(b, BorderLayout.CENTER);
        jp1.setSize(new Dimension(500,500));
        jp1.setVisible(true);
		b.paintComponent(b.getGraphics());
		jp1.repaint();
	 }	 
}


