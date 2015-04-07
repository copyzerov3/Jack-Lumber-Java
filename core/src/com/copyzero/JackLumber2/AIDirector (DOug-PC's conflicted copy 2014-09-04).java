package com.copyzero.JackLumber2;
import static com.copyzero.JackLumber2.Settings.*;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Enemies.Cop;
import Enemies.Coyote;
import Enemies.Deer;
import Enemies.Devil;
import Enemies.Enemy;
import Enemies.Fighter;
import Enemies.Fish;
import Enemies.Meteor;
import Enemies.Rats;
import Enemies.Scorpion;
import Enemies.Shark;
import Enemies.Shield;
import Enemies.SoliderEnemy;
import Enemies.SuicideBird;
import Enemies.Bosses.LaStatic;
import Enemies.Bosses.OmniDragon;
import Enemies.Bosses.SilverCannonBack;
import Enemies.Bosses.TorpedoTurtle;
import Enemies.Bosses.WaterMechasin;
public class AIDirector
{
	 private int level;
	    private int area;
	    private int stage=0;
	    private int stageMax;
	    private Player playerRef;
	    private int spawnTime;
	    private Timer spawn;
	    private BulletManager bulletManagerRef;
	    private ScrapManager scrapManager;
	    private Vector<Enemy> enemies;
	    public AIDirector(int area, int level,Player playerRef,BulletManager bulletManagerReference)
	    {
	        this.level = level;
	        this.area = area;
	        this.playerRef = playerRef;
	        if(difficulty == Settings.DIFFICULTY_NORMAL)
	        {
	            stageMax = 10;
	            spawnTime = 14;
	        }
	        else if(difficulty == Settings.DIFFICULTY_HARD)
	        {
	            stageMax = 15;
	            spawnTime = 13;
	        }
	        else if(difficulty == Settings.DIFFICULTY_SUICIDE)
	        {
	            stageMax = 20;
	            spawnTime = 12;
	        }
	        enemies = new Vector<Enemy>();
	        spawn = new Timer(2);
	        scrapManager = new ScrapManager();
	        this.bulletManagerRef = bulletManagerReference;
	    }
	    public void reset()
	    {
	        stage =0;
	        spawn.reset(2);
	        enemies.clear();
	    }
	    public boolean endOfStage()
	    {
	        return (stage == stageMax);
	    }
	    private void GetNewWave()
	    {
	    	spawn.reset(9999.0f);
	    	enemies.add(new OmniDragon(800,0,playerRef,this));
	        /*Random rand = new Random();
	        stage++;
	        if(stage%5 == 0)
	        {
	            spawn.reset(spawnTime*2);
	            if(stage == stageMax && level == 3)
	            {
	                if(area == Settings.AREA_FOREST)
	                {
	                    enemies.add(new TorpedoTurtle(800,0,playerRef,this));
	                }
	                else if(area == Settings.AREA_DESERT)
	                {
	                    enemies.add(new SilverCannonBack(800,0,playerRef,this));
	                }
	                else if(area == Settings.AREA_CITY)
	                {
	                    enemies.add(new LaStatic(800,0,playerRef,this));
	                }
	                else if(area ==Settings.AREA_SEA)
	                {
	                    enemies.add(new WaterMechasin(800,0,playerRef,this));
	                }
	                else if(area == Settings.AREA_SPACE)
	                {
	                    enemies.add(new OmniDragon(800,100,playerRef,this));
	                }
	            }
	            else
	            {
	                int ran= rand.nextInt(2);
	                if(area == Settings.AREA_FOREST)
	                {
	                    if(ran  == 0)
	                    {
	                        enemies.add(new Deer(800,100,playerRef,this));
	                    }
	                    else if(ran == 1)
	                    {
	                        enemies.add(new Devil(800,200,playerRef,this));
	                    }
	                }
	                else if(area == Settings.AREA_DESERT)
	                {
	                    if(ran  == 0)
	                    {
	                        enemies.add(new Scorpion(800,0,playerRef,this));
	                    }
	                    else if(ran == 1)
	                    {
	                        enemies.add(new Coyote(800,0,playerRef,this));
	                    }
	                }
	                else if(area == Settings.AREA_CITY)
	                {
	                    if(ran  == 0)
	                    {
	                    	 ran = rand.nextInt(2)+level;
		                     for(int i =0;i<ran;i++)
		                     {
		                            int posX= 800 + 50*i + rand.nextInt(100);
		                            enemies.add(new Cop(posX,0,playerRef,this));
		                     }
	                    }
	                    else if(ran == 1)
	                    {
	                        int posX;
	                        ran = rand.nextInt(4)+level;
	                        for(int i =0;i<ran;i++)
	                        {
	                            posX= 800 + 50*i + rand.nextInt(100);
	                            enemies.add(new Rats(posX,0,playerRef,this));
	                        }
	                    }
	                }
	                else if(area == Settings.AREA_SEA)
	                {
	                    if(ran  == 0)
	                    {
	                        int posX;
	                        ran = rand.nextInt(2)+3;
	                        for(int i =0;i<ran;i++)
	                        {
	                            posX= rand.nextInt(480 -3)+3;
	                            enemies.add(new Fish(posX,0,playerRef,this));
	                        }
	                    }
	                    else if(ran == 1)
	                    {
	                        enemies.add(new Shark(300,0,playerRef,this));
	                    }
	                }
	                else if(area == Settings.AREA_SPACE)
	                {
	                    if(ran  == 0)
	                    {
	                        int posX;
	                        ran = rand.nextInt(5)+(int)difficulty;
	                        for(int i =0;i<ran;i++)
	                        {
	                            posX= 800 + 50*i;
	                            enemies.add(new Meteor(posX,playerRef.GetY(),playerRef,this));
	                        }
	                    }
	                    else if(ran == 1)
	                    {
	                        enemies.add(new Fighter(800,0,playerRef,this));
	                    }
	                }

	            }
	        }
	        else
	        {
	            spawn.reset(spawnTime);
	            int ran = rand.nextInt(3);
	            int pos,posx;
	            if(ran  == 0)
	            {
	                ran = rand.nextInt(3) + 3 + (int)difficulty+level;

	                for(int i = 0;i<ran;i++)
	                {
	                    posx = rand.nextInt(100) + 800;
	                    pos = rand.nextInt(1200)-400;
	                    enemies.add(new SuicideBird(posx,pos,playerRef,this));

	                }
	                ran = rand.nextInt(2);
	                if(ran == 0)
	                {
	                	ran = rand.nextInt(1)+1+ (int)difficulty+level;

		                for(int i =0;i<ran;i++)
		                {
		                    pos = rand.nextInt(400);
		                    enemies.add(new SoliderEnemy(800,pos,playerRef,this));
		                }
	                }
	                else
	                {
	                	ran = rand.nextInt(1)+level;
		                for(int i = 0;i<ran;i++)
		                {
		                    pos = rand.nextInt(400);
		                    enemies.add(new Shield(800,pos,playerRef,this));
		                }
	                }
	            }
	            else if(ran == 1)
	            {
	                ran = rand.nextInt(2)+1+ (int)difficulty+level;

	                for(int i =0;i<ran;i++)
	                {
	                    pos = rand.nextInt(400);
	                    enemies.add(new SoliderEnemy(800,pos,playerRef,this));
	                }
	            }
	            else if(ran == 2)
	            {
	                ran = rand.nextInt(2)+level;
	                for(int i = 0;i<ran;i++)
	                {
	                    pos = rand.nextInt(400);
	                    enemies.add(new Shield(800,pos,playerRef,this));
	                }

	            }
	        }*/

	    }
	    public void GetAllScraps()
	    {
	    	scrapManager.ClearScraps();
	    }
	    public void update(float deltaTime)
	    {
	    	
	        spawn.update(deltaTime);
	        if(enemies.size() == 0)
	        {
	        	if(spawn.getTimeRemaining() > 2.5f)
	        	{
	        		spawn.reset(2.5f);
	        	}
	        }
	        if(!spawn.running() && !this.endOfStage())
	        {
	        	GetNewWave();
	        }
	        scrapManager.update(deltaTime, playerRef);
	        try
	        {
	        	for(int i = 0;i<enemies.size();i++)
		    	{
		    		enemies.get(i).update(deltaTime);
		    		if(enemies.get(i).isDead())
		    		{
		    			enemies.remove(i);
		    			continue;
		    		}
		    		if(playerRef.canBeHit())
		    		{
		    			if(BoundingBox.Collided(enemies.get(i).GetBoundingBox(), playerRef.GetBoundingBox()))
			    		{
			    			enemies.get(i).takeDamage(1.0f,true);
			    			playerRef.takeDamage(enemies.get(i).GetContactDamage());
			    			if(enemies.get(i).isDead())
			    			{
			    				enemies.remove(i);
			    				continue;
			    			}
			    		}
		    		}
		    		for(int j = 0;j<bulletManagerRef.GetPlayerBullets().size();j++)
		    		{
		    			if(BoundingBox.Collided(enemies.get(i).GetBoundingBox(), bulletManagerRef.GetPlayerBullets().get(j).GetBoundingBox()))
		    			{
		    				enemies.get(i).takeDamage(bulletManagerRef.GetPlayerBullets().get(j).GetDamage(),false);
		    				bulletManagerRef.GetPlayerBullets().remove(j);
		    				if(enemies.get(i).isDead())
		    				{
		    					Settings.Multiplier++;
		    					if(Settings.Multiplier >= 5)
		    						Settings.Multiplier = 5;
		    					scrapManager.AddScraps(new Scraps((int)enemies.get(i).GetX(),(int) enemies.get(i).GetY()));
		    					enemies.remove(i);
		    				}
		    				break;
		    			}
		    		}
		    		
		    	}
	        }
	        catch(ArrayIndexOutOfBoundsException e)
	        {
	        	return;
	        }
	        
	    }
	    public void draw(SpriteBatch batch)
	    {
	    	for(int i = 0;i<enemies.size();i++)
	    	{
	    		enemies.get(i).draw(batch);
	    	}
	    	scrapManager.draw(batch);
	    }
	    public BulletManager GetBulletManager()
	    {
	    	return bulletManagerRef;
	    }
	    public boolean CheckPressAgainstScrapManager(TouchInfo e)
	    {
	    	return scrapManager.CheckPress(e);
	    }
	    public Vector<Enemy> getEnemies()
	    {
	    	return enemies;
	    }
}
