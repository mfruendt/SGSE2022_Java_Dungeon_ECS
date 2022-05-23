package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class PickupRequest implements Component
{
    public Entity requester;

    public PickupRequest(Entity requester)
    {
        this.requester = requester;
    }
}
