package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

public class DamageSystem extends EntitySystem
{
    private ImmutableArray<Entity> damageableEntities;
    private ImmutableArray<Entity> meleeAttackEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<MeleeAttack> meleeAttackMapper = ComponentMapper.getFor(MeleeAttack.class);
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

                if (checkCollision(attack, positionMapper.get(attackEntity), positionMapper.get(damagedEntity)))
                {
                    HostileKi hostileKi = hostileKiMapper.get(damagedEntity);
                    PassiveKi passiveKi = passiveKiMapper.get(damagedEntity);
                    Player player = playerMapper.get(damagedEntity);

                    if (attack.receiver == MeleeAttack.Receiver.HOSTILE)
                    {
                        if (passiveKi != null)
                        {
                            damagedEntity.add(new HostileKi(0.1f, 1f, 1f, 0.5f, 8, damagedEntity.getComponent(PassiveKi.class).target));
                            damagedEntity.remove(PassiveKi.class);
                            healthMapper.get(damagedEntity).currentHealth -= attack.damage;
                            break;
                        }
                        else if (hostileKi != null)
                        {
                            healthMapper.get(damagedEntity).currentHealth -= attack.damage;
                            break;
                        }
                    }
                    else if (attack.receiver == MeleeAttack.Receiver.PLAYER)
                    {
                        if (player != null)
                        {
                            healthMapper.get(damagedEntity).currentHealth -= attack.damage;
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

                            break;
                        }
                    }
                }
            }

            engine.removeEntity(attackEntity);
        }
    }

    private boolean checkCollision(MeleeAttack attack, Position attackPosition, Position entityPosition)
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
