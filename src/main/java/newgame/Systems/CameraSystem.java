package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.PlayerControl;
import newgame.Components.Position;
import newgame.Components.Velocity;
import newgame.characters.Camera;
import org.lwjgl.system.CallbackI;

public class CameraSystem extends EntitySystem
{
    private ImmutableArray<Entity> followableEntities;

    private final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);

    private Camera camera;

    public CameraSystem(Camera camera)
    {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        followableEntities = engine.getEntitiesFor(Family.all(PlayerControl.class, Position.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        if (followableEntities.size() >= 1)
        {
            Entity entity = followableEntities.get(0);

            Position position = positionMapper.get(entity);

            camera.setPosition(new Point(position.x, position.y));
        }
    }
}
