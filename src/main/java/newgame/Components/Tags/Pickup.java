package newgame.Components.Tags;

import com.badlogic.ashley.core.Component;

public class Pickup implements Component
{
    public String displayName;

    public Pickup(String displayName)
    {
        this.displayName = displayName;
    }
}
