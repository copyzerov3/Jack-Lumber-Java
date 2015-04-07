package Enemies.Bosses;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import Bullets.EyeLaserBullet;
import Bullets.WaterMechasinGrenadeBullet;
import Bullets.Waterfall;
import Enemies.Enemy;
import static com.copyzero.JackLumber2.Settings.*;

public class WaterMechasin extends Enemy
{
	private final int STATE_MOVING =1;
	private final int STATE_GRENADE= 2;
	private final int STATE_SEARCH = 3;
	//private final int STATE_WATERFALL = 4;
	private final int STATE_WAVES = 5;
	private final int STATE_EYE_LASERS =6;
	private final int STATE_SHOOT_UP = 7;
	
	private int state = STATE_MOVING;
	
	private boolean onScreen = false;
	private NamedTexture shadow;
	
	private boolean left = false;
	private Random rand;
	
	private Timer reusableTimer;
	private Timer shootTimer;
	private Timer jumpTimer;
	private float timeTillSpecial = 5.0f;
	private float timeTillShot = 1.5f;
	
	private int shotsToTake = 0;
	private int shotsFired = 0;
	private int jumpDeceleration = 0;
	
    public WaterMechasin(int x, int y,Player playerRef,AIDirector director,boolean EndGame)
    {
        super(x, y,2,50*(int)difficulty, assetManager.GetTexture("waterMechasin"),35,playerRef,director);
        if(EndGame)
        {
        	health = 50*(int)difficulty;
        }
        y = 0-image.getHeight();
        shadow= assetManager.GetTexture("shadow");
        rand = new Random();
        reusableTimer = new Timer(10.0f);
        shootTimer = new Timer(2.0f);
        jumpTimer = new Timer(1.0f);
    }
    private void SwitchState()
    {
    	shotsFired = 0;
    	int num = rand.nextInt(3);
    	switch(num)
    	{
    	case 0:
    		state = STATE_GRENADE;
    		break;
    	case 1:
    		state = STATE_WAVES;
    		break;
    	case 2:
    		state = STATE_SEARCH;
    		shotsToTake = rand.nextInt(3)+2;
    		reusableTimer.reset(0.5f);
    		jumpDeceleration = 0;
    	}
    }
    private void Search()
    {
    	if(playerRef.GetX() >x)
    	{
    		x+=speed*2;
    		if(playerRef.GetX()<x)
    		{
    			state = STATE_SHOOT_UP;
    		}
    			
    	}
    	else
    	{
    		x-=speed*2;
    		if(playerRef.GetX()>x)
    			state = STATE_SHOOT_UP;
    	}
    }
    private void Moving()
    {
    	if(!left)
    	{
    		x+=speed;
    		if(this.x + image.getWidth()>=800)
    			left = true;
    	}
    	else
    	{
    		x-=speed;
    		if(x<=0)
    			left= false;
    	}
    	if(!shootTimer.running())
    	{
    		state = STATE_GRENADE;
    		
    	}
    	if(!reusableTimer.running())
    	{
    		SwitchState();
    	}
    }
    private void EyeLasers()
    {
    	if(!shootTimer.running())
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new EyeLaserBullet((int)x,(int)y+image.getHeight() - 20,playerRef.GetX() + playerRef.GetWidth()/2,playerRef.GetY() + playerRef.GetHeight()/2));
	    	directorRef.GetBulletManager().AddEnemyBullet(new EyeLaserBullet((int)x,(int)y+image.getHeight() - 30,playerRef.GetX() + playerRef.GetWidth()/2,playerRef.GetY() + playerRef.GetHeight()/2));
	    	shotsFired++;
	    	shootTimer.reset(timeTillShot);
	    	if(shotsToTake == shotsFired)
	    	{
	    		state = STATE_MOVING;
	    		onScreen = false;
	    		y = 0 - image.getHeight();
	    		shotsFired = 0;
	    	}
    	}
    }
    private void Grenade()
    {
    	directorRef.GetBulletManager().AddEnemyBullet(new WaterMechasinGrenadeBullet((int)x,(int)y + image.getHeight(),directorRef.GetBulletManager(),true));
    	this.shootTimer.reset(timeTillShot);
    	this.state = STATE_MOVING;
    }
    private void ShootUp(float deltaTime)
    {
    	if(y>=0)
    		onScreen=true;
    	this.jumpTimer.update(deltaTime);
    		
    	if(!jumpTimer.running())
    	{
    		jumpDeceleration++;
    		jumpTimer.reset(0.25f);
    	}
    	y+=speed*3 - this.jumpDeceleration;
    	if(y+image.getHeight() <=0)
    	{
    		shotsFired++;
    		Gdx.app.log("Checks", "Shots Fired : " + shotsFired);
    		Gdx.app.log("Checks", "shots To Take : " + shotsToTake);
    		if(shotsFired==shotsToTake)
    		{
    			state = STATE_MOVING;
    			reusableTimer.reset(timeTillSpecial);
    			shotsFired= 0;
    			jumpDeceleration = 0;
    			
    		}
    		else
    		{
    			state = STATE_SEARCH;
    			jumpDeceleration = 0;
    			y=1;
    		}
    		onScreen = false;
    	}
    }
    private void Wave()
    {
    	if(playerRef.GetX() > x)
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new Waterfall((int)x,false));
    	}
    	else
    	{
    		directorRef.GetBulletManager().AddEnemyBullet(new Waterfall((int)x,true));
    	}
    	this.reusableTimer.reset(timeTillSpecial);
    	state = STATE_MOVING;
    }
    @Override
    public void update(float deltaTime)
    {
    	reusableTimer.update(deltaTime);
    	shootTimer.update(deltaTime);
       if(state == STATE_MOVING)
       {
    	   Moving();
       }
       else if(state == STATE_GRENADE)
       {
    	   Grenade();
       }
       else if(state == STATE_SEARCH)
       {
    	   Search();
       }
       else if(state == STATE_WAVES)
       {
    	   Wave();
       }
       else if(state == STATE_EYE_LASERS)
       {
    	   EyeLasers();
       }
       else if(state == STATE_SHOOT_UP)
       {
    	   ShootUp(deltaTime);
       }
       hitBox.setPosition((int)x, (int)y);
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
    	if(state == STATE_MOVING)
    	{
    		if(damage == 1.0f && !fromContact)
    		{
    			onScreen = true;
    			
    			state = STATE_EYE_LASERS;
    			shootTimer.reset(1.0f);
    			shotsToTake = rand.nextInt(2)+3;
    			y=0;
    			return;
    		}
    	}
        health-= damage;
        if(health <=0)
        {
        	isDead = true;
            //Explosions.enemyExplosions.add(new Explosion(x,y,0,false,false) );
        }
    }
    @Override
    public void draw(SpriteBatch batch)
    {
       if(onScreen)
       {
    	   batch.draw(image,x,y);
       }
       else
       {
    	   batch.draw(shadow, x, 0);
       }
    }

	
}
