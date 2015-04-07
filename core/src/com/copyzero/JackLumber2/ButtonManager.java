package com.copyzero.JackLumber2;


import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class ButtonManager
{
    Vector<Button> buttons;

    public ButtonManager()
    {
        buttons = new Vector<Button>();
    }
    public void addButton(Button buttonToAdd)
    {
        buttons.add(buttonToAdd);
    }
    public void ClearButtons()
    {
        buttons.clear();
    }
    public Button CheckPress(TouchInfo e)
    {
        for(int i = 0;i<buttons.size();i++)
        {
            buttons.get(i).ChangeImageFromInput(e);
            if(buttons.get(i).pressed(e) && !e.upTouchResolved)
            {
                return buttons.get(i);
            }
        }
        return null;
    }
    public void Draw(SpriteBatch batch)
    {
        for(int i = 0;i<buttons.size();i++)
        {
            buttons.get(i).draw(batch);
        }
    }
    public Vector<Button> GetButtons()
    {
        return buttons;
    }
}
