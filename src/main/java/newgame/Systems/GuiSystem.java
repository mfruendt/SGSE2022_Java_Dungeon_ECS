package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import newgame.Components.Experience;
import newgame.Components.Health;
import newgame.Components.Inventory;
import newgame.Components.Tags.Player;
import newgame.gui.HudHandler;

public class GuiSystem extends EntitySystem
{
    private ImmutableArray<Entity> monitorableEntities;

    private final ComponentMapper<Health> healthMapper = ComponentMapper.getFor(Health.class);
    private final ComponentMapper<Experience> experienceMapper = ComponentMapper.getFor(Experience.class);
    private final ComponentMapper<Inventory> inventoryMapper = ComponentMapper.getFor(Inventory.class);

    private HudHandler hudHandler;

    @Override
    public void addedToEngine(Engine engine)
    {
        monitorableEntities = engine.getEntitiesFor(Family.all(Player.class).get());
        hudHandler = new HudHandler(0);
    }

    @Override
    public void update(float deltaTime)
    {
        if (monitorableEntities.size() >= 1)
        {
            Entity entity = monitorableEntities.get(0);

            Health health = healthMapper.get(entity);
            Experience experience = experienceMapper.get(entity);

            hudHandler.update(health, experience, null);
            hudHandler.updateInventory(inventoryMapper.get(entity));
        }
    }
}
