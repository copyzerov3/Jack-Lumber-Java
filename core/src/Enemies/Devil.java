package Enemies;

import Bullets.SoliderEnemyBullet;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class Devil extends Enemy 
{

	private final int STATE_MOVING = 0;
	private final int STATE_PREPARING = 1;
	private final int STATE_SPINNING = 2;
	
	private int state = STATE_MOVING;
	
    private boolean reverse=false;
    private boolean up= true;
    private boolean onScreen = false;
    private float spinTime = 2f;
    private Timer spin;
    private Timer cooldown;
    
    public Devil(int x,int y ,Player playerRef,AIDirector director)
    {
        super(x,y,3,10, assetManager.GetTexture("devil"),20,playerRef,director);
        cooldown = new Timer(5);
        spin =  new Timer(spinTime);
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
        if(state != STATE_SPINNING)
        {
            health-=damage;
        }
        else
        {
        	if(!fromContact)
        	{
        		directorRef.GetBulletManager().AddEnemyBullet(new SoliderEnemyBullet((int)x,(int)y));
        	}
        }
        if(health<=0)
        {
        	isDead= true;
        }
    }
    @Override
    public void update(float deltaTime)
    {
        spin.update(deltaTime);
        cooldown.update(deltaTime);
        
        if(state== this.STATE_MOVING)
        {
        	if(x+image.getWidth()> 800 && !onScreen)
	        {
	            x-=speed*2;
	            if(x+image.getWidth() <= 800)
	                onScreen = true;
	        }
        	 if(up)
             {
                 y-=speed;
                 if(y <= 0)
                 {
                     up = false;
                 }
             }
             else
             {
                 y+=speed;
                 if(y + image.getHeight() >= 480)
                 {
                     up = true;
                 }
             }
             if(!cooldown.running() && onScreen)
             {
                 state = STATE_PREPARING;
                 spin.reset(spinTime);
             }
             if(reverse && x+image.getWidth() <= 800)
             {
                 reverse = false;
                 cooldown.reset(5);
             }
        }
        else if(state == this.STATE_PREPARING)
        {
        	if(!spin.running())
        	{
        		state= STATE_SPINNING;
        	}
        }
        else if(state == this.STATE_SPINNING)
        {
        	if(x <=0)
            {
                reverse = true;
            }
            if(!this.spin.running())
            {
                if(!reverse)
                {
                    x-=speed*2;
                }

                else if(reverse)
                    x+=speed*2;
                if(up)
                {
                    y-=speed*2;
                    if(y <= 0)
                    {
                        up = false;
                    }
                }
                else
                {
                    y+=speed*2;
                    if(y + image.getHeight() >= 480)
                    {
                        up = true;
                    }
                }
            }
            if(reverse && x+image.getWidth() >= 800)
            {
            	state = STATE_MOVING;
                reverse = false;
                cooldown.reset(5);
            }
        }
        
        hitBox.setPosition((int)x,(int)y);

    }


}
