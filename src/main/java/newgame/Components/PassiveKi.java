package newgame.Components;

import com.badlogic.ashley.core.Component;

public class PassiveKi implements Component
{
    public float speed;
    public Position target;

    public PassiveKi()
    {

    }

    public PassiveKi(float speed, Position target)
    {
        this.speed = speed;
        this.target = target;
    }
}
