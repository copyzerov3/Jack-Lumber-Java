package com.copyzero.JackLumber2.Screens;

import static com.copyzero.JackLumber2.Settings.assetManager;
import static com.copyzero.JackLumber2.Settings.batch;
import static com.copyzero.JackLumber2.Settings.camera;
import static com.copyzero.JackLumber2.Settings.viewport;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.copyzero.JackLumber2.Button;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.Timer;
import com.copyzero.JackLumber2.TouchInfo;

import static com.copyzero.JackLumber2.Settings.*;

public class UpgradeScreen implements Screen, InputProcessor
{
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
	private NamedTexture background;
	
	 private int STATE_SELECTING = 0;
	 private int STATE_CONFIRMING =2;

	 private int CHOSEN_NONE = -1;
	 private int CHOSEN_HEALTH = 0;
	 private int CHOSEN_ARMOR = 1;
	 private int CHOSEN_TREE = 2;
	 private int CHOSEN_STATIC = 3;
	 private int CHOSEN_GRENADE =4;
	 private int CHOSEN_LAVA = 5;
	 private int CHOSEN_BUSTER =6;

	 private Button health;
	 private Button treeCannon;
	 private Button lavaBall;
	 private Button staticBall;
	 private Button waterGrenade;
	 private Button armor;
	 private Button buster;

	 private Button healthTokenHolder;
	 private Button armorTokenHolder;
	 private Button busterTokenHolder;
	 private Button treeTokenHolder;
	 private Button lavaTokenHolder;
	 private Button staticTokenHolder;
	 private Button grenadeTokenHolder;

	 private Button yes;
	 private Button no;

	 private Button back;

	 private int state = STATE_SELECTING;
	 private int chosen = -1;
	 private boolean nes = false;

	 private Timer timer;
	
