package com.copyzero.JackLumber2.Screens.GameScreens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BulletManager;
import com.copyzero.JackLumber2.Button;
import com.copyzero.JackLumber2.Dpad;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.TouchInfo;
import com.copyzero.JackLumber2.Screens.MainMenuScreen;
import com.copyzero.JackLumber2.Screens.MapSelectScreen;

import static com.copyzero.JackLumber2.Settings.*;

public class GameScreen implements Screen, InputProcessor
{
	private NamedTexture backgroundBack1,backgroundBack2,backgroundBack3,backgroundBack4,backgroundMid1,backgroundMid2,backgroundMid3,backgroundMid4,backgroundFront1,backgroundFront2,backgroundFront3,backgroundFront4;
	
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
	// states
    public static final int STATE_PLAYING = 0;
    private final int STATE_PAUSED = 1;
    private final int STATE_SWITCHING_WEAPONS=2;
    private final int STATE_BACK_TO_MENU=4;
    public static final int STATE_BOSS =3;
    private final int STATE_DEAD = 5;
    private final int STATE_WON = 6;
    //game settings
    private int state = STATE_PLAYING;
    private int prevState;

	
	private int x;
	private int area,level;
	
	private Button yes;
    private Button no;
    private Button switchWeapons;
    private Button attackButton;
    private Button busterButton;
    private Button treeButton;
    private Button lavaButton;
    private Button staticButton;
    private Button grenadeButton;
    private Button saveButton;
    private Button backButton;
    private Button menuButton;
    private Button pauseButton;
    
    private Player player;
    private AIDirector director;
    private BulletManager bulletManager;
    private Dpad pad;
    
