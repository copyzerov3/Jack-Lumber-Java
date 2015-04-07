package com.copyzero.JackLumber2;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scraps
{
	 private BoundingBox hitBox;
	 private int x;
	 private int y;
	 private int amount;
	 private NamedTexture  image;
	 private boolean pickedUp = false;
	 private Timer deathTimer;
	    public Scraps(int x,int y)
	    {
	        Random rand = new Random();
	        this.x =x;
	        this.y =y;
	        this.image = Settings.assetManager.GetTexture("scraps");
	        this.amount = (rand.nextInt(25)+1) * Settings.Multiplier;
	        deathTimer = new Timer(10.0f);
	        hitBox = new BoundingBox(x,y,image.getWidth(),image.getHeight());
	    }
	    public void update(float deltaTime)
	    {
	        deathTimer.update(deltaTime);
	        if(!deathTimer.running())
	        {
	        	pickedUp = true;
	        }
	    }
	    public void draw(SpriteBatch batch)
	    {
	        batch.draw(image,x,y);
	    }
	    public BoundingBox GetBoundingBox()
	    {
	    	return hitBox;
	    }
	    public int GetX()
	    {
	    	return x;
	    }
	    public int GetY()
	    {
	    	return y;
	    }
	    public int GetAmount()
	    {
	    	pickedUp = true;
	    	return amount;
	    }
	    public boolean hasBeenPickedUp()
	    {
	    	return pickedUp;
	    }
	    


}
