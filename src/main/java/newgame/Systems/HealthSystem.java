package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Components.Tags.Player;
import newgame.EntityMapper;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** System used to kill dead entities
 * @author Maxim Fründt
 */
public class HealthSystem extends EntitySystem
{
    /** Entities that can be killed */
    private ImmutableArray<Entity> killableEntities;

    /** Engine to which this system belongs to */
    private final Engine engine;

    /** Create new health system
     *
     * @param engine Engine to which this system belongs to
     */
    public HealthSystem(Engine engine)
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
        killableEntities = engine.getEntitiesFor(Family.all(Health.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < killableEntities.size(); i++)
        {
            Entity entity = killableEntities.get(i);
            Health health = EntityMapper.healthMapper.get(entity);
            Experience experience = EntityMapper.experienceMapper.get(entity);

            if (health.currentHealth <= 0f)
            {
                if (EntityMapper.playerMapper.get(entity) == null)
                {
                    GameEventsLogger.getLogger().info(LogMessages.MONSTER_KILLED.toString());
                    EntityMapper.experienceMapper.get(health.lastAttacker).experience += experience.experience;
                }
                engine.removeEntity(entity);
            }
        }
    }
}
