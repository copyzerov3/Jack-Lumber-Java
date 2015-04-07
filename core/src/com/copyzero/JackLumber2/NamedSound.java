package com.copyzero.JackLumber2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class NamedSound 
{
	private String m_Name;
	private Sound m_Sound;
	
	public NamedSound(String Name, String path)
	{
		m_Sound =Gdx.audio.newSound(Gdx.files.internal(path));
		this.m_Name = Name;
	}
	
	public Sound GetSound()
	{
		return m_Sound;
	}
	
	public String GetName()
	{
		return m_Name;
	}

}
