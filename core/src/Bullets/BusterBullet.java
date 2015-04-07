package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

import com.copyzero.JackLumber2.BoundingBox;;

public class BusterBullet extends Bullet
{
	 public BusterBullet(int x,int y)
	    {
	        super(x,y);
	        level = busterlvl;
	        if(level == 1)
	        {
	            asset = assetManager.GetTexture("bullet1");
	            speed = 6;
	            damage = 1;
	        }
	        else if(level == 2)
	        {
	        	asset = assetManager.GetTexture("bullet2");
	            speed = 8;
	            damage = 2;
	        }
	        else if(level == 3)
	        {
	        	asset = assetManager.GetTexture("bullet2");
	            speed = 8;
	            damage = 3;
	        }
	        else if(level == 4)
	        {
	        	asset = assetManager.GetTexture("bullet3");
	            speed = 10;
	            damage = 4;
	        }
	        y+=asset.getHeight()/2;
	        hitBox = new BoundingBox(x,y,asset.getWidth(),asset.getHeight());
	    }

}
