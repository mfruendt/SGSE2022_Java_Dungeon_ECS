package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

/** Component for ranged weapon item stats
 * @author Maxim Fründt
 */
public class RangedWeaponStats implements Component
{
    /** Uses that this item has left */
    public int usesLeft;
    /** Damage that this item deals */
    public float damage;
    /** Range of this item */
    public float range;
    /** Cooldown of this item */
    public int cooldown;
    /** Attack speed of this item */
    public float attackSpeed;
    /** Attack duration of this item */
    public int attackDuration;

    /** Create new ranged weapon stats component
     *
     * @param usesLeft Uses that this item has left
     * @param damage Damage that this item deals
     * @param range Range of this item
     * @param cooldown Cooldown of this item
     * @param attackSpeed Attack speed of this item
     * @param attackDuration Attack duration of this item
     */
    public RangedWeaponStats(int usesLeft, float damage, float range, int cooldown, float attackSpeed, int attackDuration)
    {
        this.usesLeft = usesLeft;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.attackSpeed = attackSpeed;
        this.attackDuration = attackDuration;
    }
}