	public UpgradeScreen()
	{
		timer = new Timer(0);
		health = new Button(assetManager.GetTexture("heart"),40,75);
        armor = new Button(assetManager.GetTexture("armor"),250,75);
        buster = new Button(assetManager.GetTexture("bullet1"),40,125);

        treeCannon = new Button(assetManager.GetTexture("tree"),30,175);
        lavaBall = new Button(assetManager.GetTexture("lavaBall"),250,175);
        staticBall = new Button(assetManager.GetTexture("staticBall"),30,250);
        waterGrenade = new Button(assetManager.GetTexture("grenade"),250,250);

        back = new Button(assetManager.GetTexture("backButtonReg"),assetManager.GetTexture("backButtonPressed"),675,430);

        healthTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),85,78);
        switch(healthlvl)
        {
            case 2:
                healthTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                healthTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
                healthTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }
        armorTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),315,90);
        switch(armorlvl)
        {
            case 2:
                armorTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                armorTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
                armorTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }
        busterTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),85,125);
        switch(busterlvl)
        {
            case 2:
                busterTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                busterTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
                busterTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }
        treeTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),85,190);
        switch(treeLaucherlvl)
        {
            case 2:
                treeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                treeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
                treeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }
        staticTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),85,265);
        switch(staticBalllvl)
        {
            case 2:
                staticTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                staticTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
               staticTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }
        lavaTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),315,190);
        switch(lavaBalllvl)
        {
            case 2:
                lavaTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                lavaTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
                lavaTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }
        grenadeTokenHolder = new Button(assetManager.GetTexture("upgradeTokenHolder"),315,265);
        switch(waterGrenadelvl)
        {
            case 2:
                grenadeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                break;
            case 3:
                grenadeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                break;
            case 4:
                grenadeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                break;
        }

        yes= new Button(assetManager.GetTexture("yesButtonReg"),assetManager.GetTexture("yesButtonReg"),200,180);
		no = new Button(assetManager.GetTexture("noButtonReg"),assetManager.GetTexture("noButtonPressed"),500,180);

		background = assetManager.GetTexture("standardBackground");
		Gdx.input.setInputProcessor(this);
        for(int i = 0; i < 5; i++)
        {
            touches.put(i, new TouchInfo());
           
        }
	}
	private void Vibrate()
	{
		if(vibrate)
			Gdx.input.vibrate(80);
	}
	public void update(float delta)
	{
		timer.update(delta);
        if(nes)
        {
            nes = false;
            timer.reset(2);
        }

		
		for(int i = 0;i<5;i++)
		{
			TouchInfo e = touches.get(i);
			
			if(!e.downTouchResolved)
			{
				if(state == STATE_CONFIRMING)
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
				else
				{
					if(back.pressed(e))
						back.ChangeImageToPressed();
					else
						back.ChangeImageToRegular();
				}
                
				e.downTouchResolved = true;
			}
			if(!e.upTouchResolved)
			{
				e.upTouchResolved = true;
				
                if(state == STATE_SELECTING)
                {
                	if(back.pressed(e))
	                {
						Vibrate();
	                    back.ChangeImageToRegular();
	                    game.setScreen(new MapSelectScreen());
	                }
                    if(health.pressed(e) || healthTokenHolder.pressed(e) && Settings.healthlvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_HEALTH)
                        {
                            chosen = CHOSEN_HEALTH;
                        }
                        else
                        {
                            state= STATE_CONFIRMING;
                        }

                    }
                    else if(armor.pressed(e) || armorTokenHolder.pressed(e) && Settings.armorlvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_ARMOR)
                        {
                            chosen = CHOSEN_ARMOR;
                        }
                        else
                        {
                            state= STATE_CONFIRMING;
                        }
                    }
                    else if(buster.pressed(e) || busterTokenHolder.pressed(e) && Settings.busterlvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_BUSTER)
                        {
                            chosen = CHOSEN_BUSTER;
                        }
                        else
                        {
                            state= STATE_CONFIRMING;
                        }
                    }
                    else if((treeCannon.pressed(e) || treeTokenHolder.pressed(e)) && Settings.treeUnlock && Settings.treeLaucherlvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_TREE)
                        {
                            chosen = CHOSEN_TREE;
                        }
                        else
                        {
                            state= STATE_CONFIRMING;
                        }
                    }
                    else if((lavaBall.pressed(e) || lavaTokenHolder.pressed(e)) && Settings.lavaUnlock && Settings.lavaBalllvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_LAVA)
                        {
                            chosen = CHOSEN_LAVA;
                        }
                        else
                        {
                            state= STATE_CONFIRMING;
                        }
                    }
                    else if((staticBall.pressed(e)|| staticTokenHolder.pressed(e)) && Settings.staticUnlock && Settings.staticBalllvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_STATIC)
                        {
                            chosen = CHOSEN_STATIC;
                        }
                        else
                        {
                            state= STATE_CONFIRMING;
                        }
                    }
                    else if((waterGrenade.pressed(e) || grenadeTokenHolder.pressed(e)) && Settings.grenadeUnlock && Settings.waterGrenadelvl !=4)
                    {
                    	Vibrate();
                        if(chosen != CHOSEN_GRENADE)
                        {
                            chosen = CHOSEN_GRENADE;
                        }
                        else
                        {

                            state= STATE_CONFIRMING;
                        }
                    }
                }
                else if(state ==STATE_CONFIRMING)
                {
                    if(yes.pressed(e))
                    {
                    	Vibrate();
                        if(chosen == CHOSEN_HEALTH)
                        {
                            if(Settings.scraps >= 2000)
                            {
                                Settings.scraps -=2000;
                                Settings.healthlvl++;
                                if(Settings.healthlvl == 2)
                                    healthTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                else if(Settings.healthlvl == 3)
                                    healthTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                                else if(Settings.healthlvl == 4)
                                    healthTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));

                                state = STATE_SELECTING;

                            }
                            else
                            {
                                nes =true;
                                state = STATE_SELECTING;
                            }
                        }
                        else if(chosen == CHOSEN_ARMOR)
                        {
                            if(Settings.scraps >= 2000)
                            {
                                Settings.scraps -=2000;
                                Settings.armorlvl++;
                                if(Settings.armorlvl == 2)
                                    armorTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                else if(Settings.armorlvl == 3)
                                    armorTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                                else if(Settings.armorlvl == 4)
                                    armorTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));

                                state = STATE_SELECTING;
                            }
                            else
                            {
                                nes =true;
                                state = STATE_SELECTING;
                            }
                        }
                        else if(chosen == CHOSEN_BUSTER)
                        {
                            if(Settings.busterlvl == 1)
                            {
                                if(Settings.scraps >=1000)
                                {
                                    Settings.scraps-=1000;
                                    Settings.busterlvl++;
                                    busterTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.busterlvl == 2)
                            {
                                if(Settings.scraps >=2000)
                                {
                                    Settings.scraps -=2000;
                                    Settings.busterlvl++;
                                    busterTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.busterlvl == 3)
                            {
                                if(Settings.scraps >=2500)
                                {
                                    Settings.scraps -=2500;
                                    Settings.busterlvl ++;
                                    busterTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            
                        }
                        else if(chosen == CHOSEN_TREE)
                        {
                            if(Settings.treeLaucherlvl == 1)
                            {
                                if(Settings.scraps >=1000)
                                {
                                    Settings.scraps-=1000;
                                    Settings.treeLaucherlvl++;
                                    treeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.treeLaucherlvl == 2)
                            {
                                if(Settings.scraps >=2000)
                                {
                                    Settings.scraps -=2000;
                                    Settings.treeLaucherlvl++;
                                    treeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.treeLaucherlvl == 3)
                            {
                                if(Settings.scraps >=2500)
                                {
                                    Settings.scraps -=2500;
                                    Settings.treeLaucherlvl++;
                                    treeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                        }
                        else if(chosen == CHOSEN_LAVA)
                        {
                            if(Settings.lavaBalllvl == 1)
                            {
                                if(Settings.scraps >=1000)
                                {
                                    Settings.scraps-=1000;
                                    Settings.lavaBalllvl++;
                                    lavaTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.lavaBalllvl == 2)
                            {
                                if(Settings.scraps >=2000)
                                {
                                    Settings.scraps -=2000;
                                    Settings.lavaBalllvl++;
                                    lavaTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.lavaBalllvl == 3)
                            {
                                if(Settings.scraps >=2500)
                                {
                                    Settings.scraps -=2500;
                                    Settings.lavaBalllvl++;
                                    lavaTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                        }
                        else if(chosen == CHOSEN_STATIC)
                        {
                            if(Settings.staticBalllvl == 1)
                            {
                                if(Settings.scraps >=1000)
                                {
                                    Settings.scraps-=1000;
                                    Settings.staticBalllvl++;
                                    staticTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.staticBalllvl == 2)
                            {
                                if(Settings.scraps >=2000)
                                {
                                    Settings.scraps -=2000;
                                    Settings.staticBalllvl++;
                                    staticTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.staticBalllvl == 3)
                            {
                                if(Settings.scraps >=2500)
                                {
                                    Settings.scraps -=2500;
                                    Settings.staticBalllvl++;
                                    staticTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                        }
                        else if(chosen == CHOSEN_GRENADE)
                        {
                            if(Settings.waterGrenadelvl == 1)
                            {
                                if(Settings.scraps >=1000)
                                {
                                    Settings.scraps-=1000;
                                    Settings.waterGrenadelvl++;
                                    grenadeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade1"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.waterGrenadelvl == 2)
                            {
                                if(Settings.scraps >=2000)
                                {
                                    Settings.scraps -=2000;
                                    Settings.waterGrenadelvl++;
                                    grenadeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade2"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                            else if(Settings.waterGrenadelvl == 3)
                            {
                                if(Settings.scraps >=2500)
                                {
                                    Settings.scraps -=2500;
                                    Settings.waterGrenadelvl++;
                                    grenadeTokenHolder.ChangeImage(assetManager.GetTexture("upgrade3"));
                                    state = STATE_SELECTING;
                                }
                                else
                                {
                                    nes =true;
                                    state = STATE_SELECTING;
                                }
                            }
                        }
                        yes.ChangeImageToRegular();
                    }
                    else if(no.pressed(e))
                    {
                    	Vibrate();
                        state = STATE_SELECTING;
                    }
                }
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
        batch.draw(background, 0, 0);
        assetManager.GetFont().draw(batch, "Scraps : " + Integer.toString(scraps), 600, 470);
    
        if(timer.running())
        {
            assetManager.GetFont().draw(batch,"Not enough scraps",180,470);
        }

        health.draw(batch);
        armor.draw(batch);
        buster.draw(batch);
        healthTokenHolder.draw(batch);
        armorTokenHolder.draw(batch);
        busterTokenHolder.draw(batch);

        back.draw(batch);
        if(treeUnlock)
        {
            treeCannon.draw(batch);
            treeTokenHolder.draw(batch);
        }
        if(lavaUnlock)
        {
            lavaBall.draw(batch);
            lavaTokenHolder.draw(batch);
        }
        if(staticUnlock)
        {
            staticBall.draw(batch);
            staticTokenHolder.draw(batch);
        }
        if(grenadeUnlock)
        {
            waterGrenade.draw(batch);
            grenadeTokenHolder.draw(batch);
        }
        if(state == STATE_SELECTING)
        {

            if(chosen == CHOSEN_NONE)
            {
                assetManager.GetFont().draw(batch,"Press on the item to view the next upgrade.",40,30);
            }
            else if(chosen ==CHOSEN_HEALTH)
            {
                assetManager.GetFont().draw(batch,"Increase your health. Cost : 2000",40,30);
            }
            else if(chosen == CHOSEN_ARMOR)
            {
                assetManager.GetFont().draw(batch,"Increase your armor. Cost : 2000",40,30);
            }
            else if(chosen == CHOSEN_BUSTER)
            {
                switch(busterlvl)
                {
                    case 1:
                        assetManager.GetFont().draw(batch,"Increase the speed of the bullets. Cost : 1000",40,30);
                        break;
                    case 2:
                        assetManager.GetFont().draw(batch,"Increase the damage of the bullets. Cost : 2000",40,30);
                        break;
                    case 3:
                        assetManager.GetFont().draw(batch,"Increase the size of the bullets Cost : 2500",40,30);
                }
            }
            else if(chosen == CHOSEN_TREE)
            {
                switch(treeLaucherlvl)
                {
                    case 1:
                        assetManager.GetFont().draw(batch,"Increase the speed of the trees. Cost : 1000",40,30);
                        break;
                    case 2:
                        assetManager.GetFont().draw(batch,"Piercing Pine Trees. Cost : 2000",40,30);
                        break;
                    case 3:
                        assetManager.GetFont().draw(batch,"The Trees move faster and do more damage. Cost : 2500",40,30);
                }
            }
            else if(chosen == CHOSEN_LAVA)
            {
                switch(lavaBalllvl)
                {
                    case 1:
                        assetManager.GetFont().draw(batch,"Increase the damage it does to the enemy. Cost : 1000",40,30);
                        break;
                    case 2:
                        assetManager.GetFont().draw(batch,"Increase the speed of the lava.  Cost : 2000",40,30);
                        break;
                    case 3:
                        assetManager.GetFont().draw(batch,"The lava ball melts through and hits enemies behind them. Cost : 2500",40,30);
                }
            }
            else if(chosen == CHOSEN_STATIC)
            {
                switch(staticBalllvl)
                {
                    case 1:
                        assetManager.GetFont().draw(batch,"Increase the damage of the static. Cost : 1000",40,30);
                        break;
                    case 2:
                        assetManager.GetFont().draw(batch,"Increase the speed. Cost : 2000",40,30);
                        break;
                    case 3:
                        assetManager.GetFont().draw(batch,"The Ball is now magnetized and goes through enemies. Cost : 2500",40,30);
                }
            }
            else if(chosen == CHOSEN_GRENADE)
            {
                switch(waterGrenadelvl)
                {
                    case 1:
                        assetManager.GetFont().draw(batch,"Increase the speed . Cost : 1000",40,30);
                        break;
                    case 2:
                        assetManager.GetFont().draw(batch,"The Grenades get a damage boost Cost : 2000",40,30);
                        break;
                    case 3:
                        assetManager.GetFont().draw(batch,"Increased explosion area. Cost : 2500",40,30);
                }
            }
        }


        else if(state == STATE_CONFIRMING)
        {
        	batch.draw(assetManager.GetTexture("standardBackground"), 0, 0);
            assetManager.GetFont().draw(batch,"Are you sure you want this upgrade?",100,470);
            yes.draw(batch);
            no.draw(batch);
        }

        batch.end();
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
		
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
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK)
		{
			if(state == this.STATE_SELECTING)
			{
				game.setScreen(new MapSelectScreen());
			}
			else
			{
				state = STATE_SELECTING;
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
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
			touches.get(0).touchX = (int)(screenX*result);
			result = (float)480/Gdx.graphics.getHeight();
			touches.get(0).touchY = (int) (screenY*result);
			touches.get(0).downTouchResolved = false;
			return true;
		}
		
		if(Gdx.app.getType() == Application.ApplicationType.Android)
		{
			if(pointer < 5)
			{
				float result =(float)800/Gdx.graphics.getWidth();
				touches.get(0).touchX = (int)(screenX*result);
				result = (float)480/Gdx.graphics.getHeight();
				touches.get(0).touchY = (int) (screenY*result);
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
			touches.get(0).touchX = (int)(screenX*result);
			result = (float)480/Gdx.graphics.getHeight();
			touches.get(0).touchY = (int) (screenY*result);
			touches.get(0).upTouchResolved = false;
			return true;
		}
		if(Gdx.app.getType() == Application.ApplicationType.Android)
		{
			if(pointer < 5)
			{
				float result =(float)800/Gdx.graphics.getWidth();
				touches.get(0).touchX = (int)(screenX*result);
				result = (float)480/Gdx.graphics.getHeight();
				touches.get(0).touchY = (int) (screenY*result);
	            touches.get(pointer).upTouchResolved= false;
			}
		}
		
        return true;

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
		{
			float result =(float)800/Gdx.graphics.getWidth();
			touches.get(0).touchX = (int)(screenX*result);
			
			result = (float)480/Gdx.graphics.getHeight();
			touches.get(0).touchY = (int) (screenY*result);
			touches.get(0).downTouchResolved = false;
			return true;
		}
		if(Gdx.app.getType() == Application.ApplicationType.Android)
		{
			if(pointer < 5)
			{
				float result =(float)800/Gdx.graphics.getWidth();
				touches.get(0).touchX = (int)(screenX*result);
				
				result = (float)480/Gdx.graphics.getHeight();
				touches.get(0).touchY = (int) (screenY*result);
				touches.get(0).downTouchResolved = false;
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
