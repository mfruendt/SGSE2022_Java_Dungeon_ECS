package newgame.gui;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.HUD;
import newgame.Components.Experience;
import newgame.Components.Health;
import newgame.inventory.Inventory;
import newgame.inventory.InventoryTypes;
import newgame.quests.Quest;

/** Handler for the game HUD
 * @author Maxim Fründt
 */
public class HudHandler
{
    private final HUD hud;

    private PlayerStatsHud statsHudElement;

    private InventoryHud invHudElement;

    private InventoryHud chestInvHudElement;

    /** Create new HUD handler
     *
     */
    public HudHandler(int playerInventorySize)
    {
        hud = new HUD();

        statsHudElement = new PlayerStatsHud(hud.getHudBatch());
        invHudElement = new InventoryHud(hud.getHudBatch(), 10, InventoryTypes.PLAYER_INVENTORY);
    }

    /** Update the HUD
     *
     */
    public void update(Health health, Experience experience, Quest quest)
    {
        hud.draw();

        if (statsHudElement != null)
        {
            statsHudElement.draw(health, experience, quest);

            if (invHudElement != null)
            {
                invHudElement.draw();
            }

            if (chestInvHudElement != null)
            {
                chestInvHudElement.draw();
            }
        }
    }

    public void updateInventory(newgame.Components.Inventory inventory)
    {
        if (inventory != null)
            invHudElement.setInventoryContent(inventory);
    }

    /** Display the inventory content of an inventory object
     *
     * @param inventory Inventory of the hero
     * @param inventoryType Type of the inventory
     */
    public <T extends Inventory> void displayInventory(T inventory, InventoryTypes inventoryType)
    {
        if (inventoryType == InventoryTypes.PLAYER_INVENTORY && invHudElement != null)
        {
            invHudElement.setInventoryContent(inventory);
        }
        else if (inventoryType == InventoryTypes.CHEST_INVENTORY && chestInvHudElement != null)
        {
            chestInvHudElement.setInventoryContent(inventory);
        }
    }

    /** Create a new chest view to display chest contents
     *
     * @param inventorySize Size of the chest
     * @return True if add was successful, else false
     */
    public boolean addChestView(int inventorySize)
    {
        if (inventorySize <= 0)
        {
            return false;
        }

        chestInvHudElement = new InventoryHud(hud.getHudBatch(), inventorySize, InventoryTypes.CHEST_INVENTORY);

        return true;
    }

    /** Remove currently open chest view
     *
     */
    public void removeChestView()
    {
        chestInvHudElement = null;
    }

    public InventoryHud getChestHudElement()
    {
        return this.chestInvHudElement;
    }
}
