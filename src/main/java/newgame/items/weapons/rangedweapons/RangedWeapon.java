package newgame.items.weapons.rangedweapons;

import com.badlogic.gdx.graphics.Texture;

import newgame.items.weapons.Weapon;
import newgame.items.weapons.WeaponType;

/**
 * Class for Ranged Weapons
 * @author Dominik Haacke
 *
 */
public abstract class RangedWeapon extends Weapon 
{
	protected final Texture projectileTexture;
	protected final int COOLDOWN;
	protected int cooldownTimer;
	protected boolean isCooldown = false;
	protected final float speed;
	protected final float damageFalloff;
	protected final float accuracyFalloff;
	protected final float distance;
	protected final boolean unlimitedDistance;
	protected final float projectileSize;
	
	/**
	 * Creates an inventory ranged weapon, that can be equipped by the Hero.
	 *
	 * @param damage Damage of the Weapon
	 * @param durability Durability of the Weapon
	 * @param cooldown Cooldown between shots
	 * @param speed Speed of the projectile
	 * @param damageFalloff Damage falloff of the projectile
	 * @param accuracyFalloff Accuracy falloff of the projectile
	 * @param distance Maximum travel distance of the projectile
	 * @param unlimitedDistance Sets the travel distance to unlimited
	 * @param projectileSize Size of the projectile
	 * @param weaponTexture Texture of this weapon
	 * @param projectileTexture Texture of the projectile
	 */
	public RangedWeapon(
			float damage, 
			float durability,
			int cooldown, 
			float speed, 
			float damageFalloff, 
			float accuracyFalloff, 
			float distance, 
			boolean unlimitedDistance, 
			float projectileSize,
			Texture weaponTexture,
			Texture projectileTexture) 
	{
		super(damage, durability, weaponTexture);
		this.COOLDOWN = cooldown;
		this.cooldownTimer = COOLDOWN;
		this.speed = speed;
		this.damageFalloff = damageFalloff;
		this.accuracyFalloff = accuracyFalloff;
		this.distance = distance;
		this.unlimitedDistance = unlimitedDistance;
		this.projectileSize = projectileSize;
		this.projectileTexture = projectileTexture;
	}
	
	/**
	 * Checks, if the weapon can shoot and activates cooldown.
	 *
	 * @return true, if weapon can shoot
	 */
	public boolean canShoot()
	{
		if(!isCooldown)
		{
			isCooldown = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Updates the weapon cooldown timers.
	 */
	public void updateCooldownTimer()
	{
		if(isCooldown)
		{
			cooldownTimer --;
			if(cooldownTimer <= 0)
			{
				isCooldown = false;
				cooldownTimer = COOLDOWN;
			}
		}
	}

	/**
	 * Returns the projectile texture
	 * @return Texture
	 */
	public Texture getProjectileTexture() 
	{
		return projectileTexture;
	}

	/**
	 * Returns the speed of the projectile
	 * @return float speed
	 */
	public float getSpeed() 
	{
		return speed;
	}

	/**
	 * Returns the damage falloff of the projectile
	 * @return float damageFalloff
	 */
	public float getDamageFalloff() 
	{
		return damageFalloff;
	}

	/**
	 * Returns the accuracy falloff of the projectile
	 * @return float accuracyFalloff
	 */
	public float getAccuracyFalloff() 
	{
		return accuracyFalloff;
	}

	/**
	 * Returns the maximum travel distance of the projectile
	 * @return float distance
	 */
	public float getDistance() 
	{
		return distance;
	}

	/**
	 * Returns if the travel distance of the projectile is unlimited.
	 * @return boolean unlimitedDistance
	 */
	public boolean isUnlimitedDistance() 
	{
		return unlimitedDistance;
	}

	/**
	 * Returns the projectile size for collision detection
	 * @return float size
	 */
	public float getProjectileSize() 
	{
		return projectileSize;
	}

	/**
	 * Returns the weapon type of the weapon, in the case RANGED
	 */
	@Override
	public WeaponType getWeaponType()
	{
		return WeaponType.RANGED;
	}
}
