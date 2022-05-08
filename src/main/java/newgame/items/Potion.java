package newgame.items;

import newgame.textures.ItemTextures;

/**
 * 
 * @author Dominik Haacke
 *
 */
public class Potion extends Item
{
	private static final int HEALTH_RESTORE = 20;

	public Potion() 
	{
		super(ItemTextures.FLASK_BIG_GREEN.getTexture());
	}
	
	@Override
	public ItemType getType()
	{
		return ItemType.POTION;
	}

	/**
	 * Used to replenish life of hero
	 * 
	 * @return value of health, which can be used to replenish life
	 */
	public int drink()
	{
		return HEALTH_RESTORE;
	}
}
