package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import newgame.Components.MeleeAttack;
import newgame.Components.PlayerControl;
import newgame.Components.Position;
import newgame.Components.Velocity;

public class PlayerControlSystem extends EntitySystem
{
    private ImmutableArray<Entity> controllableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<Velocity> velocityMapper = ComponentMapper.getFor(Velocity.class);
    private final ComponentMapper<PlayerControl> playerControlMapper = ComponentMapper.getFor(PlayerControl.class);

    private Engine engine;

    public PlayerControlSystem(Engine engine)
    {
        this.engine = engine;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        controllableEntities = engine.getEntitiesFor(Family.all(PlayerControl.class, Velocity.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < controllableEntities.size(); i++)
        {
            move(controllableEntities.get(i));
            attack(controllableEntities.get(i));
        }
    }

    private void move(Entity entity)
    {
        Velocity velocity = velocityMapper.get(entity);
        PlayerControl playerControl = playerControlMapper.get(entity);

        if (Gdx.input.isKeyPressed(PlayerControl.walkForwardKey))
        {
            velocity.y = playerControl.speed;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.walkBackwardKey))
        {
            velocity.y = -playerControl.speed;
        }
        else
        {
            velocity.y = 0;
        }

        if (Gdx.input.isKeyPressed(PlayerControl.walkRightKey))
        {
            velocity.x = playerControl.speed;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.walkLeftKey))
        {
            velocity.x = -playerControl.speed;
        }
        else
        {
            velocity.x = 0;
        }
    }

    private void attack(Entity entity)
    {
        Position position = positionMapper.get(entity);

        if (Gdx.input.isKeyPressed(PlayerControl.attackForwardKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.UP, 2f, MeleeAttack.Receiver.HOSTILE)).add(new Position(position)));
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackBackwardKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.DOWN, 2f, MeleeAttack.Receiver.HOSTILE)).add(new Position(position)));
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackRightKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.RIGHT, 2f, MeleeAttack.Receiver.HOSTILE)).add(new Position(position)));
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackLeftKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.LEFT, 2f, MeleeAttack.Receiver.HOSTILE)).add(new Position(position)));
        }
    }
}
