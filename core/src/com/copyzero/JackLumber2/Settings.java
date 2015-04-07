package com.copyzero.JackLumber2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Settings 
{
	public static Game game;
	public static SpriteBatch batch;
	public static StretchViewport viewport;
	public static Camera camera;
	public static AssetManager assetManager;
	
	public static boolean music=true,sound=true,vibrate = true;
	
	//DIFFICULTY ENUM
    public static final float DIFFICULTY_NORMAL = 1;
    public static final float DIFFICULTY_HARD = 1.5f;
    public static final float DIFFICULTY_SUICIDE = 2;
    //GAMETYPE ENUM
    public static final int GAMETYPE_NORMAL = 0;
    public static final int GAMETYPE_OHGODWHY = 1;
  //static area identifiers
    public static final int AREA_FOREST = 0;
    public static final int AREA_DESERT = 1;
    public static final int AREA_CITY = 2;
    public static final int AREA_SEA =3;
    public static final int AREA_SPACE = 4;

    public static final int TYPE_FIRE = 1;
    public static final int TYPE_WATER = 2;
    public static final int TYPE_GRASS = 3;
    public static final int TYPE_ELEC = 4;
    public static final int TYPE_NORMAL=5;

    public static int scraps = 0;
    public static int gameType;
    public static float difficulty;
    public static int lives =3;

    public static int busterlvl=1;
    public static int armorlvl = 1;
    public static int healthlvl = 1;
    public static int treeLaucherlvl = 1;
    public static int lavaBalllvl = 1;
    public static int staticBalllvl = 1;
    public static int waterGrenadelvl =1;

    public static boolean treeUnlock = false;
    public static boolean lavaUnlock = false;
    public static boolean staticUnlock = false;
    public static boolean grenadeUnlock = false;

    public static boolean forestLevel2 = false;
    public static boolean forestLevel3 = false;

    public static boolean desertLevel2 = false;
    public static boolean desertLevel3 = false;

    public static boolean cityLevel2 = false;
    public static boolean cityLevel3 = false;

    public static boolean seaLevel2 = false;
    public static boolean seaLevel3 = false;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    
    public static final int accelControl = 0;
	public static final int stickControl = 1;
	public static final int padControl = 2;

	public static int controlType = 2;
	
	public static int ScrapsCollectedThisLevel = 0;
	public static int Multiplier = 1;
	public static float TimeThisLevel = 0.0f;
	public static float DamageTakenThisLevel=0.0f;
	
	public static void Load()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.position.set(400, 240, 0);
		camera.update();
		viewport = new StretchViewport(800, 480, camera);
		assetManager = new AssetManager("assets.txt");
	}
	public static void Save()
	{
		
	}
	
	public static void Dispose()
	{
		batch.dispose();
		assetManager.Dispose();
		
	}
	public static boolean isNewGame()
	{
		if(scraps != 0)
			return false;
		if(forestLevel2 ||desertLevel2||cityLevel2||seaLevel2)
			return false;
		return true;
	}
	public static void Reset()
	{
		Settings.healthlvl = 1;
        Settings.armorlvl = 1;
        Settings.busterlvl = 1;
        Settings.treeLaucherlvl =1;
        Settings.lavaBalllvl = 1;
        Settings.staticBalllvl = 1;
        Settings.waterGrenadelvl = 1;
        
        Settings.grenadeUnlock = false;
        Settings.treeUnlock = false;
        Settings.lavaUnlock =false;
        Settings.staticUnlock =false;
        Settings.forestLevel2 =false;
        Settings.forestLevel3 = false;
        Settings.desertLevel2 = false;
        Settings.desertLevel3 = false;
        Settings.cityLevel2 = false;
        Settings.cityLevel3 = false;
        Settings.seaLevel2 = false;
        Settings.seaLevel3 = false;

        if(Settings.difficulty != Settings.DIFFICULTY_SUICIDE)
        {
            Settings.lives =3;
        }
        else
        {
            Settings.lives = 0;
        }
        Settings.scraps = 0;

	}
	
	public static void ClearLevelVariables()
	{
		ScrapsCollectedThisLevel = 0;
		TimeThisLevel = 0.0f;
		DamageTakenThisLevel = 0.0f;
		Multiplier = 1;
	}
	
}
