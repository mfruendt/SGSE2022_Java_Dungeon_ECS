package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.*;
import newgame.Components.Events.MeleeAttack;
import newgame.Components.Events.RangedAttack;
import newgame.Components.Tags.HostileKi;
import newgame.Components.Tags.PassiveKi;
import newgame.Components.Tags.Player;
import newgame.EntityMapper;
import newgame.textures.WeaponTextures;

import java.util.concurrent.ThreadLocalRandom;

public class KiSystem extends EntitySystem
{
    /** Entities that possess a passive KI */
    private ImmutableArray<Entity> passiveEntities;
    /** Entities that possess a hostile KI */
    private ImmutableArray<Entity> hostileEntities;
    /** Player entities */
    private ImmutableArray<Entity> playerEntities;

    /** Engine to which this system belongs to */
    private Engine engine;

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        this.engine = engine;
        passiveEntities = engine.getEntitiesFor(Family.all(PassiveKi.class, Velocity.class, Position.class, MeleeCombatStats.class).get());
        hostileEntities = engine.getEntitiesFor(Family.all(HostileKi.class, Velocity.class, Position.class, MeleeCombatStats.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(Player.class, Position.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < passiveEntities.size(); i++)
        {
            calcRandomMovement(passiveEntities.get(i));
        }

        for (int i = 0; i < hostileEntities.size(); i++)
        {
            calcMovementToTarget(hostileEntities.get(i));
            checkForAttackContact(hostileEntities.get(i));
        }
    }

    /** Check if the input entity is close enough to its target to attack
     *
     * @param entity Entity which tries to attack
     */
    private void checkForAttackContact(Entity entity)
    {
        RangedCombatStats rangedCombatStats = EntityMapper.rangedCombatStatsMapper.get(entity);
        MeleeCombatStats meleeCombatStats = EntityMapper.meleeCombatStatsMapper.get(entity);
        Position position = EntityMapper.positionMapper.get(entity);

        for (int i = 0; i < playerEntities.size(); i++)
        {
            Position targetPosition = EntityMapper.positionMapper.get(playerEntities.get(i));

            if (meleeCombatStats.framesSinceLastAttack > 0)
            {
                meleeCombatStats.framesSinceLastAttack--;
            }
            else
            {
                if (Math.abs(targetPosition.x - position.x) <= meleeCombatStats.attackRange && Math.abs(targetPosition.y - position.y) <= meleeCombatStats.attackRange)
                {
                    if (Math.abs(targetPosition.x - position.x) > Math.abs(targetPosition.y - position.y))
                    {
                        if (position.x > targetPosition.x)
                            engine.addEntity(new Entity().add(new MeleeAttack(meleeCombatStats.damage, MeleeAttack.AttackDirection.LEFT, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                        else
                            engine.addEntity(new Entity().add(new MeleeAttack(meleeCombatStats.damage, MeleeAttack.AttackDirection.RIGHT, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                    }
                    else
                    {
                        if (position.y > targetPosition.y)
                            engine.addEntity(new Entity().add(new MeleeAttack(meleeCombatStats.damage, MeleeAttack.AttackDirection.DOWN, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                        else
                            engine.addEntity(new Entity().add(new MeleeAttack(meleeCombatStats.damage, MeleeAttack.AttackDirection.UP, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                    }

                    meleeCombatStats.framesSinceLastAttack = meleeCombatStats.attackCooldown;
                }
                else if (rangedCombatStats != null)
                {
                    if (Math.abs(targetPosition.x - position.x) <= rangedCombatStats.attackRange && Math.abs(targetPosition.y - position.y) <= rangedCombatStats.attackRange)
                    {
                        float distanceX = Math.abs(targetPosition.x - position.x);
                        float distanceY = Math.abs(targetPosition.y - position.y);

                        engine.addEntity(new Entity().add(new RangedAttack(meleeCombatStats.damage, rangedCombatStats.attackDuration, meleeCombatStats.attackRange, RangedAttack.Receiver.PLAYER, entity))
                                .add(new Position(position))
                                .add(new Sprite(WeaponTextures.MONSTER_BALL.getTexture()))
                                .add(new Velocity(rangedCombatStats.attackSpeed * (targetPosition.x - position.x) / (distanceX + distanceY), rangedCombatStats.attackSpeed * (targetPosition.y - position.y) / (distanceX + distanceY))));

                        meleeCombatStats.framesSinceLastAttack = rangedCombatStats.attackCooldown;
                    }
                }
            }
        }
    }

    /** Calculate the movement to the target of the entity
     *
     * @param entity Entity that should be moved
     */
    private void calcMovementToTarget(Entity entity)
    {
        Velocity velocity = EntityMapper.velocityMapper.get(entity);
        HostileKi ki = EntityMapper.hostileKiMapper.get(entity);
        Position position = EntityMapper.positionMapper.get(entity);

        boolean canMoveInX = false;
        boolean canMoveInY = false;
        float newX;
        float newY;

        // Check if we could move in direction x
        if (position.x < ki.target.x && position.level.isTileAccessible(new Point(position.x + ki.speed, position.y))
                || position.x > ki.target.x && position.level.isTileAccessible(new Point(position.x - ki.speed, position.y)))
        {
            canMoveInX = true;
        }

        // Check if we could move in direction x
        if (position.y < ki.target.y && position.level.isTileAccessible(new Point(position.x, position.y + ki.speed))
                || position.y > ki.target.y && position.level.isTileAccessible(new Point(position.x, position.y - ki.speed)))
        {
            canMoveInY = true;
        }

        if (canMoveInX && canMoveInY)
        {
            if (position.x < ki.target.x)
            {
                newX = ki.speed / 1.414f;
            }
            else
            {
                newX = -ki.speed / 1.414f;
            }

            if (position.y < ki.target.y)
            {
                newY = ki.speed / 1.414f;
            }
            else
            {
                newY = -ki.speed / 1.414f;
            }

            velocity.x = newX;
            velocity.y = newY;
        }
        else if (canMoveInX)
        {
            if (position.x < ki.target.x)
            {
                newX = ki.speed;
            }
            else
            {
                newX = -ki.speed;
            }

            velocity.x = newX;
            velocity.y = 0;
        }
        else if (canMoveInY)
        {
            if (position.y < ki.target.y)
            {
                newY = ki.speed;
            }
            else
            {
                newY = -ki.speed;
            }

            velocity.x = 0;
            velocity.y = newY;
        }
    }

    /** Calculate random movement for the entity
     *
     * @param entity Entity that should be moved
     */
    private void calcRandomMovement(Entity entity)
    {
        Velocity velocity = EntityMapper.velocityMapper.get(entity);
        PassiveKi ki = EntityMapper.passiveKiMapper.get(entity);
        Position position = EntityMapper.positionMapper.get(entity);

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

    /** Check if the position is within the bounds of the level
     *
     * @param level Level of the entity
     * @param newPos Position that the entity wants to go to
     * @return True if the position is valid, else false
     */
    private boolean checkNewPos(DungeonWorld level, Point newPos)
    {
        return level.isTileAccessible(newPos);
    }
}
