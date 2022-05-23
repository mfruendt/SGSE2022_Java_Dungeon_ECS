package newgame.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class MeleeCombatStats implements Component
{
    public float damage;
    public float protection;
    public float attackRange;
    public float knockbackSpeed;
    public int knockbackDuration;
    public int attackCooldown;
    public int framesSinceLastAttack = 0;

    public Entity equippedWeapon;
    public Entity equippedShield;

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
