package newgame.Components.Tags;

import com.badlogic.ashley.core.Component;
import newgame.Components.Position;

public class PassiveKi implements Component
{
    public float speed;
    public Position target;

    public PassiveKi(float speed, Position target)
    {
        this.speed = speed;
        this.target = target;
    }
}
