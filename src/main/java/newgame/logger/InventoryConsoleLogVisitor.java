package newgame.logger;

import java.util.ArrayList;

import newgame.inventory.HeroInventory;
import newgame.items.Chest;
import newgame.items.Item;
import newgame.items.ItemType;
import newgame.items.Satchel;


/**
 * Class to log all items of inventories on console
 * 
 * @author Benjamin Krüger
 */
public class InventoryConsoleLogVisitor implements InventoryLogVisitor
{
    public <T extends Item> void log(Chest<T> chest)
    {
        if (chest == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return;
        }
        String itemsLog = LogMessages.CHEST_ITEMS + "\n";
        itemsLog += this.getItemsAsList(chest.getAllItems());
        GameEventsLogger.getLogger().info(itemsLog);
    }

    public void log(HeroInventory heroInventory)
    {
        if (heroInventory == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return;
        }
        String itemsLog = LogMessages.HERO_INVENTORY_ITEMS + "\n";
        itemsLog += this.getItemsAsList(heroInventory.getAllItems());
        GameEventsLogger.getLogger().info(itemsLog);
    }

    public <T extends Item> void log(Satchel<T> satchel)
    {
        if (satchel == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
            return;
        }
        String itemsLog = ItemType.SATCHEL.toString();
        itemsLog += "(" + satchel.getSatchelType() + ")[" + satchel.getNumberOfItems() + "] items:\n";
        itemsLog += this.getItemsAsList(satchel.getAllItems());
        GameEventsLogger.getLogger().info(itemsLog);
    }

    /*
     * Get String that represents items like
     * 0 -> Weapon
     * 1 -> Shield
     * 2 -> Satchel(Weapon)[0]
     * ...
     */
    private <T extends Item> String getItemsAsList(ArrayList<T> items)
    {
        StringBuilder itemList = new StringBuilder();

        if (items == null)
        {
            GameEventsLogger.getLogger().severe(LogMessages.ILLEGAL_ARGUMENT.toString());
        }
        else
        {
            for(int i = 0; i < items.size(); i ++)
            {
                // log index + 1 because it is the number which user should input to do something with the items
                itemList.append(i).append(" -> ");
    
                Item item = items.get(i);
                if (item != null)
                {
                    itemList.append(item.getClass().getSimpleName());
                    if (item instanceof Satchel)
                    {
                        Satchel<?> satchel = (Satchel<?>)item;
                        itemList.append("(").append(satchel.getSatchelType()).append(")[").append(satchel.getNumberOfItems()).append("]");
                    }
                }   
                else
                {
                    itemList.append("---");
                }
                itemList.append(" \n");
            }
        }
        return itemList.toString();
    }
}