package com.copyzero.JackLumber2.Screens.GameScreens;

import static com.copyzero.JackLumber2.Settings.assetManager;
import static com.copyzero.JackLumber2.Settings.batch;
import static com.copyzero.JackLumber2.Settings.camera;
import static com.copyzero.JackLumber2.Settings.game;
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
import com.copyzero.JackLumber2.TouchInfo;
import com.copyzero.JackLumber2.Screens.MainMenuScreen;
import com.copyzero.JackLumber2.Screens.MapSelectScreen;

public class ResultsScreen implements Screen,InputProcessor
{
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
	private NamedTexture background;
	
	private Button back,continueButton;
	private int area,level;
	private int bonus =0;
	public ResultsScreen(int area,int level)
	{
		back = new Button(assetManager.GetTexture("backButtonReg"),assetManager.GetTexture("backButtonPressed"),0,430);
		continueButton = new Button(assetManager.GetTexture("continueButtonReg"),assetManager.GetTexture("continueButtonPressed"),675,430);
		background = assetManager.GetTexture("standardBackground");
		this.area = area;
		this.level = level;
		if(Settings.TimeThisLevel <= 180)
        {
            bonus+=100;
        }
        else if(Settings.TimeThisLevel < 200)
        {
            bonus += 50;
        }
        else if(Settings.TimeThisLevel <220)
        {
            bonus += 25;
        }
        if(Settings.DamageTakenThisLevel == 0)
        {
            bonus +=200;
        }
        else if(Settings.DamageTakenThisLevel <=50)
        {
            bonus+=100;
        }
        else if(Settings.DamageTakenThisLevel <=100)
        {
            bonus+=50;
        }
        if(Settings.DamageTakenThisLevel >=1000)
        {
            bonus+=100;
        }
        else if(Settings.DamageTakenThisLevel >= 750)
        {
            bonus+=50;
        }
        else if(Settings.DamageTakenThisLevel>= 500)
        {
            bonus+=25;
        }
        if(level == 3)
        {
            bonus+=500;
        }

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
	private void updateLevels()
	{
		if(area == Settings.AREA_FOREST)
        {
            if(level ==1)
            {
                Settings.forestLevel2 = true;
            }
            else if(level == 2)
            {
                Settings.forestLevel3 = true;
            }
            else if(level == 3)
            {
                Settings.treeUnlock = true;
            }
        }
        else if(area == Settings.AREA_DESERT)
        {
            if(level ==1)
            {
                Settings.desertLevel2 = true;
            }
            else if(level == 2)
            {
                Settings.desertLevel3 = true;
            }
            else if(level == 3)
            {
                Settings.lavaUnlock = true;
            }
        }
        else if(area == Settings.AREA_CITY)
        {
            if(level ==1)
            {
                Settings.cityLevel2 = true;
            }
            else if(level == 2)
            {
                Settings.cityLevel3 = true;
            }
            else if(level == 3)
            {
                Settings.staticUnlock = true;
            }
        }
        else if(area == Settings.AREA_SEA)
        {
            if(level ==1)
            {
                Settings.seaLevel2 = true;
            }
            else if(level == 2)
            {
                Settings.seaLevel3 = true;
            }
            else if(level == 3)
            {
                Settings.grenadeUnlock = true;
            }
        }
	}
	public void update(float delta)
	{
		for(int i = 0;i<5;i++)
		{
			TouchInfo e = touches.get(i);
			
			if(!e.downTouchResolved)
			{
				if(back.pressed(e))
					back.ChangeImageToPressed();
				else
					back.ChangeImageToRegular();

				if(continueButton.pressed(e))
					continueButton.ChangeImageToPressed();
				else
					continueButton.ChangeImageToRegular();
				e.downTouchResolved = true;
			}
			if(!e.upTouchResolved)
			{
				if(back.pressed(e))
				{
					back.ChangeImageToRegular();
					game.setScreen(new GameScreen(area,level));
					Settings.ClearLevelVariables();
					Vibrate();
				}
				else if(continueButton.pressed(e))
				{
					Settings.scraps +=Settings.ScrapsCollectedThisLevel;
					Settings.scraps +=bonus;
					this.updateLevels();
					Settings.ClearLevelVariables();
	                game.setScreen(new MapSelectScreen());

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
        batch.draw(background, 0, 0);
        assetManager.GetFont().draw(batch, "Results", 350, 470);
        assetManager.GetFont().draw(batch, "Time Taken : " + Settings.TimeThisLevel, 0, 400);
        assetManager.GetFont().draw(batch, "Damage Taken : "+ Settings.DamageTakenThisLevel, 0, 350);
        assetManager.GetFont().draw(batch, "Scraps Collected : " + Settings.ScrapsCollectedThisLevel, 0, 300);
        assetManager.GetFont().draw(batch, "Bonus : " + bonus, 0, 250);
        back.draw(batch);
        continueButton.draw(batch);
        batch.end();
		
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
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.BACK)
		{
			game.setScreen(new MainMenuScreen());
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
