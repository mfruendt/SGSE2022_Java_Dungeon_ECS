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

public class GuiSystem extends EntitySystem
{
    private ImmutableArray<Entity> monitorableEntities;

    private HudHandler hudHandler;

    @Override
    public void addedToEngine(Engine engine)
    {
        monitorableEntities = engine.getEntitiesFor(Family.all(Player.class).get());
        hudHandler = new HudHandler(HeroFactory.HERO_INVENTORY_SIZE);
    }

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
