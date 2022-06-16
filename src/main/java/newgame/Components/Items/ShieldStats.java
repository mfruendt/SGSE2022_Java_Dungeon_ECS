package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

/** Component for shield item stats
 * @author Maxim Fründt
 */
public class ShieldStats implements Component
{
    /** Uses that this item has left */
    public int usesLeft;
    /** Protection of this item */
    public float protection;

    /** Create new shield stats component
     *
     * @param usesLeft Uses that this item has left
     * @param protection Protection of this item
     */
    public ShieldStats(int usesLeft, float protection)
    {
        this.usesLeft = usesLeft;
        this.protection = protection;
    }
}
