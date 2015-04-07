package com.copyzero.JackLumber2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.copyzero.JackLumber2.Settings;

import static com.copyzero.JackLumber2.Settings.*;
public class LoadingScreen implements Screen
{
	public LoadingScreen()
	{
		
	}

	public void update()
	{
		Settings.assetManager.Load();
		if(Settings.assetManager.loaded())
		{
			Settings.game.setScreen(new MainMenuScreen());
		}
	}
	
	@Override
	public void render(float delta) 
	{
		// TODO Auto-generated method stub
		update();
		
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		
		
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        assetManager.GetFont().draw(batch, "Loading ...    " + Settings.assetManager.PercentageDone(), 0, 30);
        batch.end();

	}

	@Override
	public void resize(int width, int height) {
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

}
