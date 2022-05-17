package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class DropRequest implements Component
{
    public Entity requester;
    public int index;

    public DropRequest(Entity requester, int index)
    {
        this.requester = requester;
        this.index = index;
    }
}
