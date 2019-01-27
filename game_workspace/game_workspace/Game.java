package game_workspace;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Game extends JFrame implements Runnable
{
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage testImage;
	
	public Game() 
	{
		//Makes frame shutdown on exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Sets the position and size of the frame.
		setBounds(0, 0, 1000, 800);
		
		//Sets the frame in the center of the screen.
		setLocationRelativeTo(null);
		
		
		//Adds graphics component to jframe.
		add(canvas);
		
		//Makes frame visible. Must be before createBufferStrategy.
		setVisible(true);
				
		//Create object for buffer strategy.
		canvas.createBufferStrategy(2);
		
		renderer = new RenderHandler(getWidth(), getHeight());
		testImage = loadImage("grassTile.png");
	}
	
	private BufferedImage loadImage(String path)
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
	
	
	public void render()
	{
		//sets our graphics
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		
		renderer.renderImage(testImage, 0, 0, 5, 5);
		renderer.render(graphics);
		
		graphics.dispose();
		bufferStrategy.show();
	}
	
	public void update()
	{
		
	}
	
	public void run() 
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
//		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
//		int i = 0;
//		int x = 0;
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second.;
		double deltaSeconds = 0;
		
		while(true) 
		{
			long now = System.nanoTime();
			deltaSeconds += (now - lastTime) / nanoSecondConversion;
			
			//Update every 60 seconds, 60fps.
			while(deltaSeconds >= 1)
			{
				update();
				deltaSeconds = 0;
			}
			render();
			lastTime = now;
		}
	}
	

	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}
