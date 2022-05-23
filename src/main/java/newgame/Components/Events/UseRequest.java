package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class UseRequest implements Component
{
    public Entity requester;
    public int slot;

    public UseRequest(Entity requester, int slot)
    {
        this.requester = requester;
        this.slot = slot;
    }
}
