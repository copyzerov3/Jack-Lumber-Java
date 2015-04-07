package Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class Scorpion extends Enemy
{

	private boolean reverseY= false;
    private boolean jumping = false;
    private int jumpRate = 3;
    private Timer jump;
    private NamedTexture shadow;
    
    public Scorpion(int x,int y,Player playerRef,AIDirector director)
    {
        super(x,y,2,10, assetManager.GetTexture("scorpion"),20,playerRef,director);
        jump = new Timer(3.5f);
        shadow = assetManager.GetTexture("shadow");
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
    	 if(!jumping || fromContact)
         {
         	return;
         }
    	 
        health-=damage;
        
        if(health<=0)
        {
        	isDead= true;
        }
    }
    @Override
    public BoundingBox GetBoundingBox()
    {
    	if(jumping)
    	{
    		return hitBox;
    	}
    	return new BoundingBox(-100,-100,1,1);
    }
    @Override
    public void update(float deltaTime)
    {
        jump.update(deltaTime);
        if(!jumping)
        {
            if(playerRef.GetX()+playerRef.GetWidth()/2>x+image.getWidth()/2)
            {
                x+=speed;
            }
            else
            {
                x-=speed;
            }
        }
        else if(jumping)
        {
            if(!reverseY)
            {
                y+=speed*3;
                if(BoundingBox.Collided(hitBox,playerRef.GetBoundingBox()))
                {
                    reverseY=true;
                }
                if(y>=Settings.HEIGHT-image.getHeight())
                {
                    reverseY= true;
                }
            }
            else
            {
                y-=speed*3;
                if(y<= 0)
                {
                    jumping = false;
                    jump.reset(jumpRate);
                    reverseY = false;
                }
            }
        }
        if(!jump.running())
        {
            jumping= true;
        }

        hitBox.setPosition((int)x, (int)y);
    }
    @Override
    public void draw(SpriteBatch batch)
    {
        if(jumping == false)
        {
            batch.draw(shadow,x,0);
        }
        else
        {
            batch.draw(image,x,y);
        }
    }


}
