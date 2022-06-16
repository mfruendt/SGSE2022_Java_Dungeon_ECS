package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.Experience;
import newgame.Components.Health;
import newgame.Components.Inventory;
import newgame.Components.Tags.Player;
import newgame.EntityMapper;
import newgame.Factories.HeroFactory;
import newgame.gui.HudHandler;

/** System used to display stats ont he GUI
 * @author Maxim Fründt
 */
public class GuiSystem extends EntitySystem
{
    /** Entities that can be monitored on the GUI */
    private ImmutableArray<Entity> monitorableEntities;

    /** HUD used to display the GUI */
    private HudHandler hudHandler;

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        monitorableEntities = engine.getEntitiesFor(Family.all(Player.class).get());
        hudHandler = new HudHandler(HeroFactory.HERO_INVENTORY_SIZE);
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
    @Override
    public void update(float deltaTime)
    {
        if (monitorableEntities.size() >= 1)
        {
            Entity entity = monitorableEntities.get(0);

            Health health = EntityMapper.healthMapper.get(entity);
            Experience experience = EntityMapper.experienceMapper.get(entity);

            hudHandler.update(health, experience);
            hudHandler.updateInventory(EntityMapper.inventoryMapper.get(entity));
        }
    }
}
