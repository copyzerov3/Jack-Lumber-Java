package Bullets;

import static com.copyzero.JackLumber2.Settings.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.Timer;

public class MechasinGrenadeBulletExplosion extends Bullet 
{
	private Timer deathTimer;
	private float timeTillDeath = 0.5f;
	public MechasinGrenadeBulletExplosion(int x, int y)
	{
		super(x, y,assetManager.GetTexture("grenade"),0,1);
		this.hitBox = new BoundingBox(x,y-500,asset.getWidth(),500);
		deathTimer = new Timer(timeTillDeath);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void update(float deltaTime)
	{
		deathTimer.update(deltaTime);
		if(!deathTimer.running())
			isDead = true;
	}
	@Override
	public void draw(SpriteBatch batch)
	{
		
	}

}
