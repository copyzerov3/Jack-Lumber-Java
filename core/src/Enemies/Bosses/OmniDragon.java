package Enemies.Bosses;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;
import Bullets.CannonBackLavaBullet;
import Bullets.LaStaticBallBullet;
import Bullets.TurtleTreeBullet;
import Bullets.WaterMechasinGrenadeBullet;
import Enemies.Enemy;

public class OmniDragon extends Enemy
{    
	private final int STATE_MOVING = 0;
	private final int STATE_SHOOT = 1;
	private final int STATE_SWEEP = 2;
	
	private int state = STATE_MOVING;
	
	private Timer shootTimer;
	private Timer reusableTimer;
	
	private float timeTillSpecial = 5.0f;
	private float timeTillShot = 1.0f;
	
	private Random rand;
	
    public OmniDragon(int x,int y,Player playerRef,AIDirector director)
    {
        super(x,y,2,200*(int)difficulty, assetManager.GetTexture("OmniDragon"),25,playerRef,director);
        shootTimer = new Timer(1.0f);
        reusableTimer = new Timer(timeTillSpecial);
        rand = new Random();
        
    }
    private void ChangeState()
    {
    	
    }
    private void Move()
    {
    	
    	if(shootTimer.running())
    	{
    		state = STATE_SHOOT;
    	}
    	if(!reusableTimer.running())
    	{
    		
    	}
    }
    private void Shoot()
    {
    	shootTimer.reset(timeTillShot);
    	int num = rand.nextInt(4);
    	switch(num)
    	{
    	case 0:
    		
    		break;
    	case 1:
    		break;
    	case 2:
    		break;
    	case 3:
    		break;
    	}
    	state = STATE_MOVING;
    }
    private void Sweep()
    {
    	
    }
    @Override
    public void update(float deltaTime)
    {
    	shootTimer.update(deltaTime);
    	reusableTimer.update(deltaTime);
    	switch(state)
    	{
    		case STATE_MOVING:
    			Move();
    		break;
    		case STATE_SHOOT:
    			Shoot();
    		break;
    		case STATE_SWEEP:
    			Sweep();
    		break;
    	}
    	hitBox.setPosition((int)x, (int)y);
    }


}
