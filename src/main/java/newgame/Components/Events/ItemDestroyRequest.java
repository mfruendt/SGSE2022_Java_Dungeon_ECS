package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for item destroy requests
 * @author Maxim Fründt
 */
public class ItemDestroyRequest implements Component
{
    /** Entity that owns the item */
    public Entity owner;
    /** Inventory slot of the item that should be destroyed */
    public int slot;

    /** Create new item destroy request
     *
     * @param owner Entity that owns the item
     * @param slot Inventory slot of the item that should be destroyed
     */
    public ItemDestroyRequest(Entity owner, int slot)
    {
        this.owner = owner;
        this.slot = slot;
    }
}
