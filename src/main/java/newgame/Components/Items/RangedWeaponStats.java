package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

public class RangedWeaponStats implements Component
{
    public int usesLeft;
    public float damage;
    public float range;
    public int cooldown;
    public float attackSpeed;
    public int attackDuration;

    public RangedWeaponStats(int usesLeft, float damage, float range, int cooldown, float attackSpeed, int attackDuration)
    {
        this.usesLeft = usesLeft;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.attackSpeed = attackSpeed;
        this.attackDuration = attackDuration;
    }
}
