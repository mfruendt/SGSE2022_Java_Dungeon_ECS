package newgame.Components.Items;

import com.badlogic.ashley.core.Component;

/** Component for melee weapon item stats
 * @author Maxim Fründt
 */
public class MeleeWeaponStats implements Component
{
    /** Uses that this item has left */
    public int usesLeft;
    /** Damage that this item deals */
    public float damage;
    /** Range of this item */
    public float range;
    /** Cooldown of this item */
    public int cooldown;
    /** Knockback speed of this item */
    public float knockbackSpeed;
    /** Knockback duration of this item */
    public int knockbackDuration;

    /** Create new melee weapon stats component
     *
     * @param usesLeft Uses that this item has left
     * @param damage Damage that this item deals
     * @param range Range of this item
     * @param cooldown Cooldown of this item
     * @param knockbackSpeed Knockback speed of this item
     * @param knockbackDuration Knockback duration of this item
     */
    public MeleeWeaponStats(int usesLeft, float damage, float range, int cooldown, float knockbackSpeed, int knockbackDuration)
    {
        this.usesLeft = usesLeft;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.knockbackSpeed = knockbackSpeed;
        this.knockbackDuration = knockbackDuration;
    }
}
