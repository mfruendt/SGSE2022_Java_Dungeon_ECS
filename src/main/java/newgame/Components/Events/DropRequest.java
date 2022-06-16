package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for drop requests
 * @author Maxim Fründt
 */
public class DropRequest implements Component
{
    /** Entity that requested the drop */
    public Entity requester;
    /** Inventory slot of the item that should be dropped */
    public int slot;

    /** Create new drop request component
     *
     * @param requester Entity that requested the drop
     * @param slot Inventory slot of the item that should be dropped
     */
    public DropRequest(Entity requester, int slot)
    {
        this.requester = requester;
        this.slot = slot;
    }
}
