package Bullets;

import static com.copyzero.JackLumber2.Settings.*;

public class ScatterBullet extends Bullet
{
	public boolean up;
    public ScatterBullet(int x,int y,boolean up)
    {
        super(x,y,assetManager.GetTexture("scatterBullet"),3,5);
        this.up = up;
    }
    @Override
    public void update(float deltaTime)
    {
        x-=speed;

        if(up)
        {
            y-=speed;
            if(y <= 0)
            {
                up = false;
            }
        }
        else
        {
            y+=speed;
        }
        hitBox.setPosition(x,y);
    }

}
