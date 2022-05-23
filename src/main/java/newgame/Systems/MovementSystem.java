package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Position;
import newgame.Components.Velocity;
import newgame.EntityMapper;

public class MovementSystem extends EntitySystem
{
    private ImmutableArray<Entity> movableEntities;

    @Override
    public void addedToEngine(Engine engine)
    {
        movableEntities = engine.getEntitiesFor(Family.all(Velocity.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < movableEntities.size(); i++)
        {
            move(movableEntities.get(i));
        }
    }

    private void move(Entity entity)
    {
        Velocity velocity = EntityMapper.velocityMapper.get(entity);
        Position position = EntityMapper.positionMapper.get(entity);

        if (position.level.isTileAccessible(new Point(position.x, position.y + velocity.y)))
        {
            position.y += velocity.y;
        }

        if (position.level.isTileAccessible(new Point(position.x + velocity.x, position.y)))
        {
            position.x += velocity.x;
        }
    }
}
