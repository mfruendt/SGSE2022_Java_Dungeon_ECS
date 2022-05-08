package newgame.items;

/** Enumeration of available item types in the dungeon
 * @author Maxim Fründt
 */
public enum ItemType 
{
	WEAPON("Weapon"),
	PROTECTION("Shield"), // shield, helmet, etc
	POTION("Potion"),
	SPELL("Spell"),
	SATCHEL("Satchel");

	private final String itemName;

	ItemType(String itemName)
	{
		this.itemName = itemName;
	}

	/** Get string name of the item type
	 *
	 * @return Item type name
	 */
	public String toString()
	{
		return itemName;
	}
}
