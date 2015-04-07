package Bullets;

import java.util.Random;

import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class ThunderBall extends Bullet 
{
	private final int STATE_MOVING = 0;
	private final int STATE_ATTACKING = 1;
	private final int STATE_DIEING = 2;
	private int state = STATE_MOVING;
	
	private Timer attackTimer;
	
	private int posX;
	private boolean hitY = false;
	public ThunderBall(int x, int y) 
	{
		super(x, y,assetManager.GetTexture("staticBall"),5,20);
		Random rand = new Random();
		posX = rand.nextInt(800);
		attackTimer = new Timer(1000.0f);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(float deltaTime)
	{
		attackTimer.update(deltaTime);
		if(state == STATE_MOVING)
		{
			if(!hitY)
			{
				y+=speed;
				if(y>=480-asset.getHeight())
				{
					y=480-asset.getHeight();
					hitY = true;
				}
			}
			else
			{
				x-=speed;
				if(x<=posX)
				{
					state = STATE_ATTACKING;
					attackTimer.reset(1.5f);
				}
			}
		}
		else if(state == STATE_ATTACKING)
		{
			if(!attackTimer.running())
			{
				this.asset = assetManager.GetTexture("lightening");
				this.x+=25;
				this.y = 0;
				this.hitBox = new BoundingBox(x,y,asset.getWidth(),asset.getHeight());
				state = STATE_DIEING;
				attackTimer.reset(1.0f);
			}
		}
		else if(state == STATE_DIEING)
		{
			if(!attackTimer.running())
			{
				this.isDead = true;
			}
		}
		this.hitBox.setPosition(x, y);
	}
	@Override
	public int GetDamage()
    {
        return damage;
    }

}
