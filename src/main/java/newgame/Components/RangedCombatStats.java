package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class RangedCombatStats implements Component
{
    public float damage;
    public float protection;
    public float attackRange;
    public int attackCooldown;
    public int attackDuration;
    public float attackSpeed;
    public int framesSinceLastAttack = 0;

    public Entity equippedWeapon;
    public Entity equippedShield;

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
