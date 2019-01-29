package game_workspace;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame implements Runnable
{
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	private BufferedImage testImage;
	private ArrayList<Tile> tiles;
	private int rows,columns;
	private int deltaY=0,deltaX=0;
	public static void main(String[] args) 
	{
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
	public Game() 
	{
		//Makes frame shutdown on exit.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Sets the position and size of the frame.
		setBounds(0, 0, 1000, 800);
		
		//Sets the frame in the center of the screen.
		setLocationRelativeTo(null);
		int zoom=5;
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
				{
					deltaY=(Tile.GRASSIMG.getHeight()*zoom)/2;
				}
				if(e.getKeyCode()==KeyEvent.VK_UP)
				{
					deltaY=-1*(Tile.GRASSIMG.getHeight()*zoom)/2;
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					deltaX=-1*(Tile.GRASSIMG.getWidth()*zoom)/2;
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					deltaX=(Tile.GRASSIMG.getWidth()*zoom)/2;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
				{
					deltaY=0;
				}
				if(e.getKeyCode()==KeyEvent.VK_UP)
				{
					deltaY=0;
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					deltaX=0;
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					deltaX=0;
				}
			}
			
		});
		
		
		//Adds graphics component to jframe.
		getContentPane().add(canvas);
		
		//Makes frame visible. Must be before createBufferStrategy.
		setVisible(true);
				
		//Create object for buffer strategy.
		canvas.createBufferStrategy(2);
		
		renderer = new RenderHandler(getWidth(), getHeight());
		testImage = loadImage("grassTile.png");
		tiles=new ArrayList<Tile>();
		rows=50;
		columns=60;
		for(int x=0;x<columns;x++)
		{
			for(int y=0;y<rows;y++)
			{
				tiles.add(new Tile(Tile.GRASS,x*Tile.GRASSIMG.getWidth()*zoom,y*Tile.GRASSIMG.getHeight()*zoom,zoom,zoom));
				System.out.println(x*Tile.GRASSIMG.getWidth()*zoom+" "+y*Tile.GRASSIMG.getHeight()*zoom);
			}
		}
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
		//renderer.renderImage(testImage, 0, 0, 8, 8);
		
		//Draws playing field saved in tiles array
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Tile t:tiles)
		{
			renderer.renderImage(t.getImage(),t.getxPosition(),t.getyPosition(),t.getxZoom(),t.getyZoom());
		}
		renderer.render(graphics);

		graphics.fillRect(canvas.getWidth()/2, canvas.getHeight()/2, 50, 50);
		graphics.dispose();
		bufferStrategy.show();
	}
	
	public void update()
	{
		for(Tile t:tiles)
		{
			t.setyPosition(t.getyPosition()+deltaY);
			t.setxPosition(t.getxPosition()+deltaX);
		}
	}
	
	public void run() 
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
//		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
//		int i = 0;
//		int x = 0;
		long sleepTime,maxCalcTime=0;
		long start,end,lastCom=System.nanoTime();
		while(true)
		{
			
			start=System.nanoTime()-lastCom;

			update();
			render();
			end=System.nanoTime()-lastCom-start;
			if(16-end/1000000>0)
			sleepTime=16-end/1000000;
			else
				sleepTime=0;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(1000/(sleepTime+end/1000000));
			if(maxCalcTime<end)
			{
				maxCalcTime=end;
				System.out.println((end/1000000.));
			}
			lastCom=System.nanoTime();
		
		}
	}
	

}
