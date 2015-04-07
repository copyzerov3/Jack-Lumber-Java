package com.copyzero.JackLumber2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class NamedMusic
{
	private String m_Name;
	private Music m_Music;
	
	public NamedMusic(String Name, String path)
	{
		m_Name= Name;
		m_Music = Gdx.audio.newMusic(Gdx.files.internal(path));
	}
	public Music GetMusic()
	{
		return m_Music;
	}
	public String GetName()
	{
		return m_Name;
	}

}
