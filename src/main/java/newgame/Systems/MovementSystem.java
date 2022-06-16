package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Position;
import newgame.Components.Velocity;
import newgame.EntityMapper;

/** System used to move entities
 * @author Maxim Fründt
 */
public class MovementSystem extends EntitySystem
{
    /** Entities that can be moved */
    private ImmutableArray<Entity> movableEntities;

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        movableEntities = engine.getEntitiesFor(Family.all(Velocity.class, Position.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < movableEntities.size(); i++)
        {
            move(movableEntities.get(i));
        }
    }

    /** Move the entity
     *
     * @param entity Entity that will be moved
     */
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
