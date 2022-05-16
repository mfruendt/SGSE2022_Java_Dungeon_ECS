package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class RangedAttack implements Component
{
    public float damage;
    public float bulletRange;
    public int attackDurationLeft;
    public boolean hasHit;
    public Receiver receiver;
    public Entity attacker;

    public RangedAttack(float damage, int attackDuration, float bulletRange, Receiver receiver, Entity attacker)
    {
        this.damage = damage;
        this.bulletRange = bulletRange;
        attackDurationLeft = attackDuration;
        this.receiver = receiver;
        this.attacker = attacker;
        hasHit = false;
    }

    public enum Receiver
    {
        HOSTILE,
        PLAYER
    }
}
