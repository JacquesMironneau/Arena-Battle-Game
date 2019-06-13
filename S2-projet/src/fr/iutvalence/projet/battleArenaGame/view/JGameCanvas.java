package fr.iutvalence.projet.battleArenaGame.view;
 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.iutvalence.projet.battleArenaGame.move.Coordinate;
import fr.iutvalence.projet.battleArenaGame.pawn.Pawn;

 
 //Action, Mouvement,
//Tour
//Ou deplacer
//Quel sort et ou deplacer
//maj board
/**
 *class who display the board using jpanel with event listener for the ihm Player
 */
public class JGameCanvas extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	/**
	 * default size of the image
	 */
	private int size;
	
	private ArrayList<Pawn> List;
	
	private int boardSize;
	
	private int width;
	
	private int height;
	
	private Coordinate askCoordinate;
	
	private int xIndex;
	private int yIndex;
	
	private boolean isListenerOn;
	
	private LocatedImage[][] arrayImage;
	
	private ArrayList<Rectangle> rec;
	
	public JGameCanvas(ArrayList<Pawn> pList,int pboardSize,int pwidth,int pheight){
		this.List = pList;
		this.boardSize=pboardSize;
		this.width=pwidth;
		this.height=pheight;
		this.rec = new ArrayList<Rectangle>();
		this.arrayImage = new LocatedImage[this.boardSize][this.boardSize];
	}
	
	
	public void setList(ArrayList<Pawn> list)
	{
		List = list;
	}
	
	public ArrayList<Pawn> getList()
	{
		return this.List;
	}

	/**
	 * refresh the panel 
	 */
	public void refresh(ArrayList<Pawn> list)
	{
		this.setList(list);
		this.getParent().repaint();
	}
	
	/**
	 * repaint the board with the image and mouse listener
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		final int rows =10;
		final int cols = 16;
		BufferedImage bigImg = null;
		this.size=this.height/this.boardSize;
		try
		{
			bigImg = ImageIO.read(new File("ressources/big-image.png"));
			bigImg = JGameCanvas.resize(bigImg, this.size*cols,this.size*rows);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		BufferedImage[] sprites = new BufferedImage[rows*cols];

		for(int j = 0; j <rows; j++)
			for(int i = 0; i < cols; i++)
				sprites[(j*cols)+i] = bigImg.getSubimage(i*this.size, j*this.size, this.size, this.size);
		
	
		
		
		File img3 = new File("ressources/newvide.png");
		Image voidImg = null;
		try {
			voidImg = ImageIO.read(img3);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		for(int i=0 ;i<boardSize;i++)
			for(int k=0;k<boardSize;k++)
					this.arrayImage[i][k] = new LocatedImage(null, 0, 0);
					

		for(int i=0 ;i<boardSize;i++)
		{	
			for(int k=0;k<boardSize;k++)
			{		
					g.drawImage(voidImg, i*size, k*size,this);
					this.arrayImage[i][k].setImg(voidImg);
					this.arrayImage[i][k].setCoordX(i*size);
					this.arrayImage[i][k].setCoordY(k*size);



			}
		}


		for(Pawn p : List)
		{
			g.drawImage(sprites[p.getTeamId()], p.getPos().getCoordX()*size,  p.getPos().getCoordY()*size,this);
				this.arrayImage[p.getPos().getCoordX()][p.getPos().getCoordY()].setImg(sprites[p.getTeamId()]);
				this.arrayImage[p.getPos().getCoordX()][p.getPos().getCoordY()].setCoordX(p.getPos().getCoordX()*size);
				this.arrayImage[p.getPos().getCoordX()][p.getPos().getCoordY()].setCoordY(p.getPos().getCoordY()*size);
		}
			


	
	for(int indexRow = 0; indexRow < this.boardSize; indexRow++)
		for(int indexCol = 0; indexCol < this.boardSize; indexCol++)
		{

			this.rec.add(new Rectangle(this.arrayImage[indexRow][indexCol].getCoordX(), this.arrayImage[indexRow][indexCol].getCoordY(), this.size, this.size));
		}

	this.addMouseListener(new MouseListener()
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(!isListenerOn) return;
			for(Rectangle r: rec)
			{
				if(r.contains(e.getX(),e.getY()))
				{
					
					xIndex = (int)r.getX()/size;
					yIndex= (int)r.getY()/size;
					
					askCoordinate = new Coordinate(xIndex, yIndex);
					System.out.println("clicked");
				}
					
			}		
		}

		@Override
		public void mousePressed(MouseEvent e) {
				
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	});
	this.EnableListener();
	}
	
	public void EnableListener()
	{
		this.isListenerOn = true;
	}
	
	
	public void DisableListener()
	{
		this.isListenerOn = false;
	}
	
	public Coordinate getClickedCoordinate()
	{
		return this.askCoordinate;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	
	
	public static void main(String[] args) {
		ArrayList<Pawn> List = new ArrayList<Pawn>();
		List.add(new Pawn(1,new Coordinate(0,2),"lol"));
		List.add(new Pawn(2,new Coordinate(0,6),"lol"));
		
		
		JFrame f = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(screenSize);
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JGameCanvas CC = new JGameCanvas(List,40,f.getWidth(),f.getHeight());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		f.setTitle("JULES");
		f.setVisible(true);
		f.setContentPane(CC);
		f.repaint();
		try
		{
			Thread.sleep(4000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	
		List.remove(1);
	
		List.add(new Pawn(2,new Coordinate(6,6),"lol"));
		
		ArrayList<Pawn> t = new ArrayList<Pawn>();
		t.add(new Pawn(3,new Coordinate(1,6),"lol"));
	
		t.add(new Pawn(2,new Coordinate(4,6),"lol"));
	
		t.add(new Pawn(15,new Coordinate(9,6),"lol"));
	
		t.add(new Pawn(143,new Coordinate(12,6),"lol"));
	
		
		
		CC.refresh(t);
	
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}


	@Override
	public void mousePressed(MouseEvent e) {

	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public int getXIndex() {
		return this.xIndex;
	}
	
	public int getYIndex() {
		return this.yIndex;
	}

	public ArrayList<Rectangle> getRec(){
		return this.rec;
	}
	
	public void highlight(int col, int row) {
	}
	
	public void setClickedCoordinate(Coordinate coord)
	{
		this.askCoordinate=coord;
	}
	
}