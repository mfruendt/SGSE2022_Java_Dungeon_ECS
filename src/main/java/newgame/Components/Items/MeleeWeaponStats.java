package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

public class MeleeWeaponStats implements Component
{
    public int usesLeft;
    public float damage;
    public float range;
    public int cooldown;
    public float knockbackSpeed;
    public int knockbackDuration;

    public MeleeWeaponStats(int usesLeft, float damage, float range, int cooldown, float knockbackSpeed, int knockbackDuration)
    {
        this.usesLeft = usesLeft;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.knockbackSpeed = knockbackSpeed;
        this.knockbackDuration = knockbackDuration;
    }
}
