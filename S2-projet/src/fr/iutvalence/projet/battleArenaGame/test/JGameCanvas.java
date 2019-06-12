package fr.iutvalence.projet.battleArenaGame.test;
 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	 * default width of the image
	 */
	private int sizeWidth=32;
	/**
	 * default height of the image
	 */
	private int sizeHeight=32;
	
	private ArrayList<Pawn> List;
	
	private int boardSize;
	
	private int width=1;
	
	private int height=1;
	
	private int xIndex;
	private int yIndex;
	
	private LocatedImage[][] arrayImage;
	
	private ArrayList<Rectangle> rec;
	
	public JGameCanvas(ArrayList<Pawn> pList,int pboardSize,int pwidth,int pheight){
		this.List = pList;
		this.boardSize=pboardSize;
		this.width=(pwidth*2)/3;
		this.height=(pheight*2)/3;
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
		File img = new File("ressources/newp1.png");
		File img2 = new File("ressources/newp2.png");
		File img3 = new File("ressources/newvide.png");
		Image p1 = null, p2 = null, voidImg = null, highlight = null;
		try {
			p1 = ImageIO.read(img);
			p2 = ImageIO.read(img2);
			voidImg = ImageIO.read(img3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		voidImg=voidImg.getScaledInstance(((this.width))/this.boardSize,((this.height))/this.boardSize, Image.SCALE_DEFAULT);
		p1=p1.getScaledInstance(((this.width))/this.boardSize, ((this.height))/this.boardSize, Image.SCALE_DEFAULT);
		p2=p2.getScaledInstance(((this.width))/this.boardSize, ((this.height))/this.boardSize, Image.SCALE_DEFAULT);
		this.sizeWidth=((this.width))/this.boardSize;
		this.sizeHeight=((this.height))/this.boardSize;
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		
		for(int i=0 ;i<boardSize;i++)
			for(int k=0;k<boardSize;k++)
					this.arrayImage[i][k] = new LocatedImage(null, 0, 0);
					

		for(int i=0 ;i<boardSize;i++)
		{	
			for(int k=0;k<boardSize;k++)
			{		
					g.drawImage(voidImg, i*sizeWidth, k*sizeHeight,this);
					this.arrayImage[i][k].setImg(voidImg);
					this.arrayImage[i][k].setCoordX(i*sizeWidth);
					this.arrayImage[i][k].setCoordY(k*sizeHeight);



			}
		}

	for(int i=0 ;i<boardSize;i++)
	{	
		for(int k=0;k<boardSize;k++)
			{		
				for(Pawn p : List)
					{
						if(p.getPos().getCoordX()==i && p.getPos().getCoordY()==k )
						{
							if(p.getTeamId()==1)
							{
								g.drawImage(p1, i*sizeWidth,  k*sizeHeight,this);
								this.arrayImage[i][k].setImg(p1);
								this.arrayImage[i][k].setCoordX(i*sizeWidth);
								this.arrayImage[i][k].setCoordY(k*sizeHeight);
							}
							else
							{
								g.drawImage(p2, i*sizeWidth,  k*sizeHeight,this);
								this.arrayImage[i][k].setImg(p2);
								this.arrayImage[i][k].setCoordX(i*sizeWidth);
								this.arrayImage[i][k].setCoordY(k*sizeHeight);
							}

								
						}
					}
			}
	}

	
	for(int indexRow = 0; indexRow < this.boardSize; indexRow++)
		for(int indexCol = 0; indexCol < this.boardSize; indexCol++)
		{

			this.rec.add(new Rectangle(this.arrayImage[indexRow][indexCol].getCoordX(), this.arrayImage[indexRow][indexCol].getCoordY(), this.sizeWidth, this.sizeHeight));
		}

	this.addMouseListener(new MouseListener()
	{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			for(Rectangle r: rec)
			{
				if(r.contains(e.getX(),e.getY()))
				{
					
					xIndex = (int)r.getX()/sizeWidth;
					yIndex= (int)r.getY()/sizeHeight;
					System.out.println("go");
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
	

}



	public static void main(String[] args) {
		ArrayList<Pawn> List = new ArrayList<Pawn>();
		List.add(new Pawn(1,new Coordinate(0,2),"lol"));
		List.add(new Pawn(2,new Coordinate(0,6),"lol"));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame f = new JFrame();
		f.setSize(screenSize);
		
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		System.out.println(f.getWidth());
		JGameCanvas CC = new JGameCanvas(List,15,f.getWidth(),f.getHeight());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		List.remove(1);
	
		List.add(new Pawn(2,new Coordinate(6,6),"lol"));
		
		ArrayList<Pawn> t = new ArrayList();
		t.add(new Pawn(3,new Coordinate(1,6),"lol"));
	
		t.add(new Pawn(2,new Coordinate(4,6),"lol"));
	
		t.add(new Pawn(4,new Coordinate(9,6),"lol"));
	
		t.add(new Pawn(2,new Coordinate(12,6),"lol"));
	
		
		
		CC.refresh(t);
	
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("EVENT");
		for(Rectangle r: rec)
		{
			if(r.contains(e.getX(),e.getY()))
				System.out.println("TOUCHE TOUCHE TOUCHE");
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
	
		System.out.println("EVENT");
		for(Rectangle r: rec)
		{
			if(r.contains(e.getX(),e.getY()))
				System.out.println("TOUCHE TOUCHE TOUCHE");
		}
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
//		this.arrayImage[row][col].setImg(highlight);
	}
	
	
}