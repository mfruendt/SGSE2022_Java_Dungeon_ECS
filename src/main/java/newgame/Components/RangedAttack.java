package newgame.Components;

import com.badlogic.ashley.core.Component;

public class RangedAttack implements Component
{
    public float damage;
    public float bulletRange;
    public int attackDurationLeft;
    public boolean hasHit;
    public Receiver receiver;

    public RangedAttack(float damage, int attackDuration, float bulletRange, Receiver receiver)
    {
        this.damage = damage;
        this.bulletRange = bulletRange;
        attackDurationLeft = attackDuration;
        this.receiver = receiver;
        hasHit = false;
    }

    public enum Receiver
    {
        HOSTILE,
        PLAYER
    }
}
