package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Components.Events.ItemDestroyRequest;
import newgame.Components.Events.Knockback;
import newgame.Components.Events.MeleeAttack;
import newgame.Components.Events.RangedAttack;
import newgame.Components.Items.RangedWeaponStats;
import newgame.Components.Tags.HostileKi;
import newgame.Components.Items.ShieldStats;
import newgame.Components.Items.MeleeWeaponStats;
import newgame.Components.Tags.PassiveKi;
import newgame.Components.Tags.Player;
import newgame.EntityMapper;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** System used to damage entities
 * @author Maxim Fründt
 */
public class DamageSystem extends EntitySystem
{
    /** Entities that can be damaged */
    private ImmutableArray<Entity> damageableEntities;
    /** Entities that represent a melee attack */
    private ImmutableArray<Entity> meleeAttackEntities;
    /** Entities that represent a ranged attack */
    private ImmutableArray<Entity> rangedAttackEntities;

    /** Engine to which this system belongs to */
    private final Engine engine;

    /** Create new damage system
     *
     * @param engine Engine to which this system belongs to
     */
    public DamageSystem(Engine engine)
    {
        this.engine = engine;
    }

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        damageableEntities = engine.getEntitiesFor(Family.all(Health.class, Position.class).get());
        meleeAttackEntities = engine.getEntitiesFor(Family.all(MeleeAttack.class, Position.class).get());
        rangedAttackEntities = engine.getEntitiesFor(Family.all(RangedAttack.class, Position.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < meleeAttackEntities.size(); i++)
        {
            Entity attackEntity = meleeAttackEntities.get(i);
            MeleeAttack attack = EntityMapper.meleeAttackMapper.get(attackEntity);

            for (int j = 0; j < damageableEntities.size(); j++)
            {
                Entity damagedEntity = damageableEntities.get(j);

                if (damagedEntity != attack.attacker && checkMeleeCollision(attack, EntityMapper.positionMapper.get(attackEntity), EntityMapper.positionMapper.get(damagedEntity)))
                {
                    if (executeMeleeAttack(damagedEntity, attack))
                    {
                        MeleeCombatStats meleeCombatStats = EntityMapper.meleeCombatStatsMapper.get(attack.attacker);

                        if (meleeCombatStats.equippedWeapon != null)
                        {
                            MeleeWeaponStats meleeWeaponStats = EntityMapper.meleeWeaponStatsMapper.get(meleeCombatStats.equippedWeapon);

                            meleeWeaponStats.usesLeft--;

                            if (meleeWeaponStats.usesLeft == 0)
                                engine.addEntity(new Entity().add(new ItemDestroyRequest(attack.attacker, EntityMapper.pickupMapper.get(meleeCombatStats.equippedWeapon).slot)));
                        }

                        break;
                    }
                }
            }

            engine.removeEntity(attackEntity);
        }

        for (int i = 0; i < rangedAttackEntities.size(); i++)
        {
            Entity attackEntity = rangedAttackEntities.get(i);
            RangedAttack attack = EntityMapper.rangedAttackMapper.get(attackEntity);

            for (int j = 0; j < damageableEntities.size(); j++)
            {
                Entity damagedEntity = damageableEntities.get(j);

                if (damagedEntity != attack.attacker && checkCollision(attack.bulletRange, EntityMapper.positionMapper.get(attackEntity), EntityMapper.positionMapper.get(damagedEntity)))
                {
                    if (executeRangedAttack(damagedEntity, attack))
                        break;
                }
            }

            attack.attackDurationLeft--;

            if (attack.attackDurationLeft <= 0 || attack.hasHit)
            {
                RangedCombatStats rangedCombatStats = EntityMapper.rangedCombatStatsMapper.get(attack.attacker);

                if (rangedCombatStats.equippedWeapon != null)
                {
                    RangedWeaponStats rangedWeaponStats = EntityMapper.rangedWeaponMapper.get(rangedCombatStats.equippedWeapon);

                    rangedWeaponStats.usesLeft--;

                    if (rangedWeaponStats.usesLeft == 0)
                        engine.addEntity(new Entity().add(new ItemDestroyRequest(attack.attacker, EntityMapper.pickupMapper.get(rangedCombatStats.equippedWeapon).slot)));
                }

                engine.removeEntity(attackEntity);
            }
        }
    }

