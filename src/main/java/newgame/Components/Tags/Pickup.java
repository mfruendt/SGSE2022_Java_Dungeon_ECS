package newgame.Components.Tags;

import com.badlogic.ashley.core.Component;

/** Component for pick ups
 * @author Maxim Fründt
 */
public class Pickup implements Component
{
    /** Display name of the pick up */
    public String displayName;
    /** Flag if the item is equipped */
    public boolean equipped;
    /** Slot in which this item is stored, if it is within an inventory */
    public int slot;

    public Pickup(String displayName)
    {
        this.displayName = displayName;
        equipped = false;
        slot = -1;
    }
}
