package Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class Shark extends Enemy
{
	private boolean reverse = false;
    private boolean reverseY= false;
    private boolean jumping = false;
    private int jumpRate = 3;
    
    private Timer jump;
    
    private NamedTexture shadow;
    public Shark(int x,int y,Player playerRef,AIDirector director)
    {
        super(x,y,2,20, assetManager.GetTexture("shark"),15,playerRef,director);
        jump = new Timer(jumpRate);
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
            if(!reverse)
            {
                x+=speed;
                if(x+image.getWidth() >= 800)
                {
                    reverse = true;
                }

            }
            else
            {
                x-=speed;
                if(x<=0)
                {
                    reverse = false;
                }

            }
        }
        else if(jumping)
        {
            if(!reverseY)
            {
                y+=speed*2;
                if(y + image.getHeight()>=480)
                {
                    reverseY= true;
                }
            }
            else
            {
                y-=speed*2;
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
        if(!jumping)
        {
            batch.draw(shadow,x,0);
        }
        else
        {
            batch.draw(image,x,y);
        }
    }


}
