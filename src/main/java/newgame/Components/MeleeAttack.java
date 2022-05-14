package newgame.Components;

import com.badlogic.ashley.core.Component;

public class MeleeAttack implements Component
{
    public float damage;
    public float radius;
    public AttackDirection attackDirection;
    public Receiver receiver;

    public MeleeAttack(float damage, AttackDirection attackDirection, float radius, Receiver receiver)
    {
        this.damage = damage;
        this.radius = radius;
        this.attackDirection = attackDirection;
        this.receiver = receiver;
    }

    public enum Receiver
    {
        HOSTILE,
        PLAYER
    }

    public enum AttackDirection
    {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
