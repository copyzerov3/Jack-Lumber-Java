package Weapons;

import Bullets.Bullet;
import Bullets.TreeBullet;

public class Tree extends Weapon
{
	public Tree()
    {
        super();
        maxEnergy = 10;
        shootRate =1;
    }

	@Override
	public Bullet attack(int x, int y) 
	{
		timer.reset(this.shootRate);
        return new TreeBullet(x,y);

	}

}
