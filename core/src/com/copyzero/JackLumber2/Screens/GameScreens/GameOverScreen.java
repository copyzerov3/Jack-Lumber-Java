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

public class GameOverScreen implements Screen,InputProcessor
{
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
	private NamedTexture background;
	
	private Button back;
	
	public GameOverScreen()
	{
		back = new Button(assetManager.GetTexture("backButtonReg"),assetManager.GetTexture("backButtonPressed"),675,430);
		background = assetManager.GetTexture("standardBackground");
		
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
		for(int i = 0;i<5;i++)
		{
			TouchInfo e = touches.get(i);
			
			if(!e.downTouchResolved)
			{
				if(back.pressed(e))
					back.ChangeImageToPressed();
				else
					back.ChangeImageToRegular();
				e.downTouchResolved = true;
			}
			if(!e.upTouchResolved)
			{
				if(back.pressed(e))
				{
					back.ChangeImageToRegular();
					game.setScreen(new MainMenuScreen());
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
        batch.draw(background, 0, 0);
        assetManager.GetFont().draw(batch, "Credits", 350, 470);
        assetManager.GetFont().draw(batch, "Lead Designer and Lead Programmer : Douglas McGill", 60, 400);
        assetManager.GetFont().draw(batch, "Lead Artist : Erik Fortier", 250, 350);
        back.draw(batch);
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
