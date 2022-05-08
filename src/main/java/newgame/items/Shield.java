package newgame.items;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.textures.ItemTextures;

/**
 * Shield to increase heroes defense and protect against damage.
 * 
 * @author Benjamin Krüger
 */
public class Shield extends Item
{
    private final float defense;
	private float durability;

	/**
	 * Instantiate new shield with given defense and durability.
	 *
	 * @param defense Value of to be set defense.
	 * @param durability Value of to be set start durability.
	 */
	public Shield(final float defense, final float durability)
	{
		super (ItemTextures.SHIELD.getTexture());
		if (defense < 0 || durability < 0)
		{
			GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
			throw new IllegalArgumentException();
		}
		this.defense = defense;
		this.durability = durability;
	}

	/**
	 * Get the value of shields defense.
	 *
	 * @return Value of defense.
	 */
	public float getDefense() 
	{
		return defense;
	}

	/**
	 * Get the current durability of the shield.
	 *
	 * @return Current durability.
	 */
	public float getDurability()
	{
		return this.durability;
	}

	/**
	 * Reduce the current durability.
	 *
	 * @param durability Value of durability reduction.
	 */
	public void reduceDurability(final float durability)
	{
		if (durability > 0)
		{
			this.durability -= durability;
		}
	}

	/**
	 * Get item type of the shield.
	 *
	 * @return Will always return item type PROTECTION.
	 */
	@Override
	public ItemType getType()
	{
		return ItemType.PROTECTION;
	}
}