package Enemies;

import Bullets.DeerSideBullet;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;
public class Deer extends Enemy
{

	private boolean ramming= false;
    private boolean reverse=false;
    private boolean up= true;
    private boolean onScreen = false;
    private float ramTime = 0.5f;
    private Timer Ram;
    private Timer cooldown;
    private Timer shootTimer;
    public Deer(int x,int y ,Player playerRef,AIDirector directorRef)
    {
        super(x,y,1,15, assetManager.GetTexture("deer"),10,playerRef,directorRef);
        cooldown = new Timer(5);
        Ram =  new Timer(ramTime);
        shootTimer = new Timer(0.0f);
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
        if(!fromContact)
        {
            health-=damage;
            
        }
        if(health <=0)
        	isDead= true;
    }
    @Override
    public void update(float deltaTime)
    {
        Ram.update(deltaTime);
        cooldown.update(deltaTime);
        shootTimer.update(deltaTime);
        if(x+image.getWidth()>800 && !onScreen)
        {
            x-=speed*2;
            if(x+image.getWidth() == 800)
                onScreen = true;
        }
        if(x ==0)
        {
            reverse = true;
        }
        if(ramming)
        {
            if(!this.Ram.running())
            {
                if(!reverse)
                    x-=10;
                else if(reverse)
                    x+=10;
            }
            if(!shootTimer.running())
            {
            	directorRef.GetBulletManager().AddEnemyBullet(new DeerSideBullet((int)x + image.getWidth()/2,(int)y,true));
            	directorRef.GetBulletManager().AddEnemyBullet(new DeerSideBullet((int)x + image.getWidth()/2,(int)y-image.getHeight(),false));
            	shootTimer.reset(0.5f);
            }
            if(reverse && x+image.getWidth() == 800)
            {
                reverse = false;
                ramming = false;
                cooldown.reset(5);
            }
        }
        else
        {
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
            	shootTimer.reset(0.5f);
                ramming = true;
                Ram.reset(ramTime);
            }
            if(reverse && x+image.getWidth() <= 800)
            {
                reverse = false;
                ramming = false;
                cooldown.reset(5);
            }
        }
        hitBox.setPosition((int)x,(int)y);

    }


}
