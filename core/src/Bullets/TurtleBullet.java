package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class TurtleBullet extends Bullet
{
	private float accelerationX,accelerationY;
    private float posX,posY;
    private boolean up;
	public TurtleBullet(int x, int y,int targetX,int targetY)
    {
        super(x, y, assetManager.GetTexture("turtleBullet"), 4, 5);
        posX = x;
        posY = y;
        this.GetAcceleration(targetX, targetY);
        if(targetY>y)
        {
        	up = true;
        }
        else
        {
        	up = false;
        }
    }
	public TurtleBullet(int x,int y,int targetX,int targetY,int speed)
	{
		super(x, y, assetManager.GetTexture("turtleBullet"), speed, 5);
        posX = x;
        posY = y;
        this.GetAcceleration(targetX, targetY);
        if(targetY>y)
        {
        	up = true;
        }
        else
        {
        	up = false;
        }
        this.speed = speed;
	}
	private void GetAcceleration(int newX,int newY)
    {
    	if(Math.abs(posX-newX) > Math.abs(posY-newY))
    	{
    		 accelerationX = speed;
    		 accelerationY = Math.abs(posY-newY)/(Math.abs(posX-newX)/accelerationX);
    	}
    	else if(Math.abs(posX-newX) < Math.abs(posY-newY))
    	{
    		accelerationY = speed;
   		 	accelerationX = Math.abs(posX-newX)/(Math.abs(posY-newY)/accelerationY);
    	}
    	else
    	{
    		accelerationX = speed;
    		accelerationY=speed;
    	}
    }
    @Override
    public void update(float deltaTime)
    {
    	if(up)
    	{
    		posY+=accelerationY;
    	}
    	else
    	{
    		posY-=accelerationY;
    	}
    	
    	posX-=accelerationX;
    	
        x = (int)posX;
        y = (int)posY;
        if(x+asset.getWidth() <=0)
        {
        	isDead =true;
        }
        if(y+asset.getHeight()>= 480)
        	isDead= true;
        hitBox.setPosition(x,y);
    }

}
