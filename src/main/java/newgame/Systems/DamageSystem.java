package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.Collisions;
import newgame.Components.Health;
import newgame.Components.Position;

public class DamageSystem extends EntitySystem
{
    private ImmutableArray<Entity> damageableEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<Collisions> collisionMapper = ComponentMapper.getFor(Collisions.class);

    @Override
    public void addedToEngine(Engine engine)
    {
        damageableEntities = engine.getEntitiesFor(Family.all(Health.class, Collisions.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < damageableEntities.size(); i++)
        {
            Entity entity = damageableEntities.get(i);
            Health health = healthMapper.get(entity);
            Collisions collisions = collisionMapper.get(entity);

            if (collisions.collisions.size() != 0)
            {
                health.currentHealth -= 5f;
            }
        }
    }
}
