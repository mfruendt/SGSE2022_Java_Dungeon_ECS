package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Tags.Player;
import newgame.Components.Position;
import newgame.EntityMapper;
import newgame.characters.Camera;

public class CameraSystem extends EntitySystem
{
    private ImmutableArray<Entity> followableEntities;

    private Camera camera;

    public CameraSystem(Camera camera)
    {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        followableEntities = engine.getEntitiesFor(Family.all(Player.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        if (followableEntities.size() >= 1)
        {
            Entity entity = followableEntities.get(0);

            Position position = EntityMapper.positionMapper.get(entity);

            camera.setPosition(new Point(position.x, position.y));
        }
    }
}
