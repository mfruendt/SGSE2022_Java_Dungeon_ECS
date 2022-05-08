package newgame.items.weapons.meleeweapons;

import com.badlogic.gdx.graphics.Texture;

import newgame.items.weapons.Weapon;
import newgame.items.weapons.WeaponType;

/**
 * Class for Melee Weapons
 * @author Dominik Haacke
 *
 */
public abstract class MeleeWeapon extends Weapon 
{
	public MeleeWeapon(final float damage, final float durability, final Texture texture)
	{
		super(damage, durability, texture);
	}
	
	/**
	 * @return Returns the Melee Weapon Type
	 */
	@Override
	public WeaponType getWeaponType() 
	{
		return WeaponType.MELEE;
	}
}
