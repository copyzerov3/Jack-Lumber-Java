package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class SoliderEnemyBullet extends Bullet
{

    public SoliderEnemyBullet(int x,int y)
    {
         super(x,y, assetManager.GetTexture("bullet1"),5,10);
    }
    @Override
    public void update(float deltaTime)
    {
        this.x-=speed;
        hitBox.setPosition(x,y);
    }
}
