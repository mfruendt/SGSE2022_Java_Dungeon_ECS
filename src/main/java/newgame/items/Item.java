package newgame.items;

import com.badlogic.gdx.graphics.Texture;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/**
 * Base Class for Items that are in the inventory
 * @author Dominik Haacke
 *
 */
public abstract class Item 
{
	/**
	 * HUD Texture
	 */
	protected Texture texture;
	
	/**
	 * Creates a new Inventory Item with the Texture
	 * @param texture The texture of the item, which will be displayed.
	 */
	public Item(final Texture texture) 
	{
		if (texture == null)
		{
			GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
			throw new IllegalArgumentException();
		}
		this.texture = texture;
	}

	public Texture getTexture()
	{
		return this.texture;
	}
	
	/**
	 * Returns the ItemType of this item.
	 * @return ItemType
	 */
	public ItemType getType()
	{
		return null;
	}
}
