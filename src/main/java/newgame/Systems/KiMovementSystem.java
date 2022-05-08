package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.EasyMonsterKi;
import newgame.Components.Position;
import newgame.Components.Velocity;

import java.util.concurrent.ThreadLocalRandom;

public class KiMovementSystem extends EntitySystem
{
    private ImmutableArray<Entity> easyMonsterEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<Velocity> velocityMapper = ComponentMapper.getFor(Velocity.class);
    private final ComponentMapper<EasyMonsterKi> easyKiMapper = ComponentMapper.getFor(EasyMonsterKi.class);

    @Override
    public void addedToEngine(Engine engine)
    {
        easyMonsterEntities = engine.getEntitiesFor(Family.all(EasyMonsterKi.class, Velocity.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < easyMonsterEntities.size(); i++)
        {
            calcRandomMovement(easyMonsterEntities.get(i));
        }
    }

    private void calcRandomMovement(Entity entity)
    {
        Velocity velocity = velocityMapper.get(entity);
        EasyMonsterKi ki = easyKiMapper.get(entity);
        Position position = positionMapper.get(entity);

        boolean hasMoved = false;

        // First we generate a random number
        int randomNum = ThreadLocalRandom.current().nextInt(0, 100);

        // If the number is between 40 and 100 (=60% probability) the monster will walk in the same direction as before (right if it stood still)
        if (randomNum >= 40)
        {
            if (velocity.x == 0 && velocity.y == 0)
            {
                if (randomNum >= 85)
                {
                    if (checkNewPos(position.level, new Point(position.x + ki.speed, position.y)))
                    {
                        velocity.x = ki.speed;
                        hasMoved = true;
                    }
                }
                else if (randomNum >= 70)
                {
                    if (checkNewPos(position.level, new Point(position.x - ki.speed, position.y)))
                    {
                        velocity.x = -ki.speed;
                        hasMoved = true;
                    }
                }
                else if (randomNum >= 55)
                {
                    if (checkNewPos(position.level, new Point(position.x, position.y + ki.speed)))
                    {
                        velocity.y = ki.speed;
                        hasMoved = true;
                    }
                }
                else
                {
                    if (checkNewPos(position.level, new Point(position.x, position.y - ki.speed)))
                    {
                        velocity.y = -ki.speed;
                        hasMoved = true;
                    }
                }
            }
            else
            {
                hasMoved = true;
            }
        }

        // If the number is between 30 and 39 (=10% probability) or the monster was unable to move, walk to the
        // left (from the perspective of the monster)
        if ((randomNum >= 30 && randomNum < 39) || !hasMoved)
        {
            // Right
            if (velocity.x > 0)
            {
                if (checkNewPos(position.level, new Point(position.x, position.y + ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = ki.speed;
                    hasMoved = true;
                }
            }
            // Left
            else if (velocity.x < 0)
            {
                if (checkNewPos(position.level, new Point(position.x, position.y - ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = -ki.speed;
                    hasMoved = true;
                }
            }
            // Up
            else if (velocity.y > 0)
            {
                if (checkNewPos(position.level, new Point(position.x - ki.speed, position.y)))
                {
                    velocity.x = -ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
            // Down
            else if (velocity.y < 0)
            {
                if (checkNewPos(position.level, new Point(position.x + ki.speed, position.y)))
                {
                    velocity.x = ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
            else
            {
                if (checkNewPos(position.level, new Point(position.x + ki.speed, position.y)))
                {
                    velocity.x = ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
        }

        // If the number is between 20 and 29 (=10% probability) or the monster was unable to move, walk to the
        // right (from the perspective of the monster)
        if ((randomNum >= 20 && randomNum < 30) || !hasMoved)
        {
            // Right
            if (velocity.x > 0)
            {
                if (checkNewPos(position.level, new Point(position.x, position.y - ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = -ki.speed;
                    hasMoved = true;
                }
            }
            // Left
            else if (velocity.x < 0)
            {
                if (checkNewPos(position.level, new Point(position.x, position.y + ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = ki.speed;
                    hasMoved = true;
                }
            }
            // Up
            else if (velocity.y > 0)
            {
                if (checkNewPos(position.level, new Point(position.x + ki.speed, position.y)))
                {
                    velocity.x = ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
            // Down
            else if (velocity.y < 0)
            {
                if (checkNewPos(position.level, new Point(position.x - ki.speed, position.y)))
                {
                    velocity.x = -ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
            else
            {
                if (checkNewPos(position.level, new Point(position.x - ki.speed, position.y)))
                {
                    velocity.x = -ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
        }

        // If the number is between 15 and 19 (=5% probability) or the monster was unable to move, walk to the
        // opposite direction
        if ((randomNum >= 15 && randomNum < 20) || !hasMoved)
        {
            // Right
            if (velocity.x > 0)
            {
                if (checkNewPos(position.level, new Point(position.x - ki.speed, position.y)))
                {
                    velocity.x =  - ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
            // Left
            else if (velocity.x < 0)
            {
                if (checkNewPos(position.level, new Point(position.x + ki.speed, position.y)))
                {
                    velocity.x = ki.speed;
                    velocity.y = 0;
                    hasMoved = true;
                }
            }
            // Up
            else if (velocity.y > 0)
            {
                if (checkNewPos(position.level, new Point(position.x, position.y - ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = -ki.speed;
                    hasMoved = true;
                }
            }
            // Down
            else if (velocity.y < 0)
            {
                if (checkNewPos(position.level, new Point(position.x, position.y + ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = ki.speed;
                    hasMoved = true;
                }
            }
            else
            {
                if (checkNewPos(position.level, new Point(position.x, position.y + ki.speed)))
                {
                    velocity.x = 0;
                    velocity.y = ki.speed;
                    hasMoved = true;
                }
            }
        }

        // If the number is between 0 and 14 (=15% probability) or the monster was unable to move, then idle
        if ((randomNum >= 0 && randomNum < 14) || !hasMoved)
        {
            velocity.x = velocity.y = 0;
        }
    }

    private boolean checkNewPos(DungeonWorld level, Point newPos)
    {
        return level.isTileAccessible(newPos);
    }
}
