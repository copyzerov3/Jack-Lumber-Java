package com.copyzero.JackLumber2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class NamedTexture extends Texture
{
	private String name;
	public NamedTexture(String Name,String Path)
	{
		super(Gdx.files.internal(Path));
		this.name = Name;
	}
	public String GetName()
	{
		return name;
	}
}
