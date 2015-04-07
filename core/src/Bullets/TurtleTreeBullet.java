package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TurtleTreeBullet extends Bullet
{
	private final int STATE_GOING_UP = 0;
	private final int STATE_COMMING_DOWN = 1;
	
	private int state = STATE_GOING_UP;
	
	public TurtleTreeBullet(int x,int y)
    {
        super(x,y, assetManager.GetTexture("treeBullet"),7,25);

    }
    @Override
    public void update(float deltaTime)
    {
        if(state == STATE_GOING_UP)
        {
        	y+=speed;
        	if(y >= 800)
        	{
        		Random rand = new Random();
        		state = STATE_COMMING_DOWN;
        		x = rand.nextInt(450);
        	}
        }
        else if(state == STATE_COMMING_DOWN)
        {
        	y-=speed;
        	if(y+asset.getHeight() <=0)
        		isDead= true;
        }
        if(y<=0)
        {
        	isDead= true;
        }
        hitBox.setPosition(x,y);
    }
    @Override
    public void draw(SpriteBatch batch)
    {
    	if(state == STATE_GOING_UP)
    	{
    		batch.draw(asset,x,y);
    	}
    	else
    	{
    		if(y>480)
    		{
    			batch.draw(assetManager.GetTexture("shadow"),x,0);
    		}
    		else
    		{
    			
    			batch.draw(asset, x, y, asset.getWidth(), asset.getHeight(), 0, 0, asset.getWidth(), asset.getHeight(), false, true);
    		}
    	}
    }

}