	public GameScreen(int Area, int Level)
	{
		this.area= Area;
		this.level = Level;
		
		yes= new Button(assetManager.GetTexture("yesButtonReg"),assetManager.GetTexture("yesButtonReg"),200,180);
		no = new Button(assetManager.GetTexture("noButtonReg"),assetManager.GetTexture("noButtonPressed"),500,180);
		
		backButton = new Button(assetManager.GetTexture("backButtonReg"),assetManager.GetTexture("backButtonPressed"),675,430);
        saveButton = new Button(assetManager.GetTexture("saveButtonReg"),assetManager.GetTexture("saveButtonPressed"),675,0);
        menuButton = new Button(assetManager.GetTexture("menuButtonReg"),assetManager.GetTexture("menuButtonPressed"),325,430);
        switchWeapons = new Button(assetManager.GetTexture("switchWeaponsButton"),750,0);
        if(busterlvl == 1)
        {
            busterButton = new Button(assetManager.GetTexture("bullet1"),305,35);
        }
        else if(busterlvl == 2)
        {
            busterButton = new Button(assetManager.GetTexture("bullet2"),305,35);
        }
        else if(busterlvl == 3)
        {
            busterButton = new Button(assetManager.GetTexture("bullet2"),305,35);
        }
        else if(busterlvl == 4)
        {
            busterButton = new Button(assetManager.GetTexture("bullet3"),305,35);
        }
        busterButton.setPosition(305,35);
        treeButton = new Button(assetManager.GetTexture("tree"),295,70);
        lavaButton = new Button(assetManager.GetTexture("lavaBall"),295,130);
        staticButton = new Button(assetManager.GetTexture("staticBall"),295,190);
        grenadeButton = new Button(assetManager.GetTexture("grenade"),295,250);

		pauseButton = new Button(assetManager.GetTexture("menuButtonReg"),assetManager.GetTexture("menuButtonPressed"),675,230);
		
		player = new Player(0,0);
		bulletManager= new BulletManager();
		director = new AIDirector(area,level,player,bulletManager);
		
		if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
		{
			if(Settings.controlType == padControl)
			{
				pad = new Dpad(0,0,assetManager.GetTexture("controlpad"));
			}
			if(Settings.controlType == stickControl)
			{
				pad = new Dpad(0,0,assetManager.GetTexture("stick"));
			}
			attackButton = new Button(assetManager.GetTexture("soundOn"),725,405);
		}
		
		GetBackgrounds(Area);
		Gdx.input.setInputProcessor(this);
		for(int i = 0; i < 5; i++)
		{
            touches.put(i, new TouchInfo());
        }
	}
	private void Vibrate()
	{
		if(Settings.vibrate)
			Gdx.input.vibrate(80);
	}
	private void GetBackgrounds(int Area)
	{
		switch(Area)
		{
		case AREA_FOREST:
			backgroundBack1 = assetManager.GetTexture("ForestBack1");
			backgroundBack2 = assetManager.GetTexture("ForestBack2");
			backgroundBack3 = assetManager.GetTexture("ForestBack3");
			backgroundBack4 = assetManager.GetTexture("ForestBack4");
			//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
			backgroundMid1 = assetManager.GetTexture("ForestMid1");
			backgroundMid2 = assetManager.GetTexture("ForestMid2");
			backgroundMid3 = assetManager.GetTexture("ForestMid3");
			backgroundMid4 = assetManager.GetTexture("ForestMid4");
			break;
		case AREA_DESERT:
			backgroundBack1 = assetManager.GetTexture("DesertBack1");
			backgroundBack2 = assetManager.GetTexture("DesertBack2");
			backgroundBack3 = assetManager.GetTexture("DesertBack3");
			backgroundBack4 = assetManager.GetTexture("DesertBack4");
			//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
			backgroundMid1 = assetManager.GetTexture("DesertMid1");
			backgroundMid2 = assetManager.GetTexture("DesertMid2");
			backgroundMid3 = assetManager.GetTexture("DesertMid3");
			backgroundMid4 = assetManager.GetTexture("DesertMid4");
			
			backgroundFront1 = assetManager.GetTexture("DesertFore1");
			backgroundFront2 = assetManager.GetTexture("DesertFore2");
			backgroundFront3 = assetManager.GetTexture("DesertFore3");
			backgroundFront4 = assetManager.GetTexture("DesertFore4");
			break;
		case AREA_CITY:
			backgroundBack1 = assetManager.GetTexture("CityBack1");
			backgroundBack2 = assetManager.GetTexture("CityBack2");
			backgroundBack3 = assetManager.GetTexture("CityBack3");
			backgroundBack4 = assetManager.GetTexture("CityBack4");
			//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
			backgroundMid1 = assetManager.GetTexture("CityMid1");
			backgroundMid2 = assetManager.GetTexture("CityMid2");
			backgroundMid3 = assetManager.GetTexture("CityMid3");
			backgroundMid4 = assetManager.GetTexture("CityMid4");
			
			backgroundFront1 = assetManager.GetTexture("CityFore1");
			backgroundFront2 = assetManager.GetTexture("CityFore2");
			backgroundFront3 = assetManager.GetTexture("CityFore3");
			backgroundFront4 = assetManager.GetTexture("CityFore4");
			break;
		case AREA_SEA:
			backgroundBack1 = assetManager.GetTexture("SpaceBack1");
			backgroundBack2 = assetManager.GetTexture("SpaceBack2");
			backgroundBack3 = assetManager.GetTexture("SpaceBack3");
			backgroundBack4 = assetManager.GetTexture("SpaceBack4");
			//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
			backgroundMid1 = assetManager.GetTexture("SpaceMid1");
			backgroundMid2 = assetManager.GetTexture("SpaceMid2");
			backgroundMid3 = assetManager.GetTexture("SpaceMid3");
			backgroundMid4 = assetManager.GetTexture("SpaceMid4");
			
			backgroundFront1 = assetManager.GetTexture("SpaceFore1");
			backgroundFront2 = assetManager.GetTexture("SpaceFore2");
			backgroundFront3 = assetManager.GetTexture("SpaceFore3");
			backgroundFront4 = assetManager.GetTexture("SpaceFore4");
			break;
		case AREA_SPACE:
			backgroundBack1 = assetManager.GetTexture("SpaceBack1");
			backgroundBack2 = assetManager.GetTexture("SpaceBack2");
			backgroundBack3 = assetManager.GetTexture("SpaceBack3");
			backgroundBack4 = assetManager.GetTexture("SpaceBack4");
			//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
			backgroundMid1 = assetManager.GetTexture("SpaceMid1");
			backgroundMid2 = assetManager.GetTexture("SpaceMid2");
			backgroundMid3 = assetManager.GetTexture("SpaceMid3");
			backgroundMid4 = assetManager.GetTexture("SpaceMid4");
			
			backgroundFront1 = assetManager.GetTexture("SpaceFore1");
			backgroundFront2 = assetManager.GetTexture("SpaceFore2");
			backgroundFront3 = assetManager.GetTexture("SpaceFore3");
			backgroundFront4 = assetManager.GetTexture("SpaceFore4");
			break;
			
		}
	}
	private void update(float delta)
	{
		if(state== STATE_PLAYING)
		{
			Settings.TimeThisLevel+=delta;
			x--;
			player.update(delta);
			if(player.attacking())
			{
				bulletManager.AddPlayerBullet(player.getBullet());
			}
			director.update(delta);
			bulletManager.Update(player,delta);
			if(player.GetHealth() <=0)
			{
				//state = this.STATE_DEAD;
			}
			if(director.endOfStage() && director.getEnemies().size() == 0)
			{
				director.GetAllScraps();
				player.ClearKeys();
				player.SetX(player.GetX()+(player.getSpeed()*2));
				if(player.GetX() >=Settings.WIDTH)
				{
					game.setScreen(new ResultsScreen(area,level));
				}
				
			}
		} 
		for(int i = 0;i<5;i++)
		{
			TouchInfo e = touches.get(i);
			
			if(!e.downTouchResolved)
			{
				e.downTouchResolved = true;
				 if(state == STATE_PLAYING || state == STATE_BOSS || state == STATE_WON)
	                {
	                    if(pauseButton.pressed(e))
	                    {
	                    	pauseButton.ChangeImageToPressed();
	                    	continue;
	                    }
	                    else
	                    {
	                    	pauseButton.ChangeImageToRegular();
	                    }
	                    	
	                    if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
	                    {
	                    	if(attackButton.pressed(e))
		                    {
		                    	player.UpdateKeys(Keys.SPACE, true);
		                    	continue;
		                    }
		                    else
		                    {
		                    	if(!pad.CheckBounds(e))
		                    	{
		                    		player.UpdateKeys(Keys.SPACE, false);
		                    	}
		                    }
	                    	if(pad != null)
		    				{
	                    		pad.CheckPressedDown(e, player);
		    				}
	                    }
	                }
	                else if(state == STATE_PAUSED)
	                {
	                    if(saveButton.pressed(e))
		                    saveButton.ChangeImageToPressed();
	                    else
	                    	saveButton.ChangeImageToRegular();
	                    if(menuButton.pressed(e))
	                    	menuButton.ChangeImageToPressed();
	                    else
	                    	menuButton.ChangeImageToRegular();
	                    if(backButton.pressed(e))
	                    	backButton.ChangeImageToPressed();
	                    else
	                    	backButton.ChangeImageToRegular();

	                }
	                else if(state == STATE_SWITCHING_WEAPONS)
	                {
	                    if(backButton.pressed(e))
	                    	backButton.ChangeImageToPressed();
	                    else
	                    	backButton.ChangeImageToRegular();

	                }
	                else if(state == STATE_BACK_TO_MENU || state == STATE_DEAD)
	                {
	                    if(yes.pressed(e))
	                    	yes.ChangeImageToPressed();
	                    else
	                    	yes.ChangeImageToRegular();
	                    if(no.pressed(e))
	                    	no.ChangeImageToPressed();
	                    else
	                    	no.ChangeImageToRegular();
	                }
			}
			if(!e.upTouchResolved)
			{
                if(state == STATE_PLAYING || state == STATE_BOSS)
                {
                	if(director.CheckPressAgainstScrapManager(e))
                	{
                		if(pad != null)
                		{
                			if(!pad.CheckBounds(e))
	                		{
	                			e.upTouchResolved = true;
	                			continue;
	                		}
                		}
                		
                	}
                    if(switchWeapons.pressed(e))
                    {
                        prevState = state;
                        state = STATE_SWITCHING_WEAPONS;
                    }
                    else if(pauseButton.pressed(e))
	                {
	                   	Vibrate();
	                   	pauseButton.ChangeImageToRegular();
	                   	state = STATE_PAUSED;
	                }
                    else if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
                    {
                    	if(attackButton.pressed(e))
	                    {
	                    	player.UpdateKeys(Keys.SPACE, false);
	                    	e.upTouchResolved = true;
	                    	continue;
	                    }
                    	if(pad != null)
        				{
                    		pad.CheckPressedUp(e, player);
        					
        				}
                    }
                    
                }
                else if(state == STATE_PAUSED)
                {
                    
                    if(saveButton.pressed(e))
                    {
                        Settings.Save();
                        game.setScreen(new MainMenuScreen());
                        Vibrate();
                    }
                    else if(menuButton.pressed(e))
                    {
                    	Vibrate();
                        menuButton.ChangeImageToRegular();
                        state =STATE_BACK_TO_MENU;
                    }
                    else if(backButton.pressed(e))
                    {
                    	Vibrate();
                        state = prevState;
                        backButton.ChangeImageToRegular();
                    }
                }
                else if(state == STATE_SWITCHING_WEAPONS)
                {
                    if(this.busterButton.pressed(e))
                    {
                    	Vibrate();
                        state = prevState;
                    }
                    else if(this.treeButton.pressed(e) && Settings.treeUnlock)
                    {
                    	Vibrate();
                        state = prevState;
                    }
                    else if(lavaButton.pressed(e) && Settings.lavaUnlock)
                    {
                    	Vibrate();
                        state = prevState;
                    }
                    else if(staticButton.pressed(e)&& Settings.staticUnlock)
                    {
                    	Vibrate();
                        state = prevState;
                    }
                    else if(grenadeButton.pressed(e) && Settings.grenadeUnlock)
                    {
                    	Vibrate();
                        state =prevState;
                    }
                    else if(backButton.pressed(e))
                    {
                    	Vibrate();
                        state = prevState;
                        backButton.ChangeImageToRegular();
                    }
                }
                else if(state == STATE_BACK_TO_MENU)
                {
                    if(yes.pressed(e))
                    {
                    	Vibrate();
                        yes.ChangeImageToRegular();
                        game.setScreen(new MainMenuScreen());
                        Settings.ClearLevelVariables();
                    }
                    else if(no.pressed(e))
                    {
                    	Vibrate();
                        no.ChangeImageToRegular();
                        state = STATE_PAUSED;
                    }
                }
                else if(state == STATE_DEAD)
                {
                    if(yes.pressed(e))
                    {
                        yes.ChangeImageToRegular();
                        state = STATE_PLAYING;
                        player.reset();
                        director.reset();
                        bulletManager.reset();
                        Settings.ClearLevelVariables();

                    }
                    if(no.pressed(e))
                    {
                        no.ChangeImageToRegular();
                        game.setScreen(new MapSelectScreen());
                    }
                }
                e.upTouchResolved = true;
			}
		}
	}
	@Override
	public void render(float delta) 
	{
		update(delta);
		
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		
		
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        if(backgroundBack1 != null)
        {
        	 batch.draw(backgroundBack1, x%3200, 0);
        	 if(backgroundBack2 != null)
        	 {
        		 batch.draw(backgroundBack2, 800 + x%3200, 0);
        		 batch.draw(backgroundBack3, 1600 + x%3200, 0);
        		 batch.draw(backgroundBack4, 2400 + x%3200, 0);
        		
        		 batch.draw(backgroundBack2, 4000 + x%3200, 0);
        		 batch.draw(backgroundBack3, 4800 + x%3200, 0);
        		 batch.draw(backgroundBack4, 5600 + x%3200, 0);
        	 }
        	  batch.draw(backgroundBack1, 3200+ ( x % 3200 ), 0);
        }
        if(backgroundMid1 != null)
        {
    	    batch.draw(backgroundMid1,( (x*5) % 3200 ),0);
    	    batch.draw(backgroundMid2,800 + ( (x*5) % 3200 ),0);
    	    batch.draw(backgroundMid3,1600 + ( (x*5) % 3200 ),0);
    	    batch.draw(backgroundMid4,2400 + ( (x*5) % 3200 ),0);
    	    batch.draw(backgroundMid1,3200 + ( (x*5) % 3200),0);
    	    batch.draw(backgroundMid2,4000 + ( (x*5) % 3200 ),0);
    	    batch.draw(backgroundMid3,4800 + ( (x*5) % 3200 ),0);
    	    batch.draw(backgroundMid4,5600 + ( (x*5) % 3200 ),0);
        }
        if(backgroundFront1 != null)
        {
        	batch.draw(backgroundFront1,( (x*10) % 3200 ),0);
    	    batch.draw(backgroundFront2,800 + ( (x*10) % 3200 ),0);
    	    batch.draw(backgroundFront3,1600 + ( (x*10) % 3200 ),0);
    	    batch.draw(backgroundFront4,2400 + ( (x*10) % 3200 ),0);
    	    batch.draw(backgroundFront1,3200 + ( (x*10) % 3200),0);
    	    batch.draw(backgroundFront2,4000 + ( (x*10) % 3200 ),0);
    	    batch.draw(backgroundFront3,4800 + ( (x*10) % 3200 ),0);
    	    batch.draw(backgroundFront4,5600 + ( (x*10) % 3200 ),0);
        }
        
        if(state== STATE_PLAYING)
        {
        	
        	if(Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
            {
        		attackButton.draw(batch);
            }
        	
        	switchWeapons.draw(batch);
        	
        	pauseButton.draw(batch);
        	if(pad !=null)
        		pad.Draw(batch);
        	player.draw(batch);
        	director.draw(batch) ;
        	bulletManager.Draw(batch);
        	assetManager.GetFont().draw(batch,"Scraps  : " + Settings.ScrapsCollectedThisLevel , 0, 30);
            assetManager.GetFont().draw(batch, "Health : " + player.GetHealth(), 0, 480);
            assetManager.GetFont().draw(batch, "Multiplier : " + Settings.Multiplier, 0, 60);
        }
        	

        if(state == STATE_SWITCHING_WEAPONS)
        {
        	player.draw(batch);
        	director.draw(batch) ;
        	bulletManager.Draw(batch);
            backButton.draw(batch);
            busterButton.draw(batch);
            if(Settings.treeUnlock)
            {
                treeButton.draw(batch);
                
            }
            if(Settings.lavaUnlock)
            {
                lavaButton.draw(batch);
                
            }
            if(Settings.staticUnlock)
            {
                staticButton.draw(batch);
                
            }
            if(Settings.grenadeUnlock)
            {
                grenadeButton.draw(batch);
               
            }
        }
        else if(state == STATE_PAUSED)
        {
        	player.draw(batch);
        	director.draw(batch) ;
            saveButton.draw(batch);
            menuButton.draw(batch);
            backButton.draw(batch);
            assetManager.GetFont().draw(batch,"Lives : "+Settings.lives,0,460);
        }
        else if(state == STATE_BACK_TO_MENU)
        {
            batch.draw(assetManager.GetTexture("standardBackground"),0, 0);
            assetManager.GetFont().draw(batch,"Quit to the Main Menu?",250,410);
            yes.draw(batch);
            no.draw(batch);
        }
        else if(state == STATE_DEAD)
        {
        	batch.draw(assetManager.GetTexture("standardBackground"),0, 0);
            assetManager.GetFont().draw(batch,"Would you like to try again?",240,410);
            yes.draw(batch);
            no.draw(batch);
        }
        if(state == STATE_WON)
        {
            assetManager.GetFont().draw(batch,"STAGE COMPLETE PRESS ANYWHERE TO GO TO RESULTS",100,480);
        }

        batch.end();
		
	}
	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() 
	{
		Settings.Dispose();
	}
	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			Vibrate();
			if(state == STATE_PLAYING || state == STATE_BOSS)
            {
                prevState= state;
                state = STATE_PAUSED;
            }
            else if(state == STATE_PAUSED)
            {
                state = prevState;
            }
            else if(state == STATE_SWITCHING_WEAPONS)
            {
                state = prevState;
            }
            else if(state == this.STATE_BACK_TO_MENU)
            {
            	state = STATE_PAUSED;
            }

		}
		player.UpdateKeys(keycode, true);
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) 
	{
		player.UpdateKeys(keycode, false);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			float result =(float)800/Gdx.graphics.getWidth();
			touches.get(pointer).touchX = (int)(screenX*result);
			result = (float)480/Gdx.graphics.getHeight();
			touches.get(pointer).touchY = (int) (screenY*result);
			touches.get(pointer).downTouchResolved = false;
		}
		
		if(Gdx.app.getType() == Application.ApplicationType.Android)
		{
			if(pointer < 5)
			{
				float result =(float)800/Gdx.graphics.getWidth();
				touches.get(pointer).touchX = (int)(screenX*result);
				result = (float)480/Gdx.graphics.getHeight();
				touches.get(pointer).touchY = (int) (screenY*result);
	            touches.get(pointer).downTouchResolved = false;
			}
		}
		
        return true;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			float result =(float)800/Gdx.graphics.getWidth();
			touches.get(pointer).touchX = (int)(screenX*result);
			result = (float)480/Gdx.graphics.getHeight();
			touches.get(pointer).touchY = (int) (screenY*result);
			touches.get(pointer).upTouchResolved = false;
			return true;
		}
		if(Gdx.app.getType() == Application.ApplicationType.Android)
		{
			if(pointer < 5)
			{
				float result =(float)800/Gdx.graphics.getWidth();
				touches.get(pointer).touchX = (int)(screenX*result);
				result = (float)480/Gdx.graphics.getHeight();
				touches.get(pointer).touchY = (int) (screenY*result);
	            touches.get(pointer).upTouchResolved= false;
			}
		}
		
        return true;

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{
		if(pointer < 5)
		{
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			{
				float result =(float)800/Gdx.graphics.getWidth();
				touches.get(pointer).touchX = (int)(screenX*result);
				
				result = (float)480/Gdx.graphics.getHeight();
				touches.get(pointer).touchY = (int) (screenY*result);
				touches.get(pointer).downTouchResolved = false;
				return true;
			}
			if(Gdx.app.getType() == Application.ApplicationType.Android)
			{
					float result =(float)800/Gdx.graphics.getWidth();
					touches.get(pointer).touchX = (int)(screenX*result);
					
					result = (float)480/Gdx.graphics.getHeight();
					touches.get(pointer).touchY = (int) (screenY*result);
					touches.get(pointer).downTouchResolved = false;
					return true;
			}
		}
		
		
        return false;

	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
