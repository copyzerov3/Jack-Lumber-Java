package Enemies;

import java.util.Random;
import java.util.Vector;

import Bullets.SoliderEnemyBullet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;


public class SoliderEnemy extends Enemy
{

	private final int STATE_FINDING = 0;
	private final int STATE_FOUND = 1;
	
	private int STATE = STATE_FINDING;
	
	private Timer shoot;
    private double yOffset;
    private double time;
    private int xPos;
    private int yPos;
    private float shootRate;
    Random rand = new Random();
    
    public SoliderEnemy(int x, int y,Player playerRef,AIDirector director)
    {
        super(x, y, 2, 3, assetManager.GetTexture("hover"),25,playerRef,director);

        shootRate = rand.nextInt(1)+1.5f;
        shoot = new Timer(0);
        xPos = rand.nextInt(600)+150;
        yPos = rand.nextInt(380);
        super.GetAcceleration(xPos, yPos);

    }
    @Override
    public void update(float deltaTime)
    {
        if(STATE== STATE_FINDING)
        {
        	if(x>xPos)
        	{
        		x-=accelerationX;
        		if(x<xPos)
        		{
        			x = xPos;
        		}
        	}
        	else
        	{
        		x+=accelerationX;
        		if(x>xPos)
        		{
        			x = xPos;
        		}
        	}
        	if(y>yPos)
        	{
        		y-=accelerationY;
        		if(y<yPos)
        		{
        			y = yPos;
        		}
        	}
        	else
        	{
        		y+=accelerationY;
        		if(y>yPos)
        			y = yPos;
        	}
        	if((Math.abs(x-xPos)== 1 || Math.abs(x-xPos) == 0))
        	{
        		if(Math.abs(y-yPos) == 1 || Math.abs(y-yPos) == 0)
        		{
        			Vector<Enemy> enemies = directorRef.getEnemies();
	        		for(int i = 0;i<enemies.size();i++)
	        		{
	        			if(enemies.get(i).isCollidable())
	        			{
	        				if(BoundingBox.Collided(enemies.get(i).GetBoundingBox(), this.hitBox))
	        				{
	        					boolean found = false;
	        					do
	        					{
	        						xPos = rand.nextInt(600)+150;
		        			        yPos = rand.nextInt(380);
		        			        
		        			        for(int j = 0;j<enemies.size();j++)
		        			        {
		        			        	if(!BoundingBox.Collided(enemies.get(j).GetBoundingBox(), new BoundingBox(xPos,yPos,image.getWidth(),image.getHeight())))
		        			        	{
		        			        		found = true;
		        			        		super.GetAcceleration(xPos, yPos);
		        			        	}
		        			        }
	        					}while(!found);
	        			        
	        			        hitBox.setPosition((int)x, (int)y + (int) yOffset);
	        			        return;
	        				}
	        			}
	        		}
	        		STATE= STATE_FOUND;
	        		collidable= true;
        		}
        	}
        }
        else if(STATE== STATE_FOUND)
        {
            time += deltaTime;
        	yOffset = Math.sin(time)*10;
            shoot.update(deltaTime);
            
        	if(shoot.running() == false)
	        {
	            directorRef.GetBulletManager().AddEnemyBullet(new SoliderEnemyBullet((int)x, (int)y+ (int)yOffset +20));
	            shoot.reset(shootRate + rand.nextInt(1));
	        }
        }
        
        hitBox.setPosition((int)x, (int)y + (int) yOffset);
    }
    @Override
    public void draw(SpriteBatch batch)
    {
        batch.draw(image,x,y + (int)yOffset);
    }


}
