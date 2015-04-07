package Enemies;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;

import static com.copyzero.JackLumber2.Settings.*;
public class Meteor extends Enemy
{

	public Meteor (int x,int y,Player playerRef,AIDirector director)
    {
        super(x,y,2,20, assetManager.GetTexture("meteor"),10,playerRef,director);

    }

    @Override
    public void update(float deltaTime)
    {
        x-=speed;
        if(x+image.getWidth()<0)
        {
            this.x = 800;
            y = playerRef.GetY();
        }
        hitBox.setPosition((int)x,(int)y);
    }

}
