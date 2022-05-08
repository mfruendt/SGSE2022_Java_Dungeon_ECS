package newgame.items.weapons.meleeweapons;

import newgame.textures.WeaponTextures;

public class RegularSword extends MeleeWeapon
{
    private static final float DAMAGE = 2.0f;
    private static final float DURABILITY = 2.0f;

    public RegularSword()
    {
        super(DAMAGE, DURABILITY, WeaponTextures.REGULAR_SWORD.getTexture());
    }
}
