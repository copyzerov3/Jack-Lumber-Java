package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class LavaBullet extends Bullet
{
	public LavaBullet(int x,int y)
    {
        super(x,y);
        level = lavaBalllvl;
        asset = assetManager.GetTexture("LavaBullet");
        if(level == 1)
        {
            speed = 2;
            damage = 2;
        }
        else if(level == 2)
        {
            speed = 2;
            damage = 2;
        }
        else if(level ==3)
        {
            speed = 3;
            damage = 3;

        }
        else if(level ==4)
        {
            speed = 4;
            damage = 5;
        }
        y+=asset.getHeight()/2;
    }
    @Override
    public int GetDamage()
    {
        if(lavaBalllvl == 4)
        {

        }
        else
        {
            this.isDead= true;
        }
        return damage;
    }

}
