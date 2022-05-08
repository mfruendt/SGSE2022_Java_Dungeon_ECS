package newgame.items;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;
import newgame.textures.ItemTextures;

/**
 * Spell that can be used by the Hero
 * @author Dominik Haacke
 *
 */
public class Spell extends Item
{
	private final String spell;
	
	/**
	 * Creates a Chest with a maximum capacity of items, every item takes 1 space.
	 *
	 * @param spell Predefined constant spell.
	 */
	public Spell(final String spell) 
	{
		super(ItemTextures.FLASK_BIG_BLUE.getTexture());
		if (spell == null || spell.isEmpty())
		{
			GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
			throw new IllegalArgumentException();
		}
		this.spell = spell;
	}

	/**
	 * Get item type of the object.
	 *
	 * @return Will always return item type SPELL.
	 */
	@Override
	public ItemType getType()
	{
		return ItemType.SPELL;
	}

	/**
	 * Get spell ob the object.
	 *
	 * @return Spell of the object.
	 */
	public String read()
	{
		return this.spell;
	}
}
