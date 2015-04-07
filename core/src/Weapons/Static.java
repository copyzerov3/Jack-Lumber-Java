package Weapons;

import Bullets.Bullet;
import Bullets.StaticBullet;

public class Static extends Weapon 
{
	public Static()
    {
        super();
        shootRate = 1;
        maxEnergy = 10;
    }

	@Override
	public Bullet attack(int x, int y) 
	{
		timer.reset(this.shootRate);
        return new StaticBullet(x,y);
	}

}
