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
import newgame.EntityMapper;

/** System used to handle item events
 * @author Maxim Fründt
 */
public class ItemSystem extends EntitySystem
{
    /** Entities that represent a pickup request */
    private ImmutableArray<Entity> pickupRequests;
    /** Entities that represent a drop request */
    private ImmutableArray<Entity> dropRequests;
    /** Entities that represent a use request */
    private ImmutableArray<Entity> useRequests;
    /** Entities that represent a destroy request */
    private ImmutableArray<Entity> destroyRequests;
    /** Entities that can be picked up */
    private ImmutableArray<Entity> pickupableEntities;

    /** Engine to which this system belongs to */
    private final Engine engine;
    /** Range in which pickups can be picked up */
    private static final float PICKUP_RANGE = 0.5f;

    /** Create new item system
     *
     * @param engine Engine to which this system belongs to
     */
    public ItemSystem(Engine engine)
    {
        this.engine = engine;
    }

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        pickupableEntities = engine.getEntitiesFor(Family.all(Pickup.class, Position.class).get());
        pickupRequests = engine.getEntitiesFor(Family.all(PickupRequest.class, Position.class).get());
        dropRequests = engine.getEntitiesFor(Family.all(DropRequest.class, Position.class).get());
        useRequests = engine.getEntitiesFor(Family.all(UseRequest.class).get());
        destroyRequests = engine.getEntitiesFor(Family.all(ItemDestroyRequest.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime)
    {
        for (int i = 0; i < pickupRequests.size(); i++)
        {
            Entity request = pickupRequests.get(i);

            for (int j = 0; j < pickupableEntities.size(); j++)
            {
                Entity pickup = pickupableEntities.get(j);

                if (checkContact(EntityMapper.positionMapper.get(request), EntityMapper.positionMapper.get(pickup)))
                {
                    addPickupToInventory(EntityMapper.pickupRequestMapper.get(request).requester, pickup);

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

    /** Destroy an item
     *
     * @param request Destroy request
     */
    private void destroy(Entity request)
    {
        ItemDestroyRequest destroyRequest = EntityMapper.destroyRequestMapper.get(request);
        removeItemAt(destroyRequest.owner, destroyRequest.slot);
    }

    /** Use an item
     *
     * @param request Use request
     */
    private void use(Entity request)
    {
        UseRequest useRequest = EntityMapper.useRequestMapper.get(request);
        Inventory inventory = EntityMapper.inventoryMapper.get(useRequest.requester);

        if (inventory == null)
            return;

        if (inventory.size <= useRequest.slot || inventory.usedSize == 0)
            return;

        Entity item = inventory.items.get(useRequest.slot);

        MeleeCombatStats meleeCombatStats = EntityMapper.meleeCombatStatsMapper.get(useRequest.requester);
        RangedCombatStats rangedCombatStats = EntityMapper.rangedCombatStatsMapper.get(useRequest.requester);
        MeleeWeaponStats meleeWeaponStats = EntityMapper.meleeWeaponStatsMapper.get(item);
        RangedWeaponStats rangedWeaponStats = EntityMapper.rangedWeaponStatsMapper.get(item);
        ShieldStats shieldStats = EntityMapper.shieldMapper.get(item);
        HealingPotionStats healingPotionStats = EntityMapper.healthPotionStatsMapper.get(item);

        // If the use request is for a melee weapon equip it
        if (meleeWeaponStats != null)
        {
            // If a weapon is already equipped, unequip that first
            if (rangedCombatStats.equippedWeapon != null)
            {
                EntityMapper.pickupMapper.get(rangedCombatStats.equippedWeapon).equipped = false;
                unequip(useRequest.requester, rangedCombatStats.equippedWeapon);
            }
            if (meleeCombatStats.equippedWeapon != null)
            {
                EntityMapper.pickupMapper.get(meleeCombatStats.equippedWeapon).equipped = false;
                unequip(useRequest.requester, meleeCombatStats.equippedWeapon);
            }

            EntityMapper.pickupMapper.get(inventory.items.get(useRequest.slot)).equipped = true;
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
            {
                EntityMapper.pickupMapper.get(rangedCombatStats.equippedWeapon).equipped = false;
                unequip(useRequest.requester, rangedCombatStats.equippedWeapon);
            }
            if (meleeCombatStats.equippedWeapon != null)
            {
                EntityMapper.pickupMapper.get(meleeCombatStats.equippedWeapon).equipped = false;
                unequip(useRequest.requester, meleeCombatStats.equippedWeapon);
            }

            EntityMapper.pickupMapper.get(inventory.items.get(useRequest.slot)).equipped = true;
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
            {
                EntityMapper.pickupMapper.get(meleeCombatStats.equippedShield).equipped = false;
                unequip(useRequest.requester, meleeCombatStats.equippedShield);
            }
            if (rangedCombatStats.equippedShield != null)
            {
                EntityMapper.pickupMapper.get(rangedCombatStats.equippedShield).equipped = false;
                unequip(useRequest.requester, rangedCombatStats.equippedShield);
            }

            EntityMapper.pickupMapper.get(inventory.items.get(useRequest.slot)).equipped = true;
            meleeCombatStats.equippedShield = item;
            rangedCombatStats.equippedShield = item;

            meleeCombatStats.protection += shieldStats.protection;
            rangedCombatStats.protection += shieldStats.protection;
        }

        // If the use request is for a health potion drink it
        if (healingPotionStats != null)
        {
            if (healingPotionStats.usesLeft <= 0)
                return;

            Health health = EntityMapper.healthMapper.get(useRequest.requester);

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

    /** Drop an item from the inventory
     *
     * @param request Drop request
     */
    private void dropPickupFromInventory(Entity request)
    {
        DropRequest dropRequest = EntityMapper.dropRequestMapper.get(request);
        Position position = EntityMapper.positionMapper.get(request);
        Inventory inventory = EntityMapper.inventoryMapper.get(dropRequest.requester);

        if (inventory == null)
            return;

        if (inventory.size <= dropRequest.slot || inventory.usedSize == 0)
            return;

        inventory.items.get(dropRequest.slot).add(new Position(position));
        EntityMapper.pickupMapper.get(inventory.items.get(dropRequest.slot)).slot = -1;

        removeItemAt(dropRequest.requester, dropRequest.slot);
    }

    /** Add a pickup to the inventory
     *
     * @param requester Inventory owner
     * @param pickup Pickup that will be added
     */
    private void addPickupToInventory(Entity requester, Entity pickup)
    {
        if (requester == null || pickup == null)
            return;

        Inventory inventory = EntityMapper.inventoryMapper.get(requester);

        if (inventory == null)
            return;

        if (inventory.size > inventory.usedSize)
        {
            for (int i = 0; i < inventory.size; i++)
            {
                if (inventory.items.get(i) == null)
                {
                    inventory.items.add(i, pickup);
                    EntityMapper.pickupMapper.get(pickup).slot = i;
                    break;
                }
            }

            inventory.usedSize++;
        }

        pickup.remove(Position.class);
    }

    /** Remove item from inventory at index
     *
     * @param inventoryOwner Owner of the inventory
     * @param slot Index
     */
    private void removeItemAt(Entity inventoryOwner, int slot)
    {
        Inventory inventory = EntityMapper.inventoryMapper.get(inventoryOwner);

        if (inventory == null)
            return;

        if (inventory.usedSize == 0)
            return;

        Entity destroyedItem = inventory.items.get(slot);
        unequip(inventoryOwner, destroyedItem);

        EntityMapper.pickupMapper.get(inventory.items.get(slot)).equipped = false;
        inventory.usedSize--;
        inventory.items.set(slot, null);
    }

    /** Unequip an item
     *
     * @param owner Owner of the item
     * @param equipment Equipment of the owner
     */
    private void unequip(Entity owner, Entity equipment)
    {
        MeleeCombatStats meleeCombatStats = EntityMapper.meleeCombatStatsMapper.get(owner);
        RangedCombatStats rangedCombatStats = EntityMapper.rangedCombatStatsMapper.get(owner);

        if (equipment == null)
            return;

        if (equipment == meleeCombatStats.equippedWeapon)
        {
            MeleeWeaponStats meleeWeaponStats = EntityMapper.meleeWeaponStatsMapper.get(equipment);

            meleeCombatStats.attackRange -= meleeWeaponStats.range;
            meleeCombatStats.damage -= meleeWeaponStats.damage;
            meleeCombatStats.knockbackDuration -= meleeWeaponStats.knockbackDuration;
            meleeCombatStats.knockbackSpeed -= meleeWeaponStats.knockbackSpeed;
            meleeCombatStats.attackCooldown -= meleeWeaponStats.cooldown;
            meleeCombatStats.equippedWeapon = null;
        }

        if (equipment == rangedCombatStats.equippedWeapon)
        {
            RangedWeaponStats rangedWeaponStats = EntityMapper.rangedWeaponStatsMapper.get(equipment);

            rangedCombatStats.attackSpeed -= rangedWeaponStats.attackSpeed;
            rangedCombatStats.damage -= rangedWeaponStats.damage;
            rangedCombatStats.attackRange -= rangedWeaponStats.range;
            rangedCombatStats.attackCooldown -= rangedWeaponStats.cooldown;
            rangedCombatStats.attackDuration -= rangedWeaponStats.attackDuration;
            rangedCombatStats.equippedWeapon = null;
        }

        if (equipment == meleeCombatStats.equippedShield)
        {
            ShieldStats shieldStats = EntityMapper.shieldMapper.get(equipment);

            meleeCombatStats.protection -= shieldStats.protection;
            meleeCombatStats.equippedShield = null;
        }

        if (equipment == rangedCombatStats.equippedShield)
        {
            ShieldStats shieldStats = EntityMapper.shieldMapper.get(equipment);

            rangedCombatStats.protection -= shieldStats.protection;
            rangedCombatStats.equippedShield = null;
        }
    }

    /** Check if the positions collide
     *
     * @param pos1 First position
     * @param pos2 Second position
     * @return True if the positions collide, else false
     */
    private boolean checkContact(Position pos1, Position pos2)
    {
        return Math.abs(pos1.x - pos2.x) <= PICKUP_RANGE && Math.abs(pos1.y - pos2.y) <= PICKUP_RANGE;
    }
}
