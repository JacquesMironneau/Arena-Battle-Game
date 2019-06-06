package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;


/*
 * Notes 
 * Use BufferedImage Class and use getSubImage() method from that class to extract the textures in the tileset
 * 
 */

//enum Tuile{
//	GRASS,GRASS_STONE,GRASS_BAGS,T3,T4,T5,T6,T7,T8,T9,
//	TREE,TREE_CHOMP,TREE_DEAD,T13,T14,T15,T16,T17,T18,T19,
//	ROAD_H,ROAD_V,ROAD_HV_DOWN,ROAD_HV_UP,ROAD_VH_RIGHT,ROAD_VH_LEFT,ROAD_CROSS,T27,T28,T29,
//	WALL,WALL_POSTER,WALL_END_RIGHT,WALL_END_LEFT,T34,T35,T36,T37,T38,T39,
//	T40,T41,T42,T43,T44,T45,T46,T47,T48,T49,
//	NEWS,T51,RES_1,RES_2,BUSS_1,BUSS_2,HOSP_1,HOSP_2,MARK_1,MARK_2,
//	PIZZ_1,PIZZ_2,RES_3,RES_4,BUSS_3,BUSS_4,HOSP_3,HOSP_4,MARK_3,MARK_4,
//	PIZZ_3,PIZZ_4,RES_5,RES_6,BUSS_5,BUSS_6,HOSP_5,HOSP_6,MARK_5,MARK_6
//}
//public class TileMap extends JPanel{
//	private static final int tileWidth = 32;
//	private static final int tileHeight = 32;
//	private Tuile[][] map;
//	private Image tileset;
//	
//	public TileMap(){
//		ArrayList<Pawn> listPawn = new ArrayList<Pawn>();
//		listPawn.add(new Pawn(new TeamId(1),new Coordinate(1,5),"fgrhzfs"));
//		listPawn.add(new Pawn(new TeamId(1),new Coordinate(4,1),"aahahahah"));
//
//		this.map = new Tuile[9][9];
//		for (int i=0; i<9;i++) {
//			for (int j = 0; j<9;j++) {
//				this.map[i][j] = Tuile.GRASS;
//			}
//		}
//		for (Pawn p : listPawn) {
//			this.map[p.getPos().getCoordY()][p.getPos().getCoordX()] = Tuile.TREE_CHOMP;
//		}
//		
//		tileset = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("../ressources/tileset.png"));
//	}
//	
//	protected void paintComponent(Graphics g) {
//		g.setColor(Color.black);
//		g.fillRect(0, 0, getWidth(), getHeight());
//		
//		for(int i=0; i<9; i++) {
//			for(int j=0; j<9;j++) {
//				drawTile(g,map[i][j], i*tileWidth, j*tileHeight);
//				
//			}
//		}
//	}
//
//	private void drawTile(Graphics g, Tuile tile, int x, int y) {
//		// TODO Auto-generated method stub
//		int mx = tile.ordinal()%10;
//		int my= tile.ordinal()%10;
//		g.drawImage(tileset, x, y, x+tileWidth, y+tileHeight, mx*tileWidth,my*tileHeight,mx*tileWidth+tileWidth,my*tileHeight+tileHeight,this);
//		
//	}
//}
