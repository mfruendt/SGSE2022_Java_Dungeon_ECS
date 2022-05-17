package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Components.Tags.Pickup;

public class PickupSystem extends EntitySystem
{
    private ImmutableArray<Entity> pickupRequests;
    private ImmutableArray<Entity> dropRequests;
    private ImmutableArray<Entity> pickupableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<PickupRequest> pickupRequestMapper = ComponentMapper.getFor(PickupRequest.class);
    private final ComponentMapper<DropRequest> dropRequestMapper = ComponentMapper.getFor(DropRequest.class);
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
        dropRequests = engine.getEntitiesFor(Family.all(DropRequest.class, Position.class).get());
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
                    addPickupToInventory(pickupRequestMapper.get(request).requester, pickup);

                    break;
                }
            }

            engine.removeEntity(request);
        }

        for (int i = 0; i < dropRequests.size(); i++)
        {
            Entity request = dropRequests.get(i);

            dropPickupFromInventory(request);

            engine.removeEntity(request);
        }
    }

    private void dropPickupFromInventory(Entity request)
    {
        DropRequest dropRequest = dropRequestMapper.get(request);
        Position position = positionMapper.get(request);
        Inventory inventory = inventoryMapper.get(dropRequest.requester);

        if (inventory == null)
            return;

        if (inventory.size <= dropRequest.index || inventory.usedSize == 0)
            return;

        inventory.items.get(dropRequest.index).add(new Position(position));

        inventory.usedSize--;
        inventory.items.set(dropRequest.index, null);
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
    }

    private boolean checkContact(Position pos1, Position pos2)
    {
        return Math.abs(pos1.x - pos2.x) <= PICKUP_RANGE && Math.abs(pos1.y - pos2.y) <= PICKUP_RANGE;
    }
}
