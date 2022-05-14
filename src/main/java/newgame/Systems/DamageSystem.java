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
    private final ComponentMapper<PlayerControl> playerMapper = ComponentMapper.getFor(PlayerControl.class);

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
                    PlayerControl player = playerMapper.get(damagedEntity);

                    if (attack.receiver == MeleeAttack.Receiver.HOSTILE)
                    {
                        if (passiveKi != null)
                        {
                            damagedEntity.add(new HostileKi(0.1f, 1f, 1f, damagedEntity.getComponent(PassiveKi.class).target));
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
        switch (attack.attackDirection)
        {
            case UP:
                return Math.abs(entityPosition.x - attackPosition.x) <= attack.radius && entityPosition.y - attackPosition.y >= 0 && entityPosition.y - attackPosition.y <= attack.radius;

            case DOWN:
                return Math.abs(entityPosition.x - attackPosition.x) <= attack.radius && attackPosition.y - entityPosition.y <= 0 && attackPosition.y - entityPosition.y <= attack.radius;

            case RIGHT:
                return Math.abs(entityPosition.y - attackPosition.y) <= attack.radius && entityPosition.x - attackPosition.x >= 0 && entityPosition.x - attackPosition.x <= attack.radius;

            case LEFT:
                return Math.abs(entityPosition.y - attackPosition.y) <= attack.radius && attackPosition.x - entityPosition.x <= 0 && attackPosition.x - entityPosition.x <= attack.radius;
        }

        return false;
    }
}
