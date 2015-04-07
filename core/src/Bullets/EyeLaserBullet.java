package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

import com.copyzero.JackLumber2.Settings;

public class EyeLaserBullet extends Bullet
{
	private float accelerationX,accelerationY;
    private float posX,posY;
    private boolean left;
    private boolean up;
    public EyeLaserBullet(int x,int y,int playerX,int playerY)
    {
        super(x,y, assetManager.GetTexture("eyeLaser"),8,15);
        posX = x;
        posY = y;
        this.GetAcceleration(playerX, playerY);
        if(playerX>x)
        {
        	left= false;
        }
        else
        {
        	left= true;
        }
        if(playerY>y)
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
