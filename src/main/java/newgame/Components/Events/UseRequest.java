package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for use requests
 * @author Maxim Fründt
 */
public class UseRequest implements Component
{
    /** Entity that requested the use */
    public Entity requester;
    /** Inventory slot of the item that should be used */
    public int slot;

    /** Create new use request
     *
     * @param requester Entity that requested the use
     * @param slot Inventory slot of the item that should be used
     */
    public UseRequest(Entity requester, int slot)
    {
        this.requester = requester;
        this.slot = slot;
    }
}
