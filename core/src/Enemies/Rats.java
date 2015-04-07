package Enemies;

import com.copyzero.JackLumber2.AIDirector;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.Player;

import static com.copyzero.JackLumber2.Settings.*;

public class Rats extends Enemy
{
	private final int STATE_ARRIVING = 0;
    private final int STATE_JUMP = 1;
    private final int STATE_MOVING = 2;

    private int state = STATE_ARRIVING;

    private boolean reverse=false;
    public Rats(int x,int y,Player playerRef,AIDirector director)
    {
        super(x,y,4,3, assetManager.GetTexture("rats"),20,playerRef,director);
    }

    @Override
    public void update(float deltaTime)
    {
        if(state == STATE_ARRIVING)
        {
            x-=speed;
            if(x+image.getWidth()< 800)
            {
                state=STATE_MOVING;
            }
        }
        else if(state== STATE_MOVING)
        {
            if(this.x>playerRef.GetX()+playerRef.GetWidth()/2)
            {
                x-=speed;
                if(this.x<playerRef.GetX()+playerRef.GetWidth()/2)
                	x = playerRef.GetX()+playerRef.GetWidth()/2;
            }
            else if(this.x<playerRef.GetX()+playerRef.GetWidth()/2)
            {
                x+=speed;
                if(this.x>playerRef.GetX()+playerRef.GetWidth()/2)
                	x = playerRef.GetX()+playerRef.GetWidth()/2;
            }
            else if(this.x==playerRef.GetX()+playerRef.GetWidth()/2)
            {
                state = STATE_JUMP;
            }

        }
        else if(state == STATE_JUMP)
        {
            if(!reverse)
            {
                this.y+=speed;
                if(BoundingBox.Collided(playerRef.GetBoundingBox(),hitBox))
                {
                    isDead = true;
                }
                if(y>=450)
                {
                    reverse = true;
                }
            }
            else
            {
                this.y-=speed;
                if(y<= 0)
                {
                    reverse= false;
                    state= STATE_MOVING;
                }
            }
        }
        hitBox.setPosition((int)x, (int)y);
    }


}
