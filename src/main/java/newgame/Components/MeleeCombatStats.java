package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/** Component for melee combat stats
 * @author Maxim Fründt
 */
public class MeleeCombatStats implements Component
{
    /** Damage that can be dealt */
    public float damage;
    /** Protection against melee damage */
    public float protection;
    /** Attack range of this entity */
    public float attackRange;
    /** Knockback speed that this entity can deal */
    public float knockbackSpeed;
    /** Knockback duration that this entity can deal */
    public int knockbackDuration;
    /** Cooldown of melee attacks */
    public int attackCooldown;
    /** Duration since the last attack was initiated in frames */
    public int framesSinceLastAttack = 0;

    /** Melee weapon that is equipped */
    public Entity equippedWeapon;
    /** Melee protection that is equipped */
    public Entity equippedShield;

    /** Create new melee combat stats component
     */
    public MeleeCombatStats()
    {

    }

    /** Constructor for melee combat stats
     *
     * @param damage Damage that the entity can deal
     * @param protection Protection that the entity has
     * @param attackRange Attack range of the entity
     * @param knockbackSpeed Speed of the knockback that the entity deals
     * @param knockbackDuration Duration of the knockback that the entity deals
     * @param attackCooldown Cooldown between attacks of the entity
     */
    public MeleeCombatStats(float damage, float protection, float attackRange, float knockbackSpeed, int knockbackDuration, int attackCooldown)
    {
        this.damage = damage;
        this.protection = protection;
        this.attackRange = attackRange;
        this.knockbackSpeed = knockbackSpeed;
        this.knockbackDuration = knockbackDuration;
        this.attackCooldown = attackCooldown;
    }
}
