package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for ranged combat stats
 * @author Maxim Fründt
 */
public class RangedCombatStats implements Component
{
    /** Damage that can be dealt */
    public float damage;
    /** Protection against ranged damage */
    public float protection;
    /** Attack range of this entity */
    public float attackRange;
    /** Cooldown of ranged attacks */
    public int attackCooldown;
    /** Duration of the bullets that can be shot */
    public int attackDuration;
    /** Speed of the bullets that can be shot */
    public float attackSpeed;
    /** Duration since the last attack was initiated in frames */
    public int framesSinceLastAttack = 0;

    /** Ranged weapon that is equipped */
    public Entity equippedWeapon;
    /** Ranged protection that is equipped */
    public Entity equippedShield;

    /** Create new ranged combat stats component
     */
    public RangedCombatStats()
    {

    }

    /** Constructor for melee combat stats
     *
     * @param damage Damage that the entity can deal
     * @param protection Protection that the entity has
     * @param attackRange Attack range of the entity
     * @param attackCooldown Cooldown between attacks of the entity
     */
    public RangedCombatStats(float damage, float protection, float attackRange, int attackDuration, float attackSpeed, int attackCooldown)
    {
        this.damage = damage;
        this.protection = protection;
        this.attackRange = attackRange;
        this.attackDuration = attackDuration;
        this.attackSpeed = attackSpeed;
        this.attackCooldown = attackCooldown;
    }
}
