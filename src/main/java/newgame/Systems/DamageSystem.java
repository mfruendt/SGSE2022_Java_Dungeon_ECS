package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.Collisions;
import newgame.Components.Health;
import newgame.Components.MeleeAttack;
import newgame.Components.Position;

public class DamageSystem extends EntitySystem
{
    private ImmutableArray<Entity> damageableEntities;
    private ImmutableArray<Entity> meleeAttackEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<MeleeAttack> meleeAttackMapper = ComponentMapper.getFor(MeleeAttack.class);

    private Engine engine;

    public DamageSystem(Engine engine)
    {
        this.engine = engine;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        damageableEntities = engine.getEntitiesFor(Family.all(Health.class, Position.class).get());
        meleeAttackEntities = engine.getEntitiesFor(Family.all(MeleeAttack.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < meleeAttackEntities.size(); i++)
        {
            Entity attackEntity = meleeAttackEntities.get(i);
            MeleeAttack attack = meleeAttackMapper.get(attackEntity);

            for (int j = 0; j < damageableEntities.size(); j++)
            {
                Entity damagedEntity = damageableEntities.get(j);

                if (checkCollision(attack, positionMapper.get(attackEntity), positionMapper.get(damagedEntity)))
                    healthMapper.get(damagedEntity).currentHealth -= attack.damage;
            }

            engine.removeEntity(attackEntity);
        }
    }

    private boolean checkCollision(MeleeAttack attack, Position attackPosition, Position entityPosition)
    {
        switch (attack.attackDirection)
        {
            case UP:
                return Math.abs(entityPosition.x - attackPosition.x) <= attack.radius && entityPosition.y - attackPosition.y >= 0 && entityPosition.y - attackPosition.y <= attack.radius;

            case DOWN:
                return Math.abs(entityPosition.x - attackPosition.x) <= attack.radius && attackPosition.y - entityPosition.y >= 0 && attackPosition.y - entityPosition.y <= attack.radius;

            case RIGHT:
                return Math.abs(entityPosition.y - attackPosition.y) <= attack.radius && entityPosition.x - attackPosition.x >= 0 && entityPosition.x - attackPosition.x <= attack.radius;

            case LEFT:
                return Math.abs(entityPosition.y - attackPosition.y) <= attack.radius && attackPosition.x - entityPosition.x >= 0 && attackPosition.x - entityPosition.x <= attack.radius;
        }

        return false;
    }
}
