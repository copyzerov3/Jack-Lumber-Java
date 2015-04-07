package com.copyzero.JackLumber2;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Dpad
{
	private NamedTexture image;
	
	private int x,y;
	
	public Dpad(int x,int y,NamedTexture image)
	{
		this.image= image;
		this.x= x;
		this.y = y;
	}
	public boolean CheckBounds(TouchInfo e)
	{
		if(e.touchX>=0 && e.touchX<=image.getWidth())
		{
			if(image.getHeight()<= e.touchY)
				return true;
		}

		return false;
	}
	public void CheckPressedDown(TouchInfo e,Player player)
	{
		if(e.touchX > this.x && e.touchX < this.x+image.getWidth() -1 && e.touchY>Settings.HEIGHT - this.y - image.getHeight() && e.touchY < Settings.HEIGHT - this.y+ image.getHeight()/3 - image.getHeight())
        {
            player.UpdateKeys(Keys.W,true);
        }
		else
		{
			player.UpdateKeys(Keys.W,false);
		}
        if(e.touchX > this.x && e.touchX < this.x+image.getWidth() -1 && e.touchY >Settings.HEIGHT-y-image.getHeight()/3 && e.touchY<Settings.HEIGHT-y)
        {
        	player.UpdateKeys(Keys.S,true);
        }
        else
        {
        	player.UpdateKeys(Keys.S, false);
        }
       if(e.touchY >Settings.HEIGHT - this.y -image.getHeight() && e.touchY < Settings.HEIGHT -this.y && e.touchX>this.x && e.touchX < this.x+image.getHeight()/3)
       {
    	   player.UpdateKeys(Keys.A,true);
       }
       else
       {
    	   player.UpdateKeys(Keys.A, false);
       }
       if(e.touchY > Settings.HEIGHT -this.y - image.getHeight() && e.touchY < Settings.HEIGHT -this.y && e.touchX > this.x + image.getWidth() - (image.getHeight()/3) && e.touchX < this.x+image.getWidth())
       {
    	   player.UpdateKeys(Keys.D,true);
       }
       else
       {
    	   player.UpdateKeys(Keys.D, false);
       }

	}
	public void CheckPressedUp(TouchInfo e, Player player)
	{
		if(e.touchX > this.x && e.touchX < this.x+image.getWidth() -1 && e.touchY>Settings.HEIGHT - this.y - image.getHeight() && e.touchY < Settings.HEIGHT - this.y+ image.getHeight()/3 - image.getHeight())
        {
            player.UpdateKeys(Keys.W,false);
        }
		if(e.touchX > this.x && e.touchX < this.x+image.getWidth() -1 && e.touchY >Settings.HEIGHT-y-image.getHeight()/3 && e.touchY<Settings.HEIGHT-y)
        {
        	player.UpdateKeys(Keys.S,false);
        }
		if(e.touchY >Settings.HEIGHT - this.y -image.getHeight() && e.touchY < Settings.HEIGHT -this.y && e.touchX>this.x && e.touchX < this.x+image.getHeight()/3)
       {
    	   player.UpdateKeys(Keys.A,false);
       }
		if(e.touchY > Settings.HEIGHT -this.y - image.getHeight() && e.touchY < Settings.HEIGHT -this.y && e.touchX > this.x + image.getWidth() - (image.getHeight()/3) && e.touchX < this.x+image.getWidth())
       {
    	   player.UpdateKeys(Keys.D,false);
       }
	}
	public void Draw(SpriteBatch batch)
	{
		batch.draw(image, x, y);
	}
}
