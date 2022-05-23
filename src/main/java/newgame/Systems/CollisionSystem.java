package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.Collisions;
import newgame.Components.Events.MeleeAttack;
import newgame.Components.Position;

public class CollisionSystem extends EntitySystem
{
    private ImmutableArray<Entity> collidableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<Collisions> collisionMapper = ComponentMapper.getFor(Collisions.class);
    private final ComponentMapper<MeleeAttack> attackMapper = ComponentMapper.getFor(MeleeAttack.class);

    @Override
    public void addedToEngine(Engine engine)
    {
        collidableEntities = engine.getEntitiesFor(Family.all(Position.class, Collisions.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < collidableEntities.size(); i++)
        {
            Entity entity = collidableEntities.get(i);
            Collisions collisions = collisionMapper.get(entity);
            Position position = positionMapper.get(entity);

            collisions.collisions.clear();

            for (int j = i + 1; j < collidableEntities.size(); j++)
            {
                Entity otherEntity = collidableEntities.get(i);
                Position otherPosition = positionMapper.get(otherEntity);

                if (Math.abs(position.x - otherPosition.x) < 2f && Math.abs(position.y - otherPosition.y) < 2f)
                {
                    collisions.collisions.add(otherEntity);
                }
            }
        }
    }
}
