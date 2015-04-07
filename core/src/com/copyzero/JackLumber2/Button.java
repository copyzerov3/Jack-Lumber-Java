package com.copyzero.JackLumber2;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Doug on 6/15/13.
 */


public class Button
{
    private NamedTexture imageDefault;
    private NamedTexture imagePressed;
    private NamedTexture image;

    private int x;
    private int y;
    private int width;
    private int height;
    String m_Label;
    boolean m_drawLabel = false;
    
    private BitmapFont font;
    /*If you want to change the images yourself and not let it handle itself*/
    public Button(NamedTexture image,int x,int y)
    {
        this.image = image;
        this.x = x;
        this.y = y;


    }
    public Button(Button button)
    {
    	
    }
    public Button(int x,int y,int width,int height)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }

    /* use if you want this class to actually handle everything*/
    public Button(NamedTexture imageRegular,NamedTexture imagePressed,int x,int y)
    {
        this.imageDefault = imageRegular;
        this.imagePressed = imagePressed;
        this.x=x;
        this.y = y;
        this.image = imageRegular;
    }
    /*use this for dynamic text on the buttons always centered on them though, and also used for the button Manager who handles all the buttons*/
    public Button(NamedTexture imageRegular,NamedTexture imagePressed,int x,int y,String Label,boolean drawLabel,BitmapFont font)
    {
        this.imageDefault = imageRegular;
        this.imagePressed = imagePressed;
        this.x=x;
        this.y = y;
        this.image = imageRegular;
        this.m_Label = Label;
        this.font = font;
        m_drawLabel = drawLabel;
    }
    /*Basically just returns if it is pressed just the boolean not changing the pictures*/
    public boolean pressed(TouchInfo event)
    {
    	if(image!= null)
    	{
	    	if(event.touchX > x && event.touchX< x+image.getWidth() -1 && event.touchY > y && event.touchY < y+image.getHeight() -1)
	            return true;
	        else
	            return false;
    	}
    	else
    	{
    		if(event.touchX > x && event.touchX< x+width -1 && event.touchY > y && event.touchY < y+height -1)
	            return true;
	        else
	            return false;
    	}
       
    }
    /*this will return the boolean and switch the buttons for you if they are straight buttons you can just call this and switch it*/
    public boolean ChangeImageFromInput(TouchInfo e)
    {
        if(pressed(e))
        {
            if(!e.downTouchResolved)
                image = imagePressed;
            else
                image= imageDefault;
            return true;
        }
        else
            image = imageDefault;
        return false;

    }
    /*Change the image to a new picture*/
    public void ChangeImage(NamedTexture image)
    {
        this.image = image;
    }
    public void ChangeImage(NamedTexture regularImage,NamedTexture pressedImage)
    {
        this.imageDefault = regularImage;
        this.imagePressed = pressedImage;
        image = regularImage;
    }
    public void ChangeImage()
    {
        if(image == imageDefault)
        {
            image =imagePressed;
        }
        else
        {
            image = imageDefault;
        }
    }
    public void ChangeImageToRegular()
    {
        image = imageDefault;
    }
    public void ChangeImageToPressed()
    {
        image = imagePressed;
    }
    /* call this in the draw calls for it to actually draw*/
    public void draw(SpriteBatch batch)
    {
    	if(image == null)
    		return;
    	batch.draw(image, x, 480-y-image.getHeight());
        if(m_Label != null && m_drawLabel && font != null)
            font.draw(batch,m_Label , x+image.getWidth()/2-(m_Label.length()*4)-(m_Label.length()*3),480-y-28);
        
        
    }
    /* generally used for debugging because I am lazy as hell for positioning on the screen and such dont use regularly*/
    public void setPosition(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    public String GetName()
    {
        return m_Label;
    }
    public int GetX(){return x;}
    public int GetY(){return y;}
    public void SetX(int x){this.x = x;}
    public void SetY(int y){this.y = y;}
}
