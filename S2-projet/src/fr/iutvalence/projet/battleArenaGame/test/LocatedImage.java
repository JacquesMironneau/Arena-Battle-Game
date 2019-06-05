package fr.iutvalence.projet.battleArenaGame.test;

import java.awt.Image;

public class LocatedImage
{

	private int coordX;
	
	private int coordY;
	
	private Image img;
	
	public LocatedImage(Image theImg, int theCoordX, int theCoordY)
	{
		
	}

	public int getCoordX()
	{
		return coordX;
	}

	public void setCoordX(int coordX)
	{
		this.coordX = coordX;
	}

	public int getCoordY()
	{
		return coordY;
	}

	public void setCoordY(int coordY)
	{
		this.coordY = coordY;
	}

	public Image getImg()
	{
		return img;
	}

	public void setImg(Image img)
	{
		this.img = img;
	}

	@Override
	public String toString()
	{
		return "LocatedImage [coordX=" + coordX + ", coordY=" + coordY + ", img=" + img + "]";
	}


}
