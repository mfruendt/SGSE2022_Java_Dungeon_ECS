package newgame.items.weapons.rangedweapons;

import newgame.textures.WeaponTextures;

public class Staff extends RangedWeapon
{
    private static final float DAMAGE = 2.0f;
    private static final float DURABILITY = 2.0f;
    private static final int COOLDOWN = 1;
    private static final float SPEED = 1;
    private static final float DAMAGE_FALLOFF = 1.0f;
    private static final float ACCURACY_FALLOFF = 1.0f;
    private static final float DISTANCE = 1.0f;

    public Staff(boolean unlimitedDistance, float projectileSize)
    {
        super (
            DAMAGE,
            DURABILITY,
            COOLDOWN,
            SPEED,
            DAMAGE_FALLOFF,
            ACCURACY_FALLOFF,
            DISTANCE,
            unlimitedDistance,
            projectileSize,
            WeaponTextures.STAFF.getTexture(),
            WeaponTextures.STAFF_BALL.getTexture());
    }
}
