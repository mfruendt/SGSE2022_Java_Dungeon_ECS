package newgame.Components;

import com.badlogic.ashley.core.Component;

public class HostileKi implements Component
{
    public static int ATTACK_COOLDOWN = 8;

    public float speed;
    public float damage;
    public float attackRange;
    public float knockbackSpeed;
    public int knockbackDuration;
    public Position target;
    public int framesSinceLastAttack = 0;

    public HostileKi(float speed, float damage, float attackRange, float knockbackSpeed, int knockbackDuration, Position target)
    {
        this.speed = speed;
        this.damage = damage;
        this.attackRange = attackRange;
        this.knockbackSpeed = knockbackSpeed;
        this.knockbackDuration = knockbackDuration;
        this.target = target;
    }
}
