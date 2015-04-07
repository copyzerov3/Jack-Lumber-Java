package com.copyzero.JackLumber2;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Bullets.Bullet;

public class BulletManager
{
	private Vector<Bullet> enemyBullets,playerBullets;
	
	public BulletManager()
	{
		enemyBullets = new Vector<Bullet>();
		playerBullets = new Vector<Bullet>();
	}
	
	public void AddEnemyBullet(Bullet bulletToAdd)
	{
		enemyBullets.add(bulletToAdd);
	}
	public void AddPlayerBullet(Bullet bulletToAdd)
	{
		playerBullets.add(bulletToAdd);
	}
	public void reset()
	{
		enemyBullets.clear();
		playerBullets.clear();
	}
	public void Update(Player playerRef,float deltaTime)
	{
		int i = 0;
		int length = playerBullets.size();
		int numRemoved = 0;
		try
		{
			for(i = 0;i<length;i++)
			{
				if(i == length-numRemoved)
				{
					break;
				}
				playerBullets.get(i).update(deltaTime);
				if(playerBullets.get(i).isDead())
				{
					playerBullets.remove(i);
					numRemoved++;
					continue;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return;
		}
		length = enemyBullets.size();
		numRemoved= 0;
		try
		{
			for(i = 0;i<length;i++)
			{
				if(i == length-numRemoved)
				{
					return;
				}
				enemyBullets.get(i).update(deltaTime);
				if(enemyBullets.get(i).isDead())
				{
					enemyBullets.remove(i);
					numRemoved++;
					continue;
				}
				else if(playerRef.canBeHit() && !enemyBullets.get(i).isDead())
				{
					if(BoundingBox.Collided(enemyBullets.get(i).GetBoundingBox(),playerRef.GetBoundingBox()))
					{
						playerRef.takeDamage(enemyBullets.get(i).GetDamage());
						if(enemyBullets.get(i).isDead())
						{
							enemyBullets.remove(i);
							numRemoved++;
						}
						
						continue;
					}
				}
				if(enemyBullets.get(i).Shootable())
				{
					for(int j = 0;j<playerBullets.size();j++)
					{
						if(BoundingBox.Collided(enemyBullets.get(i).GetBoundingBox(), playerBullets.get(j).GetBoundingBox()))
						{
							playerBullets.remove(j);
							enemyBullets.get(i).onShot();
							if(enemyBullets.get(i).isDead())
							{
								enemyBullets.remove(i);
								numRemoved++;
							}
							
							break;
						}
					}
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
        {
        	return;
        }
		
	}
	public Vector<Bullet> GetEnemyBullets()
	{
		return enemyBullets;
	}
	public Vector<Bullet> GetPlayerBullets()
	{
		return playerBullets;
	}
	public void Draw(SpriteBatch batch)
	{
		int i = 0;
		for(i = 0;i<playerBullets.size();i++)
		{
			playerBullets.get(i).draw(batch);
		}
		for(i = 0;i<enemyBullets.size();i++)
		{
			enemyBullets.get(i).draw(batch);
		}
	}
}