    /** Apply a ranged attack to a hit entity
     *
     * @param damagedEntity Entity that has been hit
     * @param attack Attack that will be applied to the entity
     * @return True if attack could be applied, else false
     */
    private boolean executeRangedAttack(Entity damagedEntity, RangedAttack attack)
    {
        HostileKi hostileKi = EntityMapper.hostileKiMapper.get(damagedEntity);
        PassiveKi passiveKi = EntityMapper.passiveKiMapper.get(damagedEntity);
        Player player = EntityMapper.playerMapper.get(damagedEntity);
        RangedCombatStats rangedCombatStats = EntityMapper.rangedCombatStatsMapper.get(damagedEntity);

        if (attack.receiver == RangedAttack.Receiver.HOSTILE)
        {
            damageMonster(passiveKi, hostileKi, damagedEntity, attack.damage, rangedCombatStats.protection, attack.attacker);
            attack.hasHit = true;
            return true;
        }
        else if (attack.receiver == RangedAttack.Receiver.PLAYER)
        {
            if (player != null)
            {
                if (rangedCombatStats != null)
                {
                    if (attack.damage > rangedCombatStats.protection)
                        EntityMapper.healthMapper.get(damagedEntity).currentHealth -= attack.damage - rangedCombatStats.protection;

                    if (rangedCombatStats.equippedShield != null)
                    {
                        ShieldStats shieldStats = EntityMapper.shieldMapper.get(rangedCombatStats.equippedShield);

                        shieldStats.usesLeft--;

                        if (shieldStats.usesLeft == 0)
                            engine.addEntity(new Entity().add(new ItemDestroyRequest(damagedEntity, EntityMapper.pickupMapper.get(rangedCombatStats.equippedShield).slot)));
                    }
                }
                else
                {
                    EntityMapper.healthMapper.get(damagedEntity).currentHealth -= attack.damage;
                }
                EntityMapper.healthMapper.get(damagedEntity).lastAttacker = attack.attacker;
                GameEventsLogger.getLogger().info(LogMessages.HERO_GOT_DAMAGE.toString());
                attack.hasHit = true;
                return true;
            }
        }

        return false;
    }

