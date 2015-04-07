package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

import com.copyzero.JackLumber2.BoundingBox;

public class DeerSideBullet extends Bullet {

	private boolean up;
	public DeerSideBullet(int x, int y,boolean up) 
	{
		super(x, y);
		this.up = up;
		this.asset = assetManager.GetTexture("scatterBullet");
		this.speed = 2;
        this.damage = 5;
        this.hitBox = new BoundingBox(x,y,asset.getWidth(),asset.getHeight());
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(float deltaTime)
	{
		if(up)
		{
			y-=speed;
		}
		else
		{
			y+=speed;
		}
		hitBox.setPosition(x,y);
	}

}
