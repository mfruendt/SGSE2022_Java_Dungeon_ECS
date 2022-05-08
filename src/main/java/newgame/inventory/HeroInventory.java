package newgame.inventory;

import newgame.logger.InventoryLogVisitor;

/**
 * Represents the heroes inventory, which extends general inventory.
 *
 * @author Benjamin Krüger
 */
public class HeroInventory extends Inventory
{
    private static final int MAX_NUMBER_OF_ITEMS = 10;

    /**
     * Create new object of hero inventory with fix number of items (10).
     */
    public HeroInventory()
    {
        super(MAX_NUMBER_OF_ITEMS);
    }

    /**
     * Log all items on console.
     * 
     * @param inventoryLogVisitor To be used visitor.
     */
    public void log(InventoryLogVisitor inventoryLogVisitor)
    {
        inventoryLogVisitor.log(this);
    }
}
