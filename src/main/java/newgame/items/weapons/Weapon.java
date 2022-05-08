package newgame.items.weapons;

import com.badlogic.gdx.graphics.Texture;

import newgame.items.Item;
import newgame.items.ItemType;

/**
 * Abstract Class for all Weapons
 * @author Dominik Haacke
 *
 */
public abstract class Weapon extends Item
{
	private static final float DURABILITY_REDUCE_BY_HIT = 0.05f;
	protected final float damage;
	protected float durability;
	protected WeaponType weaponType;

	public Weapon(final float damage, final float durability, final Texture texture)
	{
		super (texture);
		this.damage = damage;
		this.durability = durability;
	}

	/**
	 * Returns the damage, that the weapon can deal
	 * @return damage Damage of the weapon
	 */
	public float getDamage() 
	{
		return damage;
	}

	/**
	 * Returns the durability of the weapon
	 * @return durability Durability of the weapon
	 */
	public float getDurability()
	{
		return durability;
	}
	
	/**
	 * Returns the ItemType
	 */
	@Override
	public ItemType getType()
	{
		return ItemType.WEAPON;
	}
	
	/**
	 * Reduces the durability of the weapon
	 */
	public void reduceDurability()
	{
		this.durability -= DURABILITY_REDUCE_BY_HIT;
	}
	
	/**
	 * Returns the WeaponType of the Weapon, MELEE or RANGED
	 * @return weaponType Enum-Value of WeaponType
	 */
	public WeaponType getWeaponType()
	{
		return weaponType;
	}
}
