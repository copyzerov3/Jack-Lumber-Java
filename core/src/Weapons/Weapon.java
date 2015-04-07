package Weapons;

import Bullets.Bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Timer;

public abstract class Weapon
{
	protected Timer timer;
    protected float shootRate;
    protected int maxEnergy=10;
    protected int energy = maxEnergy;
    protected NamedTexture image;
	public Weapon()
	{
		timer = new Timer(0);
	}
    public abstract Bullet attack(int x,int y);
    public boolean canFire()
    {
    	if(timer.running())
        {
            return false;
        }
        else
            return true;
    }
    public void update(float deltaTime)
    {
    	timer.update(deltaTime);
    }
    public void draw(SpriteBatch batch)
    {
    	
    }
    


}
