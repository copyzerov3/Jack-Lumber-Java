package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class TreeBullet extends Bullet
{
    public TreeBullet(int x,int y)
    {
        super(x,y);
        level = treeLaucherlvl;
        asset = assetManager.GetTexture("treeBullet");

        if(level == 1)
        {
            speed = 2;
            damage = 2;
        }
        else if(level == 2)
        {
            speed = 3;
            damage = 3;

        }
        else if(level == 3)
        {
            speed = 3;
            damage = 3;
        }
        else if(level == 4)
        {
            speed = 4;
            damage = 5;
        }
        y+=asset.getHeight()/2;
    }
    @Override
    public int GetDamage()
    {
        if(treeLaucherlvl >=3)
        {

        }
        else
        {
            this.isDead= true;
        }
        return damage;
    }


}
