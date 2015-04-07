package com.copyzero.JackLumber2;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrapManager 
{
	private Vector<Scraps> scraps;
	
	public ScrapManager()
	{
		scraps = new Vector<Scraps>();
	}
	
	public void AddScraps(Scraps ScrapsToAdd)
	{
		scraps.add(ScrapsToAdd);
	}
	public void update(float deltaTime,Player player)
	{
		int numRemoved = 0;
		int length = scraps.size();
		try
		{
			for(int i= 0;i<length;i++)
			{
				if(i == length-numRemoved)
				{
					return;
				}
				scraps.get(i).update(deltaTime);
				if(scraps.get(i).hasBeenPickedUp())
				{
					scraps.remove(i);
					numRemoved++;
					continue;
				}
				else if(BoundingBox.Collided(scraps.get(i).GetBoundingBox(), player.GetBoundingBox()))
				{
					Settings.ScrapsCollectedThisLevel+= scraps.get(i).GetAmount();
					scraps.remove(i);
					numRemoved++;
				}
				
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return;
		}
	}
	public boolean CheckPress(TouchInfo e)
	{
		try
		{
			for(int i= 0;i<scraps.size();i++)
			{
				if(BoundingBox.Collided(scraps.get(i).GetBoundingBox(), new BoundingBox(e.touchX,480-e.touchY,1,1)))
				{
					Settings.ScrapsCollectedThisLevel +=scraps.get(i).GetAmount();
					scraps.remove(i);
					return true;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException ex)
		{
			return false;
		}
		return false;
	}
	public void ClearScraps()
	{
		for(int i = 0;i<scraps.size();i++)
		{
			if(!scraps.get(i).hasBeenPickedUp())
			{
				Settings.ScrapsCollectedThisLevel+=scraps.get(i).GetAmount();
			}
		}
		scraps.clear();
	}
	public void draw(SpriteBatch batch)
	{
		for(int i = 0;i<scraps.size();i++)
		{
			scraps.get(i).draw(batch);
		}
	}
}
