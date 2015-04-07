package Enemies;

import java.util.Random;

import Bullets.WolfBullets;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;
public class Coyote extends Enemy
{

	private final int STATE_ARRIVING = 0;
    private final int STATE_MOVING = 1;

    private int state = STATE_ARRIVING;

    private Timer cooldown;
    private int jumpRate = 5;
    private boolean reverse = false;
    private boolean jumping= false;
    private boolean reverseJumping=false;
    
    private Timer shootTimer;
    private float shootTime = 0.5f;
    
    public Coyote(int x,int y,Player playerRef,AIDirector directorRef)
    {
        super(x,y, 5,10,assetManager.GetTexture("coyote"),20,playerRef,directorRef);
        cooldown = new Timer(jumpRate);
        shootTimer = new Timer(shootTime);
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
        if(!jumping)
        {
        	if(fromContact)
        		return;		
        }
        health-=damage;
        
        if(health<=0)
        {
        	isDead= true;
        }
    }
    @Override
    public void update(float deltaTime)
    {
        cooldown.update(deltaTime);
        shootTimer.update(deltaTime);
        if(state == STATE_ARRIVING)
        {
            x-=speed;
            if(x+image.getWidth()< 800)
            {
                state=STATE_MOVING;
            }
        }
        else if(state == STATE_MOVING)
        {
            if(!reverse)
            {
                

                if(!jumping)
                {
                	x-=speed;
                	if(x<=0)
	                {
	                    reverse = true;
	                }
                }
                else
                {
                	x-=speed/2;
                }
                
            }
            else
            {
                x+=speed;
                if(!jumping)
                {
                	if(x+image.getWidth()>=800)
	                {
	                    reverse = false;
	                }
                }
                else
                {
                	 x+=speed/2;
                }
                
            }
            if(!cooldown.running())
            {
                cooldown.reset(jumpRate);
                jumping = true;
            }
            if(jumping)
            {
                if(!reverseJumping)
                {
                    y+=speed;
                    if(BoundingBox.Collided(hitBox,playerRef.GetBoundingBox()))
                    {
                        this.reverseJumping = true;
                    }
                    if(y>=380)
                    {
                        this.reverseJumping = true;
                    }
                }
                else
                {
                    y-=speed;
                    if(y<= 0)
                    {
                    	Random rand = new Random();
                        y=0;
                        this.jumping = false;
                        this.reverseJumping= false;
                        this.cooldown.reset(jumpRate + rand.nextInt(5));
                    }
                }
                if(!shootTimer.running())
                {
                	Random rand = new Random();
                	shootTimer.reset(shootTime + rand.nextInt(1));
                	directorRef.GetBulletManager().AddEnemyBullet(new WolfBullets((int)x,(int)y+image.getHeight()/2,true));
                	directorRef.GetBulletManager().AddEnemyBullet(new WolfBullets((int)x+ image.getWidth(),(int)y + image.getHeight()/2,false));
                }
            }
        }
    }


}
