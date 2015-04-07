package com.copyzero.JackLumber2.Screens;

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
public class MainMenuScreen implements Screen,InputProcessor
{
	private NamedTexture background;
	private NamedTexture backgroundBack1,backgroundBack2,backgroundBack3,backgroundBack4,backgroundMid1,backgroundMid2,backgroundMid3,backgroundMid4;
	private Button play,resume,credits,music,sound,vibrate;
	private int x=0;
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();

	private NamedTexture off;
	public MainMenuScreen()
	{
		
		init();
	}
	public MainMenuScreen(int x)
	{
		this.x = x;
		init();
	}
	private void init()
	{
		play = new Button(assetManager.GetTexture("newGameButtonReg"),assetManager.GetTexture("newGameButtonPressed"),298,175);
		resume = new Button(assetManager.GetTexture("continueButtonReg"),assetManager.GetTexture("continueButtonPressed"),298,240);
		credits = new Button(assetManager.GetTexture("creditsButtonReg"),assetManager.GetTexture("creditsButtonPressed"),298,315);
		music = new Button(0,405,75,75);
		sound = new Button(75,405,75,75);
		vibrate = new Button(150,405,75,75);
		backgroundBack1 = assetManager.GetTexture("ForestBack1");
		backgroundBack2 = assetManager.GetTexture("ForestBack2");
		backgroundBack3 = assetManager.GetTexture("ForestBack3");
		backgroundBack4 = assetManager.GetTexture("ForestBack4");
		//backgroundFront = assetManager.GetTexture("Forrest_Fore_Complete");
		backgroundMid1 = assetManager.GetTexture("ForestMid1");
		backgroundMid2 = assetManager.GetTexture("ForestMid2");
		backgroundMid3 = assetManager.GetTexture("ForestMid3");
		backgroundMid4 = assetManager.GetTexture("ForestMid4");
		background = assetManager.GetTexture("StartScreen");
		off= assetManager.GetTexture("off");
		if(!Settings.music)
		{
			music.ChangeImage();
		}
		if(!Settings.sound)
		{
			sound.ChangeImage();
		}
		if(!Settings.vibrate)
		{
			vibrate.ChangeImage();
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
	public void update(float delta)
	{
		x--;
		for(int i = 0;i<5;i++)
		{
			TouchInfo e = touches.get(i);
			
			if(!e.downTouchResolved)
			{
				if(play.pressed(e))
					play.ChangeImageToPressed();
				else
					play.ChangeImageToRegular();
				
				if(credits.pressed(e))
					credits.ChangeImageToPressed();
				else
					credits.ChangeImageToRegular();
				
				if(resume.pressed(e))
					resume.ChangeImageToPressed();
				else
					resume.ChangeImageToRegular();

				e.downTouchResolved = true;
			}
			if(!e.upTouchResolved)
			{
				if(play.pressed(e))
				{
					play.ChangeImageToRegular();
					Vibrate();
					game.setScreen(new DifficultySelectScreen(x));
				}
					
				
				if(credits.pressed(e))
				{
					credits.ChangeImageToRegular();
					Vibrate();
					game.setScreen(new CreditsScreen());
				}
					
				
				if(resume.pressed(e))
				{
					resume.ChangeImageToRegular();
					Vibrate();
					//game.setScreen(new ResumeGameScreen());
				}
					
				if(music.pressed(e))
				{
					Settings.music = !Settings.music;
					Vibrate();
				}
				if(sound.pressed(e))
				{
					Settings.sound = !Settings.sound;
					Vibrate();
				}
				if(vibrate.pressed(e))
				{
					Settings.vibrate = !Settings.vibrate;
					Vibrate();
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
        batch.draw(background,0,0);
        play.draw(batch);
        resume.draw(batch);
        credits.draw(batch);
        if(!Settings.sound)
        {
        	batch.draw(off,105,40);
        }
        if(!Settings.music)
        {
        	batch.draw(off,30,40);
        }
        if(!Settings.vibrate)
        {
        	batch.draw(off,180,40);
        }
        batch.end();
		// TODO Auto-generated method stub
		
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
			Gdx.app.exit();
		}
		// TODO Auto-generated method stub
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
