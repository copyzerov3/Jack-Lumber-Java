package com.copyzero.JackLumber2;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Bullets.Bullet;
import Weapons.Buster;
import Weapons.Weapon;

public class Player 
{
	private BoundingBox hitBox;
	private int x = 0;
	private int y=0;
	
	private int health;
    private int maxHealth = 100;
    
    private final float playerHitTime=2.0f;
	public Weapon currentWeapon;
    
    private Timer hitTimer;

    private NamedTexture image;

    private boolean UP = false;
    private boolean DOWN = false;
    private boolean LEFT = false;
    private boolean RIGHT = false;
    private boolean ATTACKING = false;
    
    
	public Player(int x,int y)
	{
		this.x = x;
		this.y = y;
		this.image = Settings.assetManager.GetTexture("playerNormal");
		hitBox = new BoundingBox(x,y,image.getWidth(),image.getHeight());
        

        if(Settings.difficulty != Settings.DIFFICULTY_SUICIDE)
            maxHealth = 100;
        else
            maxHealth = 50;
        switch(Settings.healthlvl)
        {
            case 2:
                if(Settings.difficulty == Settings.DIFFICULTY_SUICIDE)
                    maxHealth+=20;
                else
                    maxHealth = 133;
                break;
            case 3:
                if(Settings.difficulty == Settings.DIFFICULTY_SUICIDE)
                    maxHealth+=35;
                else
                    maxHealth = 166;
                break;
            case 4:
                if(Settings.difficulty == Settings.DIFFICULTY_SUICIDE)
                    maxHealth= 100;
                else
                    maxHealth = 200;
                break;
        }
        health= maxHealth;
        hitTimer = new Timer(2);
        this.currentWeapon = new Buster();
	}
	public void reset()
	{
		health = maxHealth;
		currentWeapon = new Buster();
		x=0;
		y=0;
	}
	public void update(float deltaTime)
	{
		currentWeapon.update(deltaTime);
    	hitTimer.update(deltaTime);
		if(UP)
		{
			y+=getSpeed();
			if(y>Settings.HEIGHT-image.getHeight())
			{
				y = Settings.HEIGHT-image.getHeight();
			}
		}
		if(DOWN)
		{
			y-=getSpeed();
			if(y <0)
			{
				y=0;
			}
		}
		if(LEFT)
		{
			x-=getSpeed();
			if(x<0 )
				x=0;
		}
		if(RIGHT)
		{
			x+=getSpeed();
			if(x>Settings.WIDTH - image.getWidth())
				x= Settings.WIDTH - image.getWidth();
		}
		hitBox.setPosition(x, y);
	}
	public void ClearKeys()
	{
		DOWN= false;
		UP= false;
		LEFT= false;
		RIGHT = false;
		ATTACKING = false;
	}
	public void UpdateKeys(int keycode, boolean Down)
	{
		if(Down)
		{
			if(keycode == Keys.W || keycode == Keys.UP)
			{
				UP = true;
			}
			else if(keycode == Keys.S || keycode == Keys.DOWN)
			{
				DOWN = true;
			}
			else if(keycode == Keys.A || keycode == Keys.LEFT)
			{
				LEFT = true;
			}
			else if(keycode == Keys.D || keycode == Keys.RIGHT)
			{
				RIGHT = true;
			}
			else if(keycode == Keys.SPACE)
			{
				ATTACKING = true;
			}
			
		}
		else
		{
			if(keycode == Keys.W || keycode == Keys.UP)
			{
				UP = false;
			}
			else if(keycode == Keys.S || keycode == Keys.DOWN)
			{
				DOWN = false;
			}
			else if(keycode == Keys.A || keycode == Keys.LEFT)
			{
				LEFT = false;
			}
			else if(keycode == Keys.D || keycode == Keys.RIGHT)
			{
				RIGHT = false;
			}
			else if(keycode == Keys.SPACE)
			{
				ATTACKING = false;
			}
		}
	}

	public int getSpeed()
	{
		return 3;
	}
    public void draw(SpriteBatch batch)
    {
        batch.draw(image,x,y);
        
    }
    public boolean takeDamage(float damage)
    {
    	if(hitTimer.running())
    	{
    		return false;
    	}
    	Settings.Multiplier = 1;
    	Settings.DamageTakenThisLevel +=damage;
       health-=damage;
       hitTimer.reset(playerHitTime);
       return (health <=0);
    }
    public boolean canBeHit()
    {
        return (!hitTimer.running());
    }
    public int GetX()
    {
    	return x;
    }
    public int GetY()
    {
    	return y;
    }
    public void SetX(int x)
    {
    	this.x= x;
    }
    public void SetY(int y)
    {
    	this.y = y;
    }
    public int GetWidth()
    {
    	return image.getWidth();
    }
    public int GetHeight()
    {
    	return image.getHeight();
    }
    public BoundingBox GetBoundingBox()
    {
    	return hitBox;
    }
    public int GetHealth()
    {
    	return health;
    }
    public void SetHealth(int health)
    {
    	this.health = health;
    }
    public boolean attacking()
    {
    	if(ATTACKING)
    	{
    		if(currentWeapon.canFire())
    			return true;
    	}
    	return false;
    }
    public Bullet getBullet()
    {
        return currentWeapon.attack(x+image.getWidth(),y+(image.getHeight() /2));
    }

}
