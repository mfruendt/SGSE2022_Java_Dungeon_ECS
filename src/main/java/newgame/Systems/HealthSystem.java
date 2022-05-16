package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Interpolation;
import newgame.Components.*;
import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

public class HealthSystem extends EntitySystem
{
    private ImmutableArray<Entity> killableEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<Experience> experienceMapper = ComponentMapper.getFor(Experience.class);
    private final ComponentMapper<Player> playerMapper = ComponentMapper.getFor(Player.class);

    private final Engine engine;

    public HealthSystem(Engine engine)
    {
        this.engine = engine;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        killableEntities = engine.getEntitiesFor(Family.all(Health.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < killableEntities.size(); i++)
        {
            Entity entity = killableEntities.get(i);
            Health health = healthMapper.get(entity);
            Experience experience = experienceMapper.get(entity);

            if (health.currentHealth <= 0f)
            {
                if (playerMapper.get(entity) == null)
                {
                    GameEventsLogger.getLogger().info(LogMessages.MONSTER_KILLED.toString());
                    experienceMapper.get(health.lastAttacker).experience += experience.experience;
                }
                engine.removeEntity(entity);
            }
        }
    }
}
