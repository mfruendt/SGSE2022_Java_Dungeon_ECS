package newgame.ItemUsages;

import com.badlogic.ashley.core.Entity;

public interface ItemUsage
{
    public void onUse(Entity item, Entity user);

    public void onConsumed(Entity item, Entity user);
}
