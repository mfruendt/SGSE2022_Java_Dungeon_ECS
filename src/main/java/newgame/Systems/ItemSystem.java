package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.*;
import newgame.Components.Events.*;
import newgame.Components.Items.HealingPotionStats;
import newgame.Components.Items.RangedWeaponStats;
import newgame.Components.Items.ShieldStats;
import newgame.Components.Items.MeleeWeaponStats;
import newgame.Components.Tags.Pickup;

public class ItemSystem extends EntitySystem
{
    private ImmutableArray<Entity> pickupRequests;
    private ImmutableArray<Entity> dropRequests;
    private ImmutableArray<Entity> useRequests;
    private ImmutableArray<Entity> destroyRequests;
    private ImmutableArray<Entity> pickupableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
    private final ComponentMapper<Pickup> pickupMapper = ComponentMapper.getFor(Pickup.class);
    private final ComponentMapper<PickupRequest> pickupRequestMapper = ComponentMapper.getFor(PickupRequest.class);
    private final ComponentMapper<DropRequest> dropRequestMapper = ComponentMapper.getFor(DropRequest.class);
    private final ComponentMapper<UseRequest> useRequestMapper = ComponentMapper.getFor(UseRequest.class);
    private final ComponentMapper<ItemDestroyRequest> destroyRequestMapper = ComponentMapper.getFor(ItemDestroyRequest.class);
    private final ComponentMapper<Inventory> inventoryMapper = ComponentMapper.getFor(Inventory.class);
    private final ComponentMapper<MeleeWeaponStats> meleeWeaponStatsMapper = ComponentMapper.getFor(MeleeWeaponStats.class);
    private final ComponentMapper<RangedWeaponStats> rangedWeaponStatsMapper = ComponentMapper.getFor(RangedWeaponStats.class);
    private final ComponentMapper<ShieldStats> shieldStatsMapper = ComponentMapper.getFor(ShieldStats.class);
    private final ComponentMapper<HealingPotionStats> healthPotionStatsMapper = ComponentMapper.getFor(HealingPotionStats.class);
    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<RangedCombatStats> rangedCombatStatsMapper = ComponentMapper.getFor(RangedCombatStats.class);
    private final ComponentMapper<MeleeCombatStats> meleeCombatStatsMapper = ComponentMapper.getFor(MeleeCombatStats.class);

    private final Engine engine;
    private static final float PICKUP_RANGE = 0.5f;

    public ItemSystem(Engine engine)
    {
        this.engine = engine;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        pickupableEntities = engine.getEntitiesFor(Family.all(Pickup.class, Position.class).get());
        pickupRequests = engine.getEntitiesFor(Family.all(PickupRequest.class, Position.class).get());
        dropRequests = engine.getEntitiesFor(Family.all(DropRequest.class, Position.class).get());
        useRequests = engine.getEntitiesFor(Family.all(UseRequest.class).get());
        destroyRequests = engine.getEntitiesFor(Family.all(ItemDestroyRequest.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < pickupRequests.size(); i++)
        {
            Entity request = pickupRequests.get(i);

            for (int j = 0; j < pickupableEntities.size(); j++)
            {
                Entity pickup = pickupableEntities.get(j);

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

        for (int i = 0; i < useRequests.size(); i++)
        {
            Entity request = useRequests.get(i);

            use(request);

            engine.removeEntity(request);
        }

        for (int i = 0; i < destroyRequests.size(); i++)
        {
            Entity request = destroyRequests.get(i);

            destroy(request);

            engine.removeEntity(request);
        }
    }

    private void destroy(Entity request)
    {
        ItemDestroyRequest destroyRequest = destroyRequestMapper.get(request);
        removeItemAt(destroyRequest.owner, destroyRequest.slot);
    }

    private void use(Entity request)
    {
        UseRequest useRequest = useRequestMapper.get(request);
        Inventory inventory = inventoryMapper.get(useRequest.requester);

        if (inventory == null)
            return;

        if (inventory.size <= useRequest.slot || inventory.usedSize == 0)
            return;

        Entity item = inventory.items.get(useRequest.slot);

        MeleeCombatStats meleeCombatStats = meleeCombatStatsMapper.get(useRequest.requester);
        RangedCombatStats rangedCombatStats = rangedCombatStatsMapper.get(useRequest.requester);
        MeleeWeaponStats meleeWeaponStats = meleeWeaponStatsMapper.get(item);
        RangedWeaponStats rangedWeaponStats = rangedWeaponStatsMapper.get(item);
        ShieldStats shieldStats = shieldStatsMapper.get(item);
        HealingPotionStats healingPotionStats = healthPotionStatsMapper.get(item);

        // If the use request is for a melee weapon equip it
        if (meleeWeaponStats != null)
        {
            // If a weapon is already equipped, unequip that first
            if (meleeCombatStats.equippedWeapon != null)
                unequip(useRequest.requester, meleeCombatStats.equippedWeapon);
            if (rangedCombatStats.equippedWeapon != null)
                unequip(useRequest.requester, rangedCombatStats.equippedWeapon);

            pickupMapper.get(inventory.items.get(useRequest.slot)).equipped = true;
            meleeCombatStats.equippedWeapon = item;

            meleeCombatStats.attackCooldown += meleeWeaponStats.cooldown;
            meleeCombatStats.damage += meleeWeaponStats.damage;
            meleeCombatStats.attackRange += meleeWeaponStats.range;
            meleeCombatStats.knockbackSpeed += meleeWeaponStats.knockbackSpeed;
            meleeCombatStats.knockbackDuration += meleeWeaponStats.knockbackDuration;
        }

        // If the use request is for a ranged weapon equip it
        if (rangedWeaponStats != null)
        {
            // If a weapon is already equipped, unequip that first
            if (rangedCombatStats.equippedWeapon != null)
                unequip(useRequest.requester, rangedCombatStats.equippedWeapon);
            if (meleeCombatStats.equippedWeapon != null)
                unequip(useRequest.requester, meleeCombatStats.equippedWeapon);

            pickupMapper.get(inventory.items.get(useRequest.slot)).equipped = true;
            rangedCombatStats.equippedWeapon = item;

            rangedCombatStats.attackCooldown += rangedWeaponStats.cooldown;
            rangedCombatStats.damage += rangedWeaponStats.damage;
            rangedCombatStats.attackRange += rangedWeaponStats.range;
            rangedCombatStats.attackSpeed += rangedWeaponStats.attackSpeed;
            rangedCombatStats.attackDuration += rangedWeaponStats.attackDuration;
        }

        // If the use request is for a shield equip it
        if (shieldStats != null)
        {
            // If a weapon is already equipped, unequip that first
            if (meleeCombatStats.equippedShield != null)
                unequip(useRequest.requester, meleeCombatStats.equippedShield);
            if (rangedCombatStats.equippedShield != null)
                unequip(useRequest.requester, rangedCombatStats.equippedShield);

            pickupMapper.get(inventory.items.get(useRequest.slot)).equipped = true;
            meleeCombatStats.equippedShield = item;
            rangedCombatStats.equippedShield = item;

            meleeCombatStats.protection += shieldStats.protection;
            rangedCombatStats.protection += shieldStats.protection;
        }

        // If the use request is for a health potion drink it
        if (healingPotionStats != null)
        {
            Health health = healthMapper.get(useRequest.requester);

            if (health != null)
            {
                if (health.currentHealth + healingPotionStats.healthRestored >= health.maxHealth)
                {
                    health.currentHealth = health.maxHealth;
                }
                else
                {
                    health.currentHealth += healingPotionStats.healthRestored;
                }

                healingPotionStats.usesLeft--;

                if (healingPotionStats.usesLeft == 0)
                {
                    engine.addEntity(new Entity().add(new ItemDestroyRequest(useRequest.requester, useRequest.slot)));
                }
            }
        }
    }

    private void dropPickupFromInventory(Entity request)
    {
        DropRequest dropRequest = dropRequestMapper.get(request);
        Position position = positionMapper.get(request);
        Inventory inventory = inventoryMapper.get(dropRequest.requester);

        if (inventory == null)
            return;

        if (inventory.size <= dropRequest.slot || inventory.usedSize == 0)
            return;

        inventory.items.get(dropRequest.slot).add(new Position(position));
        pickupMapper.get(inventory.items.get(dropRequest.slot)).slot = -1;

        removeItemAt(dropRequest.requester, dropRequest.slot);
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
                    pickupMapper.get(pickup).slot = i;
                    break;
                }
            }

            inventory.usedSize++;
        }

        pickup.remove(Position.class);
    }

