package Bullets;

import static com.copyzero.JackLumber2.Settings.assetManager;

import com.copyzero.JackLumber2.BoundingBox;

public class WolfBullets extends Bullet {

	private boolean Left;
	public WolfBullets(int x, int y,boolean Left) 
	{
		super(x, y);
		this.Left = Left;
		this.asset = assetManager.GetTexture("scatterBullet");
		this.speed = 3;
        this.damage = 5;
        this.hitBox = new BoundingBox(x,y,asset.getWidth(),asset.getHeight());
		// TODO Auto-generated constructor stub
	}
	@Override
	public void update(float deltaTime)
	{
		if(Left)
		{
			x-=speed;
		}
		else
		{
			x+=speed;
		}
		hitBox.setPosition(x,y);
	}
}
