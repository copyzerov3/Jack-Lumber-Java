package com.copyzero.JackLumber2.Screens;

import static com.copyzero.JackLumber2.Settings.assetManager;
import static com.copyzero.JackLumber2.Settings.batch;
import static com.copyzero.JackLumber2.Settings.camera;
import static com.copyzero.JackLumber2.Settings.game;
import static com.copyzero.JackLumber2.Settings.viewport;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.copyzero.JackLumber2.Button;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Settings;
import com.copyzero.JackLumber2.TouchInfo;
import com.copyzero.JackLumber2.Screens.GameScreens.GameScreen;

import static com.copyzero.JackLumber2.Settings.*;
public class MapSelectScreen implements Screen,InputProcessor
{
	private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
	
	private Button level1,level2,level3,forest,desert,city,sea,upgrade,mainMenu,yes,no,back,continueButton;
	
	private NamedTexture background;
	
	private final int STATE_SELECTING_AREA = 0;
	private final int STATE_SELECTING_LEVEL = 1;
	private final int STATE_GO_BACK = 2;
	
	private int selectedArea = -1;
	
	private int STATE= STATE_SELECTING_AREA;
	public MapSelectScreen()
	{
		
		level1 = new Button(assetManager.GetTexture("stage1ButtonReg"),assetManager.GetTexture("stage1ButtonPressed"),0,0);
		level2 = new Button(assetManager.GetTexture("stage2ButtonReg"),assetManager.GetTexture("stage2ButtonPressed"),0,0);
		level3 = new Button(assetManager.GetTexture("stage3ButtonReg"),assetManager.GetTexture("stage3ButtonPressed"),0,0);
		forest = new Button(assetManager.GetTexture("ForestAreaButton"),0,0);
		desert = new Button(assetManager.GetTexture("DesertAreaButton"),400,0);
		city = new Button(assetManager.GetTexture("cityAreaButton"),0,240);
		sea = new Button(assetManager.GetTexture("oceanAreaButton"),400,240);
		upgrade = new Button(assetManager.GetTexture("upgradeButtonReg"),assetManager.GetTexture("upgradeButtonPressed"),0,0);
		mainMenu = new Button(assetManager.GetTexture("menuButtonReg"),assetManager.GetTexture("menuButtonPressed"),675,430);
		yes= new Button(assetManager.GetTexture("yesButtonReg"),assetManager.GetTexture("yesButtonReg"),200,180);
		no = new Button(assetManager.GetTexture("noButtonReg"),assetManager.GetTexture("noButtonPressed"),500,180);
		back = new Button(assetManager.GetTexture("backButtonReg"),assetManager.GetTexture("backButtonPressed"),675,430);
		continueButton = new Button(assetManager.GetTexture("continueButtonReg"),assetManager.GetTexture("continueButtonPressed"),675,430);
		if(Settings.treeUnlock && Settings.lavaUnlock  && Settings.staticUnlock && Settings.grenadeUnlock)
        {
        	selectedArea = Settings.AREA_SPACE;
        }
		if(selectedArea != AREA_SPACE && Settings.gameType == Settings.GAMETYPE_OHGODWHY)
		{
			pickArea();
		}
		Gdx.input.setInputProcessor(this);
        for(int i = 0; i < 5; i++)
        {
            touches.put(i, new TouchInfo());    
        }
	}
	private void pickArea()
    {
        Random rand = new Random();
        int temp;

        do
        {
            temp = rand.nextInt(4);
            if(temp == Settings.AREA_FOREST && !Settings.treeUnlock)
            {
                selectedArea = Settings.AREA_FOREST;
            }
            else if(temp == Settings.AREA_DESERT && !Settings.lavaUnlock)
            {
            	selectedArea = Settings.AREA_DESERT;
            }
            else if(temp == Settings.AREA_CITY && !Settings.staticUnlock)
            {
            	selectedArea = Settings.AREA_CITY;
            }
            else if(temp == Settings.AREA_SEA && !Settings.grenadeUnlock)
            {
            	selectedArea = Settings.AREA_SEA;
            }
            if(Settings.treeUnlock && Settings.lavaUnlock  && Settings.staticUnlock && Settings.grenadeUnlock)
            {
            	selectedArea = Settings.AREA_SEA;
            }
        }while(selectedArea == -1);
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
				if(STATE == STATE_SELECTING_AREA)
				{
					if(upgrade.pressed(e))
						upgrade.ChangeImageToPressed();
					else
						upgrade.ChangeImageToRegular();
					
					if(back.pressed(e))
						back.ChangeImageToPressed();
					else
						back.ChangeImageToRegular();
					
					if(mainMenu.pressed(e))
						mainMenu.ChangeImageToPressed();
					else
						mainMenu.ChangeImageToRegular();
					if(selectedArea == AREA_SPACE)
					{
						if(continueButton.pressed(e))
						{
							continueButton.ChangeImageToPressed();
						}
						else
						{
							continueButton.ChangeImageToRegular();
						}
					}
				}
				else if(STATE == STATE_SELECTING_LEVEL)
				{
					if(upgrade.pressed(e))
						upgrade.ChangeImageToPressed();
					else
						upgrade.ChangeImageToRegular();
					
					if(back.pressed(e))
						back.ChangeImageToPressed();
					else
						back.ChangeImageToRegular();
					if(level1.pressed(e))
						level1.ChangeImageToPressed();
					else
						level1.ChangeImageToRegular();
					if(level2.pressed(e))
					{
						if(selectedArea == AREA_FOREST && forestLevel2)
						{
							level2.ChangeImageToPressed();
						}
						else if(selectedArea == AREA_DESERT && desertLevel2)
						{
							level2.ChangeImageToPressed();
						}
						else if(selectedArea == AREA_CITY && cityLevel2)
						{
							level2.ChangeImageToPressed();
						}
						else if(selectedArea == AREA_SEA && seaLevel2)
						{
							level2.ChangeImageToPressed();
						}
					}
					else
					{
						if(selectedArea == AREA_FOREST && forestLevel2)
						{
							level2.ChangeImageToRegular();
						}
						else if(selectedArea == AREA_DESERT && desertLevel2)
						{
							level2.ChangeImageToRegular();
						}
						else if(selectedArea == AREA_CITY && cityLevel2)
						{
							level2.ChangeImageToRegular();
						}
						else if(selectedArea == AREA_SEA && seaLevel2)
						{
							level2.ChangeImageToRegular();
						}
					}
					if(level3.pressed(e))
					{
						if(selectedArea == AREA_FOREST && forestLevel3)
						{
							level3.ChangeImageToPressed();
						}
						else if(selectedArea == AREA_DESERT && desertLevel3)
						{
							level3.ChangeImageToPressed();
						}
						else if(selectedArea == AREA_CITY && cityLevel3)
						{
							level3.ChangeImageToPressed();
						}
						else if(selectedArea == AREA_SEA && seaLevel3)
						{
							level3.ChangeImageToPressed();
						}
					}
					else
					{
						if(selectedArea == AREA_FOREST && forestLevel3)
						{
							level3.ChangeImageToRegular();
						}
						else if(selectedArea == AREA_DESERT && desertLevel3)
						{
							level3.ChangeImageToRegular();
						}
						else if(selectedArea == AREA_CITY && cityLevel3)
						{
							level3.ChangeImageToRegular();
						}
						else if(selectedArea == AREA_SEA && seaLevel3)
						{
							level3.ChangeImageToRegular();
						}
					}
					
				}
				else if(STATE== STATE_GO_BACK)
				{
					if(yes.pressed(e))
						yes.ChangeImageToPressed();
					else
						yes.ChangeImageToRegular();
					
					if(no.pressed(e))
						no.ChangeImageToPressed();
					else
						no.ChangeImageToRegular();
				} 
				e.downTouchResolved = true;
			}
			if(!e.upTouchResolved)
			{
				if(STATE == STATE_SELECTING_AREA)
				{
					if(upgrade.pressed(e))
					{
						Vibrate();
						upgrade.ChangeImageToRegular();
						game.setScreen(new UpgradeScreen());
					}
					else if(back.pressed(e))
					{
						Vibrate();
						back.ChangeImageToRegular();
						STATE= STATE_GO_BACK;
					}
					else if(mainMenu.pressed(e))
					{
						Vibrate();
						mainMenu.ChangeImageToRegular();
						STATE= STATE_SELECTING_AREA;
					}
					if(selectedArea != AREA_SPACE)
					{
						if(desert.pressed(e))
						{
							level1.setPosition(70,245);
		                    level2.setPosition(175,130);
		                    level3.setPosition(420,50);
	
		                    if(!desertLevel3)
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3Locked"));
	                        }
	                        else
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3ButtonReg"));
	                        }
	                        if(!desertLevel2)
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2Locked"));
	                        }
	                        else
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2ButtonReg"));
	                        }
		                    
