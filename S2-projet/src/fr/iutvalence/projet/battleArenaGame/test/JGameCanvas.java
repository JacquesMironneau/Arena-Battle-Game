package fr.iutvalence.projet.battleArenaGame.test;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
import fr.iutvalence.projet.battleArenaGame.pawn.TeamId;
 
 
public class JGameCanvas extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int sizeWidth=32;
	int sizeHeigth;
	ArrayList<Pawn> List;
	int boardSize;
	int width;
	int height;
	private Image[][] arrayImage;
	ArrayList<Rectangle> rec;
	
	public JGameCanvas(ArrayList<Pawn> pList,int pboardSize,int pwidth,int pheight){
		List = pList;
		boardSize=pboardSize;
		width=pwidth;
		height=pheight;
		this.rec = new ArrayList<Rectangle>();

		this.arrayImage = new Image[this.boardSize][this.boardSize];
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("AAAAAAAAAAA");
		File img = new File("ressources/p1.png");
		File img2 = new File("ressources/p2.png");
		File img3 = new File("ressources/vide.png");
		Image p1 = null, p2 = null, voidImg = null;
		System.out.println(getHeight()+ " "+ this.getWidth());
		try {
			p1 = ImageIO.read(img);
			p2 = ImageIO.read(img2);
			voidImg = ImageIO.read(img3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(((this.getHeight()*2)/3)/this.boardSize);
		System.out.println("WIDTH" + this.getWidth()+"HEIGHT"+this.getHeight());
		voidImg=voidImg.getScaledInstance(((this.getWidth()*2)/3)/this.boardSize,((this.getHeight()*2)/3)/this.boardSize, Image.SCALE_DEFAULT);
		p1=p1.getScaledInstance(((this.getWidth()*2)/3)/this.boardSize, ((this.getHeight()*2)/3)/this.boardSize, Image.SCALE_DEFAULT);
		p2=p2.getScaledInstance(((this.getWidth()*2)/3)/this.boardSize, ((this.getHeight()*2)/3)/this.boardSize, Image.SCALE_DEFAULT);
		this.sizeWidth=((this.getWidth()*2)/3)/this.boardSize;
		this.sizeHeigth=((this.getHeight()*2)/3)/this.boardSize;
		for(int i=0 ;i<boardSize;i++)
		{	
			for(int k=0;k<boardSize;k++)
				{		
					g.drawImage(voidImg, i*sizeWidth, k*sizeHeigth,this);
					this.arrayImage[i][k] = voidImg;
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
							if(p.getTeamId().getId()==1)
							{
								g.drawImage(p1, i*sizeWidth,  k*sizeHeigth,this);
								this.arrayImage[i][k] = p1;
							}
							else
							{
								g.drawImage(p2, i*sizeWidth,  k*sizeHeigth,this);
								this.arrayImage[i][k] = p2;

							}

								
						}
					}
			}
	}
//	Graphics2D g2 = (Graphics2D) g;

	int index = 0;
	for(Image[] i: this.arrayImage)
	{
		for(Image i1 : i)
		{
//			System.out.println("ALLO"+i1.getWidth(this)+"HE"+i1.getHeight(this)+" "+index+" "+this.sizeHeigth);
			Rectangle r = new Rectangle(i1.getWidth(this), i1.getHeight(this), this.sizeWidth, this.sizeHeigth);
//			g2.setColor(Color.BLACK);
//			g2.draw(r);
			this.rec.add(r);
			
			index++;
			
		}
	}
	this.addMouseListener(new MouseListener()
	{

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("EVENTAAA");
			for(Rectangle r: rec)
			{
//				System.out.println(e.getX());
				System.out.println(r.getX()+ " "+r.getY());
				if(r.getX()==e.getX())
					System.out.println(r.getX()==e.getX());
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
		
	});
}



public static void main(String[] args) {
	System.out.println();
	ArrayList<Pawn> List = new ArrayList<Pawn>();
	List.add(new Pawn(new TeamId(1),new Coordinate(0,2),"lol"));
	List.add(new Pawn(new TeamId(2),new Coordinate(0,6),"lol"));
	
//	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JFrame f = new JFrame();
//	f.setSize(screenSize.getWidth(),screenSize.getHeight());
	f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	JGameCanvas CC = new JGameCanvas(List,15,f.getWidth(),f.getHeight());
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setTitle("JULES");
	f.setVisible(true);
	f.setContentPane(CC);
	f.repaint();
	
	
}


@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
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
}