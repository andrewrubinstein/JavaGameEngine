package game_workspace;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
{
	private BufferedImage view;
	private Rectangle camera;
	private int pixels[];
	
	public RenderHandler(int width, int height)
	{
		//Creates buffered image that represents our view.
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//System.out.println(view);
		
		//Create an array for the pixels.
		//The rectangle, known as the Raster's bounding rectangle and available by means of the getBounds method
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		
		camera = new Rectangle(0,0, width, height);
		camera.x = 0;
		camera.y = 0;
			
	}
	//Renders the array of pixels to the screen.
	public void render(Graphics graphics)
	{
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
	//Renders asset image to the array of pixels. 
	//View.getWidth() and getHeight() equal setBounds args for the jframe window.
	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom)
	{
		int imagePixels[] = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		//System.out.println(image.getWidth());
		for(int y = 0; y < image.getHeight(); y++)
		{
			for(int x = 0; x < image.getWidth(); x++)
				for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
					for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
						setPixel(imagePixels[x + y * image.getWidth()], (x * xZoom) + xPosition + xZoomPosition,  ((y* yZoom) + yPosition + yZoomPosition)); 					
		}
	}
		
	//Prevents crashing if zoomed image is off the screen. 
	private void setPixel(int pixel, int x, int y)
	{
		if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h) 
		{
			int pixelIndex = (x - camera.x) + ((y - camera.y) * view.getWidth());
			if(pixels.length > pixelIndex)
				pixels[pixelIndex] = pixel;
		}

	}
}