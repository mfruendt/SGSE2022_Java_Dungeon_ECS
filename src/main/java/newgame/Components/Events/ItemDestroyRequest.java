package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class ItemDestroyRequest implements Component
{
    public Entity owner;
    public int slot;

    public ItemDestroyRequest(Entity owner, int slot)
    {
        this.owner = owner;
        this.slot = slot;
    }
}