							Vibrate();
							selectedArea= AREA_DESERT;
							STATE= STATE_SELECTING_LEVEL;
							background = assetManager.GetTexture("DesertAreaBackground");
						}
						else if(sea.pressed(e))
						{
							level1.setPosition(405,130);
		                    level2.setPosition(70, 120);
		                    level3.setPosition(270,230);
		                    
		                    if(!seaLevel3)
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3Locked"));
	                        }
	                        else
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3ButtonReg"));
	                        }
	                        if(!seaLevel2)
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2Locked"));
	                        }
	                        else
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2ButtonReg"));
	                        }
		                    
							selectedArea = AREA_SEA;
							Vibrate();
							STATE= STATE_SELECTING_LEVEL;
							background = assetManager.GetTexture("OceanAreaBackground");
						}
						else if(forest.pressed(e))
						{
							level1.setPosition(75,140);
		                    level2.setPosition(190,205);
		                    level3.setPosition(360,85);
		                    if(!forestLevel3)
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3Locked"));
	                        }
	                        else
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3ButtonReg"));
	                        }
	                        if(!forestLevel2)
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2Locked"));
	                        }
	                        else
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2ButtonReg"));
	                        }
	
							Vibrate();
							selectedArea = AREA_FOREST;
							STATE= STATE_SELECTING_LEVEL;
							background = assetManager.GetTexture("ForestAreaBackground");
						}
						else if(city.pressed(e))
						{
							level1.setPosition(225,40);
		                    level2.setPosition(320,170);
		                    level3.setPosition(90,225);
		                    if(!cityLevel3)
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3Locked"));
	                        }
	                        else
	                        {
	                            level3.ChangeImage(assetManager.GetTexture("stage3ButtonReg"));
	                        }
	                        if(!cityLevel2)
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2Locked"));
	                        }
	                        else
	                        {
	                            level2.ChangeImage(assetManager.GetTexture("stage2ButtonReg"));
	                        }
							Vibrate();
							selectedArea = AREA_CITY;
							STATE= STATE_SELECTING_LEVEL;
							background = assetManager.GetTexture("CityAreaBackground");
						}
					}
					else
					{
						if(continueButton.pressed(e))
						{
							Vibrate();
							game.setScreen(new GameScreen(selectedArea ,1));
						}
					}
				}
				else if(STATE == STATE_SELECTING_LEVEL)
				{
					if(level1.pressed(e))
					{
						Vibrate();
						level1.ChangeImageToRegular();
						game.setScreen(new GameScreen(selectedArea,1));
						
					}
					else if(level2.pressed(e))
					{
						if(selectedArea == AREA_FOREST && forestLevel2)
						{
							level2.ChangeImageToRegular();
							Vibrate();
							game.setScreen(new GameScreen(selectedArea,2));
						}
						else if(selectedArea == AREA_DESERT && desertLevel2)
						{
							level2.ChangeImageToRegular();
							Vibrate();
							game.setScreen(new GameScreen(selectedArea,2));
						}
						else if(selectedArea == AREA_CITY && cityLevel2)
						{
							level2.ChangeImageToRegular();
							Vibrate();
							game.setScreen(new GameScreen(selectedArea,2));
						}
						else if(selectedArea == AREA_SEA && seaLevel2)
						{
							level2.ChangeImageToRegular();
							Vibrate();
							game.setScreen(new GameScreen(selectedArea,2));
						}
					}
					else if(level3.pressed(e))
					{
						
						if(selectedArea == AREA_FOREST && forestLevel3)
						{
							Vibrate();
							level3.ChangeImageToRegular();
							game.setScreen(new GameScreen(selectedArea,3));
						}
						else if(selectedArea == AREA_DESERT && desertLevel3)
						{
							Vibrate();
							level3.ChangeImageToRegular();
							game.setScreen(new GameScreen(selectedArea,3));
						}
						else if(selectedArea == AREA_CITY && cityLevel3)
						{
							Vibrate();
							level3.ChangeImageToRegular();
							game.setScreen(new GameScreen(selectedArea,3));
						}
						else if(selectedArea == AREA_SEA && seaLevel3)
						{
							Vibrate();
							level3.ChangeImageToRegular();
							game.setScreen(new GameScreen(selectedArea,3));
						}
						
					}
					else if(upgrade.pressed(e))
					{
						Vibrate();
						upgrade.ChangeImageToRegular();
						game.setScreen(new UpgradeScreen());
					}
					else if(back.pressed(e))
					{
						Vibrate();
						back.ChangeImageToRegular();
						this.mainMenu.ChangeImageToRegular();
						STATE = STATE_SELECTING_AREA;
					}
				}
				else if(STATE== STATE_GO_BACK)
				{
					if(yes.pressed(e))
					{
						Vibrate();
						yes.ChangeImageToRegular();
						Settings.Save();
						game.setScreen(new MainMenuScreen());
						
					}
					else if(no.pressed(e))
					{
						Vibrate();
						no.ChangeImageToRegular();
						mainMenu.ChangeImageToRegular();
						STATE = STATE_SELECTING_AREA;
					}
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
        if(STATE== STATE_SELECTING_AREA && selectedArea != AREA_SPACE)
        {
        	forest.draw(batch);
        	desert.draw(batch);
        	sea.draw(batch);
        	city.draw(batch);
        	upgrade.draw(batch);
        	mainMenu.draw(batch);
        }
        else if(STATE== STATE_SELECTING_LEVEL)
        {
        	batch.draw(background,0,0);
        	level1.draw(batch);
        	level2.draw(batch);
        	level3.draw(batch);
        	back.draw(batch);
        	upgrade.draw(batch);
        }
        else if(STATE == STATE_GO_BACK)
        {
        	batch.draw(assetManager.GetTexture("standardBackground"), 0, 0);
        	assetManager.GetFont().draw(batch,"Go Back To Menu?",300,470);
        	assetManager.GetFont().draw(batch,"All information will automatically save.",175,410);
        	yes.draw(batch);
        	no.draw(batch);
        }
        else if(selectedArea == AREA_SPACE)
        {
        	batch.draw(background,0,0);
        	upgrade.draw(batch);
        	continueButton.draw(batch);
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Input.Keys.BACK)
		{
			if(STATE == STATE_SELECTING_LEVEL)
			{
				STATE = STATE_SELECTING_AREA;
			}
			else if(STATE == STATE_SELECTING_AREA)
			{
				STATE = STATE_GO_BACK;
			}
			else if(STATE== STATE_GO_BACK)
			{
				STATE = STATE_SELECTING_AREA;
			}
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
