package newgame.logger;

import newgame.inventory.HeroInventory;
import newgame.gui.HudHandler;
import newgame.inventory.InventoryTypes;
import newgame.items.Chest;
import newgame.items.Item;
import newgame.items.Satchel;

/** Class to log inventory content on the HUD
 * @author Maxim Fründt
 */
public class InventoryHudLogVisitor implements InventoryLogVisitor
{
    private final HudHandler hud;

    /** Create a new HUD log visitor
     *
     * @param hud HUD on which the inventory content will be displayed
     */
    public InventoryHudLogVisitor(HudHandler hud)
    {
        this.hud = hud;
    }

    /** Log content of a chest inventory on the HUD
     *
     * @param chest Chest to be logged
     * @param <T> Item type of the chest
     */
    public <T extends Item> void log(Chest<T> chest)
    {
        hud.displayInventory(chest, InventoryTypes.CHEST_INVENTORY);
    }

    /** Log content of the player inventory on the HUD
     *
     * @param heroInventory Player inventory to be logged
     */
    public void log(HeroInventory heroInventory)
    {
        hud.displayInventory(heroInventory, InventoryTypes.PLAYER_INVENTORY);
    }

    /** Log content of a satchel inventory on the HUD
     *
     * @param satchel Satchel to be logged
     * @param <T> Item type of the satchel
     */
    public <T extends Item> void log(Satchel<T> satchel)
    {

    }
}
