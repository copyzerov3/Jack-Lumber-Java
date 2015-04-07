package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class StaticBullet extends Bullet
{
	public StaticBullet(int x,int y)
    {
        super(x,y);
        level = staticBalllvl;
        asset = assetManager.GetTexture("staticBall");
        if(level == 1)
        {
            speed = 4;
            damage = 2;
        }
        else if(level == 2)
        {
            speed = 4;
            damage = 2;

        }
        else if(level == 3)
        {
            speed = 5;
            damage = 3;
        }
        else if(level == 4)
        {
            speed = 6;
            damage = 4;
        }

        y+=asset.getHeight()/2;
    }
    @Override
    public int GetDamage()
    {
        if(staticBalllvl ==4)
        {

        }
        else
        {
            this.isDead= true;
        }
        return damage;
    }


}
