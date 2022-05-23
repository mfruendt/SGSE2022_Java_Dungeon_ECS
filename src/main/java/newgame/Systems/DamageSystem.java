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
import newgame.Components.Tags.Pickup;
import newgame.Components.Tags.Player;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

public class DamageSystem extends EntitySystem
{
    private ImmutableArray<Entity> damageableEntities;
    private ImmutableArray<Entity> meleeAttackEntities;
    private ImmutableArray<Entity> rangedAttackEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<MeleeAttack> meleeAttackMapper = ComponentMapper.getFor(MeleeAttack.class);
    private final ComponentMapper<RangedAttack> rangedAttackMapper = ComponentMapper.getFor(RangedAttack.class);
    private final ComponentMapper<HostileKi> hostileKiMapper = ComponentMapper.getFor(HostileKi.class);
    private final ComponentMapper<PassiveKi> passiveKiMapper = ComponentMapper.getFor(PassiveKi.class);
    private final ComponentMapper<Player> playerMapper = ComponentMapper.getFor(Player.class);
    private final ComponentMapper<MeleeWeaponStats> meleeWeaponMapper = ComponentMapper.getFor(MeleeWeaponStats.class);
    private final ComponentMapper<RangedWeaponStats> rangedWeaponMapper = ComponentMapper.getFor(RangedWeaponStats.class);
    private final ComponentMapper<ShieldStats> shieldMapper = ComponentMapper.getFor(ShieldStats.class);
    private final ComponentMapper<MeleeCombatStats> meleeCombatStatsMapper = ComponentMapper.getFor(MeleeCombatStats.class);
    private final ComponentMapper<RangedCombatStats> rangedCombatStatsMapper = ComponentMapper.getFor(RangedCombatStats.class);
    private final ComponentMapper<Pickup> pickupMapper = ComponentMapper.getFor(Pickup.class);

    private final Engine engine;

    public DamageSystem(Engine engine)
    {
        this.engine = engine;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        damageableEntities = engine.getEntitiesFor(Family.all(Health.class, Position.class).get());
        meleeAttackEntities = engine.getEntitiesFor(Family.all(MeleeAttack.class, Position.class).get());
        rangedAttackEntities = engine.getEntitiesFor(Family.all(RangedAttack.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < meleeAttackEntities.size(); i++)
        {
            Entity attackEntity = meleeAttackEntities.get(i);
            MeleeAttack attack = meleeAttackMapper.get(attackEntity);

            for (int j = 0; j < damageableEntities.size(); j++)
            {
                Entity damagedEntity = damageableEntities.get(j);

                if (damagedEntity != attack.attacker && checkMeleeCollision(attack, positionMapper.get(attackEntity), positionMapper.get(damagedEntity)))
                {
                    if (executeMeleeAttack(damagedEntity, attack))
                    {
                        MeleeCombatStats meleeCombatStats = meleeCombatStatsMapper.get(attack.attacker);

                        if (meleeCombatStats.equippedWeapon != null)
                        {
                            MeleeWeaponStats meleeWeaponStats = meleeWeaponMapper.get(meleeCombatStats.equippedWeapon);

                            meleeWeaponStats.usesLeft--;

                            if (meleeWeaponStats.usesLeft == 0)
                                engine.addEntity(new Entity().add(new ItemDestroyRequest(attack.attacker, pickupMapper.get(meleeCombatStats.equippedWeapon).slot)));
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
            RangedAttack attack = rangedAttackMapper.get(attackEntity);

            for (int j = 0; j < damageableEntities.size(); j++)
            {
                Entity damagedEntity = damageableEntities.get(j);

                if (damagedEntity != attack.attacker && checkCollision(attack.bulletRange, positionMapper.get(attackEntity), positionMapper.get(damagedEntity)))
                {
                    if (executeRangedAttack(damagedEntity, attack))
                        break;
                }
            }

            attack.attackDurationLeft--;

            if (attack.attackDurationLeft <= 0 || attack.hasHit)
            {
                RangedCombatStats rangedCombatStats = rangedCombatStatsMapper.get(attack.attacker);

                if (rangedCombatStats.equippedWeapon != null)
                {
                    RangedWeaponStats rangedWeaponStats = rangedWeaponMapper.get(rangedCombatStats.equippedWeapon);

                    rangedWeaponStats.usesLeft--;

                    if (rangedWeaponStats.usesLeft == 0)
                        engine.addEntity(new Entity().add(new ItemDestroyRequest(attack.attacker, pickupMapper.get(rangedCombatStats.equippedWeapon).slot)));
                }

                engine.removeEntity(attackEntity);
            }
        }
    }

    private boolean executeRangedAttack(Entity damagedEntity, RangedAttack attack)
    {
        HostileKi hostileKi = hostileKiMapper.get(damagedEntity);
        PassiveKi passiveKi = passiveKiMapper.get(damagedEntity);
        Player player = playerMapper.get(damagedEntity);
        RangedCombatStats rangedCombatStats = rangedCombatStatsMapper.get(damagedEntity);

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
                        healthMapper.get(damagedEntity).currentHealth -= attack.damage - rangedCombatStats.protection;

                    if (rangedCombatStats.equippedShield != null)
                    {
                        ShieldStats shieldStats = shieldMapper.get(rangedCombatStats.equippedShield);

                        shieldStats.usesLeft--;

                        if (shieldStats.usesLeft == 0)
                            engine.addEntity(new Entity().add(new ItemDestroyRequest(damagedEntity, pickupMapper.get(rangedCombatStats.equippedShield).slot)));
                    }
                }
                else
                {
                    healthMapper.get(damagedEntity).currentHealth -= attack.damage;
                }
                healthMapper.get(damagedEntity).lastAttacker = attack.attacker;
                GameEventsLogger.getLogger().info(LogMessages.HERO_GOT_DAMAGE.toString());
                attack.hasHit = true;
                return true;
            }
        }

        return false;
    }

    private boolean executeMeleeAttack(Entity damagedEntity, MeleeAttack attack)
    {
        HostileKi hostileKi = hostileKiMapper.get(damagedEntity);
        PassiveKi passiveKi = passiveKiMapper.get(damagedEntity);
        Player player = playerMapper.get(damagedEntity);
        MeleeCombatStats meleeCombatStats = meleeCombatStatsMapper.get(damagedEntity);

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
                    healthMapper.get(damagedEntity).currentHealth -= attack.damage - meleeCombatStats.protection;

                if (meleeCombatStats.equippedShield != null)
                {
                    ShieldStats shieldStats = shieldMapper.get(meleeCombatStats.equippedShield);

                    shieldStats.usesLeft--;

                    if (shieldStats.usesLeft == 0)
                        engine.addEntity(new Entity().add(new ItemDestroyRequest(damagedEntity, pickupMapper.get(meleeCombatStats.equippedShield).slot)));
                }
            }
            else
            {
                healthMapper.get(damagedEntity).currentHealth -= attack.damage;
            }
            healthMapper.get(damagedEntity).lastAttacker = attack.attacker;
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

    private void damageMonster(PassiveKi passiveKi, HostileKi hostileKi, Entity damagedEntity, float damage, float protection, Entity attacker)
    {
        Health health = healthMapper.get(damagedEntity);

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

    private boolean checkCollision(float range, Position pos1, Position pos2)
    {
        return Math.abs(pos1.x - pos2.x) <= range && Math.abs(pos1.y - pos2.y) <= range;
    }

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
