package Bullets;

import static com.copyzero.JackLumber2.Settings.assetManager;

import com.copyzero.JackLumber2.Player;

public class LaStaticBallBullet extends Bullet
{
	Player playerRef;
	
    public LaStaticBallBullet(int x,int y,Player playerRef)
    {
        super(x,y,assetManager.GetTexture("staticBall") ,5,20);
        this.playerRef =playerRef;
    }
    @Override
    public void update(float deltaTime)
    {
        x-=speed;
        if(x> 800/2)
        {
            if(playerRef.GetY()+asset.getHeight()/2 < y)
            {
                y-=speed;
                if(playerRef.GetY()+asset.getHeight()/2 > y)
                {
                	y = playerRef.GetY()+asset.getHeight()/2;
                }
            }
            else
            {
                y+=speed;
                if(playerRef.GetY()+asset.getHeight()/2 < y)
                {
                	y = playerRef.GetY()+asset.getHeight()/2;
                }
            }
        }

        hitBox.setPosition(x, y);
    }

}
