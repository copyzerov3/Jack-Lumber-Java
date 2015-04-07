package Enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;

public abstract class Enemy
{
	protected BoundingBox hitBox;
	protected float x = 0;
	protected float y=0;
	protected NamedTexture image;
	protected float health;
    protected int contactDamage;
    protected Player playerRef;
    protected boolean isDead = false;
	
    protected boolean collidable =false;
    
    protected float accelerationX = 0.0f;
    protected float accelerationY = 0.0f;
    
    protected AIDirector directorRef;
    
	protected  int speed;
	
	public Enemy(int x,int y,int speed,int health,NamedTexture image,int contactDamage,Player playerRef,AIDirector directorRef)
	{
        this.speed = speed;
        this.health = health;
		this.x = x;
		this.y = y;
        this.image = image;
        this.contactDamage = contactDamage;
        this.playerRef = playerRef;
        this.directorRef = directorRef;
        hitBox = new BoundingBox(x,y,image.getWidth(),image.getHeight());
	}
    public void takeDamage(float damage,boolean fromContact)
    {
        health-= damage;
        if(health <=0)
        {
        	isDead = true;
            //Explosions.enemyExplosions.add(new Explosion(x,y,0,false,false) );
        }
    }
    public boolean isCollidable()
    {
    	return collidable;
    }
    public boolean isDead()
    {
    	return isDead;
    }
    public float GetX()
    {
    	return x;
    }
    public float GetY()
    {
    	return y;
    }
	public void setPosition(int x,int y)
    {
        this.x = x;
        this.y=y;
        hitBox.setPosition(x, y);
    }
	public abstract void update(float deltaTime);
    public void draw(SpriteBatch batch)
    {
        batch.draw(image,x,y);
    }
    public BoundingBox GetBoundingBox()
    {
    	return hitBox;
    }
    public void GetAcceleration(int newX,int newY)
    {
    	if(Math.abs(x-newX) > Math.abs(y-newY))
    	{
    		 accelerationX = speed;
    		 accelerationY = Math.abs(y-newY)/(Math.abs(x-newX)/accelerationX);
    	}
    	else if(Math.abs(x-newX) < Math.abs(y-newY))
    	{
    		accelerationY = speed;
   		 	accelerationX = Math.abs(x-newX)/(Math.abs(y-newY)/accelerationY);
    	}
    	else
    	{
    		accelerationX = speed;
    		accelerationY=speed;
    	}
    }
    public float GetContactDamage()
    {
    	return contactDamage;
    }
}
