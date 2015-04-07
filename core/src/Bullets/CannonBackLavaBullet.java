package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

import com.copyzero.JackLumber2.Settings;

public class CannonBackLavaBullet extends Bullet
{
	private float accelerationX,accelerationY;
    private float posX,posY;
    private boolean left;
    private boolean up;
	
    public CannonBackLavaBullet(int x,int y,int targetX,int targetY)
    {
        super(x,y, assetManager.GetTexture("lavaBall"),8,35);
        posX = x;
        posY = y;
        this.GetAcceleration(targetX, targetY);
        if(targetX>x)
        {
        	left= false;
        }
        else
        {
        	left= true;
        }
        if(targetY>y)
        {
        	up = true;
        }
        else
        {
        	up = false;
        }
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
    	if(left)
    	{
    		posX-=accelerationX;
    	}
    	else
    	{
    		posX+=accelerationX;
    	}
    	if(up)
    	{
    		posY+=accelerationY;
    	}
    	else
    	{
    		posY-=accelerationY;
    	}
    	
        x = (int)posX;
        y = (int)posY;
        hitBox.setPosition(x,y);
        
        if(x<=0 || y>=Settings.HEIGHT || y<=-100)
        	isDead = true;
    }

}
