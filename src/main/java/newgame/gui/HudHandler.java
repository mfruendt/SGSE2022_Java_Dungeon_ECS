package newgame.gui;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.HUD;
import newgame.Components.Experience;
import newgame.Components.Health;

/** Handler for the game HUD
 * @author Maxim Fründt
 */
public class HudHandler
{
    private final HUD hud;

    private final PlayerStatsHud statsHudElement;

    private final InventoryHud invHudElement;

    /** Create new HUD handler
     *
     */
    public HudHandler(int playerInventorySize)
    {
        hud = new HUD();

        statsHudElement = new PlayerStatsHud(hud.getHudBatch());
        invHudElement = new InventoryHud(hud.getHudBatch(), playerInventorySize);
    }

    /** Update the HUD
     *
     */
    public void update(Health health, Experience experience)
    {
        hud.draw();

        if (statsHudElement != null)
        {
            statsHudElement.draw(health, experience);

            if (invHudElement != null)
            {
                invHudElement.draw();
            }
        }
    }

    public void updateInventory(newgame.Components.Inventory inventory)
    {
        if (inventory != null)
            invHudElement.setInventoryContent(inventory);
    }
}
