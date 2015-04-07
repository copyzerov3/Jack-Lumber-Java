package Enemies;

import Bullets.CopBullet;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;
public class Cop extends Enemy
{
	private final int STATE_ARRIVING = 0;
    private final int STATE_ATTACKING =1;
    private final int STATE_MOVING = 2;

    private int state= STATE_ARRIVING;

    private Timer canAttack;
    private Timer cooldown;
    private float shootRate = 0.5f;

    private int shotsFired=0;

    private boolean left = true;

    public Cop(int x,int y,Player playerRef,AIDirector DirectorRef)
    {
        super(x,y,2,15, assetManager.GetTexture("cop"),20,playerRef,DirectorRef);
        canAttack = new Timer(shootRate);
        cooldown = new Timer(5);
    }

    @Override
    public void update(float deltaTime)
    {
        canAttack.update(deltaTime);
        cooldown.update(deltaTime);
        if(state == STATE_ARRIVING)
        {
            x-=speed;
            if(x+image.getWidth() < 800)
            {
                canAttack.reset(2);
                cooldown.reset(5);
                state = STATE_MOVING;
            }
        }
        else if(state ==STATE_MOVING)
        {
            if(left)
            {
                x-=speed;
                if(x<200)
                {
                    left = false;
                }
            }
            else
            {
                x+=speed;
                if(800 == x+ image.getWidth())
                {
                    left = true;
                }
            }
            if(!cooldown.running())
            {
                canAttack.reset(shootRate);
                state = STATE_ATTACKING;
            }
        }
        else if(state ==STATE_ATTACKING)
        {
            if(!canAttack.running())
            {
                canAttack.reset(shootRate);
                shotsFired++;
                directorRef.GetBulletManager().AddEnemyBullet(new CopBullet(x,y+image.getHeight(),playerRef.GetX(),playerRef.GetY()));
            }
            if(shotsFired == 5)
            {
                shotsFired=0;
                cooldown.reset(5);
                state = STATE_MOVING;
            }
        }
        hitBox.setPosition((int)x,(int)y);
    }

	

}
