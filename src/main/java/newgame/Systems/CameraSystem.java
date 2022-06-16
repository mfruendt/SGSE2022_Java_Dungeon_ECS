package newgame.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import newgame.Components.Tags.Player;
import newgame.Components.Position;
import newgame.EntityMapper;
import newgame.characters.Camera;

/** System used to monitor player characters
 * @author Maxim Fründt
 */
public class CameraSystem extends EntitySystem
{
    /** Entities that can be followed with a camera */
    private ImmutableArray<Entity> followableEntities;

    /** Camera that is used */
    private final Camera camera;

    /** Create new camera system
     *
     * @param camera Camera that is used
     */
    public CameraSystem(Camera camera)
    {
        this.camera = camera;
    }

    /** Callback that will be invoked when this system is added to an engine
     *
     * @param engine The {@link Engine} this system was added to.
     */
    @Override
    public void addedToEngine(Engine engine)
    {
        followableEntities = engine.getEntitiesFor(Family.all(Player.class, Position.class).get());
    }

    /** Update the system
     *
     * @param deltaTime The time passed since last frame in seconds.
     */
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
