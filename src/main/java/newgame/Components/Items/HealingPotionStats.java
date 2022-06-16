package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

/** Component for healing potions item stats
 * @author Maxim Fründt
 */
public class HealingPotionStats implements Component
{
    /** Health that this item restores */
    public float healthRestored;
    /** Uses that this item has left */
    public int usesLeft;
    /** Cooldown between uses */
    public int cooldown;

    /** Create new healing potion stats component
     *
     * @param healthRestored Health that this item restores
     * @param usesLeft Uses that this item has left
     * @param cooldown Cooldown between uses
     */
    public HealingPotionStats(float healthRestored, int usesLeft, int cooldown)
    {
        this.healthRestored = healthRestored;
        this.usesLeft = usesLeft;
        this.cooldown = cooldown;
    }
}
