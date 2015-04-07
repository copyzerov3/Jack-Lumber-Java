package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class GrenadeBullet extends Bullet
{
	public GrenadeBullet(int x,int y)
    {
        super(x,y);
        level = waterGrenadelvl;
        asset = assetManager.GetTexture("waterGrenade");
        if(level == 1)
        {
            damage = 3;
            speed = 2;
        }
        else if(level == 2)
        {
            damage = 3;
            speed = 3;
        }
        else if(level == 3)
        {
            damage = 4;
            speed = 3;
        }
        else if(level == 4)
        {
            damage = 4;
            speed = 3;
        }
        y+=asset.getHeight()/2;
    }
    @Override
    public int GetDamage()
    {
        this.isDead = true;
        /*if(waterGrenadelvl != 4)
            Explosions.playerExplosions.add( new Explosion(x,y,damage,false,true));
        else
            Explosions.enemyExplosions.add( new Explosion(x,y,damage,true,true));*/
        return 0;
    }


}
