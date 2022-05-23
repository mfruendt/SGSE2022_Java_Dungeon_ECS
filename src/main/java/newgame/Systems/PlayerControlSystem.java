package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import newgame.Components.*;
import newgame.Components.Events.*;
import newgame.EntityMapper;
import newgame.textures.WeaponTextures;

public class PlayerControlSystem extends EntitySystem
{
    private ImmutableArray<Entity> controllableEntities;

    private final Engine engine;

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
            Entity controllableEntity = controllableEntities.get(i);

            move(controllableEntity);
            if (EntityMapper.meleeCombatStatsMapper.get(controllableEntity) != null)
                meleeAttack(controllableEntity);
            if (EntityMapper.rangedCombatStatsMapper.get(controllableEntity) != null)
                rangedAttack(controllableEntity);
            handleItemInput(controllableEntity);
        }
    }

    private void handleItemInput(Entity entity)
    {
        if (Gdx.input.isKeyPressed(PlayerControl.pickupKey))
        {
            engine.addEntity(new Entity().add(new PickupRequest(entity)).add(new Position(EntityMapper.positionMapper.get(entity))));
        }

        if (Gdx.input.isKeyPressed(PlayerControl.dropKey))
        {
            int invSlot = getInvSlotInput();

            if (invSlot != -1)
            {
                engine.addEntity(new Entity().add(new DropRequest(entity, invSlot)).add(new Position(EntityMapper.positionMapper.get(entity))));
            }
        }

        if (Gdx.input.isKeyPressed(PlayerControl.useKey))
        {
            int invSlot = getInvSlotInput();

            if (invSlot != -1)
            {
                engine.addEntity(new Entity().add(new UseRequest(entity, invSlot)));
            }
        }
    }

    private int getInvSlotInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
        {
            return 0;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
        {
            return 1;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3))
        {
            return 2;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_4))
        {
            return 3;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_5))
        {
            return 4;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_6))
        {
            return 5;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_7))
        {
            return 6;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_8))
        {
            return 7;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_9))
        {
            return 8;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_0))
        {
            return 9;
        }

        return -1;
    }

    private void move(Entity entity)
    {
        Velocity velocity = EntityMapper.velocityMapper.get(entity);
        PlayerControl playerControl = EntityMapper.playerControlMapper.get(entity);

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

    private void meleeAttack(Entity entity)
    {
        Position position = EntityMapper.positionMapper.get(entity);
        MeleeCombatStats meleeCombatStats = EntityMapper.meleeCombatStatsMapper.get(entity);

        if (meleeCombatStats == null)
            return;

        if (meleeCombatStats.damage == 0)
            return;

        if (meleeCombatStats.framesSinceLastAttack != 0)
        {
            meleeCombatStats.framesSinceLastAttack--;
            return;
        }


        if (Gdx.input.isKeyPressed(PlayerControl.attackForwardKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.UP, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed, MeleeAttack.Receiver.HOSTILE, entity)).add(new Position(position)));
            meleeCombatStats.framesSinceLastAttack = meleeCombatStats.attackCooldown;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackBackwardKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.DOWN, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed,  MeleeAttack.Receiver.HOSTILE, entity)).add(new Position(position)));
            meleeCombatStats.framesSinceLastAttack = meleeCombatStats.attackCooldown;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackRightKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.RIGHT, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed,  MeleeAttack.Receiver.HOSTILE, entity)).add(new Position(position)));
            meleeCombatStats.framesSinceLastAttack = meleeCombatStats.attackCooldown;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackLeftKey))
        {
            engine.addEntity(new Entity().add(new MeleeAttack(1f, MeleeAttack.AttackDirection.LEFT, meleeCombatStats.attackRange, meleeCombatStats.knockbackDuration, meleeCombatStats.knockbackSpeed,  MeleeAttack.Receiver.HOSTILE, entity)).add(new Position(position)));
            meleeCombatStats.framesSinceLastAttack = meleeCombatStats.attackCooldown;
        }
    }

    private void rangedAttack(Entity entity)
    {
        Position position = EntityMapper.positionMapper.get(entity);
        RangedCombatStats rangedCombatStats = EntityMapper.rangedCombatStatsMapper.get(entity);

        if (rangedCombatStats == null)
            return;

        if (rangedCombatStats.damage == 0)
            return;

        if (rangedCombatStats.framesSinceLastAttack != 0)
        {
            rangedCombatStats.framesSinceLastAttack--;
            return;
        }

        if (Gdx.input.isKeyPressed(PlayerControl.attackForwardKey))
        {
            engine.addEntity(new Entity().add(new RangedAttack(1f, rangedCombatStats.attackDuration, rangedCombatStats.attackRange, RangedAttack.Receiver.HOSTILE, entity))
                    .add(new Position(position))
                    .add(new Velocity(0, rangedCombatStats.attackSpeed))
                    .add(new Sprite(WeaponTextures.STAFF_BALL.getTexture())));
            rangedCombatStats.framesSinceLastAttack = rangedCombatStats.attackCooldown;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackBackwardKey))
        {
            engine.addEntity(new Entity().add(new RangedAttack(1f, rangedCombatStats.attackDuration, rangedCombatStats.attackRange, RangedAttack.Receiver.HOSTILE, entity))
                    .add(new Position(position))
                    .add(new Velocity(0, -rangedCombatStats.attackSpeed))
                    .add(new Sprite(WeaponTextures.STAFF_BALL.getTexture())));
            rangedCombatStats.framesSinceLastAttack = rangedCombatStats.attackCooldown;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackRightKey))
        {
            engine.addEntity(new Entity().add(new RangedAttack(1f, rangedCombatStats.attackDuration, rangedCombatStats.attackRange, RangedAttack.Receiver.HOSTILE, entity))
                    .add(new Position(position))
                    .add(new Velocity(rangedCombatStats.attackSpeed, 0))
                    .add(new Sprite(WeaponTextures.STAFF_BALL.getTexture())));
            rangedCombatStats.framesSinceLastAttack = rangedCombatStats.attackCooldown;
        }
        else if (Gdx.input.isKeyPressed(PlayerControl.attackLeftKey))
        {
            engine.addEntity(new Entity().add(new RangedAttack(1f, rangedCombatStats.attackDuration, rangedCombatStats.attackRange, RangedAttack.Receiver.HOSTILE, entity))
                    .add(new Position(position))
                    .add(new Velocity(-rangedCombatStats.attackSpeed, 0))
                    .add(new Sprite(WeaponTextures.STAFF_BALL.getTexture())));
            rangedCombatStats.framesSinceLastAttack = rangedCombatStats.attackCooldown;
        }
    }
}
