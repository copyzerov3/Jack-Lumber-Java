package Enemies.Bosses;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.Timer;



import Bullets.TurtleBullet;
import Bullets.TurtleTreeBullet;
import Enemies.Enemy;
import static com.copyzero.JackLumber2.Settings.*;

public class TorpedoTurtle extends Enemy
{
    private final int STATE_ARRIVING = 0;
    private final int STATE_MOVING = 1;
    private final int STATE_SINGLE_SHOT = 2;
    private final int STATE_SWEEP_SHOT = 3;
    private final int STATE_RAMMING = 4;
    private final int STATE_SHOOTING_TREES = 5;
    
    private int state= STATE_ARRIVING;
    
    private Timer reusableTimer;
    private Timer shootTimer;
    
    private float timeTillShot = 1.0f;
    private float timeTillRam = 0.5f;
    private float timeTillSweep = 0.1f;
    private float timeTillSpecial = 5.0f;
    private float timeTillTreeLaunch = 1.0f;
    
    private boolean left = true;
    private int bulletsCreated = 0;
    
    private Random rand;
    public TorpedoTurtle(int x, int y,Player playerRef,AIDirector director,boolean EndGame)
    {
        super(x, y,3,50*(int)difficulty, assetManager.GetTexture("torpedoTurtle"),50,playerRef,director);
        if(EndGame)
        {
        	health = 50*(int)difficulty;
        }
        reusableTimer = new Timer(0.0f);
        shootTimer = new Timer(0.0f);
        rand = new Random();
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
        if(state == STATE_RAMMING)
        {
        	if(fromContact)
        		return;
        }
        health-=damage;
        if(health <=0)
        	isDead= true;
    }
    private void Arriving()
    {
    	x-=speed;
    	if(x <= 800-image.getWidth())
    	{
    		x = 800-image.getWidth();
    		state = STATE_MOVING;
    		shootTimer.reset(timeTillShot);
    		
    	}
    }
    private void ChangeState()
    {
    	int choice = rand.nextInt(4);
    	switch(choice)
    	{
    	case 0:
    		state = STATE_RAMMING;
    		reusableTimer.reset(timeTillRam);
    		left = true;
    		break;
    	case 1:
    		state = STATE_SWEEP_SHOT;
    		reusableTimer.reset(timeTillSweep);
    		
    		break;
    	case 2:
    		state = STATE_SINGLE_SHOT;
    		reusableTimer.reset(timeTillShot);

    		break;
    	case 3:
    		state = STATE_SHOOTING_TREES;
    		reusableTimer.reset(timeTillTreeLaunch);
    		break;
    	}
    }
    private void SingleShot()
    {
    	for(int i = 0;i<3+Settings.difficulty;i++)
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new TurtleBullet((int)x,(int)y,0,rand.nextInt(Settings.HEIGHT)));
    	}
    	shootTimer.reset(timeTillShot+ rand.nextFloat());
    	reusableTimer.reset(timeTillSpecial);
    	state = STATE_MOVING;
    }
    private void SweepShot()
    {
    	if(!reusableTimer.running())
    	{
    		for(int i = 0;i<rand.nextInt(2)+Settings.difficulty;i++)
        	{
    			directorRef.GetBulletManager().AddEnemyBullet(new TurtleBullet((int)x,(int)y,0,bulletsCreated*30-(i*15),10));
        	}
    		
    		reusableTimer.reset(timeTillSweep);
    		bulletsCreated++;
	    	if(bulletsCreated*30 >= 480)
	    	{
	    		state = STATE_MOVING;
	    		bulletsCreated =0;
	    		reusableTimer.reset(timeTillSpecial);
	    	}
    	}
    	
    }
    private void Ramming()
    {
    	
    	if(!reusableTimer.running())
    	{
    		if(left)
    		{
    			Gdx.app.log("Checks", "Going Left");
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
    }
    private void Moving()
    {
    	if(!shootTimer.running() && x >= 400)
    	{
    		state = STATE_SINGLE_SHOT;
    	}
    	else if(!reusableTimer.running())
    	{
    		ChangeState();
    	}
    	else
    	{
    		if(left)
    		{
    			x-=speed;
    			if(x<=0)
    			{
    				left = false;
    			}
    		}
    		else
    		{
    			x+=speed;
    			if(x+image.getWidth() >=800)
    			{
    				left = true;
    			}
    		}
    	}
    }
    private void ShootingTrees()
    {
    	if(!reusableTimer.running())
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new TurtleTreeBullet((int)x+(image.getWidth()/2),(int)y + image.getHeight()));
    		bulletsCreated++;
    		reusableTimer.reset(timeTillTreeLaunch);
    	}
    	if(bulletsCreated > 3 + difficulty)
    	{
    		state = STATE_MOVING;
    		bulletsCreated = 0;
    		reusableTimer.reset(timeTillSpecial);
    		shootTimer.reset(timeTillShot);
    	}
    	
    }
    @Override
    public void update(float deltaFloat)
    {
    	reusableTimer.update(deltaFloat);
    	shootTimer.update(deltaFloat);
        if(state == STATE_ARRIVING)
        {
        	Arriving();
        }
        else if(state== STATE_MOVING)
        {
        	Moving();
        }
        else if(state == STATE_SINGLE_SHOT)
        {
        	SingleShot();
        }
        else if(state == STATE_SWEEP_SHOT)
        {
        	SweepShot();
        }
        else if(state == STATE_RAMMING)
        {
        	Ramming();
        }
        else if(state == STATE_SHOOTING_TREES)
        {
        	ShootingTrees();
        }
        hitBox.setPosition((int)x,(int) y);

    }
}
