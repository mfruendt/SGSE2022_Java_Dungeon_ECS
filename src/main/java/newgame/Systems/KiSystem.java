package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.*;
import newgame.textures.WeaponTextures;

import java.util.concurrent.ThreadLocalRandom;

public class KiSystem extends EntitySystem
{
    private ImmutableArray<Entity> passiveEntities;
    private ImmutableArray<Entity> hostileEntities;
    private ImmutableArray<Entity> playerEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<Velocity> velocityMapper = ComponentMapper.getFor(Velocity.class);
    private final ComponentMapper<PassiveKi> easyKiMapper = ComponentMapper.getFor(PassiveKi.class);
    private final ComponentMapper<HostileKi> hardKiMapper = ComponentMapper.getFor(HostileKi.class);
    private final ComponentMapper<BossKi> bossKiMapper = ComponentMapper.getFor(BossKi.class);

    private Engine engine;

    @Override
    public void addedToEngine(Engine engine)
    {
        this.engine = engine;
        passiveEntities = engine.getEntitiesFor(Family.all(PassiveKi.class, Velocity.class, Position.class).get());
        hostileEntities = engine.getEntitiesFor(Family.all(HostileKi.class, Velocity.class, Position.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(Player.class, Position.class, Health.class).get());
    }

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

    private void checkForAttackContact(Entity entity)
    {
        HostileKi ki = hardKiMapper.get(entity);
        BossKi bossKi = bossKiMapper.get(entity);
        Position position = positionMapper.get(entity);

        for (int i = 0; i < playerEntities.size(); i++)
        {
            Position targetPosition = positionMapper.get(playerEntities.get(i));

            if (ki.framesSinceLastAttack > 0)
            {
                ki.framesSinceLastAttack--;
            }
            else
            {
                if (Math.abs(targetPosition.x - position.x) <= ki.attackRange && Math.abs(targetPosition.y - position.y) <= ki.attackRange)
                {
                    if (Math.abs(targetPosition.x - position.x) > Math.abs(targetPosition.y - position.y))
                    {
                        if (position.x > targetPosition.x)
                            engine.addEntity(new Entity().add(new MeleeAttack(ki.damage, MeleeAttack.AttackDirection.LEFT, ki.attackRange, ki.knockbackDuration, ki.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                        else
                            engine.addEntity(new Entity().add(new MeleeAttack(ki.damage, MeleeAttack.AttackDirection.RIGHT, ki.attackRange, ki.knockbackDuration, ki.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                    }
                    else
                    {
                        if (position.y > targetPosition.y)
                            engine.addEntity(new Entity().add(new MeleeAttack(ki.damage, MeleeAttack.AttackDirection.DOWN, ki.attackRange, ki.knockbackDuration, ki.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                        else
                            engine.addEntity(new Entity().add(new MeleeAttack(ki.damage, MeleeAttack.AttackDirection.UP, ki.attackRange, ki.knockbackDuration, ki.knockbackSpeed, MeleeAttack.Receiver.PLAYER, entity)).add(new Position(position)));
                    }

                    ki.framesSinceLastAttack = HostileKi.ATTACK_COOLDOWN;
                }
                else if (bossKi != null)
                {
                    if (Math.abs(targetPosition.x - position.x) <= bossKi.rangedTargetRange && Math.abs(targetPosition.y - position.y) <= bossKi.rangedTargetRange)
                    {
                        float distanceX = Math.abs(targetPosition.x - position.x);
                        float distanceY = Math.abs(targetPosition.y - position.y);

                        engine.addEntity(new Entity().add(new RangedAttack(ki.damage, bossKi.rangedAttackDuration, ki.attackRange, RangedAttack.Receiver.PLAYER, entity)).
                                add(new Position(position)).
                                add(new Sprite(WeaponTextures.MONSTER_BALL.getTexture())).
                                add(new Velocity(bossKi.rangedAttackSpeed * (targetPosition.x - position.x) / (distanceX + distanceY), bossKi.rangedAttackSpeed * (targetPosition.y - position.y) / (distanceX + distanceY))));

                        ki.framesSinceLastAttack = BossKi.RANGED_ATTACK_COOLDOWN;
                    }
                }
            }
        }
    }

    private void calcMovementToTarget(Entity entity)
    {
        Velocity velocity = velocityMapper.get(entity);
        HostileKi ki = hardKiMapper.get(entity);
        Position position = positionMapper.get(entity);

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

    private void calcRandomMovement(Entity entity)
    {
        Velocity velocity = velocityMapper.get(entity);
        PassiveKi ki = easyKiMapper.get(entity);
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
