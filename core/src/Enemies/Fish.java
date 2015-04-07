package Enemies;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.Player;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class Fish extends Enemy
{

	private boolean reverse = false;
    private boolean reverseY= false;
    private boolean jumping = false;
    private int jumpRate = 3;
    private Timer jump;
    
    public Fish(int x,int y,Player playerRef,AIDirector director)
    {
        super(x,y,2,5, assetManager.GetTexture("fish"),5,playerRef,director);
        jump = new Timer(jumpRate);
    }
    @Override
    public void takeDamage(float damage,boolean fromContact)
    {
        if(!jumping)
        {
        	if(fromContact)
        		return;		
        }
        health-=damage;
        
        if(health<=0)
        {
        	isDead= true;
        }
    }
    @Override
    public void update(float deltaTime)
    {
        jump.update(deltaTime);
        if(!reverse)
        {
            x+=speed;
            if(x+image.getWidth() >= 800)
            {
                reverse = true;
            }

        }
        else
        {
            x-=speed;
            if(x<=0)
            {
                reverse = false;
            }

        }
        if(jumping)
        {
            if(!reverseY)
            {
                y+=speed*2;
                if(y + image.getHeight()>=480)
                {
                    reverseY= true;
                }
            }
            else
            {
                y-=speed*2;
                if(y + image.getHeight()<= 0)
                {
                    jumping = false;
                    jump.reset(jumpRate);
                    reverseY = false;
                }
            }
        }
        if(!jump.running())
        {
            jumping = true;
        }
        hitBox.setPosition((int)x,(int)y);
    }


}
