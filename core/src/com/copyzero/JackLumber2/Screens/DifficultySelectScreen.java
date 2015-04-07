package com.copyzero.JackLumber2.Screens;

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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.copyzero.JackLumber2.Button;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.TouchInfo;

import static com.copyzero.JackLumber2.Settings.*;

public class DifficultySelectScreen implements Screen,InputProcessor
{
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
	private NamedTexture background;
	private NamedTexture backgroundBack1,backgroundBack2,backgroundBack3,backgroundBack4,backgroundMid1,backgroundMid2,backgroundMid3,backgroundMid4;
	
	private Button normal,hard,suicide,ohGodWhy,back,continueButton,normalMode;
	
	private final int STATE_NORMAL = 0;
	private final int STATE_OHGODWHY = 1;
	private final int STATE_OVERWRITE = 2;
	private int STATE = STATE_NORMAL;
	private int PREV_STATE;
	
	private int x;
	
	public DifficultySelectScreen(int x)
	{
		this.x = x;
		normal = new Button(assetManager.GetTexture("normalButtonReg"),assetManager.GetTexture("normalButtonPressed"),298,175);
		hard = new Button(assetManager.GetTexture("hardButtonReg"),assetManager.GetTexture("hardButtonPressed"),298,240);
		suicide = new Button(assetManager.GetTexture("suicideButtonReg"),assetManager.GetTexture("suicideButtonPressed"),298,315);
		ohGodWhy = new Button(assetManager.GetTexture("ohGodWhyButtonReg"),assetManager.GetTexture("ohGodWhyButtonPressed"),325,430);
		back = new Button(assetManager.GetTexture("backButtonReg"),assetManager.GetTexture("backButtonPressed"),0,430);
		continueButton = new Button(assetManager.GetTexture("continueButtonReg"),assetManager.GetTexture("continueButtonPressed"),675,430);
		normalMode = new Button(assetManager.GetTexture("normalModeButtonReg"),assetManager.GetTexture("normalModeButtonPressed"),325,430);
		
		backgroundBack1 = assetManager.GetTexture("ForestBack1");
		backgroundBack2 = assetManager.GetTexture("ForestBack2");
		backgroundBack3 = assetManager.GetTexture("ForestBack3");
		backgroundBack4 = assetManager.GetTexture("ForestBack4");
		//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
		backgroundMid1 = assetManager.GetTexture("ForestMid1");
		backgroundMid2 = assetManager.GetTexture("ForestMid2");
		backgroundMid3 = assetManager.GetTexture("ForestMid3");
		backgroundMid4 = assetManager.GetTexture("ForestMid4");
		
		background = assetManager.GetTexture("Settings");
		
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
	public void update(float delta)
	{
		x--;
		for(int i = 0;i<5;i++)
		{
			TouchInfo e = touches.get(i);
			
			if(!e.downTouchResolved)
			{
				e.downTouchResolved = true;
				
				if(normal.pressed(e))
					normal.ChangeImageToPressed();
				else
					normal.ChangeImageToRegular();
				
				if(hard.pressed(e))
					hard.ChangeImageToPressed();
				else
					hard.ChangeImageToRegular();
				
				if(suicide.pressed(e))
					suicide.ChangeImageToPressed();
				else
					suicide.ChangeImageToRegular();
				
				if(ohGodWhy.pressed(e))
					ohGodWhy.ChangeImageToPressed();
				else
					ohGodWhy.ChangeImageToRegular();
				
				if(back.pressed(e))
					back.ChangeImageToPressed();
				else
					back.ChangeImageToRegular();
				
				if(continueButton.pressed(e))
					continueButton.ChangeImageToPressed();
				else
					continueButton.ChangeImageToRegular();
				
				if(normalMode.pressed(e))
					normalMode.ChangeImageToPressed();
				else
					normalMode.ChangeImageToRegular();
			}
			if(!e.upTouchResolved)
			{
				e.upTouchResolved = true;
				if(back.pressed(e))
				{
					back.ChangeImageToRegular();
					if(STATE!= STATE_OVERWRITE)
						game.setScreen(new MainMenuScreen(x));
					else
						STATE = PREV_STATE;
					Vibrate();
				}
				else if(STATE== STATE_OVERWRITE)
				{
					if(continueButton.pressed(e))
					{
						continueButton.ChangeImageToRegular();
						Vibrate();
						Settings.Reset();
						gameType = PREV_STATE;
						game.setScreen(new MapSelectScreen());
					}
				}
				else if(STATE != STATE_OVERWRITE)
				{
					if(normal.pressed(e))
					{
						normal.ChangeImageToRegular();
						Vibrate();
						if(Settings.isNewGame())
						{
							
							difficulty= DIFFICULTY_NORMAL;
							lives = 5;
							Settings.Reset();
							gameType = STATE;
							game.setScreen(new MapSelectScreen());
						}
						else
						{
							PREV_STATE= STATE;
							STATE=STATE_OVERWRITE;
							
						}
					}
					else if(hard.pressed(e))
					{
						hard.ChangeImageToRegular();
						Vibrate();
						if(Settings.isNewGame())
						{
							
							difficulty= DIFFICULTY_HARD;
							lives= 3;
							gameType = STATE;
							Settings.Reset();
							game.setScreen(new MapSelectScreen());
						}
						else
						{
							PREV_STATE= STATE;
							STATE= STATE_OVERWRITE;
						}
					}
					else if(suicide.pressed(e))
					{
						suicide.ChangeImageToRegular();
						Vibrate();
						if(Settings.isNewGame())
						{
							difficulty= DIFFICULTY_SUICIDE;
							lives= 1;
							gameType = STATE;
							Settings.Reset();
							game.setScreen(new MapSelectScreen());
						}
						else
						{
							PREV_STATE= STATE;
							STATE= STATE_OVERWRITE;
						}
					}
					if(STATE == STATE_NORMAL)
					{
						
						if(ohGodWhy.pressed(e))
						{
							ohGodWhy.ChangeImageToRegular();
							normalMode.ChangeImageToRegular();
							Vibrate();
							STATE= STATE_OHGODWHY;
						}
					}
					else if(STATE== STATE_OHGODWHY)
					{
						if(normalMode.pressed(e))
						{
							normalMode.ChangeImageToRegular();
							ohGodWhy.ChangeImageToRegular();
							Vibrate();
							STATE= STATE_NORMAL;
						}
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
        batch.draw(background, 0, 0);
        back.draw(batch);
        if(STATE != STATE_OVERWRITE)
        {
        	normal.draw(batch);
        	hard.draw(batch);
        	suicide.draw(batch);
        }
        
        if(STATE == STATE_NORMAL)
        {
        	assetManager.GetFont().draw(batch, "Pick your areas and levels and go at your own pace", 70, 400);
        	ohGodWhy.draw(batch);
        	assetManager.GetFont().draw(batch, "NORMAL MODE", 300, 470);
        }
        else if(STATE == this.STATE_OHGODWHY)
        {
        	assetManager.GetFont().draw(batch, "Randomly selects your stages and levels ", 150, 400);
        	normalMode.draw(batch);
        	assetManager.GetFont().draw(batch, "OH GOD WHY MODE", 260, 470);
        }
        else if(STATE == STATE_OVERWRITE)
        {
        	continueButton.draw(batch);
        	assetManager.GetFont().draw(batch, "CONFIRM", 300, 470);
        }
        	
        
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
			if(STATE!= STATE_OVERWRITE)
				game.setScreen(new MainMenuScreen());
			else
				STATE = PREV_STATE;
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
