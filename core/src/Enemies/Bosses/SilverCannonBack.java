package Enemies.Bosses;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import Bullets.CannonBackLavaBullet;
import Bullets.HeatBeamBullet;
import Enemies.Enemy;
import Enemies.SuicideVultures;
import static com.copyzero.JackLumber2.Settings.*;

public class SilverCannonBack extends Enemy
{

	private final int STATE_ARRIVING = 4;
    private final int STATE_MOVING = 0;
    private final int STATE_JUMPING = 1;
    private final int STATE_HEAT_BULLET = 2;
    private final int STATE_HEAT_BEAM = 3;
    private final int STATE_LAVA_BALL = 5;

    private int state= STATE_ARRIVING;

    private Random rand;
    private NamedTexture shadow;
    
    private boolean up = true;
    private boolean returning = false;
    
    private int attackCounter = 0;
    private int numberOfAttacks = 0;
    
    private Timer reusableTimer;
    private Timer jumpTimer;
    private Timer shootTimer;
    
    private float waitTime = 0.6f;
    private float timePerShot = 0.25f;
    private float timeTillSpecial = 3.0f;
    
    public SilverCannonBack(int x, int y,Player playerRef,AIDirector director,boolean EndGame)
    {
        super(x, y,5,50*(int)difficulty, assetManager.GetTexture("silverCannonBack"),35,playerRef,director);
        if(EndGame)
        {
        	health= 50 * difficulty;
        }
        shadow = assetManager.GetTexture("shadow");
        rand = new Random();
        reusableTimer = new Timer(0);
        jumpTimer= new Timer(0);
        shootTimer = new Timer(0);
    }
    private void PickState()
    {
    	int choice = rand.nextInt(4);
    	reusableTimer.reset(timePerShot);
    	numberOfAttacks= rand.nextInt(2)+3;
    	switch(choice)
    	{
    	case 0:
    		up = true;
    		state = STATE_JUMPING;
    		break;
    	case 1:
    		state = STATE_LAVA_BALL;
    		break;
    	case 2:
    		state = STATE_HEAT_BULLET;
    		break;
    	case 3:
    		state = STATE_LAVA_BALL;
    		break;
    	}
    }
    private void Arriving()
    {
    	x-=speed;
    	if(x <= 800-image.getWidth())
    	{
    		x = 800-image.getWidth();
    		jumpTimer.reset(waitTime);
    		reusableTimer.reset(timeTillSpecial);
    		state = STATE_MOVING;   		
    	}
    }
    private void Moving()
    {
    	if(!jumpTimer.running())
    	{
    		if(up)
	    	{
	    		y+=speed;
	    		if(y+image.getHeight()>=440)
	    			up = false;
	    	}
	    	else
	    	{
	    		y-=speed;
	    		if(y<=0)
	    		{
	    			up = true;
	    			jumpTimer.reset(waitTime);
	    		}
	    			
	    	}
    		if(!shootTimer.running())
    		{
    			state = STATE_HEAT_BULLET;
    		}
    		if(!reusableTimer.running())
    		{
    			this.PickState();
    		}
    	}
    	
    }
    private void SpawnBirds()
    {
    	int ran = rand.nextInt(2)+3;
        int pos;
    	 for(int i = 0;i<ran;i++)
         {
             pos = rand.nextInt(450);
             directorRef.getEnemies().add(new SuicideVultures(800,pos,playerRef,directorRef));

         }
    }
    private void Jumping()
    {
    	if(!reusableTimer.running())
    	{
    		if(returning)
    		{
    			if(y>=480)
    			{
    				x = 800-image.getWidth();
    				state = STATE_MOVING;
    				numberOfAttacks = 0;
    				attackCounter = 0;
    				reusableTimer.reset(timeTillSpecial);
    				returning = false;
    			}
    		}			
    		if(up)
    		{
    			y+=speed*2;
    			if(y>=800)
    			{
    				up = false;
    				this.x = playerRef.GetX();
    				SpawnBirds();
    			}
    		}
    		else
    		{
    			y-=speed*2;
    			if(y<=0)
    			{
    				attackCounter++;
    				if(attackCounter == numberOfAttacks)
    				{
    					returning = true;
    				}
    				
    				up = true;
    			}
    		}
    	}
    }
    private void HeatBullet()
    {
    	directorRef.GetBulletManager().AddEnemyBullet(new HeatBeamBullet((int)x,(int)y+image.getHeight()/2));
    	shootTimer.reset(timePerShot + rand.nextFloat());
    	state = STATE_MOVING;
    }
    private void HeatBeam()
    {
    	if(attackCounter != numberOfAttacks)
    	{
    		if(y <  playerRef.GetY())
    		{
    			y+= speed;
    			if(y >  playerRef.GetY())
    			{
    				y= playerRef.GetY();
    			}
    			
    		}
    		else
    		{
    			y-=speed;
    			if(y <  playerRef.GetY())
    			{
    				y= playerRef.GetY();
    			}
    		}
    		if(playerRef.GetY() == y)
    		{
    			attackCounter++;
    			directorRef.GetBulletManager().AddEnemyBullet(new Bullets.HeatBeam((int)x,(int)y+image.getHeight()/2));
    			reusableTimer.reset(1.0f);
    		}
    	}
    	else
    	{
    		if(!reusableTimer.running())
    		{
    			state = STATE_MOVING;
    			numberOfAttacks = 0;
    			attackCounter = 0;
    			reusableTimer.reset(timeTillSpecial);
    		}
    	}
    }
    private void LavaBall()
    {
    	if(y!= 0)
    	{
    		y-=speed;
    		if(y<=0)
    			y= 0;
    	}
    	if(!reusableTimer.running())
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new CannonBackLavaBullet((int)x,(int)y,rand.nextInt(200),rand.nextInt(480)));
    		this.attackCounter++;
    		reusableTimer.reset(timePerShot);
    		if(attackCounter == numberOfAttacks)
    		{
    			int temp = rand.nextInt(2);
    			if(temp == 0)
    			{
    				state = STATE_MOVING;
	    			numberOfAttacks = 0;
	    			attackCounter = 0;
	    			reusableTimer.reset(timeTillSpecial);
    			}
    			else
    			{
    				state= STATE_HEAT_BEAM;
    				attackCounter = 0;
    				numberOfAttacks =1;
    			}
    			
    		}
    		
    	}
    }
    @Override
    public void update(float deltaTime)
    {
    	jumpTimer.update(deltaTime);
    	reusableTimer.update(deltaTime);
    	switch(state)
    	{
    	case STATE_ARRIVING:
    		Arriving();
    		break;
    	case STATE_MOVING:
        	shootTimer.update(deltaTime);
    		Moving();
    		break;
    	case STATE_JUMPING:
    		Jumping();
    		break;
    	case STATE_HEAT_BULLET:
    		HeatBullet();
    		break;
    	case STATE_HEAT_BEAM:
    		HeatBeam();
    		break;
    	case STATE_LAVA_BALL:
    		LavaBall();
    		break;
    	}
        hitBox.setPosition((int)x, (int)y);
    }

    @Override
    public void draw(SpriteBatch batch)
    {
    	if(state == STATE_JUMPING && !up)
    	{
    		if(y>=480)
    		{
    			batch.draw(shadow, x,0);
    		}
    	}
        batch.draw(image,x,y);
    }


}
