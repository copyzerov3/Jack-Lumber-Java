package Enemies;

import java.util.Random;

import Bullets.SoliderEnemyBullet;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class Fighter  extends Enemy
{

	 private Timer shoot;
	 private Random rand = new Random();
	 private int xPos;
	 private float shootRate=1;
	 public Fighter(int x,int y,Player playerRef,AIDirector director)
	    {
	        super(x,y,2,40, assetManager.GetTexture("fighter"),20,playerRef,director);
	        shoot = new Timer(5);
	        xPos = rand.nextInt(250)+150;
	    }

	    @Override
	    public void update(float deltaTime)
	    {
	        shoot.update(deltaTime);
	        if(xPos<this.x)
	        {
	            x-=speed;
	        }
	        if(playerRef.GetY()+playerRef.GetHeight()/2 < y)
	        {
	            y-=speed;
	        }
	        else
	        {
	            y+=speed;
	        }
	        if(shoot.running() == false)
	        {
	            directorRef.GetBulletManager().AddEnemyBullet(new SoliderEnemyBullet((int)x,(int) y + (image.getHeight() / 2)));
	            shoot.reset(shootRate);
	        }
	    }


}
