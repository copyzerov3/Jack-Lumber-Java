package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class CopBullet extends Bullet
{
    private float accelerationX,accelerationY;
    private float posX,posY;
    private boolean left;
    
    public CopBullet(float x,float y,int targetX,int targetY)
    {
        super((int)x,(int)y, assetManager.GetTexture("scatterBullet"),5,10);
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
    public void update()
    {
    	if(left)
    	{
    		posX-=accelerationX;
    	}
    	else
    	{
    		posX+=accelerationX;
    	}
    	posY+=accelerationY;
        x = (int)posX;
        y = (int)posY;
        hitBox.setPosition(x,y);
        
        if(x<=-50|| x+asset.getWidth()>=800|| y>=500)
        {
        	isDead = true;
        }
    }
}
