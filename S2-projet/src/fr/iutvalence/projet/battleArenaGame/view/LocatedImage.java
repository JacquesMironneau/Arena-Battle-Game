package fr.iutvalence.projet.battleArenaGame.view;

import java.awt.Image;

/**
 * represent an image with her coordinate in the panel.
 * use in the display of board, display an image on panel using jpanel and save her coordinate in two attributes 
 */


public class LocatedImage
{
	/**
	 * the X coordinate
	 */
	private int coordX;
	
	/**
	 * y coordinate
	 */
	private int coordY;
	
	/**
	 * the image will be paint in the panel 
	 */
	private Image img;
	
	public LocatedImage(Image theImg, int theCoordX, int theCoordY)
	{
		this.img=theImg;
		this.coordX=theCoordX;
		this.coordY=theCoordY;
	}

	/**
	 * getter for coordX
	 */
	public int getCoordX()
	{
		return coordX;
	}

	/**
	 * setter for CoordX
	 */
	public void setCoordX(int coordX)
	{
		this.coordX = coordX;
	}

	/**
	 * getter for coordY
	 */
	public int getCoordY()
	{
		return coordY;
	}

	/**
	 * setter for coordY
	 */
	public void setCoordY(int coordY)
	{
		this.coordY = coordY;
	}

	/**
	 * getter for img
	 */
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
