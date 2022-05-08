package newgame.inventory;

import java.util.ArrayList;

import newgame.items.*;
import newgame.items.weapons.Weapon;
import newgame.logger.GameEventsLogger;
import newgame.logger.InventoryLogVisitor;
import newgame.logger.LogMessages;

/**
 * Represents an general inventory with a list of items and initialized capacity.
 * 
 * @author Benjamin Krüger
 */
public abstract class Inventory
{
    
    protected ArrayList<Item> items;

    protected final int capacity;

    /**
     * Create a new inventory
     * 
     * @param maxNumberOfItems fix capacity of the inventory
     */
    public Inventory(int maxNumberOfItems) 
    {
        if (maxNumberOfItems <= 0)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            throw new IllegalArgumentException();
        }
        this.capacity = maxNumberOfItems;
        this.items = new ArrayList<>(maxNumberOfItems);
        
        // initialize inventory with null-objects, because no items inherits at the beginning
        for (int i = 0; i < this.capacity; i++)
        {
            this.items.add(null);
        }
    }

    /**
     * Get the fixed set capacity 
     * 
     * @return fixed set capacity of inventory
     */
    public int getCapacity()
    {
        return this.capacity;
    }

    /** Get the number of free inventory spaces
     *
     * @return Number of free inventory spaces
     */
    public int getRemainingCapacity()
    {
        int freeSpace = 0;

        for (Item item : items)
        {
            if (item == null)
            {
                freeSpace++;
            }
        }

        return freeSpace;
    }

    /**
     * Get all items of inventory.
     * 
     * @return items of the inventory
     */
    public ArrayList<Item> getAllItems()
	{
		return items;
	}

    /**
     * Get the item at given index.
     *
     * @param index Index of inventory.
     * @return Item at index. Null, if place is empty or index is out of range.
     */
    public Item getItemAt(final int index)
    {
        if (this.items == null) {
            GameEventsLogger.getLogger().severe(LogMessages.INVENTORY_NULL.toString());
            throw new IllegalAccessError(LogMessages.INVENTORY_NULL.toString());
        }
        if (this.items.isEmpty()) {
            return null;
        }
        if ((index < 0) || (index >= capacity)) {
            GameEventsLogger.getLogger().severe(LogMessages.ARGUMENT_OUT_OF_RANGE.toString());
            return null;
        }

        return this.items.get(index);
    }

    /**
     * Set inventory slot which toBeRemovedItems inherits to null.
     * 
     * @param toBeRemovedItem To be removed item.
     * @return Success of removing item from inventory. dropped = true, otherwise false.
     */
    public boolean removeItem(Item toBeRemovedItem)
    {
        if (toBeRemovedItem == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return false;
        }
        return this.emptyInventorySlot(this.items.indexOf(toBeRemovedItem));
    }

    /**
     * Set inventory slot to null (if index valid)
     * 
     * @param index to be dropped item
     * @return success of dropping item from inventory. dropped = true, otherwise
     *         false
     */
    public boolean emptyInventorySlot(int index)
    {
        if (this.items == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.INVENTORY_NULL.toString());
            throw new IllegalAccessError(LogMessages.INVENTORY_NULL.toString());
        }
        if (this.items.isEmpty())
        {
            GameEventsLogger.getLogger().severe(LogMessages.INVENTORY_ITEMS_ARRAY_IS_EMPTY.toString());
            return false;
        }
        if ((index < 0) || (index >= this.capacity))
        {
            GameEventsLogger.getLogger().severe(LogMessages.ARGUMENT_OUT_OF_RANGE.toString());
            return false;
        }

        this.items.set(index, null);
        GameEventsLogger.getLogger().fine(LogMessages.ITEM_DROPPED_FROM_INVENTORY.toString());
        return true;
    }

    /**
     * Add item to the inventory if it is not full, if its full, try to add it to a matching satchel
     * 
     * @param toBeAddedItem to be added item
     * @return success of adding item to inventory. added = true, otherwise false
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean addItem(Item toBeAddedItem)
    {
        if (this.items == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.INVENTORY_NULL.toString());
            throw new IllegalAccessError(LogMessages.INVENTORY_NULL.toString());
        }
        if (toBeAddedItem == null)
        {
            return false;
        }
        // first check, if satchel with suitable type is in inventory. if yes -> add item to satchel
        for(int i = 0; i < capacity; i++)
        {
            Item itemAtIndex = getItemAt(i);
            if(itemAtIndex instanceof Satchel)
            {
                Satchel satchel = (Satchel)itemAtIndex;
                ItemType satchelType = satchel.getSatchelType();
                ItemType typeOfToBeAddedItem = toBeAddedItem.getType();
                boolean isSatchelTypeSuitable = satchelType.equals(typeOfToBeAddedItem);

                if (isSatchelTypeSuitable)
                {
                    boolean itemSuccessfullyAdded = false;
                    switch (typeOfToBeAddedItem)
                    {
                        case WEAPON:
                            itemSuccessfullyAdded = satchel.addItem((Weapon) toBeAddedItem);
                            break;
                        case PROTECTION:
                            itemSuccessfullyAdded = satchel.addItem((Shield) toBeAddedItem);
                            break;
                        case POTION:
                            itemSuccessfullyAdded = satchel.addItem((Potion) toBeAddedItem);
                            break;
                        case SPELL:
                            itemSuccessfullyAdded = satchel.addItem((Spell) toBeAddedItem);
                            break;
                        default:
                            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
                    }
    
                    if(itemSuccessfullyAdded)
                    {
                        GameEventsLogger.getLogger().fine(LogMessages.ITEM_ADDED_TO_SATCHEL_IN_INVENTORY.toString());
                        return true;
                    }
                }
            }
        }
        
        int indexOfFreePlace = getIndexOfFreePlace();
        if (indexOfFreePlace == -1)
        {
            GameEventsLogger.getLogger().fine(LogMessages.INVENTORY_FULL.toString());
        	return false;
        }

        this.items.set(indexOfFreePlace, toBeAddedItem);
        GameEventsLogger.getLogger().fine(LogMessages.ITEM_ADDED_TO_INVENTORY.toString());
        return true;
    }

    protected abstract void log(InventoryLogVisitor inventoryLogVisitor);

    // find free place in inventory. RETURN -1 WHEN NO ONE FOUND
    private int getIndexOfFreePlace()
    {
        if (this.items == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.INVENTORY_NULL.toString());
            throw new IllegalAccessError(LogMessages.INVENTORY_NULL.toString());
        }

        for (int i = 0; i < this.capacity; i++)
        {
            if (items.get(i) == null) 
            {
                return i;
            }
        }

        return -1;
    }
}