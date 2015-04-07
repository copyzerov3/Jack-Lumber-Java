package Bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.NamedTexture;
import com.copyzero.JackLumber2.Timer;

import static com.copyzero.JackLumber2.Settings.*;

public class Explosion 
{
	public BoundingBox hitBox;
    private int x,y;
    private Timer onScreen;
    private final int time = 1;
    public boolean isDead = false;
    public int damage;

    private NamedTexture image;
    public Explosion(int x,int y,int damage,boolean big,boolean isFromGrenade)
    {
        this.x=x;
        this.y =y;
        this.damage = damage;
        
        if(isFromGrenade)
        {
        	image= assetManager.GetTexture("waterExplosion");
        }
        else
        {
        	image= assetManager.GetTexture("Explosion");
        }
        
        onScreen = new Timer(time);
        if(big)
        {
            hitBox = new BoundingBox(x,y,image.getWidth()+20,image.getHeight()+20);
        }
        else
        {
            hitBox = new BoundingBox(x,y,image.getWidth(),image.getHeight());
        }
    }
    public void update(float deltaTime)
    {
        this.onScreen.update(deltaTime);
        if(!onScreen.running())
        {
            isDead = true;
        }
    }
    public void draw(SpriteBatch batch)
    {
        batch.draw(image,x,y);
    }


}
