package Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;

import static com.copyzero.JackLumber2.Settings.*;

public class SuicideVultures extends Enemy 
{
	double yOffset=0;
	 double time;
	public SuicideVultures(int x,int y,Player playerRef,AIDirector directorRef)
	{
		super(x,y,5,3,assetManager.GetTexture("bird"),10,playerRef,directorRef);
	}
	
	
	@Override
	public void update(float deltaTime)
	{
		x-=speed;
		yOffset = Math.sin(time)*15;
		if(x+image.getWidth() <=0)
        {
            isDead= true;
        }
		hitBox.setPosition((int)x,(int)y+(int) yOffset);
		// TODO Auto-generated method stub

	}
	 @Override
	    public void draw(SpriteBatch batch)
	    {
	        batch.draw(image,x,y+(int)yOffset);
	    }

}
