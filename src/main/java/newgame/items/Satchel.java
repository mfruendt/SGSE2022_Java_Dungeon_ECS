package newgame.items;

import java.util.ArrayList;

import newgame.logger.GameEventsLogger;
import newgame.logger.InventoryConsoleLogVisitor;
import newgame.logger.LogMessages;
import newgame.textures.ItemTextures;

/**
 * A Satchel that can hole one specific item type and enlarges the inventory
 * @author Dominik Haacke
 *
 * @param <T> Specific item type
 */
public class Satchel<T extends Item> extends Item
{
	private final ArrayList<T> items;
	
	private final int capacity;
	
	private final ItemType satchelType;
	
	/**
	 * Creates an empty satchel with a ItemType
	 * @param capacity max items
	 * @param satchelType ItemType
	 */
	public Satchel(final int capacity, final ItemType satchelType)
	{
		super(ItemTextures.SATCHEL.getTexture());
		if (capacity < 1)
		{
			GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		this.satchelType = satchelType;
		this.items = new ArrayList<>();
	}
	
	/**
	 * Add an item to the satchel, if it still fits and the type is correct
	 * @param toBeAddedItem to be added
	 * @return boolean if the item was added successful
	 */
	public boolean addItem(T toBeAddedItem)
	{
		if(toBeAddedItem == null)
		{
			GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
			return false;
		}
		if(toBeAddedItem.getType() != satchelType)
		{
			return false;
		}
		if(capacity > items.size())
		{
			items.add(toBeAddedItem);
			return true;
		}
		return false;
	}
	
	/**
	 * Return the item at an specific index, if it exists
	 *
	 * @param index Index of satchel.
	 * @return Item at Index.
	 */
	public T getItemAt(final int index)
	{
		try
		{
			return items.get(index);
		}
		catch (IndexOutOfBoundsException e) 
		{
			return null;
		}
	}
	
	/**
	 * Removes the item at an specific index
	 *
	 * @param index Index of satchel at which item should be removed.
	 */
	public boolean removeItemAt(final int index)
	{
		try
		{
			items.remove(index);
			return true;
		}
		catch (IndexOutOfBoundsException e) 
		{
			return false;
		}
	}
	
	/**
	 * Returns the ItemType of this satchel
	 * @return ItemType
	 */
	public ItemType getSatchelType()
	{
		return satchelType;
	}
	
	/**
	 * Returns the Type of this item, in this case Satchel
	 */
	@Override
	public ItemType getType()
	{
		return ItemType.SATCHEL;
	}

	/**
	 * Log all items from satchel.
	 * 
	 * @param inventoryConsoleLogVisitor To be used visitor.
	 */
	public void log(InventoryConsoleLogVisitor inventoryConsoleLogVisitor)
	{
		inventoryConsoleLogVisitor.log(this);
	}

	/**
	 * Get number of items in satchel
	 * 
	 * @return number of items in satchel
	 */
	public int getNumberOfItems()
	{
		return this.items.size();
	}

	/**
	 * Get all items in Satchel
	 * 
	 * @return List of all items in satchel
	 */
	public ArrayList<T> getAllItems()
	{
		return items;
	}
}
