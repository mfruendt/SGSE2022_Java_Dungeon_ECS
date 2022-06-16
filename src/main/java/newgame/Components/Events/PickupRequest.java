package newgame.Components.Events;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for pick up requests
 * @author Maxim Fründt
 */
public class PickupRequest implements Component
{
    /** Entity that requested the pick up */
    public Entity requester;

    /** Create new pick up request
     *
     * @param requester Entity that requested the pick up
     */
    public PickupRequest(Entity requester)
    {
        this.requester = requester;
    }
}
