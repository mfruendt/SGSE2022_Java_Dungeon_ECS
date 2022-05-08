package newgame.items.weapons.rangedweapons;

import newgame.textures.WeaponTextures;

public class Bow extends RangedWeapon
{
    private static final float DAMAGE = 2.0f;
    private static final float DURABILITY = 2.0f;
    private static final int COOL_DOWN = 1;
    private static final float SPEED = 1;
    private static final float DAMAGE_FALLOFF = 1.0f;
    private static final float ACCURACY_FALLOFF = 1.0f;
    private static final float DISTANCE = 1.0f;

    public Bow(boolean unlimitedDistance, float projectileSize)
    {
        super (
            DAMAGE,
            DURABILITY,
            COOL_DOWN,
            SPEED,
            DAMAGE_FALLOFF,
            ACCURACY_FALLOFF,
            DISTANCE,
            unlimitedDistance,
            projectileSize,
            WeaponTextures.BOW.getTexture(),
            WeaponTextures.ARROW.getTexture());
    }
}
