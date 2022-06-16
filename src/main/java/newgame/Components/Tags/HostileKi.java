package newgame.Components.Tags;

import com.badlogic.ashley.core.Component;
import newgame.Components.Position;

/** Component for hard monsters
 * @author Maxim Fründt
 */
public class HostileKi implements Component
{
    /** Speed of the monster */
    public float speed;
    /** Target of the monster */
    public Position target;

    /** Create new hard monster component
     *
     * @param speed Speed of the monster
     * @param target Target of the monster
     */
    public HostileKi(float speed, Position target)
    {
        this.speed = speed;
        this.target = target;
    }
}
