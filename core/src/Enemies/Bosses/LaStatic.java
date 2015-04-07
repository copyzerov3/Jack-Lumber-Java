package Enemies.Bosses;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.Timer;

import Bullets.LaStaticBallBullet;
import Bullets.ThunderBall;
import Enemies.Enemy;
import static com.copyzero.JackLumber2.Settings.*;

public class LaStatic extends Enemy
{
	private final int STATE_ARRIVING = 0;
	private final int STATE_MOVING = 1;
	private final int STATE_STATIC_BALL = 2;
	private final int STATE_CLAW = 3;
	private final int STATE_LIFE_STEAL = 4;
	private final int STATE_THUNDER_STRIKE = 5;
	
	private int state = STATE_ARRIVING;
	
	private Random rand = new Random();

	private boolean left = true;
	
	private int hitCounter = 0;
	private int moveCounter = 0;
	private Timer reusableTimer;
	private Timer moveTimer;
	
	private int shotsFired =0;
	private int numberOfShots=0;
	
	private float timeTillMove = 1.5f;
	private float timeTillSpecial = 5.0f;
    public LaStatic(int x, int y,Player playerRef,AIDirector director,boolean EndGame)
    {
        super(x, y,3,40*(int)difficulty, assetManager.GetTexture("static"),35,playerRef,director);
        if(EndGame)
        {
        	health = 40*(int)difficulty;
        }
        rand = new Random();
        reusableTimer = new Timer(0.0f);
        moveTimer = new Timer(0.0f);
    }
    private void SwitchState()
    {
    	int num = rand.nextInt(4);
    	switch(num)
    	{
    	case 0:
    		state = STATE_STATIC_BALL;
    		break;
    	case 1:
    		state = STATE_CLAW;
    		y = playerRef.GetY();
    		break;
    	case 2:
    		state = STATE_LIFE_STEAL;
    		break;
    	case 3:
    		state = STATE_THUNDER_STRIKE;
    		numberOfShots =rand.nextInt(3)+3;
    		this.x = 800-image.getWidth();
    		break;
    	}
    }
    private void Arriving()
    {
    	x-=speed;
    	if(x <= 800-image.getWidth())
    	{
    		x = 800-image.getWidth();
    		state = STATE_MOVING;
    		reusableTimer.reset(timeTillSpecial);
    	}
    }
    private void Moving()
    {
    	if(!moveTimer.running())
    	{
    		x = rand.nextInt(600 - image.getWidth())+200;
    		y = rand.nextInt(480-image.getHeight());
    		state = STATE_STATIC_BALL;
    	}
    	if(!reusableTimer.running())
    	{
    		SwitchState();
    	}
    }
    private void StaticBall()
    {
    	directorRef.GetBulletManager().AddEnemyBullet(new LaStaticBallBullet((int)x,(int)y + (image.getHeight()/2),playerRef));
    	state = STATE_MOVING;
    	moveTimer.reset(timeTillMove);
    }
    private void Claw()
    {
    	if(left)
		{
			x-=speed*4;
			if(x<=0)
			{
				left = false;
			}
		}
		else
		{
			x+=speed*4;
			if(x>=Settings.WIDTH-image.getWidth())
			{
				state = STATE_MOVING;
				left = true;
				reusableTimer.reset(timeTillSpecial);
			}
		}	
    }
    private void LifeSteal()
    {
    	if(!moveTimer.running())
    	{
    		x = rand.nextInt(600 - image.getWidth())+200;
    		y = rand.nextInt(480-image.getHeight());
    		moveTimer.reset(1.0f);
    		moveCounter++;
    		if(moveCounter ==5)
    		{
    			moveTimer.reset(2.0f);
    			playerRef.takeDamage(10);
    			this.health+=10;
    			moveCounter = 0;
    		}
    	}
    }
    private void ThunderStrike()
    {
    	if(!reusableTimer.running())
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new ThunderBall((int)x,(int)y));
    		reusableTimer.reset(0.5f);
    		shotsFired++;
    		if(shotsFired == numberOfShots)
    		{
    			state = STATE_MOVING;
    			this.reusableTimer.reset(timeTillSpecial);
    			shotsFired= 0;
    		}
    	}
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
        if(state == STATE_CLAW)
        {
        	if(fromContact)
        		return;
        }
        if(state == STATE_LIFE_STEAL)
        {
        	health--;
        	hitCounter++;
        	if(hitCounter == 10)
        	{
        		state = STATE_MOVING;
        		hitCounter = 0;
        		moveCounter= 0;
        		reusableTimer.reset(timeTillSpecial);
        		moveTimer.reset(timeTillMove);
        	}
        }
        else
        {
        	health-=damage;
        	if(health <=0)
        		isDead= true;
        }
        
    }
    @Override
    public void update(float deltaTime)
    {
    	moveTimer.update(deltaTime);
    	reusableTimer.update(deltaTime);
    	switch(state)
    	{
	    	case STATE_ARRIVING:
	    		Arriving();
	    		break;
	    	case STATE_MOVING:
	    		Moving();
	    		break;
	    	case STATE_STATIC_BALL:
	    		StaticBall();
	    		break;
	    	case STATE_CLAW:
	    		Claw();
	    		break;
	    	case STATE_LIFE_STEAL:
	    		LifeSteal();
	    		break;
	    	case STATE_THUNDER_STRIKE:
	    		ThunderStrike();
	    		break;
    	}
    	hitBox.setPosition((int)x,(int) y);
    }
}
