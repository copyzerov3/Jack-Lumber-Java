package Weapons;

import Bullets.Bullet;
import Bullets.LavaBullet;

public class Lava extends Weapon
{
	public Lava()
    {
        super();
        shootRate = 1;
        maxEnergy = 10;
    }
	@Override
	public Bullet attack(int x, int y)
	{
		timer.reset(shootRate);
        return new LavaBullet(x,y);

	}

}
