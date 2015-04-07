package Bullets;

import static com.copyzero.JackLumber2.Settings.assetManager;
import com.copyzero.JackLumber2.Timer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HeatBeam extends Bullet {

	private int bulletWidth = 0;
	private Timer deathTimer;
	public HeatBeam(int x, int y) 
	{
		super(x, y, assetManager.GetTexture("heatBullet"),20,25);
		hitBox.setHeight(asset.getHeight()*2);
		deathTimer = new Timer(1.0f);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void update(float deltaTime)
	{
		deathTimer.update(deltaTime);
		x-=speed;
		bulletWidth+=speed;
		hitBox.setPosition(x, y);
		hitBox.setWidth(bulletWidth);
		if(!deathTimer.running())
		{
			isDead= true;
		}
	}
	@Override
	public int GetDamage()
	{
		return damage;
	}
	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(asset, x, y, bulletWidth, asset.getHeight()*2);
	}
}
