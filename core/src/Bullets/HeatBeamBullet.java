package Bullets;


import static com.copyzero.JackLumber2.Settings.*;

public class HeatBeamBullet extends Bullet
{
	public HeatBeamBullet(int x, int y)
	{
        super(x, y, assetManager.GetTexture("heatBullet"),8,15);
    }

    @Override
    public  void update(float deltaTime)
    {
        x-=speed;
        hitBox.setPosition(x, y);
    }

}
