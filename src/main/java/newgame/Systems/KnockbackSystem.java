package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Components.Events.Knockback;
import newgame.Components.Tags.Player;
import newgame.EntityMapper;

/** System used to knock back entities
 * @author Maxim Fründt
 */
public class KnockbackSystem extends EntitySystem
{
    /** Entities that can be knocked back */
    private ImmutableArray<Entity> knockbackedEntities;

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        knockbackedEntities = engine.getEntitiesFor(Family.all(Velocity.class, Knockback.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
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
