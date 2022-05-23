package newgame.Components.Tags;

import com.badlogic.ashley.core.Component;

public class Pickup implements Component
{
    public String displayName;
    public boolean equipped;
    public int slot;

    public Pickup(String displayName)
    {
        this.displayName = displayName;
        equipped = false;
        slot = -1;
    }
}
