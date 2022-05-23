package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Components.Events.Knockback;
import newgame.Components.Tags.Player;
import newgame.EntityMapper;

public class KnockbackSystem extends EntitySystem
{
    private ImmutableArray<Entity> knockbackedEntities;

    @Override
    public void addedToEngine(Engine engine)
    {
        knockbackedEntities = engine.getEntitiesFor(Family.all(Velocity.class, Knockback.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < knockbackedEntities.size(); i++)
        {
            Entity entity = knockbackedEntities.get(i);

            Knockback knockback = EntityMapper.knockbackMapper.get(entity);
            Velocity velocity = EntityMapper.velocityMapper.get(entity);

            if (knockback.knockbackFrames > 0)
            {
                knockback.knockbackFrames--;

                switch (knockback.direction)
                {
                    case UP:
                        velocity.y = knockback.speed;
                        velocity.x = 0;
                    break;

                    case DOWN:
                        velocity.y = -knockback.speed;
                        velocity.x = 0;
                    break;

                    case RIGHT:
                        velocity.x = knockback.speed;
                        velocity.y = 0;
                    break;

                    case LEFT:
                        velocity.x = -knockback.speed;
                        velocity.y = 0;
                    break;
                }
            }

            if (knockback.knockbackFrames == 0)
            {
                if (EntityMapper.playerMapper.get(entity) != null)
                    entity.add(new PlayerControl(0.2f));

                entity.remove(Knockback.class);
            }
        }
    }
}
