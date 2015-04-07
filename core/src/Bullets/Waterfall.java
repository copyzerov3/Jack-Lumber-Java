package Bullets;
import static com.copyzero.JackLumber2.Settings.*;

import java.util.Random;

import com.copyzero.JackLumber2.Timer;
public class Waterfall extends Bullet 
{
	private int version = 0;
	private boolean left = false;
	private Timer decelTimer;
	private int deceleration = 0;
	public Waterfall(int x) 
	{
		super(x, 0,assetManager.GetTexture("waterfall"),8,25);
		// TODO Auto-generated constructor stub
		version =1;
		decelTimer = new Timer(0.2f);
		y-=asset.getHeight();
	}
	public Waterfall(int x,boolean left)
	{
		super(x,0,assetManager.GetTexture("waterfall"),3,25);
		this.left = left;
		version = 2;
	}
	@Override
	public int GetDamage()
    {
        return damage;
    }
	@Override
	public void update(float deltaTime)
	{
		if(version == 1)
		{
			decelTimer.update(deltaTime);
	    	   if(!decelTimer.running())
	    	   {
	    		   deceleration++;
	    		   decelTimer.reset(0.2f);
	    	   }
	    	   y+=speed-deceleration;
	    	   if(y<0-asset.getHeight())
	    	   {
	    			isDead = true;
	    	   }
	    	   
		}
		else
		{
			if(left)
			{
				x-=speed;
				if(x+asset.getWidth() <=0)
					isDead=true;
			}
			else
			{
				x+=speed;
				if(x> 800)
					isDead=true;
			}
			y-=speed;
		}
		hitBox.setPosition(x, y);
	}
	
	

}
