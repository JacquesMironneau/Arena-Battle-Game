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
 
 //Action, Mouvement,
//Tour
//Ou deplacer
//Quel sort et ou deplacer
//maj board
public class JGameCanvas extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int sizeWidth=32;
	int sizeHeight;
	ArrayList<Pawn> List;
	int boardSize;
	int width;
	int height;
	private LocatedImage[][] arrayImage;
	ArrayList<Rectangle> rec;
	
	public JGameCanvas(ArrayList<Pawn> pList,int pboardSize,int pwidth,int pheight){
		List = pList;
		boardSize=pboardSize;
		width=pwidth;
		height=pheight;
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

	public void refresh(ArrayList<Pawn> list)
	{
		this.setList(list);
		this.getParent().repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		g.setColor(Color.MAGENTA);
//		g.fillRect(0, this.getWidth(), this.getWidth(), this.getHeight());
//		g.drawString(", 1000, 1000);
		System.out.println(" AAAAAAAAAAA" +this.getWidth());
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
//		System.out.println(((this.getHeight()*2)/3)/this.boardSize);
//		System.out.println("WIDTH" + this.getWidth()+"HEIGHT"+this.getHeight());
		voidImg=voidImg.getScaledInstance(((this.getWidth()*2)/3)/this.boardSize,((this.getHeight()*2)/3)/this.boardSize, Image.SCALE_DEFAULT);
		p1=p1.getScaledInstance(((this.getWidth()*2)/3)/this.boardSize, ((this.getHeight()*2)/3)/this.boardSize, Image.SCALE_DEFAULT);
		p2=p2.getScaledInstance(((this.getWidth()*2)/3)/this.boardSize, ((this.getHeight()*2)/3)/this.boardSize, Image.SCALE_DEFAULT);
		this.sizeWidth=((this.getWidth()*2)/3)/this.boardSize;
		this.sizeHeight=((this.getHeight()*2)/3)/this.boardSize;
//		System.out.println(sizeWidth);
		
		
		for(int i=0 ;i<boardSize;i++)
			for(int k=0;k<boardSize;k++)
					this.arrayImage[i][k] = new LocatedImage(null, 0, 0);
					

		for(int i=0 ;i<boardSize;i++)
		{	
			for(int k=0;k<boardSize;k++)
			{		
					g.drawImage(voidImg, i*sizeWidth, k*sizeHeight,this);
//					System.out.println(this.arrayImage[i][k]);
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
								this.arrayImage[i][k].setImg(voidImg);
								this.arrayImage[i][k].setCoordX(i*sizeWidth);
								this.arrayImage[i][k].setCoordY(k*sizeHeight);
							}
							else
							{
								g.drawImage(p2, i*sizeWidth,  k*sizeHeight,this);
//								System.out.println(i*sizeWidth);
								this.arrayImage[i][k].setImg(voidImg);
								this.arrayImage[i][k].setCoordX(i*sizeWidth);
								this.arrayImage[i][k].setCoordY(k*sizeHeight);
							}

								
						}
					}
			}
	}
//	Graphics2D g2 = (Graphics2D) g;

		System.out.println(this.arrayImage[0][0].getCoordX()+ " EN 0 0 QUOI "+this.arrayImage[0][0].getCoordY());
	
	for(int indexRow = 0; indexRow < this.boardSize; indexRow++)
		for(int indexCol = 0; indexCol < this.boardSize; indexCol++)
		{
//			System.out.println(" aaa "+ this.arrayImage[indexRow][indexCol] +" "+  this.arrayImage[indexRow][indexCol]);
			this.rec.add(new Rectangle(this.arrayImage[indexRow][indexCol].getCoordX(), this.arrayImage[indexRow][indexCol].getCoordY(), this.sizeWidth, this.sizeHeight));
		}
//	System.out.println(" AAA" +this.rec.get(0).getX());
	//	
//	for(Image[] i: this.arrayImage)
//	{
//		for(Image i1 : i)
//		{
////			System.out.println("ALLO"+i1.getWidth(this)+"HE"+i1.getHeight(this)+" "+index+" "+this.sizeHeigth);
//			Rectangle r = new Rectangle(i1, i1.getHeight(this), this.sizeWidth, this.sizeHeight);
//			i1.getWidth(this);
////			g2.setColor(Color.BLACK);
////			g2.draw(r);
//			this.rec.add(r);
//			
//			index++;
//			
//		}
//	}
	
	for(Rectangle r1:  rec)
	{
		System.out.println(r1.getX()+" et " + r1.getY());
	}
	this.addMouseListener(new MouseListener()
	{

		@Override
		public void mouseClicked(MouseEvent e) {
//System.out.println("EVENTAAA"+e.getXOnScreen()+ " " +e.getYOnScreen());
			
			for(Rectangle r: rec)
			{
//				System.out.println(e.getX());
//				System.out.println(r.getX()+ " "+r.getY()+ r.x);
//				if(r.getX()==e.getX())
//					System.out.println(r.getX()==e.getX());
				if(r.contains(e.getX(),e.getY()))
				{
					
					int xindex = (int)r.getX()/sizeWidth;
					int yindex = (int)r.getY()/sizeHeight;
					
					System.out.println("GETX : "+r.getX() + " "+ r.getY());
					//int index = rec.indexOf(r);
					System.out.println(arrayImage[xindex][yindex].getCoordX()+" aaa"+arrayImage[xindex][yindex].getCoordY());
					System.out.println("VRAI X: "+xindex + " vrai y"+yindex);
				
				}
					
//					System.out.println("TOUCHE TOUCHE TOUCHE");
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
	System.out.println();
	ArrayList<Pawn> List = new ArrayList<Pawn>();
	List.add(new Pawn(1,new Coordinate(0,2),"lol"));
	List.add(new Pawn(2,new Coordinate(0,6),"lol"));
	
//	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JFrame f = new JFrame();
//	f.setSize(screenSize.getWidth(),screenSize.getHeight());
	f.setExtendedState(JFrame.MAXIMIZED_BOTH);
	JGameCanvas CC = new JGameCanvas(List,15,f.getWidth(),f.getHeight());
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	f.setResizable(false);
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
	//	f.repaint();
	
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