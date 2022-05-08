package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.EasyMonsterKi;
import newgame.Components.Health;
import newgame.Components.Position;
import newgame.Components.Velocity;

public class HealthSystem extends EntitySystem
{
    private ImmutableArray<Entity> killableEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private Engine engine;

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

            if (health.currentHealth <= 0f)
            {
                engine.removeEntity(entity);
            }
        }
    }
}
