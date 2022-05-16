package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.Inventory;
import newgame.Components.Position;
import newgame.Components.Sprite;
import newgame.Components.Tags.Pickup;
import newgame.Components.PickupRequest;

public class PickupSystem extends EntitySystem
{
    private ImmutableArray<Entity> pickupRequests;
    private ImmutableArray<Entity> pickupableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<PickupRequest> requestMapper = ComponentMapper.getFor(PickupRequest.class);
    private final ComponentMapper<Inventory> inventoryMapper = ComponentMapper.getFor(Inventory.class);

    private final Engine engine;
    private static final float PICKUP_RANGE = 0.5f;

    public PickupSystem(Engine engine)
    {
        this.engine = engine;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        pickupableEntities = engine.getEntitiesFor(Family.all(Pickup.class, Position.class).get());
        pickupRequests = engine.getEntitiesFor(Family.all(PickupRequest.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < pickupRequests.size(); i++)
        {
            Entity request = pickupRequests.get(i);

            for (int j = 0; j < pickupableEntities.size(); j++)
            {
                Entity pickup = pickupableEntities.get(i);

                if (checkContact(positionMapper.get(request), positionMapper.get(pickup)))
                {
                    addPickupToInventory(requestMapper.get(request).requester, pickup);

                    break;
                }
            }

            engine.removeEntity(request);
        }
    }

    private void addPickupToInventory(Entity requester, Entity pickup)
    {
        if (requester == null || pickup == null)
            return;

        Inventory inventory = inventoryMapper.get(requester);

        if (inventory == null)
            return;

        if (inventory.size > inventory.usedSize)
        {
            for (int i = 0; i < inventory.size; i++)
            {
                if (inventory.items.get(i) == null)
                {
                    inventory.items.add(i, pickup);
                    break;
                }
            }

            inventory.usedSize++;
        }

        pickup.remove(Position.class);
        pickup.remove(Sprite.class);
    }

    private boolean checkContact(Position pos1, Position pos2)
    {
        return Math.abs(pos1.x - pos2.x) <= PICKUP_RANGE && Math.abs(pos1.y - pos2.y) <= PICKUP_RANGE;
    }
}