    private void removeItemAt(Entity inventoryOwner, int slot)
    {
        Inventory inventory = inventoryMapper.get(inventoryOwner);

        if (inventory == null)
            return;

        if (inventory.usedSize == 0)
            return;

        Entity destroyedItem = inventory.items.get(slot);
        unequip(inventoryOwner, destroyedItem);

        pickupMapper.get(inventory.items.get(slot)).equipped = false;
        inventory.usedSize--;
        inventory.items.set(slot, null);
    }

    private void unequip(Entity owner, Entity equipment)
    {
        MeleeCombatStats meleeCombatStats = meleeCombatStatsMapper.get(owner);
        RangedCombatStats rangedCombatStats = rangedCombatStatsMapper.get(owner);

        if (equipment == null)
            return;

        if (equipment == meleeCombatStats.equippedWeapon)
        {
            MeleeWeaponStats meleeWeaponStats = meleeWeaponStatsMapper.get(equipment);

            meleeCombatStats.attackRange -= meleeWeaponStats.range;
            meleeCombatStats.damage -= meleeWeaponStats.damage;
            meleeCombatStats.knockbackDuration -= meleeWeaponStats.knockbackDuration;
            meleeCombatStats.knockbackSpeed -= meleeWeaponStats.knockbackSpeed;
            meleeCombatStats.attackCooldown -= meleeWeaponStats.cooldown;
            meleeCombatStats.equippedWeapon = null;
        }

        if (equipment == rangedCombatStats.equippedWeapon)
        {
            RangedWeaponStats rangedWeaponStats = rangedWeaponStatsMapper.get(equipment);

            rangedCombatStats.attackSpeed -= rangedWeaponStats.attackSpeed;
            rangedCombatStats.damage -= rangedWeaponStats.damage;
            rangedCombatStats.attackRange -= rangedWeaponStats.range;
            rangedCombatStats.attackCooldown -= rangedWeaponStats.cooldown;
            rangedCombatStats.attackDuration -= rangedWeaponStats.attackDuration;
            rangedCombatStats.equippedWeapon = null;
        }

        if (equipment == meleeCombatStats.equippedShield)
        {
            ShieldStats shieldStats = shieldStatsMapper.get(equipment);

            meleeCombatStats.protection -= shieldStats.protection;
            meleeCombatStats.equippedShield = null;
        }

        if (equipment == rangedCombatStats.equippedShield)
        {
            ShieldStats shieldStats = shieldStatsMapper.get(equipment);

            rangedCombatStats.protection -= shieldStats.protection;
            rangedCombatStats.equippedShield = null;
        }
    }

    private boolean checkContact(Position pos1, Position pos2)
    {
        return Math.abs(pos1.x - pos2.x) <= PICKUP_RANGE && Math.abs(pos1.y - pos2.y) <= PICKUP_RANGE;
    }
}
