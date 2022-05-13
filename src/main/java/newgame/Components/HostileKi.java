package newgame.Components;

import com.badlogic.ashley.core.Component;

public class HostileKi implements Component
{
    public float speed;
    public float damage;
    public Position target;

    public HostileKi(float speed, float damage, Position target)
    {
        this.speed = speed;
        this.damage = damage;
        this.target = target;
    }
}
