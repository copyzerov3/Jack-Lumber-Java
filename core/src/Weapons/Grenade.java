package Weapons;

import Bullets.Bullet;
import Bullets.GrenadeBullet;

public class Grenade extends Weapon
{
	public Grenade()
    {
        super();
        shootRate = 1;
        maxEnergy = 10;
    }

	@Override
	public Bullet attack(int x, int y)
	{
		timer.reset(shootRate);
        return new GrenadeBullet(x,y);
	}


}
