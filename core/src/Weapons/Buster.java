package Weapons;

import Bullets.Bullet;
import Bullets.BusterBullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Buster extends Weapon
{
	public Buster()
	{
		 super();
	     shootRate = 0.5f;
	     this.energy = 0;

	}
	@Override
	public Bullet attack(int x, int y) 
	{
		timer.reset(this.shootRate);
        return new BusterBullet(x,y);
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}

}
