package com.copyzero.JackLumber2;

public class BoundingBox
{
	private int x=0;
	private int y=0;
	private int width=0;
	private int height = 0;
	
	public BoundingBox(int x,int y,int width,int height)
	{
		this.x = x;
		this.y = y;
		this.width  = width;;
		this.height = height;
	}

	public static boolean Collided(BoundingBox box1,BoundingBox box2)
	{
		if((box1.x + box1.width) >= box2.x && box1.x<= (box2.x+box2.width))
        {
            if((box1.y + box1.height) >= box2.y && box1.y <= (box2.y+box2.height))
            {
                return true;
            }
        }
		return false;
	}
	public static boolean Collided(int x1,int y1,int width1,int height1,int x2,int y2,int width2,int height2)
	{
		if((x1 +width1) > x2 && (x1 < x2))
		{
			if(y1 < y2 && (y1+height1 >y2))
			{
				return true;
			}
		}
		if((x2 +width2) > x1 && (x2 < x1))
		{
			if(y2 < y1 && (y2+height2 >y1))
			{
				return true;
			}
		}
		return false;
	}
    public void setPosition(int x,int y)
    {
        this.x=x;
        this.y=y;
    }
    public int GetX()
    {
    	return x;
    }
    public int GetY()
    {
    	return y;
    }
    public int GetWidth()
    {
    	return width;
    }
    public int GetHeight()
    {
    	return height;
    }
    public void setWidth(int width)
    {
    	this.width = width;
    }
    public void setHeight(int height)
    {
    	this.height = height;
    }
}
