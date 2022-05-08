package newgame.items.weapons;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Projectile implements IDrawable, IEntity
{
	private final Point currentPosition;
	private double rotation;
	private final float speed;
	private float distance;
	private final float damageFalloff;
	private final float accuracyFalloff;
	private float damage;
	private final float size;
	private final boolean unlimitedDistance;
	private final DungeonWorld currentDungeonWorld;
	private boolean isDestroyed = false;
	private final Texture texture;
	
	/**
	 * Creates a new projectile
	 * @param x Value of the x position
	 * @param y Value of the y position
	 * @param rotation Rotation of the projectile
	 * @param speed Speed of the projectile
	 * @param damage Damage of the projectile
	 * @param damageFalloff Damage falloff of the projectile
	 * @param accuracyFalloff Accuracy falloff of the projectile
	 * @param distance Maximum travel distance of the projectile
	 * @param unlimitedDistance Sets the travel distance to unlimited
	 * @param size Size of the projectile
	 * @param initialOffset Initial offset of the projectile
	 * @param texture Texture of the projectile
	 * @param dungeonWorld Current Dungeon World
	 */
	public Projectile(
			float x, 
			float y, 
			double rotation, 
			float speed,
			float damage, 
			float damageFalloff, 
			float accuracyFalloff,
			float distance,
			boolean unlimitedDistance,
			float size, 
			float initialOffset,
			Texture texture, 
			DungeonWorld dungeonWorld)
	{
		currentPosition = new Point(x, y);
		this.rotation = rotation;
		this.speed = speed;
		this.distance = distance;
		this.damageFalloff = damageFalloff;
		this.accuracyFalloff = accuracyFalloff;
		this.damage = damage;
		this.size = size;
		this.unlimitedDistance = unlimitedDistance;
		this.currentDungeonWorld = dungeonWorld;
		this.texture = texture;
		move(initialOffset);
	}
	
	/**
	 * Iteratively checks, if there is a wall in the next step, so it wont go threw.
	 *
	 * @param directionX X Direction
	 * @param directionY Y Direction
	 * @param speed Speed/Distance of the next step
	 * @return Status, if projectile will collide with wall at given coordinates.
	 */
	public boolean checkForWall(float directionX, float directionY, float speed)
	{
		for(float i = 0; i < speed; i+=0.01f)
		{
			Point tempPos = new Point(currentPosition);
			tempPos.x += directionX * i;
			tempPos.y += directionY * i;
			if(!currentDungeonWorld.isTileAccessible(tempPos))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Move the projectile in the given direction at the speed parameter.
	 * @param speed Speed of the projectile
	 */
	public void move(float speed)
	{
		float directionX = (float) Math.cos(Math.toRadians(rotation));
		float directionY = (float) Math.sin(Math.toRadians(rotation));
		
		float length = directionX * directionX + directionY * directionY;
		if (length > 0) 
		{
		    directionX /= length;
		    directionY /= length;
		}

		if(!checkForWall(directionX, directionY, speed))
		{
			float velocityX = directionX * speed;
			float velocityY = directionY * speed;
			currentPosition.x += velocityX;
			currentPosition.y += velocityY;
		}
		else
		{
			isDestroyed = true;
		}
	}
	
	/**
	 * Updates travel distance, damage and accuracy based on movement.
	 */
	public void updateParameter()
	{
		if(!unlimitedDistance)
		{
			distance -= speed;
			if(distance <= 0)
			{
				isDestroyed = true;
			}
		}
		if(damageFalloff > 0)
		{
			if(damage > 0)
			{
				damage -= damageFalloff;
				if(damage < 0)
				{
					damage = 0;
				}
			}
		}
		if(accuracyFalloff > 0)
		{
			int changeAccuracy = ThreadLocalRandom.current().nextInt(0, 3);
			if(changeAccuracy == 1)
			{
				rotation += accuracyFalloff;
			}
			else if(changeAccuracy == 2)
			{
				rotation -= accuracyFalloff;
			}
		}
	}
	
	/**
	 * Set isDestroyed to true, gets removed in GameHandler
	 */
	public void destroy() 
	{
		isDestroyed = true;
	}
	
	/**
	 * Returns the size of the projectile
	 * @return float size
	 */
	public float getSize()
	{
		return size;
	}
	
	/**
	 * Returns the destroyed status of the projectile
	 * @return true if destroyed
	 */
	public boolean isDestroyed() 
	{
		return isDestroyed;
	}
	
	/**
	 * Returns the current damage of the projectile
	 * @return float damage
	 */
	public float getDamage() 
	{
		return damage;
	}
	
	@Override
	public Point getPosition() 
	{
		return currentPosition;
	}

	@Override
	public boolean deleteable()
	{
		return isDestroyed;
	}

	@Override
	public void update() 
	{
		if(!isDestroyed)
		{
			this.drawWithScaling(0.5f, 0.5f);
			move(speed);
			updateParameter();
		}
	}

	@Override
	public Texture getTexture() 
	{
		return texture;
	}
}
