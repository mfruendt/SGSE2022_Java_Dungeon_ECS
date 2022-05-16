package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Factories.MonsterFactory;
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

                if (checkMeleeCollision(attack, positionMapper.get(attackEntity), positionMapper.get(damagedEntity)))
                {
                    if (executeMeleeAttack(damagedEntity, attack))
                        break;
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

                if (checkCollision(attack.bulletRange, positionMapper.get(attackEntity), positionMapper.get(damagedEntity)))
                {
                    if (executeRangedAttack(damagedEntity, attack))
                        break;
                }
            }

            attack.attackDurationLeft--;

            if (attack.attackDurationLeft <= 0 || attack.hasHit)
                engine.removeEntity(attackEntity);
        }
    }

    private boolean executeRangedAttack(Entity damagedEntity, RangedAttack attack)
    {
        HostileKi hostileKi = hostileKiMapper.get(damagedEntity);
        PassiveKi passiveKi = passiveKiMapper.get(damagedEntity);
        Player player = playerMapper.get(damagedEntity);

        if (attack.receiver == RangedAttack.Receiver.HOSTILE)
        {
            damageMonster(passiveKi, hostileKi, damagedEntity, attack.damage, attack.attacker);
            attack.hasHit = true;
            return true;
        }
        else if (attack.receiver == RangedAttack.Receiver.PLAYER)
        {
            if (player != null)
            {
                healthMapper.get(damagedEntity).currentHealth -= attack.damage;
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

        if (attack.receiver == MeleeAttack.Receiver.HOSTILE)
        {
            damageMonster(passiveKi, hostileKi, damagedEntity, attack.damage, attack.attacker);
            return true;
        }
        else if (attack.receiver == MeleeAttack.Receiver.PLAYER)
        {
            if (player != null)
            {
                healthMapper.get(damagedEntity).currentHealth -= attack.damage;
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
        }

        return false;
    }

    private void damageMonster(PassiveKi passiveKi, HostileKi hostileKi, Entity damagedEntity, float damage, Entity attacker)
    {
        if (passiveKi != null)
        {
            damagedEntity.add(new HostileKi(passiveKi.speed,
                    MonsterFactory.EASY_MONSTER_DAMAGE,
                    MonsterFactory.EASY_MONSTER_RANGE,
                    MonsterFactory.EASY_MONSTER_KNOCKBACK_SPEED,
                    MonsterFactory.EASY_MONSTER_KNOCKBACK_DURATION,
                    passiveKi.target));
            damagedEntity.remove(PassiveKi.class);
            healthMapper.get(damagedEntity).currentHealth -= damage;
            healthMapper.get(damagedEntity).lastAttacker = attacker;
        }
        else if (hostileKi != null)
        {
            healthMapper.get(damagedEntity).currentHealth -= damage;
            healthMapper.get(damagedEntity).lastAttacker = attacker;
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
