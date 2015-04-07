package Bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.copyzero.JackLumber2.BoundingBox;
import com.copyzero.JackLumber2.BulletManager;
import com.copyzero.JackLumber2.NamedTexture;

public abstract class Bullet 
{
	protected BoundingBox hitBox;
    protected boolean isDead = false;
    protected boolean isShootable = false;
    protected int speed;
    protected int damage;
    protected NamedTexture asset;
    protected int level;
    protected int x;
    protected int y;
    protected BulletManager bulletManagerRef = null;
    public Bullet(int x,int y,NamedTexture Asset,int speed,int damage)
    {
        this.x = x;
        this.y = y;
        this.asset = Asset;
        this.speed = speed;
        this.damage = damage;
        this.hitBox = new BoundingBox(x,y,asset.getWidth(),asset.getHeight());
    }
    public Bullet(int x,int y)
    {
        this.x =x;
        this.y =y;
    }
    public Bullet(int x,int y,NamedTexture Asset,int speed,int damage,BulletManager bulletManagerRef)
    {
    	this.x = x;
        this.y = y;
        this.asset = Asset;
        this.speed = speed;
        this.damage = damage;
        this.bulletManagerRef = bulletManagerRef;
        this.hitBox = new BoundingBox(x,y,asset.getWidth(),asset.getHeight());
    }

	public BoundingBox GetBoundingBox()
    {
    	return hitBox;
    }
    public void draw(SpriteBatch batch)
    {
        batch.draw(asset,x,y);
    }
    public void update(float deltaTime)
    {
        this.x+= speed;
        if(x> 800)
            isDead = true;
        hitBox.setPosition(x,y);
    }
    public int GetDamage()
    {
        this.isDead = true;
        return damage;
    }
    public boolean isDead()
    {
    	return isDead;
    }
    public boolean Shootable()
    {
    	return isShootable;
    }
    public void onShot()
    {
    	
    }
}
