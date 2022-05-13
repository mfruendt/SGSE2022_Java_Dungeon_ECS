package newgame.Components;

import com.badlogic.ashley.core.Component;

public class HardMonsterKi implements Component
{
    public float speed;
    public float damage;
    public Position target;

    public HardMonsterKi(float speed, float damage, Position target)
    {
        this.speed = speed;
        this.damage = damage;
        this.target = target;
    }
}
