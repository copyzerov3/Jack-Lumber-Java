package com.copyzero.JackLumber2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.copyzero.JackLumber2.Screens.LoadingScreen;


public class Main extends Game {

	
	@Override
	public void create () 
	{
		 Gdx.input.setCatchBackKey(true);
		Settings.Load();
		Settings.game = this;
		
		setScreen(new LoadingScreen());
		
	}
	@Override
	public void dispose()
	{
		super.dispose();
		getScreen().dispose();
	}
}
