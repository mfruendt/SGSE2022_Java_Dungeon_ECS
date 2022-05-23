package newgame.Components.Tags;

import com.badlogic.ashley.core.Component;
import newgame.Components.Position;

public class HostileKi implements Component
{
    public float speed;
    public Position target;

    public HostileKi(float speed, Position target)
    {
        this.speed = speed;
        this.target = target;
    }
}