    /** Apply a melee attack to a hit entity
     *
     * @param damagedEntity Entity that has been hit
     * @param attack Attack that will be applied to the entity
     * @return True if attack could be applied, else false
     */
    private boolean executeMeleeAttack(Entity damagedEntity, MeleeAttack attack)
    {
        HostileKi hostileKi = EntityMapper.hostileKiMapper.get(damagedEntity);
        PassiveKi passiveKi = EntityMapper.passiveKiMapper.get(damagedEntity);
        Player player = EntityMapper.playerMapper.get(damagedEntity);
        MeleeCombatStats meleeCombatStats = EntityMapper.meleeCombatStatsMapper.get(damagedEntity);

        if (attack.receiver == MeleeAttack.Receiver.HOSTILE && (passiveKi != null || hostileKi != null))
        {
            damageMonster(passiveKi, hostileKi, damagedEntity, attack.damage, meleeCombatStats.protection, attack.attacker);
            return true;
        }
        else if (attack.receiver == MeleeAttack.Receiver.PLAYER && player != null)
        {
            if (meleeCombatStats != null)
            {
                if (attack.damage > meleeCombatStats.protection)
                    EntityMapper.healthMapper.get(damagedEntity).currentHealth -= attack.damage - meleeCombatStats.protection;

                if (meleeCombatStats.equippedShield != null)
                {
                    ShieldStats shieldStats = EntityMapper.shieldMapper.get(meleeCombatStats.equippedShield);

                    shieldStats.usesLeft--;

                    if (shieldStats.usesLeft == 0)
                        engine.addEntity(new Entity().add(new ItemDestroyRequest(damagedEntity, EntityMapper.pickupMapper.get(meleeCombatStats.equippedShield).slot)));
                }
            }
            else
            {
                EntityMapper.healthMapper.get(damagedEntity).currentHealth -= attack.damage;
            }
            EntityMapper.healthMapper.get(damagedEntity).lastAttacker = attack.attacker;
            GameEventsLogger.getLogger().info(LogMessages.HERO_GOT_DAMAGE.toString());
            damagedEntity.remove(PlayerControl.class);

            switch (attack.attackDirection)
            {
                case UP:
                    damagedEntity.add(new Knockback(attack.knockbackDuration, attack.knockbackSpeed, Knockback.Direction.UP));
                    break;

                case DOWN:
                    damagedEntity.add(new Knockback(attack.knockbackDuration, attack.knockbackSpeed, Knockback.Direction.DOWN));
                    break;

                case RIGHT:
                    damagedEntity.add(new Knockback(attack.knockbackDuration, attack.knockbackSpeed, Knockback.Direction.RIGHT));
                    break;

                case LEFT:
                    damagedEntity.add(new Knockback(attack.knockbackDuration, attack.knockbackSpeed, Knockback.Direction.LEFT));
                    break;
            }

            return true;
        }

        return false;
    }

    /** Damage a monster
     *
     * @param passiveKi Passive KI of the monster
     * @param hostileKi Hostile KI of the monster
     * @param damagedEntity Entity that will be damaged
     * @param damage Damage that will be applied
     * @param protection Protection of the entity
     * @param attacker Entity that attacked
     */
    private void damageMonster(PassiveKi passiveKi, HostileKi hostileKi, Entity damagedEntity, float damage, float protection, Entity attacker)
    {
        Health health = EntityMapper.healthMapper.get(damagedEntity);

        if (passiveKi != null)
        {
            damagedEntity.add(new HostileKi(passiveKi.speed, passiveKi.target));
            damagedEntity.remove(PassiveKi.class);
            if (damage > protection)
                health.currentHealth -= damage - protection;
            health.lastAttacker = attacker;
        }
        else if (hostileKi != null)
        {
            if (damage > protection)
                health.currentHealth -= damage - protection;
            health.lastAttacker = attacker;
        }
    }

    /** Check if two positions collide with range
     *
     * @param range Range of the collision
     * @param pos1 First position to compare
     * @param pos2 Second position to compare
     * @return True if the positions collide, else false
     */
    private boolean checkCollision(float range, Position pos1, Position pos2)
    {
        return Math.abs(pos1.x - pos2.x) <= range && Math.abs(pos1.y - pos2.y) <= range;
    }

    /** Check if the melee collision has collided with the position
     *
     * @param attack Melee attack that will be checked
     * @param attackPosition Position of the attack
     * @param entityPosition Position of the entity that will be checked
     * @return True if melee attack has collided with the other entity, else false
     */
    private boolean checkMeleeCollision(MeleeAttack attack, Position attackPosition, Position entityPosition)
    {
        if (Math.abs(entityPosition.x - attackPosition.x) <= attack.radius && Math.abs(entityPosition.y - attackPosition.y) <= attack.radius)
        {
            switch (attack.attackDirection)
            {
                case UP:
                    return attackPosition.y <= entityPosition.y;

                case DOWN:
                    return entityPosition.y <= attackPosition.y;

                case RIGHT:
                    return attackPosition.x <= entityPosition.x;

                case LEFT:
                    return entityPosition.x <= attackPosition.x;
            }
        }

        return false;
    }
}
