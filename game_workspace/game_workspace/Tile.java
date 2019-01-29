package game_workspace;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile 
{
	private int xPosition,yPosition,xZoom,yZoom,tileType;
	public static final int GRASS=0;
	public static final BufferedImage GRASSIMG=loadImage("GrassTile.png");
	private BufferedImage image;
	public Tile(int type,int xPosition, int yPosition, int xZoom,int yZoom)
	{
		this.xPosition=xPosition;
		this.yPosition = yPosition;
		this.xZoom = xZoom;
		this.yZoom = yZoom;
		tileType=type;
		switch(tileType)
		{
		case GRASS:
			image=GRASSIMG;
				break;
		}
	}
	private static BufferedImage loadImage(String path)
	{
		try
		{
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formattedImage;
		}
		catch(IOException exception)
		{
			exception.printStackTrace();
			return null;
		}
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getxZoom() {
		return xZoom;
	}
	public void setxZoom(int xZoom) {
		this.xZoom = xZoom;
	}
	public int getyZoom() {
		return yZoom;
	}
	public void setyZoom(int yZoom) {
		this.yZoom = yZoom;
	}
	public BufferedImage getImage() {
		return image;
	}

	public int getWidth()
	{		
		return image.getWidth()*xZoom;	
	}
	public int getHeight()
	{
		return image.getHeight()*yZoom;
	}
	
}
