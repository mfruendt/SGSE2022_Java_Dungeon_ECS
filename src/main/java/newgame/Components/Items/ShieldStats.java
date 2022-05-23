package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

public class ShieldStats implements Component
{
    public int usesLeft;
    public float protection;
    public int slot;

    public ShieldStats(int usesLeft, float protection)
    {
        this.usesLeft = usesLeft;
        this.protection = protection;
        slot = -1;
    }
}
