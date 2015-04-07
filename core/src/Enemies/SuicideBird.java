package Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;

import static com.copyzero.JackLumber2.Settings.*;
public class SuicideBird extends Enemy
{

	private final int STATE_TARGETING =0;
	private final int STATE_ATTACKING = 1;
	
	private int STATE = STATE_TARGETING;
	
	double yOffset=0;
    double time;
    public SuicideBird(int x, int y,Player PlayerRef,AIDirector DirectorRef)
    {
        super(x, y, 2, 1, assetManager.GetTexture("bird"),10,PlayerRef,DirectorRef);
    }

    @Override
    public void update(float deltaTime)
    {
        if(STATE == STATE_TARGETING)
        { 
        	time+=deltaTime;
        	yOffset = Math.sin(time)*15;
        	
        	x-=speed;
        	
        	if(y> playerRef.GetY() + playerRef.GetHeight()/2)
	        {
	            y-=speed;
	            if(y< playerRef.GetY() + playerRef.GetHeight()/2)
	            {
	            	y = playerRef.GetY() + playerRef.GetHeight()/2;
	            }
	        }
	        else if(y< playerRef.GetY() + playerRef.GetHeight()/2)
	        {
	            y+=speed;
	            if(y> playerRef.GetY() + playerRef.GetHeight()/2)
		        {
	            	y = playerRef.GetY() + playerRef.GetHeight()/2;
		        }
	        }
        	
        	if(x<=500)
        	{
        		STATE = STATE_ATTACKING;
        	}
        }
        else
        {
        	x-=10;
        }
        
        hitBox.setPosition((int)x,(int)y+(int) yOffset);
        
        if(x+image.getWidth() <=0)
        {
            isDead= true;
        }
    }
    @Override
    public void draw(SpriteBatch batch)
    {
        batch.draw(image,x,y+(int)yOffset);
    }


}
