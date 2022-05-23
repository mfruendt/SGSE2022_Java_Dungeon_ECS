package newgame.Components;

import com.badlogic.ashley.core.Component;
import newgame.ItemUsages.ItemUsage;

public class Item implements Component
{
    public boolean isConsumed;
    public int usesLeft;
    public ItemUsage usage;

    public Item(boolean isConsumed, int usesLeft, ItemUsage usage)
    {
        this.isConsumed = isConsumed;
        this.usesLeft = usesLeft;
        this.usage = usage;
    }
}
