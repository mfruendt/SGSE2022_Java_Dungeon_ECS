package newgame.Components;

import com.badlogic.ashley.core.Component;

public class MeleeAttack implements Component
{
    public float damage;
    public float radius;
    public AttackDirection attackDirection;

    public MeleeAttack(float damage, AttackDirection attackDirection, float radius)
    {
        this.damage = damage;
        this.radius = radius;
        this.attackDirection = attackDirection;
    }

    public enum AttackDirection
    {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
