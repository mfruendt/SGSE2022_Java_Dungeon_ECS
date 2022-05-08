package newgame.logger;

import newgame.inventory.HeroInventory;
import newgame.items.Chest;
import newgame.items.Item;
import newgame.items.Satchel;

/**
 * Interface pattern for logging inventory items
 * 
 * @author Benjamin Krüger
 */
public interface InventoryLogVisitor 
{
    <T extends Item> void log(Chest<T> chest);
    void log(HeroInventory heroInventory);
    <T extends Item> void log(Satchel<T> satchel);
}
