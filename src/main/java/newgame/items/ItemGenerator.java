package newgame.items;

import java.util.concurrent.ThreadLocalRandom;

import newgame.items.weapons.Weapon;
import newgame.items.weapons.meleeweapons.RegularSword;
import newgame.items.weapons.rangedweapons.Bow;
import newgame.items.weapons.rangedweapons.Staff;

/**
 * Generates a random item
 * @author Dominik Haacke
 *
 */
public class ItemGenerator 
{
	public static Item generateItem()
	{
		int typeOfItem = ThreadLocalRandom.current().nextInt(0, 5);
		switch (typeOfItem) 
		{
			case 0: 
			{
				int weaponType = ThreadLocalRandom.current().nextInt(0, 2);
				switch (weaponType)
				{
					case 0:
						return new RegularSword();
					case 1:
						return new Bow(true, 0.4f);
					case 2:
						return new Staff(true, 0.4f);
				}
				
			}
			case 1: 
			{
				float defense = ThreadLocalRandom.current().nextFloat() * 5;
				float durability = ThreadLocalRandom.current().nextFloat() * 5 + 0.1f;
				return new Shield(defense, durability);
			}
			case 2: 
			{
				return new Potion();
			}
			case 3: 
			{
				return new Spell("TestSpell");
			}
			case 4: 
			{
				int satchelType = ThreadLocalRandom.current().nextInt(0, 3);
				int capacity = ThreadLocalRandom.current().nextInt(1, 6);
				switch (satchelType)
				{
					case 0:
					{
						return new Satchel<Weapon>(capacity, ItemType.WEAPON);
					}
					case 1:
					{
						return new Satchel<Shield>(capacity, ItemType.PROTECTION);
					}
					case 2:
					{
						return new Satchel<Potion>(capacity, ItemType.POTION);
					}
					case 3:
					{
						return new Satchel<Spell>(capacity, ItemType.SPELL);
					}
				}
				break;
			}
		}
		return null;
	}
}
