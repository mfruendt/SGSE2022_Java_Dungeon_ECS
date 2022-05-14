package newgame.Components;

import com.badlogic.ashley.core.Component;

public class HostileKi implements Component
{
    public static int attackCooldown = 8;

    public float speed;
    public float damage;
    public float attackRange;
    public Position target;
    public int framesSinceLastAttack = 0;

    public HostileKi(float speed, float damage, float attackRange, Position target)
    {
        this.speed = speed;
        this.damage = damage;
        this.attackRange = attackRange;
        this.target = target;
    }
}
