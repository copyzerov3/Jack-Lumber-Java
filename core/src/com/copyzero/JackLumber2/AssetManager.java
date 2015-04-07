package com.copyzero.JackLumber2;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class AssetManager
{
	String[] lines;
	private int amountOfAssetsToLoad = 0,assetsLoaded = 0;
	
	private Vector<Vector<NamedTexture>> textures;
	private Vector<Vector<Animation>> animations;
	private Vector<Vector<NamedMusic>> music;
	private Vector<Vector<NamedSound>> sound;
	
	private BitmapFont font;
	
	public AssetManager(String pathToFile)
	{
		FileHandle fileHandle = Gdx.files.internal(pathToFile);
		lines = fileHandle.readString().split("\n");

		amountOfAssetsToLoad = lines.length;
		textures = new Vector<Vector<NamedTexture>>();
		animations = new Vector<Vector<Animation>>();
		music = new Vector<Vector<NamedMusic>>();
		sound = new Vector<Vector<NamedSound>>();
		
		font = new BitmapFont();
		font.scale(1);
		
	}
	public void Dispose()
	{
		font.dispose();
		for(int i = 0;i<textures.size();i++)
		{
			for(int j = 0;j<textures.get(i).size();j++)
			{
				textures.get(i).get(j).dispose();
			}
		}
		for(int i = 0;i<animations.size();i++)
		{
			for(int j = 0;j<animations.get(i).size();j++)
			{
				animations.get(i).get(j).Dispose();
			}
		}
		for(int i = 0;i<music.size();i++)
		{
			for(int j = 0;j<music.get(i).size();j++)
			{
				music.get(i).get(j).GetMusic().dispose();
			}
		}
		for(int i = 0;i<sound.size();i++)
		{
			for(int j = 0;j<sound.get(i).size();j++)
			{
				sound.get(i).get(j).GetSound().dispose();
			}
		}
	}
	public BitmapFont GetFont()
	{
		return font;
	}
	public void SetFontColour(Color color)
	{
		font.setColor(color);
	}
	public void SetFontScaling(int ScalingAmount)
	{
		font.setScale(ScalingAmount);
	}
	public void Load()
	{
		String line = lines[assetsLoaded];
    	if(line == null)
            return;
		if(line.endsWith("\r"))
    		line = line.substring(0, line.length()-1);
		String extension = line.substring(line.length() -3,line.length());
		if(extension.equals("txt"))
		{
			this.LoadAnimation(line);
		}
		else if(extension.equals("png"))
		{
			this.LoadImage(line);
		}
		else if(extension.equals("mp3"))
		{
			if(line.charAt(0) == 'M')
			{
				LoadMusic(line);
			}
			else if(line.charAt(0) == 'S')
			{
				LoadSound(line);
			}
		}
		
		assetsLoaded ++;
		
		if(assetsLoaded == amountOfAssetsToLoad)
			lines = null;
		
		
	}
	public int PercentageDone()
	{
		float result =(float)assetsLoaded/amountOfAssetsToLoad;
		int x = (int) (result*100);
		return (int)x;
	}
	public boolean loaded()
	{
		if(assetsLoaded == amountOfAssetsToLoad)
		{
			return true;
		}
		return false;
	}
	private void LoadImage(String path)
	{
		int i = -1;
		String name = "";
		do
		{
			i++;
			if(path.charAt(i) == '/'|| path.charAt(i) == '\\')
			{
				name = "";
				continue;
			}
			else if(path.charAt(i) == '.')
			{
				break;
			}
			
			name+=path.charAt(i);
			
			
			
		}while(true);
		NamedTexture texture = new NamedTexture(name,path);
		
		for(i = 0;i<textures.size();i++)
		{
			for(int j = 0;j<textures.get(i).size();)
			{
				if(textures.get(i).get(j).GetName().charAt(0) == texture.GetName().charAt(0))
				{
					textures.get(i).add(texture);
                    texture = null;
                    break;
				}
				else
					break;
			}
			if(texture == null)
                break;
		}
		if(textures.size() == 0 || texture != null)
        {
            Vector<NamedTexture> newVector = new Vector<NamedTexture>();
            newVector.add(texture);
            textures.add(newVector);
            texture = null;
        }
		
	}
	private void LoadAnimation(String path)
	{
		String[] strings;
		FileHandle fileHandle = Gdx.files.internal(path);
		strings = fileHandle.readString().split("\n");
		
		for(int i = 0;i<strings.length;i++)
		{
			if(strings[i] != null)
			{
				if(strings[i].endsWith("/r"))
				{
					strings[i] = strings[i].substring(0, strings[i].length()-1);
				}
			}
		}
		
		Animation anim = new Animation(strings[0], path, Integer.parseInt(strings[1]),Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Float.parseFloat(strings[5]));
		
		for(int i = 0;i<animations.size();i++)
		{
			for(int j = 0;j<animations.get(i).size();)
			{
				if(animations.get(i).get(j).GetName().charAt(0) == anim.GetName().charAt(0))
				{
					animations.get(i).add(anim);
                    anim = null;
                    break;
				}
				else
					break;
			}
			if(anim == null)
                break;
		}
		if(animations.size() == 0 || anim != null)
        {
            Vector<Animation> newVector = new Vector<Animation>();
            newVector.add(anim);
            animations.add(newVector);
            anim = null;
        }
		
		
	}
	private void LoadMusic(String path)
	{
		int i = -1;
		String name = "";
		do
		{
			i++;
			if(path.charAt(i) == '.')
			{
				break;
			}
			if(path.charAt(i) == '/'|| path.charAt(i) == '\\')
			{
				name = "";
				continue;
			}
			name+=path.charAt(i);
			
			
			
		}while(true);
		NamedMusic tempMusic = new NamedMusic(name,path);
		
		for(i = 0;i<music.size();i++)
		{
			for(int j = 0;j<music.get(i).size();)
			{
				if(music.get(i).get(j).GetName().charAt(0) == tempMusic.GetName().charAt(0))
				{
					music.get(i).add(tempMusic);
                    tempMusic = null;
                    break;
				}
				else
					break;
			}
			if(tempMusic == null)
                break;
		}
		if(music.size() == 0 || tempMusic != null)
        {
            Vector<NamedMusic> newVector = new Vector<NamedMusic>();
            newVector.add(tempMusic);
            music.add(newVector);
            tempMusic = null;
        }
	}
	private void LoadSound(String path)
	{
		int i = -1;
		String name = "";
		do
		{
			i++;
			if(path.charAt(i) == '.')
			{
				break;
			}
			if(path.charAt(i) == '/' || path.charAt(i) == '\\')
			{
				name = "";
				continue;
			}
			name+=path.charAt(i);
			
			
			
		}while(true);
		NamedSound tempSound = new NamedSound(name,path);
		
		for(i = 0;i<sound.size();i++)
		{
			for(int j = 0;j<sound.get(i).size();)
			{
				if(sound.get(i).get(j).GetName().charAt(0) == tempSound.GetName().charAt(0))
				{
					sound.get(i).add(tempSound);
                    tempSound = null;
                    break;
				}
				else
					break;
			}
			if(tempSound == null)
                break;
		}
		if(sound.size() == 0 || tempSound != null)
        {
            Vector<NamedSound> newVector = new Vector<NamedSound>();
            newVector.add(tempSound);
            sound.add(newVector);
            tempSound = null;
        }
	}
	
	public NamedTexture GetTexture(String Name)
	{
		for(int i = 0;i<textures.size();i++)
		{
			for(int j = 0;j<textures.get(i).size();j++)
			{
				if(textures.get(i).get(j).GetName().charAt(0) == Name.charAt(0))
				{
					if(textures.get(i).get(j).GetName().equals(Name))
					{
						return textures.get(i).get(j);
					}
				}
				else
					break;
			}
		}
		throw new RuntimeException("Can't Get Texture with name : " + Name);
	}
	public Sound GetSound(String Name)
	{
		for(int i = 0;i<sound.size();i++)
		{
			for(int j = 0;j<sound.get(i).size();j++)
			{
				if(sound.get(i).get(j).GetName().charAt(0) == Name.charAt(0))
				{
					if(sound.get(i).get(j).GetName().equals(Name))
					{
						return sound.get(i).get(j).GetSound();
					}
				}
				else
					break;
			}
		}
		throw new RuntimeException("Can't Get Sound with name : " + Name);
	}
	public Music GetMusic(String Name)
	{
		for(int i = 0;i<music.size();i++)
		{
			for(int j = 0;j<music.get(i).size();j++)
			{
				if(music.get(i).get(j).GetName().charAt(0) == Name.charAt(0))
				{
					if(music.get(i).get(j).GetName().equals(Name))
					{
						return music.get(i).get(j).GetMusic();
					}
				}
				else
					break;
			}
		}
		throw new RuntimeException("Can't Get Music with name : " + Name);
	}
	public Animation GetAnimation(String Name)
	{
		for(int i = 0;i<animations.size();i++)
		{
			for(int j = 0;j<animations.get(i).size();j++)
			{
				if(animations.get(i).get(j).GetName().charAt(0) == Name.charAt(0))
				{
					if(animations.get(i).get(j).GetName().equals(Name))
					{
						return animations.get(i).get(j);
					}
				}
				else
					break;
			}
		}
		throw new RuntimeException("Can't Get Animation with name : " + Name);
	}

}
