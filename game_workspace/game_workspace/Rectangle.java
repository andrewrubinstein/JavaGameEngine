package game_workspace;

public class Rectangle 
{
	public int x,y,w,h;
	
	Rectangle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	Rectangle()
	{
		this(0,0,0,0);
	}
}
