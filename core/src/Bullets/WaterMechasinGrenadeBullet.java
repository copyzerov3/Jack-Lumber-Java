package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

import java.util.Random;

import com.copyzero.JackLumber2.BulletManager;
import com.copyzero.JackLumber2.Timer;


public class WaterMechasinGrenadeBullet extends Bullet
{
	private final int STATE_VERTICAL = 0;
	private final int STATE_FLOATING = 1;
	
	private int state = STATE_VERTICAL;
	
	private int deceleration =0;
	private Timer decelTimer;
	private Timer blowUpTimer;
	public WaterMechasinGrenadeBullet(int x,int y,BulletManager bulletManagerRef,boolean CauseWaterfall)
    {
        super(x,1, assetManager.GetTexture("grenade"),8,25,bulletManagerRef);
        this.isShootable = true;
        if(CauseWaterfall)
        	blowUpTimer = new Timer(0.0f);
        decelTimer = new Timer(0.2f);
        
    }
	@Override
	public void onShot()
	{
		this.isDead = true;
		if(this.bulletManagerRef != null)
		{
			bulletManagerRef.AddPlayerBullet(new MechasinGrenadeBulletExplosion(x,y));
		}
	}
    @Override
    public void update(float deltaTime)
    {
    	
       if(state == STATE_VERTICAL)
       {
    	   decelTimer.update(deltaTime);
    	   if(!decelTimer.running())
    	   {
    		   deceleration++;
    		   decelTimer.reset(0.2f);
    	   }
    	   y+=speed-deceleration;
    	   if(y<0)
    	   {
    			if(blowUpTimer !=null)
    			{
    				 Random rand = new Random();
    				 blowUpTimer.reset(rand.nextFloat()*2.0f);
    			}
    			state = STATE_FLOATING;
    		}
    	   
       }
       else 
       {
    	   if(blowUpTimer != null)
    	   {
    		    blowUpTimer.update(deltaTime);
    		    if(!blowUpTimer.running())
    		    {
    		    	bulletManagerRef.AddEnemyBullet(new Waterfall(x));
    		    	isDead= true;
    		    }
    	   }
    	  
    	   x-=speed/2;
    	   if(x+asset.getWidth()<=0)
    		   this.isDead= true;
       }
       this.hitBox.setPosition(x, y);
    }
    
}
